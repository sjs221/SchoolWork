package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.HashTable;

import java.util.HashMap;

public class HashTableImpl<Key,Value> implements HashTable<Key,Value>{

	private class Entry<Key, Value>{
		Key key;
		Value value;
		Entry<Key, Value> next = null;
		public Entry (Key k, Value v){
			if (k == null){
				throw new IllegalArgumentException();
			}
			key = k;
			value = v;
		}
		private Key getKey (){
			return key;
		}
		private Value getValue (){
			return value;
		}
		private void setNext(Entry<Key, Value> e){
			next = e;
		}
		private Entry<Key, Value> getNext(){
			return this.next;
		}
	}

	private Entry<Key, Value>[] table;
	private int size = 0;

	public HashTableImpl(){
		this.table = new Entry[5];
	}

//	public HashTableImpl(int i) {
//		this.table = new Entry[i];
//	}

	private int hashFunction(Key key){
		return (key.hashCode() & 0x7fffffff) % this.table.length;
	}

	/**
	 * @param k the key whose value should be returned
	 * @return the value that is stored in the HashTable for k, or null if there is no such key in the table
	 */
	@Override
	public Value get(Key k){
		int index = this.hashFunction(k);
		Entry<Key, Value> current = this.table[index];
		if(current != null){
			while (current != null){
				if(current.getKey().equals(k)) {
					return current.getValue();
				}
				current = current.getNext();
			}
		}
		return null;
	}

	/**
	 * @param k the key at which to store the value
	 * @param v the value to store.
	 * To delete an entry, put a null value.
	 * @return if the key was already present in the HashTable, return the previous value stored for the key.
	 * If the key was not already present, return null.
	 */
	@Override
	public Value put(Key k, Value v){
		int index = this.hashFunction(k);
		Value preExistingValue = get(k);
		Entry<Key, Value> putEntry = new Entry<Key, Value>(k, v);
		Entry<Key, Value> head = this.table[index];//change to "current"
		if(v == null){//Delete Entry
			if (this.table[index] != null) {// check front of list
				if (this.table[index].getKey().equals(k)) {
					this.table[index] = this.table[index].next;
				} else {// check rest of list
					while (head.next != null && !head.next.getKey().equals(k)) {
						head = head.next;
					}
					if (head.next != null && head.next.getKey().equals(k)) {// if the element is found, remove it
						head.next = head.next.next;
					}
				}
			}
			size--;
			return preExistingValue;
		}
		if(this.table.length <= this.size/4){//determine <= or < -- check load factor
			rehash();
		}
		if(head != null){//Set Entry
			putEntry.setNext(head);
			this.table[index] = putEntry;//may not need
		}
		this.table[index] = putEntry;
		size++;
		if(preExistingValue != null) {
			return preExistingValue;
		}
		return null;
	}

	private void rehash() {
		Entry<Key, Value>[] holderTable = this.table;
		this.table = new Entry[2 * this.table.length];
		for (Entry<Key,Value> current : holderTable){//old loop - (int i = 0; i < this.table.length; i++)
			while(current != null ){
				Entry next = current.next;
				current.next = null;
				this.put(current.getKey(), current.getValue());
				current = next;
			}
		}
	}
}