package edu.yu.da;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class WaitNoMoreTest {

    @Test
    public void leffTest(){
        final int[] durations = {2, 4};
        final int[] weights = {1, 1};
        final WaitNoMoreI wnm = new WaitNoMore ();
        // should equal 2
        final int totalWaitingTime = wnm.minTotalWaitingTime (durations, weights);

        System.out.println(totalWaitingTime);
        System.out.println();
    }

    @Test
    public void barelyATest(){
        final int[] durations = {2, 4, 6, 10, 1, 1};
        final int[] weights = {1, 2, 3, 2, 2, 5};
        final WaitNoMoreI wnm = new WaitNoMore ();
        final int totalWaitingTime = wnm.minTotalWaitingTime (durations, weights);

        System.out.println(totalWaitingTime);
        System.out.println();
    }

    @Test
    public void cardTest(){
        final int[] durations = {2, 5, 4};
        final int[] weights = {1, 3, 2};
        final WaitNoMoreI wnm = new WaitNoMore ();
        final int totalWaitingTime = wnm.minTotalWaitingTime (durations, weights);

        System.out.println(totalWaitingTime);
        System.out.println();
    }

    @Test
    public void jakeTest(){
        final int[] durations = {2, 6};
        final int[] weights = {3, 7};
        final WaitNoMoreI wnm = new WaitNoMore ();
        final int totalWaitingTime = wnm.minTotalWaitingTime (durations, weights);

        System.out.println(totalWaitingTime);
        System.out.println();
    }

}