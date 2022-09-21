package edu.yu.cs.com1320.project.stage2.impl;

import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore;

import java.net.URI;
import java.util.Arrays;

public class DocumentImpl implements Document {
	private URI uri;
	private String txt;
	private byte[] binaryData;
	private final DocumentStore.DocumentFormat format;

	public DocumentImpl(URI uri, String txt) throws java.lang.IllegalArgumentException{
		if (uri == null || txt == null || uri.toString().length() == 0 || txt.length() == 0){
			throw new IllegalArgumentException();
		}
		this.uri = uri;
		this.txt = txt;
		this.format = DocumentStoreImpl.DocumentFormat.TXT; // check
	}

	public DocumentImpl(URI uri, byte[] binaryData) throws java.lang.IllegalArgumentException{
		if (uri == null || binaryData == null || uri.toString().length() == 0 || binaryData.length == 0){
			throw new IllegalArgumentException();
		}
		this.uri = uri;
		this.binaryData = binaryData;
		this.format = DocumentStoreImpl.DocumentFormat.BINARY; // check
	}

	@Override
	public String getDocumentTxt(){//if format != TXT null
		if (format != DocumentStoreImpl.DocumentFormat.TXT){
			return null;
		}
		return this.txt;
	}

	@Override
	public byte[] getDocumentBinaryData(){//if format != BINARY null
		if (format != DocumentStoreImpl.DocumentFormat.BINARY){
			return null;
		}
		return this.binaryData;
	}

	@Override
	public URI getKey(){
		return this.uri;
	}

	@Override
    public boolean equals(Object o) {
		return (this.hashCode() == o.hashCode() && o instanceof DocumentImpl);//or maybe check to see that txt or binaryData is the same
	}

	//check
	@Override
	public int hashCode() {
		int result = this.uri.hashCode();
		result = 31 * result + (this.txt != null ? this.txt.hashCode(): 0);
		result = 31 * result + Arrays.hashCode(this.binaryData);
		return result;
	}
}