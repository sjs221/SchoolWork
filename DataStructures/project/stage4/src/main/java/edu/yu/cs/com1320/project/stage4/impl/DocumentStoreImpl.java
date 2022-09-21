package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.*;
import edu.yu.cs.com1320.project.Stack;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import edu.yu.cs.com1320.project.stage4.Document;

import javax.print.Doc;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore{
	private HashTableImpl<URI, Document> table;
	private StackImpl<Undoable> commandStack;
	private TrieImpl<Document> trie;
	private MinHeap<Document> memHeap;
	private int docLimit;
	private int byteLimit;
	private int docCount;
	private int byteCount;

	public DocumentStoreImpl(){
		this.table = new HashTableImpl<>();
		this.commandStack = new StackImpl<>();
		this.trie = new TrieImpl<>();
		this.memHeap = new MinHeapImpl();
		this.docLimit = -1;
		this.byteLimit = -1;
		this.docCount = 0;
		this.byteCount = 0;
	}

	@Override
	public int putDocument(InputStream input, URI uri, DocumentStore.DocumentFormat format) throws IOException {
		if(uri == null){
			throw new IllegalArgumentException();
		}
		if (input == null){
			Document docToDel = getDocument(uri);
			if (deleteDocument(uri)){
				return docToDel.hashCode();
			}
		}
		byte[] inputBA = input.readAllBytes();
		Document doc = constructDoc(inputBA, uri, format);

		if (this.byteLimit != -1 || this.docLimit != -1){
			makeRoomInHeap(doc);
		}
		this.docCount++;
		this.byteCount += (doc.getDocumentTxt() != null ? doc.getDocumentTxt().getBytes().length : doc.getDocumentBinaryData().length);

		Document prevDoc = table.put(uri, doc);//maybe after heap work



		if(prevDoc != null){
			removeFromTrie(prevDoc);
			addToTrie(doc);
			//ovrwritelambda
			Function<URI, Boolean> undoReplacement = (URI uriToDelete) -> putBackPrevDoc(uriToDelete, prevDoc);
			this.commandStack.push(new GenericCommand<>(uri, undoReplacement));

			doc.setLastUseTime(System.nanoTime());
			memHeap.insert(doc);
			memHeap.reHeapify(doc);//maybe should be insert

			return prevDoc.hashCode();
		}
		//new lambda
		Function<URI, Boolean> undoNewPut = (URI uriToDelete) -> deleteDocumentLambda(uriToDelete);
		addToTrie(doc);//originally was after commandstack
		this.commandStack.push(new GenericCommand<>(uri, undoNewPut));

		doc.setLastUseTime(System.nanoTime());
		memHeap.insert(doc);
		memHeap.reHeapify(doc);//maybe should be insert

		return 0;//if there is no prevDoc
	}

	private void makeRoomInHeap(Document doc) {
		if (this.byteLimit != -1 && (doc.getDocumentTxt() != null ? doc.getDocumentTxt().getBytes().length : doc.getDocumentBinaryData().length) > this.byteLimit){
			throw new IllegalArgumentException();
		}
		if ((this.docCount + 1 > this.docLimit && this.docLimit != -1) || (this.byteCount + (doc.getDocumentTxt() != null ? doc.getDocumentTxt().getBytes().length : doc.getDocumentBinaryData().length) > this.byteLimit && this.byteLimit != -1)){
			while ((this.docCount + 1 > this.docLimit && this.docLimit != -1) || (this.byteCount + (doc.getDocumentTxt() != null ? doc.getDocumentTxt().getBytes().length : doc.getDocumentBinaryData().length) > this.byteLimit && this.byteLimit != -1)){
				eraseAllTracks(memHeap.remove());
			}
		}

	}

	private void eraseAllTracks(Document removedDoc) {
		removeFromTrie(removedDoc);
		removeFromStack(removedDoc);
		this.table.put(removedDoc.getKey(), null);
		this.docCount--;
		this.byteCount -= (removedDoc.getDocumentTxt() != null ? removedDoc.getDocumentTxt().getBytes().length : removedDoc.getDocumentBinaryData().length);
	}

	private void removeFromStack(Document removedDoc) {//check
		Stack<Undoable> auxStack= new StackImpl<>();
		boolean done = false;
		while (!done){
			if(this.commandStack.peek() instanceof CommandSet && ((CommandSet) this.commandStack.peek()).containsTarget(removedDoc.getKey())){
				Iterator<? extends GenericCommand<?>> iterator = ((CommandSet<?>) this.commandStack.peek()).iterator();//try this
				while(iterator.hasNext()){
					if(iterator.next().getTarget().equals(removedDoc.getKey())){
						iterator.remove();
					}
				}
				//((CommandSet) this.commandStack.peek()).undo(uri);
				if (((CommandSet) this.commandStack.peek()).size() == 0){
					this.commandStack.pop();
				}
//				undone = true;
			} else if (this.commandStack.peek() instanceof GenericCommand){
				if (((GenericCommand) this.commandStack.peek()).getTarget().equals(removedDoc.getKey())){
//					((GenericCommand) this.commandStack.peek()).undo();
					this.commandStack.pop();
//					undone = true;
				}
			}
			if(this.commandStack.size() != 0){
				auxStack.push(this.commandStack.pop());
			}else{
				done = true;
			}
		}
		while ((auxStack.size() != 0)){
			this.commandStack.push(auxStack.pop());
		}
	}

	private void removeFromTrie(Document prevDoc) {//check if allowed to be void - or just change to boolean
		for(String s : prevDoc.getWords()){
			this.trie.delete(s, prevDoc);
		}
	}

	private void addToTrie(Document doc) {//check if allowed to be void - or just change to boolean
		for (String s : doc.getWords()){
			this.trie.put(s, doc);
		}
	}

	@Override
	public Document getDocument(URI uri){
//		table.get(uri).setLastUseTime(System.nanoTime());//og
		Document returnDoc = table.get(uri);
		if(returnDoc == null){
			return null;
		}
		returnDoc.setLastUseTime(System.nanoTime());
//		memHeap.reHeapify(table.get(uri));//og
		memHeap.reHeapify(returnDoc);
//		return table.get(uri);
		return returnDoc;
	}

	@Override
	public boolean deleteDocument(URI uri){
		if (!this.contains(uri)){
			return false;
		}
		removeFromTrie(getDocument(uri));
		this.docCount--;
		this.byteCount -= ((getDocument(uri)).getDocumentTxt() != null ? getDocument(uri).getDocumentTxt().getBytes().length : getDocument(uri).getDocumentBinaryData().length);
		//actually remove the doc from hashtable
		Document prevDoc = table.put(uri, null);
		Function<URI, Boolean> undoDelete = (URI uriToRevive) -> putDocumentLambda(uriToRevive, prevDoc);
		this.commandStack.push(new GenericCommand<>(uri, undoDelete));//might be CommandSet
		return true;
	}

	@Override
	public void undo() throws IllegalStateException {
		if(this.commandStack.size() == 0) {
			throw new IllegalStateException();
		}
		if(this.commandStack.peek() instanceof CommandSet){
			((CommandSet) this.commandStack.peek()).undoAll();
		}else{
			this.commandStack.peek().undo();//is genericCMD
		}
		this.commandStack.pop();
	}

	@Override
	public void undo(URI uri) throws IllegalStateException {
		Stack<Undoable> auxStack= new StackImpl<>();
		boolean undone = false;
		while(!undone){
			Undoable cmd= this.commandStack.peek();


			if(cmd instanceof CommandSet){
				CommandSet cmdSet = (CommandSet) cmd;

				if(cmdSet.containsTarget(uri)){
					this.docCount--;
					//maybe bytes
				}

			    cmdSet.undo(uri);
                if (cmdSet.size() == 0){
                    this.commandStack.pop();
                }
                undone = true;
			}else if (this.commandStack.peek() instanceof GenericCommand){
				if (((GenericCommand) this.commandStack.peek()).getTarget().equals(uri)){
					((GenericCommand) this.commandStack.peek()).undo();
					this.commandStack.pop();
					undone = true;
				}
			}
            if(this.commandStack.size() != 0){
                auxStack.push(this.commandStack.pop());
            }else if(!undone && this.commandStack.size() == 0){
                while ((auxStack.size() != 0)){//don't like this as a fix to line 186
                    this.commandStack.push(auxStack.pop());
                }
                throw new IllegalStateException();
            }
		}
		while ((auxStack.size() != 0)){
			this.commandStack.push(auxStack.pop());
		}
	}

	@Override
	public List<Document> search(String keyword) {
		List<Document> returnList = this.trie.getAllSorted(keyword, (doc1, doc2) -> {
			if ((int) doc2.wordCount(keyword) < (int) doc1.wordCount(keyword)) {
				return -1;
			} else if ((int) doc1.wordCount(keyword) < (int) doc2.wordCount(keyword)) {
				return 1;
			}
			return 0;
		});
		long lastTimeUsed = System.nanoTime();
		for (Document d : returnList){
			d.setLastUseTime(lastTimeUsed);
			memHeap.reHeapify(d);
		}

		return returnList;
	}

	@Override
	public List<Document> searchByPrefix(String keywordPrefix) {
		List<Document> returnList = this.trie.getAllWithPrefixSorted(keywordPrefix, (doc1, doc2) -> {
			Set<String> doc1Words = doc1.getWords();
			int doc1WordsWithPre = 0;
			for(String s : doc1Words){
				if (s.startsWith(keywordPrefix.toUpperCase())){
					doc1WordsWithPre += doc1.wordCount(s);
				}
			}
			Set<String> doc2Words = doc2.getWords();
			int doc2WordsWithPre = 0;
			for(String s : doc2Words){
				if (s.startsWith(keywordPrefix.toUpperCase())){
					doc2WordsWithPre += doc2.wordCount(s);
				}
			}
			return doc2WordsWithPre - doc1WordsWithPre;
		});
		long lastTimeUsed = System.nanoTime();
		for (Document d : returnList){
			d.setLastUseTime(lastTimeUsed);
			memHeap.reHeapify(d);
		}
		return returnList;
	}

	@Override
	public Set<URI> deleteAll(String keyword) {
		Set<Document> docSet = this.trie.deleteAll(keyword);
		Set<URI> returnSet = new HashSet<>();
		for(Document d : docSet){
			returnSet.add(d.getKey());
		}
		CommandSet<GenericCommand> cmdSet = new CommandSet<>();
		Object[] uriArray = returnSet.toArray();
		for(int i = 0; i < uriArray.length; i++){
			deleteMultipleDocs((URI) uriArray[i], cmdSet);
		}
		this.commandStack.push(cmdSet);
		return returnSet;
	}

	private boolean deleteMultipleDocs(URI uri, CommandSet cmdSet) {
		if (!this.contains(uri)){
			return false;
		}
		removeFromTrie(getDocument(uri));
		//actually remove the doc from hashtable
		Document prevDoc = table.put(uri, null);
		Function<URI, Boolean> undoDelete = (URI uriToRevive) -> putDocumentLambda(uriToRevive, prevDoc);
		cmdSet.addCommand(new GenericCommand<>(uri, undoDelete));
		return true;
	}

	@Override
	public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
		Set<Document> docSet = this.trie.deleteAllWithPrefix(keywordPrefix);
		Set<URI> returnSet = new HashSet<>();
		for(Document d : docSet){
			returnSet.add(d.getKey());
		}
		CommandSet<GenericCommand> cmdSet = new CommandSet<>();
		Object[] uriArray = returnSet.toArray();
		for(int i = 0; i < uriArray.length; i++){
			deleteMultipleDocs((URI) uriArray[i], cmdSet);
		}
		this.commandStack.push(cmdSet);
		return returnSet;
	}

	@Override
	public void setMaxDocumentCount(int limit) {
		this.docLimit = limit;
		if(this.docCount > this.docLimit){
			cutDownHeap();
		}
	}

	@Override
	public void setMaxDocumentBytes(int limit) {
		this.byteLimit = limit;
		if(this.byteCount > this.byteLimit){
			cutDownHeap();
		}
	}

	private void cutDownHeap() {
		while ((this.docCount > this.docLimit && this.docLimit != -1) || (this.byteCount  > this.byteLimit && this.byteLimit != -1)){
			eraseAllTracks(memHeap.remove());
		}
	}

	private Document constructDoc(byte[] inputBA, URI uri, DocumentStore.DocumentFormat format) throws IllegalArgumentException{
		if (format != DocumentStoreImpl.DocumentFormat.TXT && format != DocumentStoreImpl.DocumentFormat.BINARY){
			throw new IllegalArgumentException();
		}
		Document doc;
		if (format == DocumentStoreImpl.DocumentFormat.TXT){
			String inputAS = new String(inputBA);
			doc = (Document) new DocumentImpl(uri, inputAS);//didn't have to cast before stage 4
		}else{ //format == BINARY
			doc = (Document) new DocumentImpl(uri, inputBA);//didn't have to cast before stage 4
		}
		return doc;
	}

	private boolean contains(URI uri){
		return table.get(uri) != null;
	}

	private boolean deleteDocumentLambda(URI uri){
		if (!this.contains(uri)){
			return false;
		}
		removeFromTrie(getDocument(uri));
		//actually remove the doc from hashtable
		table.put(uri, null);
		return true;
	}

	private Boolean putDocumentLambda(URI uriToRevive, Document docToRevive) {
		table.put(uriToRevive, docToRevive);
		addToTrie(getDocument(uriToRevive));

		if (this.byteLimit != -1 || this.docLimit != -1){
			makeRoomInHeap(getDocument(uriToRevive));
		}
		this.docCount++;
		this.byteCount += (getDocument(uriToRevive).getDocumentTxt() != null ? getDocument(uriToRevive).getDocumentTxt().getBytes().length : getDocument(uriToRevive).getDocumentBinaryData().length);

		getDocument(uriToRevive).setLastUseTime(System.nanoTime());//check
		memHeap.insert(getDocument(uriToRevive));
		memHeap.reHeapify(getDocument(uriToRevive));//maybe should be insert

		return true;
	}



	private boolean putBackPrevDoc(URI uriToDelete, Document prevDoc) {
		table.put(uriToDelete, prevDoc);
		addToTrie(getDocument(uriToDelete));

		if (this.byteLimit != -1 || this.docLimit != -1){
			makeRoomInHeap(getDocument(uriToDelete));
		}
		this.docCount++;
		this.byteCount += (getDocument(uriToDelete).getDocumentTxt() != null ? getDocument(uriToDelete).getDocumentTxt().getBytes().length : getDocument(uriToDelete).getDocumentBinaryData().length);

		getDocument(uriToDelete).setLastUseTime(System.nanoTime());//check
		memHeap.reHeapify(getDocument(uriToDelete));//maybe should be insert

		return true;
	}
}