package edu.yu.introtoalgs;

/** Enhances the Queue enqueue() and dequeue() API with a O(1) max()
 * method and O(1) size().  The dequeue() method is O(1), the enqueue
 * is amortized O(1).  The implementation is O(n) in space.
 *
 * @author Avraham Leff
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MaxQueue {
    private LinkedList<Integer> list;
    private LinkedList<Integer> maxList;
    private int max;

    public MaxQueue() {
        list = new LinkedList<Integer>();
        maxList = new LinkedList<Integer>();
        max = 0;
    }

    /** Insert the element with FIFO semantics
     *
     * @param x the element to be inserted.
     */
    public void enqueue(int x) {
        list.add(x);
        if(x > max){
            maxList = new LinkedList<Integer>();
            maxList.add(x);
            max = x;
        }else if (maxList.size() != 0 && x > maxList.peekLast()){
            while (maxList.size() != 0 && x > maxList.peekLast()){
                maxList.removeLast();
            }
            maxList.add(x);
        }else if(maxList.size() != 0 && x <= maxList.peekLast()){
            maxList.add(x);
        }
    }

    /** Dequeue an element with FIFO semantics.
     *
     * @return the element that satisfies the FIFO semantics if the queue is not
     * empty.
     * @throws NoSuchElementException if the queue is empty
     */
    public int dequeue() {
        if(list.size() == 0){
            throw new NoSuchElementException();
        }

        int returnInt = list.remove();
        if (returnInt == max && maxList.size() > 1){
            maxList.remove();
            max = maxList.peekFirst();
        }else if(returnInt == max && maxList.size() == 1){
            maxList.remove();
            max = 0;
        }
        return returnInt;
    }

    /** Returns the number of elements in the queue
     *
     * @return number of elements in the queue
     */
    public int size() {
        return list.size();
    }


    /** Returns the element with the maximum value
     *
     * @return the element with the maximum value
     * @throws NoSuchElementException if the queue is empty
     */
    public int max() {
        if(list.size() == 0){
            throw new NoSuchElementException();
        }
        return max;
    }

} // MaxQueue
