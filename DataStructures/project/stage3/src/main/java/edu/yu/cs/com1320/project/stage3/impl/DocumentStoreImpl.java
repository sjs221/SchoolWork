package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Stack;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage3.DocumentStore;
import edu.yu.cs.com1320.project.stage3.Document;

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

	public DocumentStoreImpl(){
		this.table = new HashTableImpl<>();
		this.commandStack = new StackImpl<>();
		this.trie = new TrieImpl<>();
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
		Document prevDoc = table.put(uri, doc);
		if(prevDoc != null){
			removeFromTrie(prevDoc);
			addToTrie(doc);

			//ovrwritelambda
			Function<URI, Boolean> undoReplacement = (URI uriToDelete) -> putBackPrevDoc(uriToDelete, prevDoc);
			this.commandStack.push(new GenericCommand<>(uri, undoReplacement));
			return prevDoc.hashCode();
		}
		//new lambda
		Function<URI, Boolean> undoNewPut = (URI uriToDelete) -> deleteDocumentLambda(uriToDelete);
		this.commandStack.push(new GenericCommand<>(uri, undoNewPut));
		addToTrie(doc);
		return 0;//if there is no prevDoc
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
		return table.get(uri);
	}

	@Override
	public boolean deleteDocument(URI uri){
		if (!this.contains(uri)){
			return false;
		}
		removeFromTrie(getDocument(uri));
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
			if(this.commandStack.peek() instanceof CommandSet && ((CommandSet) this.commandStack.peek()).containsTarget(uri)){
			    ((CommandSet) this.commandStack.peek()).undo(uri);
                if (((CommandSet) this.commandStack.peek()).size() == 0){
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
		return this.trie.getAllSorted(keyword, (doc1, doc2) -> {
			if ((int) doc2.wordCount(keyword) < (int) doc1.wordCount(keyword)) {
				return -1;
			} else if ((int) doc1.wordCount(keyword) < (int) doc2.wordCount(keyword)) {
				return 1;
			}
			return 0;});
	}

	@Override
	public List<Document> searchByPrefix(String keywordPrefix) {
		return this.trie.getAllWithPrefixSorted(keywordPrefix, (doc1, doc2) -> {
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

	private Document constructDoc(byte[] inputBA, URI uri, DocumentStore.DocumentFormat format) throws IllegalArgumentException{
		if (format != DocumentStoreImpl.DocumentFormat.TXT && format != DocumentStoreImpl.DocumentFormat.BINARY){
			throw new IllegalArgumentException();
		}
		Document doc;
		if (format == DocumentStoreImpl.DocumentFormat.TXT){
			String inputAS = new String(inputBA);
			doc = new DocumentImpl(uri, inputAS);
		}else{ //format == BINARY
			doc = new DocumentImpl(uri, inputBA);
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
		return true;
	}

	private boolean putBackPrevDoc(URI uriToDelete, Document prevDoc) {
		table.put(uriToDelete, prevDoc);
		return true;
	}
}