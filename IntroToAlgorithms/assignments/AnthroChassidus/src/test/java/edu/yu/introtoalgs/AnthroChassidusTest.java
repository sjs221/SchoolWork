package edu.yu.introtoalgs;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class AnthroChassidusTest {

    @Test
    public void basicTest(){
        int[] a = {1, 1, 4};
        int[] b = {2, 3, 5};
        int n = 10;

        AnthroChassidus aC = new AnthroChassidus(n,a,b);

        Assert.assertEquals(3, aC.nShareSameChassidus(1));
        Assert.assertEquals(2, aC.nShareSameChassidus(5));
        Assert.assertEquals(7, aC.getLowerBoundOnChassidusTypes());
    }

    @Test
    public void leffPiazzaTest(){
        int[] a = {0};
        int[] b = {1};
        int n = 50;

        AnthroChassidus aC = new AnthroChassidus(n,a,b);

        Assert.assertEquals(2, aC.nShareSameChassidus(0));
        Assert.assertEquals(2, aC.nShareSameChassidus(1));
        Assert.assertEquals(49, aC.getLowerBoundOnChassidusTypes());
    }

    @Test
    public void tryingToFixnShareSameChassidus(){
        int[] a = {2, 44, 39, 33, 44, 32};
        int[] b = {1, 50, 22, 44, 32, 2};
        int n = 50;

        AnthroChassidus aC = new AnthroChassidus(n,a,b);

        Assert.assertEquals(6, aC.nShareSameChassidus(44));
        Assert.assertEquals(6, aC.nShareSameChassidus(32));
        Assert.assertEquals(44, aC.getLowerBoundOnChassidusTypes());
    }

//    private static double calculateLogBase2(double n){
//        return (Math.log(n)/Math.log(2));
//    }
//
//    @Test
//    public void timedConstructorTest() {
//
//        System.out.println("Testing Constructor Time Complexity");
//        System.out.println();
//        for (int i = 1; i <= 10; i++) {
//            System.out.println("Trial number " + i + ":");
//            for (int n = 80000; n <= 20480000; n *= 2){
//                int[] a = new int[n-n/4];
//                int[] b = new int[n-n/4];
//
//                int indexCount = 0;
//                int arraySize = n-n/4;
//                while (indexCount != arraySize){
//                    int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
//                    a[indexCount] = randomInt;
//                    randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
//                    b[indexCount] = randomInt;
//                    indexCount++;
//                }
//                double start = System.nanoTime();
//                AnthroChassidus aC = new AnthroChassidus(n,a,b);
//                double timeElapsed = System.nanoTime() - start;
//                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
//            }
//
//            System.out.println();
//        }
//    }
//
//    @Test
//    public void timedLowerBoundTest() {
//        System.out.println("Testing lowerBound Time Complexity");
//        System.out.println();
//        for (int i = 1; i <= 10; i++) {
//            System.out.println("Trial number " + i + ":");
//            for (int n = 80000; n <= 20480000; n *= 2){
//                int[] a = new int[n-n/4];
//                int[] b = new int[n-n/4];
//
//                int indexCount = 0;
//                int arraySize = n-n/4;
//                while (indexCount != arraySize){
//                    int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
//                    a[indexCount] = randomInt;
//                    randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
//                    b[indexCount] = randomInt;
//                    indexCount++;
//                }
//                AnthroChassidus aC = new AnthroChassidus(n,a,b);
//                double start = System.nanoTime();
//                int holder = aC.getLowerBoundOnChassidusTypes();
//                double timeElapsed = System.nanoTime() - start;
//                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
//            }
//
//            System.out.println();
//        }
//    }
//
//    @Test
//    public void timedSharedChassidusTest() {
//        System.out.println("Testing sharedChassidus Time Complexity");
//        System.out.println();
//        for (int i = 1; i <= 10; i++) {
//            System.out.println("Trial number " + i + ":");
//            for (int n = 80000; n <= 20480000; n *= 2){
//                int[] a = new int[n-n/4];
//                int[] b = new int[n-n/4];
//
//                int indexCount = 0;
//                int lastEntry = n - n/4;
//                int arraySize = lastEntry;
//                while (indexCount != arraySize){
//                    int randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
//                    a[indexCount] = randomInt;
//                    randomInt = (int) ((Math.random() * (1000 - 1)) + 1);
//                    b[indexCount] = randomInt;
//                    indexCount++;
//                }
//                a[lastEntry - 1] = 1;
//                b[lastEntry - 1 ] = 2;
//                AnthroChassidus aC = new AnthroChassidus(n,a,b);
//                double start = System.nanoTime();
//                int holder = aC.nShareSameChassidus(1);
//                double timeElapsed = System.nanoTime() - start;
//                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
//            }
//
//            System.out.println();
//        }
//    }

    @Test(expected = IllegalArgumentException.class)
    public void testingExceptions1(){
        int[] a = {2, 44, 0, 33, -3, 32};
        int[] b = {1, 50, 22, 44, 32, 2};
        int n = 50;

        AnthroChassidus aC = new AnthroChassidus(n,a,b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testingExceptions2(){
        int[] a = {2, 44, 0, 33, 3, 32};
        int[] b = {1, 51, 22, 44, 32, 2};
        int n = 50;

        AnthroChassidus aC = new AnthroChassidus(n,a,b);
    }

}