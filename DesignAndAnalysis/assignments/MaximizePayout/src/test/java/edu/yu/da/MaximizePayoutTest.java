package edu.yu.da;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class MaximizePayoutTest{

    @Test
    public void barelyATest(){
        List<Long> A = new ArrayList<>();
        A.add(1L);
        A.add(2L);
        A.add(3L);
        List<Long> B = new ArrayList<>();
        B.add(3L);
        B.add(2L);
        B.add(1L);

        MaximizePayout mp = new MaximizePayout();
        Long res = mp.max(A,B);

        System.out.println(res);
    }

}