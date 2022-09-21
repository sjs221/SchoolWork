package edu.yu.introtoalgs;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class CountStringsFJTest{

    @Test
    public void basicTest1(){
        String[] input = {"hi", "hello", "shalom", "salam", "hi", "hi", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello", "hello"};
        CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", 3);
        int result = CSFJ.doIt();
        Assert.assertEquals(3, result);
    }

    @Test
    public void basicTest2(){
        String[] input = new String[100];
        for (int i = 0; i < 100; i++) {
            if((i % 10) == 0){
                input[i] = "hi";
            }else{
                input[i] = Integer.toString(i);
            }
        }
        CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", 3);
        int result = CSFJ.doIt();
        Assert.assertEquals(10, result);
    }

    @Test
    public void sequentialTimingTestThreshold(){
        System.out.println("Testing Sequential Time Complexity:");
        System.out.println();
        for (int n = 80000; n <= 20480000; n *= 2) {
            String[] input = new String[n];
            for (int i = 0; i < n; i++) {
                if ((i % 10) == 0) {
                    input[i] = "hi";
                } else {
                    input[i] = Integer.toString(i);
                }
            }
            CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", n);
            double start = System.nanoTime();
            int result = CSFJ.doIt();
            double timeElapsed = System.nanoTime() - start;
            System.out.printf("%10d %10.1f\n", n, timeElapsed);
            Assert.assertEquals((n/10), result);
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void sequentialTimingTestLoop(){
        System.out.println("Testing Sequential Time Complexity:");
        System.out.println();
        for (int n = 80000; n <= 20480000; n *= 2) {
            String[] input = new String[n];
            for (int i = 0; i < n; i++) {
                if ((i % 10) == 0) {
                    input[i] = "hi";
                } else {
                    input[i] = Integer.toString(i);
                }
            }
            int result = 0;
            double start = System.nanoTime();
            for (int i = 0; i < n; i++){
                if(input[i].equals("hi")){
                    result++;
                }
            }
            double timeElapsed = System.nanoTime() - start;
            System.out.printf("%10d %10.1f\n", n, timeElapsed);
            Assert.assertEquals((n/10), result);
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestOnes(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 1; threshold < 10; threshold+= 1) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestTens(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 10; threshold < 100; threshold+= 10) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestHundreds(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 100; threshold < 1000; threshold+= 100) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestThousands(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 1000; threshold < 10000; threshold+= 1000) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestTenThousands(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 10000; threshold < 100000; threshold+= 10000) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestHundredThousands(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 100000; threshold < 1000000; threshold+= 100000) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestMillions(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 1000000; threshold < 10000000; threshold+= 1000000) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    @Test
    public void forkjoinTimingTestTenMillions(){
        System.out.println("Testing Fork/Join Time Complexity:");
        System.out.println();
        for (int threshold = 10000000; threshold < 100000000; threshold+= 10000000) {
            for (int n = 80000; n <= 20480000; n *= 2) {
                String[] input = new String[n];
                for (int i = 0; i < n; i++) {
                    if ((i % 10) == 0) {
                        input[i] = "hi";
                    } else {
                        input[i] = Integer.toString(i);
                    }
                }
                CountStringsFJ CSFJ = new CountStringsFJ(input, "hi", threshold);
                double start = System.nanoTime();
                int result = CSFJ.doIt();
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10d %10.1f\n", threshold, n, timeElapsed);
                Assert.assertEquals((n/10), result);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }




    private static double calculateLogBase2(double n){
        return (Math.log(n)/Math.log(2));
    }

}