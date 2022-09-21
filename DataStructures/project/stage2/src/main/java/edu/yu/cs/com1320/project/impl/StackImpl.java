package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Stack;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class StackImpl<T> implements Stack<T> {
    private LinkedList<T> stack;

    public StackImpl() {
        this.stack = new LinkedList<>();
    }

    @Override
    public void push(T element) {
        this.stack.add(element);
    }

    @Override
    public T pop() {
        if (this.peek() == null){
            return null;
        }
        return this.stack.removeLast();
    }

    @Override
    public T peek() {
        if (this.size() == 0){
            return null;
        }
        return this.stack.getLast();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

}