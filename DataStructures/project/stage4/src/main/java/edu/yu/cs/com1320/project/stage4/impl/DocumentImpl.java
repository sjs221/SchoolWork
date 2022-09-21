package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.DocumentStore;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DocumentImpl implements Document  {
	private URI uri;
	private String txt;
	private byte[] binaryData;
	private final DocumentStore.DocumentFormat format;
	private HashMap<String, Integer> wordCountMap = new HashMap<>();
	private long lastUseTime;

	public DocumentImpl(URI uri, String txt) throws java.lang.IllegalArgumentException{
		if (uri == null || txt == null || uri.toString().length() == 0 || txt.length() == 0){
			throw new IllegalArgumentException();
		}
		this.uri = uri;
		this.txt = txt;
		this.format = DocumentStoreImpl.DocumentFormat.TXT;

		//double check
		String[] mapTxtKeys = this.txt.replaceAll("[^A-Za-z0-9\\s+]", "").trim().toUpperCase().split("\\s+");
		for (String s : mapTxtKeys) {
//			if (!s.equals("\\s+")){
//				wordCountMap.put(s, this.wordCountMap.getOrDefault(s,0) + 1);//check
//			}
			wordCountMap.put(s, this.wordCountMap.getOrDefault(s,0) + 1);//check

//			System.out.println("Putting word " + s + " with value " + this.wordCountMap.get(s));
		}
	}

	public DocumentImpl(URI uri, byte[] binaryData) throws java.lang.IllegalArgumentException{
		if (uri == null || binaryData == null || uri.toString().length() == 0 || binaryData.length == 0){
			throw new IllegalArgumentException();
		}
		this.uri = uri;
		this.binaryData = binaryData;
		this.format = DocumentStoreImpl.DocumentFormat.BINARY;
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
	public int wordCount(String word) {
		if (this.format == DocumentStoreImpl.DocumentFormat.BINARY){
			return 0;
		}
		return this.wordCountMap.getOrDefault(word.replaceAll("[^A-Za-z0-9\\s+]", "").trim().toUpperCase(), 0);
	}

	@Override
	public Set<String> getWords() {
		if (this.format == DocumentStoreImpl.DocumentFormat.BINARY){
			return new HashSet<String>();
		}
		return this.wordCountMap.keySet();
	}

	@Override
	public long getLastUseTime() {//check
		return this.lastUseTime;
	}

	@Override
	public void setLastUseTime(long timeInNanoseconds) {//check
		this.lastUseTime = timeInNanoseconds;
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

	@Override
	public int compareTo(Document o) {//look into this
		//return 0;//return this.getLastUseTime() - o.getLastUseTime();
		if(this.getLastUseTime() - o.getLastUseTime() == 0){
			return 0;
		}else if(this.getLastUseTime() - o.getLastUseTime() < 0){
			return -1;
		}
		return 1;
	}
}