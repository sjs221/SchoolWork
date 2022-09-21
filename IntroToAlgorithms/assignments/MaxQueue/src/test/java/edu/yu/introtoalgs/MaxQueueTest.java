package edu.yu.introtoalgs;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MaxQueueTest {

    @Test
    public void basicTest(){
        MaxQueue mQ = new MaxQueue();
        for (int i = 0; i < 10; i++){
            mQ.enqueue(i);
        }

        assertEquals(10,mQ.size());
        assertEquals(9, mQ.max());
        assertEquals(0, mQ.dequeue());
        assertEquals(1, mQ.dequeue());
        assertEquals(2, mQ.dequeue());
        assertEquals(3, mQ.dequeue());
        assertEquals(4, mQ.dequeue());
        assertEquals(5, mQ.dequeue());
    }

    @Test
    public void randomTest(){
        MaxQueue mQ = new MaxQueue();
        int dataCount = 0;
        int dataSize = 100;
        while (dataCount != dataSize){
            int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
            mQ.enqueue(randomInt);
            System.out.print(randomInt + ", ");
            dataCount++;
        }

        System.out.println();
        System.out.println("Size : " + mQ.size());
        System.out.println("Max : " + mQ.max());
    }

    @Test
    public void timingTestEnqueue(){
//        MaxQueue mQ = new MaxQueue();
        System.out.println("Testing MaxQueue.enqueue() (time recorded as nanoseconds):");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                int dataCount = 0;
                int dataSize = n;
                double start = System.nanoTime();
                while (dataCount != dataSize){
                    int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
                    mQ.enqueue(randomInt);
                    dataCount++;
                }
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }

    }

    @Test
    public void timingTestSize(){
//        MaxQueue mQ = new MaxQueue();
        System.out.println("Testing MaxQueue.size() (time recorded as nanoseconds):");
        for (int n = 80000; n <= 20480000; n *= 2){
            MaxQueue mQ = new MaxQueue();
            int dataCount = 0;
            int dataSize = n;
            while (dataCount != dataSize){
                int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
                mQ.enqueue(randomInt);
                dataCount++;
            }
            double start = System.nanoTime();
            mQ.size();
            double timeElapsed = System.nanoTime() - start;
            System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
        }

        System.out.println();
    }

    @Test
    public void timingTestMax(){
//        MaxQueue mQ = new MaxQueue();
        System.out.println("Testing MaxQueue.max() (time recorded as nanoseconds):");
        for (int n = 80000; n <= 20480000; n *= 2){
            MaxQueue mQ = new MaxQueue();
            int dataCount = 0;
            int dataSize = n;
            while (dataCount != dataSize){
                int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
                mQ.enqueue(randomInt);
                dataCount++;
            }
            double start = System.nanoTime();
            mQ.max();
            double timeElapsed = System.nanoTime() - start;
            System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
        }

        System.out.println();
    }

    @Test
    public void timingTestDequeue(){
//        MaxQueue mQ = new MaxQueue();
        System.out.println("Testing MaxQueue.dequeue() (time recorded as nanoseconds):");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                int dataCount = 0;
                int dataSize = n;
                while (dataCount != dataSize){
                    int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
                    mQ.enqueue(randomInt);
                    dataCount++;
                }
                double start = System.nanoTime();
                mQ.dequeue();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }
    }

    @Test
    public void realUseTest(){
        MaxQueue mQ = new MaxQueue();
        mQ.enqueue(55);
        mQ.enqueue(55);
        mQ.enqueue(55);
        mQ.enqueue(55);
        mQ.enqueue(55);

        assertEquals(55, mQ.max());

        for (int i = 0; i < 3; i++) {
            mQ.dequeue();
        }

        assertEquals(2,mQ.size());

        mQ.enqueue(75);
        mQ.enqueue(55);
        mQ.enqueue(100);
        mQ.enqueue(55);
        mQ.enqueue(40);

        assertEquals(7,mQ.size());

        assertEquals(100, mQ.max());

        for (int i = 0; i < 6; i++) {
            mQ.dequeue();
        }

        assertEquals(40, mQ.max());
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueExceptionTest() {
        MaxQueue mQ = new MaxQueue();
        mQ.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void maxExceptionTest() {
        MaxQueue mQ = new MaxQueue();
        mQ.max();
    }

    private static double calculateLogBase2(double n){
        return (Math.log(n)/Math.log(2));
    }

    @Test
    public void worstCaseEnqueueTest(){
        System.out.println("Testing Worst Case MaxQueue.enqueue() (time recorded as nanoseconds):");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                double start = System.nanoTime();
                for(int j = n; j > 0; j--){
                    mQ.enqueue(j);
                }
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }
    }

    @Test
    public void worstCaseDequeueTest(){
        System.out.println("Testing Worst Case MaxQueue.dequeue() (time recorded as nanoseconds):");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                for(int j = n; j > 0; j--){
                    mQ.enqueue(j);
                }
                double start = System.nanoTime();
                for(int j = n; j > 1; j--){
                    mQ.dequeue();
                }
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }
    }

    @Test
    public void bestCaseEnqueueTest(){
        System.out.println("Testing Best Case MaxQueue.enqueue() (time recorded as nanoseconds):");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                double start = System.nanoTime();
                for(int j = 0; j > n; j++){
                    mQ.enqueue(j);
                }
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }
    }

    @Test
    public void bestCaseDequeueTest(){
        System.out.println("Testing Best Case MaxQueue.dequeue() (time recorded as nanoseconds):");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                for(int j = 0; j > n; j++){
                    mQ.enqueue(j);
                }
                double start = System.nanoTime();
                for(int j = 0; j > n - 1; j++){
                    mQ.dequeue();
                }
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }
    }

    @Test
    public void oneMoreTest(){
        System.out.println("Testing Enqueue Buildup:");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                mQ.enqueue(n);
                int j = n/4;
                while (j > 0){
                    mQ.enqueue(j);
//                    System.out.print(j + ", ");
                    j--;
                }
                double start = System.nanoTime();
                mQ.enqueue(n/2);
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));

            }
            System.out.println();
        }
    }

    @Test
    public void oneMoreTest2(){
        System.out.println("Testing Enqueue Buildup:");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 300; n <= 2000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                mQ.enqueue(n);
                int j = n/4;
                while (j > 0){
                    mQ.enqueue(j);
//                    System.out.print(j + ", ");
                    j--;
                }
                System.out.println();
                double start = System.nanoTime();
                mQ.enqueue(n/2);
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));

            }
            System.out.println();
        }
    }

    @Test
    public void oneMoreTest3(){
        System.out.println("Testing oneMoreTest3 (time recorded as nanoseconds):");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                MaxQueue mQ = new MaxQueue();
                mQ.enqueue(n);
                int j = n/4;
                while (j > 0){
                    mQ.enqueue(j);
                    j--;
                }
                double start = System.nanoTime();
                mQ.dequeue();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void oneMoreTest4(){
        MaxQueue mQ = new MaxQueue();
        mQ.enqueue(15);
        mQ.dequeue();
        mQ.max();
    }
}