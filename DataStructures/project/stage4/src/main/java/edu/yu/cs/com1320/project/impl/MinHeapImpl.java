package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;

import javax.lang.model.element.Element;
import java.util.HashMap;
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

    public static void main (String[] args){
        MinHeap<Integer> heap = new MinHeapImpl();
        heap.insert(33);
        heap.insert(44);
        heap.insert(22);
        heap.insert(555);
        System.out.println(heap.remove() + ": should be 22");
        System.out.println(heap.remove() + ": should be 33");

        heap.insert(77);
        heap.insert(66);
        heap.insert(11);
        heap.insert(99);
        heap.insert(999);
        System.out.println(heap.remove() + ": should be 11");
        System.out.println(heap.remove() + ": should be 44");
        System.out.println(heap.remove() + ": should be 66");
        System.out.println(heap.remove() + ": should be 77");
        System.out.println(heap.remove() + ": should be 99");
        System.out.println(heap.remove() + ": should be 555");


        //heap.reHeapify(555);

    }
}
