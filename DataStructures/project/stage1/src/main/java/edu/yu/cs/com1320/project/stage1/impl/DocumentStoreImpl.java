package edu.yu.cs.com1320.project.stage1.impl;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage1.DocumentStore;
import edu.yu.cs.com1320.project.stage1.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;//just for now

public class DocumentStoreImpl implements DocumentStore{
	HashTableImpl<URI, Document> table;

	public DocumentStoreImpl(){
		this.table = new HashTableImpl<>();
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
			return prevDoc.hashCode();
		}
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
		table.put(uri, null);//maybe able to just return this line
		return true;

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

}