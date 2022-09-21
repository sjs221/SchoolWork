package edu.yu.introtoalgs;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class MergeAnIntervalTest {

    @Test
    public void example1FromReqsDoc(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(1,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,2));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 4)));
    }

    @Test
    public void example2FromReqsDoc(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(3,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,2));
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 2)));
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(3, 4)));
    }

    @Test
    public void overlapTest(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(0,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(2,3));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 4)));
    }

    @Test
    public void onLeftLineExact(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(3,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,3));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 4)));
    }

    @Test
    public void onRightLineExact(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(0,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(4,8));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 8)));
    }

    @Test
    public void leftOverlap(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(2,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,3));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 4)));
    }

    @Test
    public void rightOverlap(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(2,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(3,8));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(2, 8)));
    }

    @Test
    public void entireOverlap(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(2,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,8));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 8)));
    }

    @Test
    public void alreadyPresent(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(0,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(1,3));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(0, 4)));
    }

    @Test
    public void negativesTest1(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(-10,-2));
        intervals.add(new MergeAnInterval.Interval(-3,4));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(-2,3));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(-10, 4)));
    }

    @Test
    public void negativesTest2(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        intervals.add(new MergeAnInterval.Interval(-10,-2));
        intervals.add(new MergeAnInterval.Interval(-1,4));
        intervals.add(new MergeAnInterval.Interval(6,10));

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(7,8));
        Assert.assertEquals(3, results.size());
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(-10, -2)));
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(-1, 4)));
        Assert.assertTrue(results.contains(new MergeAnInterval.Interval(6, 10)));
    }

    @Test
    public void loopTest100(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        for(int i = -50; i < 50; i += 2){
            intervals.add(new MergeAnInterval.Interval((i),(i + 1)));
        }

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,20));
        Assert.assertEquals(40, results.size());
    }

    @Test
    public void compareToTest(){
        Set<MergeAnInterval.Interval> intervals = new TreeSet<>();
        intervals.add(new MergeAnInterval.Interval(-5,-2));
        intervals.add(new MergeAnInterval.Interval(1,4));
        intervals.add(new MergeAnInterval.Interval(-17,-16));
        intervals.add(new MergeAnInterval.Interval(10,15));
        intervals.add(new MergeAnInterval.Interval(5,6));
        intervals.add(new MergeAnInterval.Interval(-100,-99));

        System.out.println(intervals);

        ArrayList<MergeAnInterval.Interval> sorted = new ArrayList<MergeAnInterval.Interval>(intervals);
        Collections.sort(sorted);

        System.out.println(sorted);

        Assert.assertTrue(true);
    }

    @Test
    public void loopTest10(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        for(int i = -10; i < 10; i += 2){
            intervals.add(new MergeAnInterval.Interval((i),(i + 1)));
        }

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,20));
        Assert.assertEquals(6, results.size());
    }

    @Test
    public void randomTest1(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        for(int i = -10; i < 10; i += 3){
            intervals.add(new MergeAnInterval.Interval((i),(i + 1)));
        }

        Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,20));
        Assert.assertEquals(4, results.size());
    }

    @Test
    public void randomTest2(){
        Set<MergeAnInterval.Interval> intervals = new HashSet<>();
        for(int i = -10; i < 10; i += 5){
            intervals.add(new MergeAnInterval.Interval((i),(i + 1)));
        }

        Set<MergeAnInterval.Interval> results1 = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval(0,20));
        Assert.assertEquals(3, results1.size());

        Set<MergeAnInterval.Interval> results2 = MergeAnInterval.merge(results1, new MergeAnInterval.Interval(21,22));
        Assert.assertEquals(4, results2.size());
    }

    @Test
    public void timedMergeIntervalTest() {
        System.out.println("Testing MergeAnInterval Time Complexity");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 80000; n <= 20480000; n *= 2){
                Set<MergeAnInterval.Interval> intervals = new HashSet<>();
                for(int j = 1; j <= n; j += 2){
                    intervals.add(new MergeAnInterval.Interval((j),(j + 1)));
                }

                double start = System.nanoTime();
                Set<MergeAnInterval.Interval> results = MergeAnInterval.merge(intervals, new MergeAnInterval.Interval((n-10),n));
                double timeElapsed = System.nanoTime() - start;
                System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
            }

            System.out.println();
        }
    }

    private static double calculateLogBase2(double n){
        return (Math.log(n)/Math.log(2));
    }

}