package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Trie;

import java.util.*;

public class TrieImpl<Value> implements Trie<Value> {

    //alphabetsize will be used to create an
    private final int alphabetSize = 36;
    // root of trie
    private Node<Value> root;

    public class Node<Value> {
        //set of values stored in the node
        protected HashSet<Value> vals = new HashSet<>();
        //links to deeper nodes
        protected Node[] links = new Node[alphabetSize];
    }

    //Constructor - Makes node for root
    public TrieImpl() {
        this.root = new Node<>();
    }

    @Override
    public void put(String key, Value val) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        //make string all caps to be case insensitive
        String removeCase = key.toUpperCase();
        //put val so long as it is not null
        if (val != null){
            this.root = put(this.root, removeCase, val, 0);
        }
    }

    private Node put(Node x, String key, Value val, int d) {
        //create a new node
        if (x == null) {
            x = new Node();
        }
        //we've reached the last node in the key, set the value for the key and return the node
        if (d == key.length()) {
            if(x.vals == null){
                x.vals = new HashSet<Value>();
            }
            x.vals.add(val);
            return x;
        }
        //proceed to the next node in the chain of nodes that forms the desired key
        char c = key.charAt(d);
        int i = c >= 48 && c <= 57 ? c - 48 : c - 64;//convert c into some int i which will be either 0-9 or correspond to A-Z
        x.links[i] = this.put(x.links[i], key, val, d + 1);
        return x;
    }

    @Override
    public List<Value> getAllSorted(String key, Comparator<Value> comparator) {
        if (key == null || comparator == null){
            throw new IllegalArgumentException();
        }
        //make string all caps to be case insensitive
        String removeCase = key.toUpperCase();
        //get node and sort its values
        Node x = this.get(this.root, removeCase, 0);
        if(x != null && x.vals != null) {
            List<Value> returnList = new ArrayList<Value>(x.vals);
            returnList.sort(comparator);
            return returnList;
        }
        return new ArrayList<Value>();
    }

    private Node get(Node x, String key, int d) {
        //link was null - return null, indicating a miss
        if (x == null) {
            return null;
        }
        //we've reached the last node in the key, return the node
        if (d == key.length()) {
            return x;
        }
        //proceed to the next node in the chain of nodes that forms the desired key
        char c = key.charAt(d);
        int i = c >= 48 && c <= 57 ? c - 48 : c - 64;//convert c into some int i which will be either 0-9 or correspond to A-Z
        return this.get(x.links[i], key, d + 1);
    }

    @Override
    public List<Value> getAllWithPrefixSorted(String prefix, Comparator<Value> comparator) {
        if (prefix == null || comparator == null){
            throw new IllegalArgumentException();
        }
        if (prefix.equals("") || prefix.equals("\\s+")){
            return new ArrayList<>();
        }
        //make string all caps to be case insensitive
        String removeCase = prefix.toUpperCase();
        //make a set to hold all the values in the subtrie of prefix
        Set<Value> holderSet = new HashSet<>();
        //get and sort all values in the subtrie of prefix
        this.getAllWithPrefixSorted(this.root, removeCase, 0, holderSet);
        List<Value> returnList = new ArrayList<Value>(holderSet);
        returnList.sort(comparator);
        return returnList;
    }

    private Node getAllWithPrefixSorted(Node x, String prefix, int d, Set<Value> s){
        if (x == null){
            return null;
        }
        //we've reached the last node of the prefix
        if (d == prefix.length()){
            //collect values from the subtrie of prefix
            this.collect(x, new StringBuilder(prefix), s);
            return x;
        }
        //proceed to the next node in the chain of nodes that forms the desired prefix
        char c = prefix.charAt(d);
        int i = c >= 48 && c <= 57 ? c - 48 : c - 64;//convert c into some int i which will be either 0-9 or correspond to A-Z
        return this.getAllWithPrefixSorted(x.links[i], prefix, d + 1, s);
    }

    private void collect(Node x, StringBuilder prefix, Set<Value> s) {
        //add all values of the current node to s
        if (x.vals != null){
            s.addAll(x.vals);
        }
        //visit each of the active links of the current node to collect their subtrie's values
        for (char c = 0; c < alphabetSize; c++) {
            if(x.links[c]!=null){
                //add child's char to the string and collect its subtrie's values
                prefix.append(c);
                this.collect(x.links[c], prefix, s);
                //remove the child's char to prepare for next iteration
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }

    @Override
    public Set<Value> deleteAllWithPrefix(String prefix) {
        if (prefix == null){
            throw new IllegalArgumentException();
        }
        if (prefix.equals("") || prefix.equals("\\s+")){
            return new HashSet<>();
        }
        //make string all caps to be case insensitive
        String removeCase = prefix.toUpperCase();
        //make a set to hold all the values in the subtrie of prefix which will be deleted
        Set<Value> returnSet = new HashSet<>();
        //get all values in the subtrie of prefix to be returned once deleted
        this.getAllWithPrefixSorted(this.root, removeCase, 0, returnSet);
        //delete subtrie of prefix
        this.root = deleteFromPrefix(this.root, removeCase, 0);
        return returnSet;
    }

    private Node deleteFromPrefix(Node x, String prefix, int d) {
        if(x == null){
            return null;
        }
        if(d == prefix.length()){
            //we're at the last node of prefix, delete its subtrie by setting it to null - maybe need to do one node up?
            x = null;
        }else{
            //proceed to the next node in the chain of nodes that forms the desired prefix
            char c = prefix.charAt(d);
            int i = c >= 48 && c <= 57 ? c - 48 : c - 64;//convert c into some int i which will be either 0-9 or correspond to A-Z
            x.links[i] = deleteFromPrefix(x.links[i], prefix, d +1);
        }
        return x;//probaby wrong - still unsure
    }

    @Override
    public Set<Value> deleteAll(String key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        //make string all caps to be case insensitive
        String removeCase = key.toUpperCase();
        //make a set to hold all the values which were deleted
        Set<Value> returnSet = new HashSet<>();
        this.deleteAll(this.root, removeCase, 0, returnSet);
        return returnSet;
    }

    private Node deleteAll(Node x, String key, int d, Set<Value> s){
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            //we're at the node to del - add the val to s and set it to null
            s.addAll(x.vals);
            x.vals = null;
        } else {
            //continue down the trie to the target node
            char c = key.charAt(d);
            int i = c >= 48 && c <= 57 ? c - 48 : c - 64;
            x.links[i] =  this.deleteAll(x.links[i], key, d + 1, s);
        }
        //this node has a val – do nothing, return the node
        if (x.vals != null){
            return x;
        }
        //remove subtrie rooted at x if it is completely empty
        for (int c = 0; c < alphabetSize; c++) {
            if (x.links[c] != null) {
                return x;
            }
        }
        //empty - set this link to null in the parent
        return null;
    }

    @Override
    public Value delete(String key, Value val) {
        if (key == null || val == null){
            throw new IllegalArgumentException();
        }
        //make string all caps to be case insensitive
        String removeCase = key.trim().toUpperCase();//find out if trim here
        //make object array to return the deleted value - open to any better idea
        Object[] returnVal = new Object[1];
        delete(this.root, removeCase, val, 0, returnVal);
        return (Value) returnVal[0];
    }

    private Node<Value> delete(Node x, String key, Value val, int d, Object[] returnVal) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            //we're at the node to delete

            if(x.vals == null){
                System.out.println("null vals");
            }

            Object[] arrayOfVals = x.vals.toArray();
            for (int v = 0; v < arrayOfVals.length; v++){
                if (arrayOfVals[v].equals(val)){
                    returnVal[0] = arrayOfVals[v];
                    x.vals.remove(arrayOfVals[v]);
                }
            }
        } else {
            //continue down the trie to the target node
            char c = key.charAt(d);
            int i = c >= 48 && c <= 57 ? c - 48 : c - 64;
            x.links[i] =  this.delete(x.links[i], key, val, d + 1,returnVal);
        }
        //this node has a val – do nothing, return the node
        if (x.vals != null){

//            System.out.println("returning not null in delete");
            return x;
        }

        return null;
    }
}
