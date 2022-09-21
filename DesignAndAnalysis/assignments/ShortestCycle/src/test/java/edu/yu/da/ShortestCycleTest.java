package edu.yu.da;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class ShortestCycleTest {

    @Test
    public void barelyATestRegSP(){
        List<ShortestCycleBase.Edge> edges = new LinkedList<>();
        edges.add(new ShortestCycleBase.Edge(1,2,3));
        edges.add(new ShortestCycleBase.Edge(1,3,4));
        edges.add(new ShortestCycleBase.Edge(1,4,3));
        edges.add(new ShortestCycleBase.Edge(1,6,3));
        edges.add(new ShortestCycleBase.Edge(3,5,1));
        edges.add(new ShortestCycleBase.Edge(4,6,1));
        edges.add(new ShortestCycleBase.Edge(6,5,3));
        edges.add(new ShortestCycleBase.Edge(0,1,1));
        edges.add(new ShortestCycleBase.Edge(0,2,5));
        edges.add(new ShortestCycleBase.Edge(0,6,6));

        System.out.println(edges);

        ShortestCycle sc1 = new ShortestCycle(edges, new ShortestCycleBase.Edge(0,6,6));
        System.out.println(sc1.doIt());

        ShortestCycle sc2 = new ShortestCycle(edges, new ShortestCycleBase.Edge(1,6,3));
        System.out.println(sc2.doIt());

        ShortestCycle sc4 = new ShortestCycle(edges, new ShortestCycleBase.Edge(0,1,1));
        System.out.println(sc4.doIt());
    }

    @Test
    public void errorTest(){
        List<ShortestCycleBase.Edge> edges = new LinkedList<>();
        edges.add(new ShortestCycleBase.Edge(1,2,3));
        edges.add(new ShortestCycleBase.Edge(1,3,1));
        edges.add(new ShortestCycleBase.Edge(2,3,7));
        edges.add(new ShortestCycleBase.Edge(2,4,5));
        edges.add(new ShortestCycleBase.Edge(3,4,2));
        edges.add(new ShortestCycleBase.Edge(2,5,1));
        edges.add(new ShortestCycleBase.Edge(4,5,7));

        System.out.println(edges);

        ShortestCycle sc1 = new ShortestCycle(edges, new ShortestCycleBase.Edge(1,2,3));
        System.out.println(sc1.doIt());

    }
}