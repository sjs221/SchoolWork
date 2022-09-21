package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.*;
import edu.yu.cs.com1320.project.Stack;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import edu.yu.cs.com1320.project.stage5.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore{
    private BTreeImpl<URI, Document> bTree;//my have to be object that points to Document
	private StackImpl<Undoable> commandStack;
	private TrieImpl<URI> trie;//uri get docs here -- make URI
	private MinHeap<MemHeapObj> memHeap;
	private int docLimit;
	private int byteLimit;
	private int docCount;
	private int byteCount;

	//tracks disk - poorly named
	private Set<MemHeapObj> heapTracker;

	private class MemHeapObj implements Comparable<MemHeapObj> {
		private URI uri;

		public MemHeapObj(URI uri) {
			this.uri = uri;
		}

		@Override
		public int compareTo(MemHeapObj o) {
			if(DocumentStoreImpl.this.bTree.get(uri) != null && DocumentStoreImpl.this.bTree.get(o.uri) != null){
				if(DocumentStoreImpl.this.bTree.get(uri).getLastUseTime() - DocumentStoreImpl.this.bTree.get(o.uri).getLastUseTime() == 0){
					return 0;
				}else if(DocumentStoreImpl.this.bTree.get(uri).getLastUseTime() - DocumentStoreImpl.this.bTree.get(o.uri).getLastUseTime() < 0){
					return -1;
				}
				return 1;
			}else{ //try this
				if(DocumentStoreImpl.this.bTree.get(uri) != null){
					return 1;
				}else if(DocumentStoreImpl.this.bTree.get(o.uri) != null){
					return -1;
				}
			}
			return 0;
		}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MemHeapObj that = (MemHeapObj) o;
            return Objects.equals(uri, that.uri);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uri);
        }
    }

	//Zero Args Constructor
	public DocumentStoreImpl(){
        this(null);
	}

	//One Arg Constructor
	public DocumentStoreImpl(File baseDir){
		this.bTree = new BTreeImpl<>();
		this.commandStack = new StackImpl<>();
		this.trie = new TrieImpl<>();
		this.memHeap = new MinHeapImpl();
		this.docLimit = -1;
		this.byteLimit = -1;
		this.docCount = 0;
		this.byteCount = 0;
		this.bTree.setPersistenceManager(new DocumentPersistenceManager(baseDir));

		this.heapTracker = new HashSet<>();
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
		MemHeapObj mHO = new MemHeapObj(doc.getKey());

		if(this.contains(uri)){
			removeFromTrie(uri);
		}

        Document prevDoc = bTree.put(uri, doc);

		if (this.byteLimit != -1 || this.docLimit != -1){
			makeRoomInHeap(mHO);
		}
		this.docCount++;
		this.byteCount += (doc.getDocumentTxt() != null ? doc.getDocumentTxt().getBytes().length : doc.getDocumentBinaryData().length);

		if(prevDoc != null){
			removeFromTrie(prevDoc.getKey());
			addToTrie(doc.getKey());

			//ovrwritelambda
			Function<URI, Boolean> undoReplacement = (URI uriToDelete) -> putBackPrevDoc(uriToDelete, prevDoc);
			this.commandStack.push(new GenericCommand<>(uri, undoReplacement));

			doc.setLastUseTime(System.nanoTime());
			memHeap.insert(mHO);
			memHeap.reHeapify(mHO);//maybe should be insert

			return prevDoc.hashCode();
		}

		//new lambda
		Function<URI, Boolean> undoNewPut = (URI uriToDelete) -> deleteDocumentLambda(uriToDelete);
		addToTrie(doc.getKey());

		this.commandStack.push(new GenericCommand<>(uri, undoNewPut));

		doc.setLastUseTime(System.nanoTime());
		memHeap.insert(mHO);
		memHeap.reHeapify(mHO);//maybe should be insert

		return 0;//if there is no prevDoc
	}

	private void makeRoomInHeap(MemHeapObj mHO) {;
		this.heapTracker.remove(new MemHeapObj(mHO.uri));

		if (this.byteLimit != -1 && (this.bTree.get(mHO.uri).getDocumentTxt() != null ? this.bTree.get(mHO.uri).getDocumentTxt().getBytes().length : this.bTree.get(mHO.uri).getDocumentBinaryData().length) > this.byteLimit){
			throw new IllegalArgumentException();
		}
		if ((this.docCount + 1 > this.docLimit && this.docLimit != -1) || (this.byteCount + (this.bTree.get(mHO.uri).getDocumentTxt() != null ? this.bTree.get(mHO.uri).getDocumentTxt().getBytes().length : this.bTree.get(mHO.uri).getDocumentBinaryData().length) > this.byteLimit && this.byteLimit != -1)){
			while ((this.docCount + 1 > this.docLimit && this.docLimit != -1) || (this.byteCount + (this.bTree.get(mHO.uri).getDocumentTxt() != null ? this.bTree.get(mHO.uri).getDocumentTxt().getBytes().length : this.bTree.get(mHO.uri).getDocumentBinaryData().length) > this.byteLimit && this.byteLimit != -1)){
                moveToDisk(memHeap.remove().uri);
			}
		}

	}

    private void moveToDisk(URI removedDocURI) {//change to moveToDisk(doc);
        this.docCount--;
        this.byteCount -= (this.bTree.get(removedDocURI).getDocumentTxt() != null ? this.bTree.get(removedDocURI).getDocumentTxt().getBytes().length : this.bTree.get(removedDocURI).getDocumentBinaryData().length);
        this.heapTracker.add(new MemHeapObj(removedDocURI));
		try {
			this.bTree.moveToDisk(this.bTree.get(removedDocURI).getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeFromStack(Document removedDoc) {
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
				if (((CommandSet) this.commandStack.peek()).size() == 0){
					this.commandStack.pop();
				}
			} else if (this.commandStack.peek() instanceof GenericCommand){
				if (((GenericCommand) this.commandStack.peek()).getTarget().equals(removedDoc.getKey())){
					this.commandStack.pop();
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

	private void removeFromTrie(URI uri, String alreadyRemovedFrom) {//check if allowed to be void - or just change to boolean
		this.heapTracker.remove(new MemHeapObj(uri));

		alreadyRemovedFrom = alreadyRemovedFrom.toUpperCase();

		for(String s : this.bTree.get(uri).getWords()){
			if(!s.equals(alreadyRemovedFrom)){
				this.trie.delete(s, uri);
			}
		}
	}

	private void removeFromTrie(URI uri) {//check if allowed to be void - or just change to boolean
		this.heapTracker.remove(new MemHeapObj(uri));
		for(String s : this.bTree.get(uri).getWords()){
			this.trie.delete(s, uri);
		}
	}

	private void addToTrie(URI uri) {//check if allowed to be void - or just change to boolean
		this.heapTracker.remove(new MemHeapObj(uri));
		for (String s : this.bTree.get(uri).getWords()){
			this.trie.put(s, uri);
		}
	}

	@Override
	public Document getDocument(URI uri){
		boolean deserialized = false;
		if(this.heapTracker.remove(new MemHeapObj(uri))){
			deserialized = true;
		}
		Document returnDoc = this.bTree.get(uri);
		if(returnDoc == null){
			return null;
		}
		returnDoc.setLastUseTime(System.nanoTime());
		if(!deserialized && (this.docCount <= this.docLimit || this.byteLimit >= (this.bTree.get(uri).getDocumentTxt() != null ? this.bTree.get(uri).getDocumentTxt().getBytes().length : this.bTree.get(uri).getDocumentBinaryData().length) )){
			memHeap.insert(new MemHeapObj(this.bTree.get(uri).getKey()));
		}
		if(!deserialized){
			memHeap.reHeapify(new MemHeapObj(returnDoc.getKey()));
		}
		this.heapTracker.remove(new MemHeapObj(uri));
		return returnDoc;
	}

	@Override
	public boolean deleteDocument(URI uri){
		this.heapTracker.remove(new MemHeapObj(uri));
		if (!this.contains(uri)){
			return false;
		}
		removeFromTrie(uri);
		this.docCount--;
		this.byteCount -= ((getDocument(uri)).getDocumentTxt() != null ? getDocument(uri).getDocumentTxt().getBytes().length : getDocument(uri).getDocumentBinaryData().length);
		//actually remove the doc from bTree
		Document prevDoc = bTree.put(uri, null);
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
					undone = true;
				}

			    cmdSet.undo(uri);
                if (cmdSet.size() == 0){
                    this.commandStack.pop();
                }
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
                while ((auxStack.size() != 0)){//don't like this
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
		List<URI> uriList = this.trie.getAllSorted(keyword, (uri1, uri2) -> {

			this.heapTracker.remove(uri1);
			this.heapTracker.remove(uri2);

			if ((int) this.bTree.get(uri2).wordCount(keyword) < (int) this.bTree.get(uri1).wordCount(keyword)) {
				return -1;
			} else if ((int) this.bTree.get(uri1).wordCount(keyword) < (int) this.bTree.get(uri2).wordCount(keyword)) {
				return 1;
			}
			return 0;
		});
		long lastTimeUsed = System.nanoTime();
		List<Document> returnList = new ArrayList<Document>();
		for (URI uri : uriList){
			boolean deserialized = false;
			if(this.heapTracker.remove(new MemHeapObj(uri))){
				deserialized = true;
			}
			this.bTree.get(uri).setLastUseTime(lastTimeUsed);
			returnList.add(this.bTree.get(uri));
			if(deserialized){
				makeRoomInHeap(new MemHeapObj(this.bTree.get(uri).getKey()));
				this.docCount++;
				this.byteCount += (this.bTree.get(uri).getDocumentTxt() != null ? this.bTree.get(uri).getDocumentTxt().getBytes().length : this.bTree.get(uri).getDocumentBinaryData().length);
				memHeap.insert(new MemHeapObj(this.bTree.get(uri).getKey()));
			}
			if(!deserialized && (this.docCount <= this.docLimit || this.byteLimit >= (this.bTree.get(uri).getDocumentTxt() != null ? this.bTree.get(uri).getDocumentTxt().getBytes().length : this.bTree.get(uri).getDocumentBinaryData().length) )){
				memHeap.insert(new MemHeapObj(this.bTree.get(uri).getKey()));
			}
			if(!this.heapTracker.contains(new MemHeapObj(uri))){
				memHeap.reHeapify(new MemHeapObj(this.bTree.get(uri).getKey()));
			}
		}
		return returnList;
	}

	@Override
	public List<Document> searchByPrefix(String keywordPrefix) {
		List<URI> uriList = this.trie.getAllWithPrefixSorted(keywordPrefix, (uri1, uri2) -> {

			this.heapTracker.remove(uri1);
			this.heapTracker.remove(uri2);

			Set<String> doc1Words = this.bTree.get(uri1).getWords();
			int doc1WordsWithPre = 0;
			for(String s : doc1Words){
				if (s.startsWith(keywordPrefix.toUpperCase())){
					doc1WordsWithPre += this.bTree.get(uri1).wordCount(s);
				}
			}
			Set<String> doc2Words = this.bTree.get(uri2).getWords();
			int doc2WordsWithPre = 0;
			for(String s : doc2Words){
				if (s.startsWith(keywordPrefix.toUpperCase())){
					doc2WordsWithPre += this.bTree.get(uri2).wordCount(s);
				}
			}
			return doc2WordsWithPre - doc1WordsWithPre;
		});
		long lastTimeUsed = System.nanoTime();
		List<Document> returnList = new ArrayList<Document>();
		int counter = 0;
		for (URI uri : uriList){
			boolean deserialized = false;
			if(this.heapTracker.remove(new MemHeapObj(uri))){
				deserialized = true;
			}
			this.bTree.get(uri).setLastUseTime(lastTimeUsed);
			returnList.add(this.bTree.get(uri));
			if(deserialized){
				makeRoomInHeap(new MemHeapObj(this.bTree.get(uri).getKey()));
				this.docCount++;
				this.byteCount += (this.bTree.get(uri).getDocumentTxt() != null ? this.bTree.get(uri).getDocumentTxt().getBytes().length : this.bTree.get(uri).getDocumentBinaryData().length);
				memHeap.insert(new MemHeapObj(this.bTree.get(uri).getKey()));
			}
			if(!this.heapTracker.contains(new MemHeapObj(uri))){
				memHeap.reHeapify(new MemHeapObj(this.bTree.get(uri).getKey()));
			}
		}
		return returnList;
	}

	@Override
	public Set<URI> deleteAll(String keyword) {
		Set<URI> returnSet = this.trie.deleteAll(keyword);//try but prob wrong
		CommandSet<GenericCommand> cmdSet = new CommandSet<>();
		Object[] uriArray = returnSet.toArray();
		for(int i = 0; i < uriArray.length; i++){
//			deleteMultipleDocs((URI) uriArray[i], keyword, cmdSet);

			this.docCount--;
			this.byteCount -= (this.bTree.get((URI) uriArray[i]).getDocumentTxt() != null ? this.bTree.get((URI) uriArray[i]).getDocumentTxt().getBytes().length : this.bTree.get((URI) uriArray[i]).getDocumentBinaryData().length);

			deleteMultipleDocs((URI) uriArray[i], keyword, cmdSet);
		}
		this.commandStack.push(cmdSet);
		return returnSet;
	}

	private boolean deleteMultipleDocs(URI uri, String keyword, CommandSet cmdSet) {
		if (!this.contains(uri)){
			return false;
		}
		removeFromTrie(uri, keyword);
		//actually remove the doc from bTree
		Document prevDoc = bTree.put(uri, null);
		Function<URI, Boolean> undoDelete = (URI uriToRevive) -> putDocumentLambda(uriToRevive, prevDoc);
		cmdSet.addCommand(new GenericCommand<>(uri, undoDelete));
		return true;
	}

	@Override
	public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
		Set<URI> returnSet = this.trie.deleteAllWithPrefix(keywordPrefix);//try but prob wrong
		CommandSet<GenericCommand> cmdSet = new CommandSet<>();
		Object[] uriArray = returnSet.toArray();
		for(int i = 0; i < uriArray.length; i++){
			deleteMultipleDocs((URI) uriArray[i], keywordPrefix, cmdSet);
		}
		this.commandStack.push(cmdSet);
		return returnSet;
	}

	@Override
	public void setMaxDocumentCount(int limit) {
		if(limit < 0){
			throw new IllegalArgumentException();
		}
		this.docLimit = limit;
		if(this.docCount > this.docLimit){
			cutDownHeap();
		}
	}

	@Override
	public void setMaxDocumentBytes(int limit) {
		if(limit < 0){
			throw new IllegalArgumentException();
		}
		this.byteLimit = limit;
		if(this.byteCount > this.byteLimit){
			cutDownHeap();
		}
	}

	private void cutDownHeap() {
		while ((this.docCount > this.docLimit && this.docLimit != -1) || (this.byteCount  > this.byteLimit && this.byteLimit != -1)){
            moveToDisk(memHeap.remove().uri);
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
		this.heapTracker.remove(uri); //not sure
		return bTree.get(uri) != null;
	}

	private boolean deleteDocumentLambda(URI uri){
		if (!this.contains(uri)){
			return false;
		}
		removeFromTrie(uri);
		//actually remove the doc from hashbTree
		bTree.put(uri, null);
		return true;
	}

	private Boolean putDocumentLambda(URI uriToRevive, Document docToRevive) {
		bTree.put(uriToRevive, docToRevive);//idk if still need

		boolean deserialized = false;
		if(this.heapTracker.remove(new MemHeapObj(uriToRevive))){
			deserialized = true;
		}






//		addToTrie(getDocument(uriToRevive));
		addToTrie(uriToRevive);

		if (this.byteLimit != -1 || this.docLimit != -1){
			makeRoomInHeap(new MemHeapObj(getDocument(uriToRevive).getKey()));
		}
		this.docCount++;
		this.byteCount += (getDocument(uriToRevive).getDocumentTxt() != null ? getDocument(uriToRevive).getDocumentTxt().getBytes().length : getDocument(uriToRevive).getDocumentBinaryData().length);

		getDocument(uriToRevive).setLastUseTime(System.nanoTime());//check
		memHeap.insert(new MemHeapObj(getDocument(uriToRevive).getKey()));
		memHeap.reHeapify(new MemHeapObj(getDocument(uriToRevive).getKey()));//maybe should be insert
//
		return true;
	}



	private boolean putBackPrevDoc(URI uriToDelete, Document prevDoc) {
		bTree.put(uriToDelete, prevDoc);
//		addToTrie(getDocument(uriToDelete));
		addToTrie(uriToDelete);

		if (this.byteLimit != -1 || this.docLimit != -1){
			makeRoomInHeap(new MemHeapObj(getDocument(uriToDelete).getKey()));
		}
		this.docCount++;
		this.byteCount += (getDocument(uriToDelete).getDocumentTxt() != null ? getDocument(uriToDelete).getDocumentTxt().getBytes().length : getDocument(uriToDelete).getDocumentBinaryData().length);

		getDocument(uriToDelete).setLastUseTime(System.nanoTime());//check
		memHeap.reHeapify(new MemHeapObj(getDocument(uriToDelete).getKey()));//maybe should be insert

		return true;
	}
}