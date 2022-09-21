package edu.yu.introtoalgs;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class TransportRadioactivesTest {

    @Test
    public void barelyATest(){
        List<TransportationState> list = TransportRadioactives.transportIt(2,1);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void test22(){
        List<TransportationState> list = TransportRadioactives.transportIt(2,2);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void test23(){
        List<TransportationState> list = TransportRadioactives.transportIt(2,3);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void test44(){
        List<TransportationState> list = TransportRadioactives.transportIt(4,4);

        Assert.assertEquals(0, list.size());
    }

    @Test
    public void test54(){
        List<TransportationState> list = TransportRadioactives.transportIt(5,4);
        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void test104(){
        List<TransportationState> list = TransportRadioactives.transportIt(10,4);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void test2015(){
        List<TransportationState> list = TransportRadioactives.transportIt(20,15);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void test2021(){
        List<TransportationState> list = TransportRadioactives.transportIt(20,21);

        Assert.assertEquals(0, list.size());
    }

    @Test
    public void test1615(){
        List<TransportationState> list = TransportRadioactives.transportIt(16,15);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void test98(){
        List<TransportationState> list = TransportRadioactives.transportIt(9,8);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegInput(){
        List<TransportationState> list = TransportRadioactives.transportIt(-1,-3);
    }

    @Test
    public void test11(){
        List<TransportationState> list = TransportRadioactives.transportIt(1,1);

        boolean success = true;

        for (TransportationState ts : list){
            if ((ts.getMithiumSrc() < ts.getCathiumSrc() && ts.getMithiumSrc() != 0) || (ts.getCathiumDest() > ts.getMithiumDest() && ts.getMithiumDest() != 0)){
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void timingTest(){

        System.out.println("Testing TransportRadioactives Time Complexity");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 10; n <= 12000; n *= 2){
                int m = n;
                int c = n - 1;
                double startClock = System.nanoTime();
                List<TransportationState> list = TransportRadioactives.transportIt(m,c);
                double timeElapsed = System.nanoTime() - startClock;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }

    }

    private static double calculateLogBase2(double n){
        return (Math.log(n)/Math.log(2));
    }
}