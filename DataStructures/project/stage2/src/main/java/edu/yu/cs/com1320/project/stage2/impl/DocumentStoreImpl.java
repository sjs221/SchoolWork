package edu.yu.cs.com1320.project.stage2.impl;
import edu.yu.cs.com1320.project.Command;
import edu.yu.cs.com1320.project.Stack;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage2.DocumentStore;
import edu.yu.cs.com1320.project.stage2.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore{
	private HashTableImpl<URI, Document> table;
	private StackImpl<Command> commandStack;

	public DocumentStoreImpl(){
		this.table = new HashTableImpl<>();
		this.commandStack = new StackImpl<>();
	}

	/**
	 * @param input the document being put
	 * @param uri unique identifier for the document
	 * @param format indicates which type of document format is being passed
	 * @return if there is no previous doc at the given URI, return 0. If there is a previous doc,
	 * return the hashCode of the previous doc. If InputStream is null, this is a delete, and thus return
	 * either the hashCode of the deleted doc or 0 if there is no doc to delete.
	 */
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
			//ovrwritelambda
			Function<URI, Boolean> undoReplacement = (URI uriToDelete) -> putBackPrevDoc(uriToDelete, prevDoc);
			this.commandStack.push(new Command(uri, undoReplacement));
			return prevDoc.hashCode();
		}
		//new lambda
		Function<URI, Boolean> undoNewPut = (URI uriToDelete) -> deleteDocumentLambda(uriToDelete);
		this.commandStack.push(new Command(uri, undoNewPut));
		return 0;//if there is no prevDoc
	}

	@Override
	public Document getDocument(URI uri){
		return table.get(uri);// check
	}

	/**
	 * @param uri the unique identifier of the document to delete
	 * @return true if the document is deleted, false if no document exists with that URI
	 */
	@Override
	public boolean deleteDocument(URI uri){
		if (!this.contains(uri)){
			return false;
		}
		//actually remove the doc from hashtable
		Document prevDoc = table.put(uri, null);
		Function<URI, Boolean> undoDelete = (URI uriToRevive) -> putDocumentLambda(uriToRevive, prevDoc);
		this.commandStack.push(new Command(uri, undoDelete));
		return true;
	}

	@Override
	public void undo() throws IllegalStateException {
		if(this.commandStack.size() == 0) {
			throw new IllegalStateException();
		}
		undo(this.commandStack.peek().getUri());
		//or just pop()
	}

	@Override
	public void undo(URI uri) throws IllegalStateException {
		Stack<Command> auxStack= new StackImpl<>();
		Command current = this.commandStack.peek();
		while(!current.getUri().equals(uri) ){
			auxStack.push(this.commandStack.pop());
			if(this.commandStack.size() != 0){
				current = commandStack.peek();
			}else{
				throw new IllegalStateException();
			}
		}
		if(current.getUri().equals(uri)){
			commandStack.pop().undo();
		}
		while ((auxStack.size() != 0)){
			this.commandStack.push(auxStack.pop());
		}
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
		//actually remove the doc from hashtable
		table.put(uri, null);//maybe able to just return this line
		return true;
	}

	private Boolean putDocumentLambda(URI uriToRevive, Document docToRevive) {
		table.put(uriToRevive, docToRevive);
		return true; //double check
	}

	private boolean putBackPrevDoc(URI uriToDelete, Document prevDoc) {
		table.put(uriToDelete, prevDoc);
		return true;
	}

}