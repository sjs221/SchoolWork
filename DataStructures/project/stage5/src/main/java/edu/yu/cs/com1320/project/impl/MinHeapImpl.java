package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
//import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl; ---change

import javax.lang.model.element.Element;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MinHeapImpl extends MinHeap {
    private int size;
    private HashMap<Comparable, Integer> comparableIntegerHashMap= new HashMap<>();

    public MinHeapImpl() {
        this.size = 10;
        this.elements = new Comparable[this.size];//check
    }

    @Override
    public void reHeapify(Comparable element) {//check thoroughly
        /**
         * getArrayIndex
         * upHeap
         * downHeap
         * */
//        if (isEmpty() || !this.comparableIntegerHashMap.containsKey(element)){
//            throw new NoSuchElementException();
//        }
        int index = this.getArrayIndex(element);
        upHeap(index);
        downHeap(index);
    }

    @Override
    protected int getArrayIndex(Comparable element) {//check
        updateMap();
        if (isEmpty() || !this.comparableIntegerHashMap.containsKey(element)){
            throw new NoSuchElementException();
        }
        return this.comparableIntegerHashMap.get(element);
    }

    private void updateMap() {//check - nervous about going through data too many times
        this.comparableIntegerHashMap.clear();
        for (int i = 1; i < count+1; i++){
            this.comparableIntegerHashMap.put(this.elements[i], i);
        }
    }

    @Override
    protected void doubleArraySize() {//check - when array is full
        this.size *= 2;
        Comparable[] newArray = new Comparable[this.size];
        for(int i = 1; i < this.elements.length; i++){//start at 1 since 0 isnt used for algorithm
            newArray[i] = this.elements[i];
        }
        this.elements = newArray;
    }


}
