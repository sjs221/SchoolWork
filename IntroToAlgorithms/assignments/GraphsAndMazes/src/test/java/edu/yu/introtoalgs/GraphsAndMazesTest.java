package edu.yu.introtoalgs;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static edu.yu.introtoalgs.GraphsAndMazes.searchMaze;

public class GraphsAndMazesTest{

    @Test
    public void exampleTest(){
        final int[][] exampleMaze = {
                {0, 0, 0},
                {0, 1, 1},
                {0, 1, 0}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(2, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 2);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(2,0);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(1,0);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(0,0);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(0,1);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(0,2);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);

        Assert.assertEquals(l, path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInputTest(){
        final int[][] exampleMaze = new int[0][0];

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(2, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 2);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

    }

    @Test
    public void slightModFromExampleTest1(){
        final int[][] exampleMaze = {
                {1, 0, 0},
                {0, 0, 1},
                {0, 1, 0}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(2, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 2);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(2,0);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(1,0);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(1,1);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(0,1);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(0,2);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);

        Assert.assertEquals(l, path);
    }

    @Test
    public void slightModFromExampleTest2(){
        final int[][] exampleMaze = {
                {1, 0, 0},
                {0, 1, 1},
                {0, 1, 0}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(2, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 2);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();

        Assert.assertEquals(l, path);
    }

    @Test
    public void slightModFromExampleTest3(){
        final int[][] exampleMaze = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(2, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 2);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(2,0);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(1,0);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(0,0);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(0,1);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(0,2);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);

        Assert.assertEquals(l, path);
    }

    @Test
    public void slightModFromExampleTest4(){
        final int[][] exampleMaze = {
                {0, 0, 0},
                {0, 0, 1},
                {0, 1, 0}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(2, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 2);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(2,0);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(1,0);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(0,0);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(0,1);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(0,2);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);

        Assert.assertEquals(l, path);
    }

    @Test
    public void slightModFromExampleTest5(){
        final int[][] exampleMaze = {
                {0, 0, 0, 0},
                {0, 1, 0, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(3, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 3);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(2,0);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(1,0);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(0,0);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(0,1);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(0,2);
        GraphsAndMazes.Coordinate c11 = new GraphsAndMazes.Coordinate(3,0);
        GraphsAndMazes.Coordinate c12 = new GraphsAndMazes.Coordinate(0,3);
        l.add(c11);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);
        l.add(c12);

        Assert.assertEquals(l, path);
    }

    @Test
    public void slightModFromExampleTest6(){
        final int[][] exampleMaze = {
                {1, 0, 0, 0},
                {0, 1, 0, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(3, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 3);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(2,0);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(2,1);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(2,2);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(1,2);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(0,2);
        GraphsAndMazes.Coordinate c11 = new GraphsAndMazes.Coordinate(3,0);
        GraphsAndMazes.Coordinate c12 = new GraphsAndMazes.Coordinate(0,3);
        l.add(c11);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);
        l.add(c12);

        Assert.assertEquals(l, path);
    }

    @Test
    public void slightModFromExampleTest7(){
        final int[][] exampleMaze = {
                {0, 0, 0, 0},
                {0, 1, 0, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(3, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(3, 0);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c11 = new GraphsAndMazes.Coordinate(3,0);
        l.add(c11);

        Assert.assertEquals(l, path);
    }

    @Test
    public void slightModFromExampleTest8(){
        final int[][] exampleMaze = {
                {0, 0, 0, 0},
                {0, 1, 0, 1},
                {0, 0, 0, 1},
                {0, 1, 0, 1},
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(2, 0);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(3, 0);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(2,0);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(3,0);
        l.add(c1);
        l.add(c2);

        Assert.assertEquals(l, path);
    }

    @Test
    public void test10by10a(){
        final int[][] exampleMaze = {
                {1,0,1,1,1,1,1,1,1,1},
                {1,0,1,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,1,1,0,1},
                {1,0,1,0,1,0,1,0,0,1},
                {1,0,1,0,0,0,1,1,1,1},
                {1,0,1,1,1,0,0,0,0,1},
                {1,0,0,0,1,1,1,1,0,1},
                {1,1,1,0,0,0,0,0,0,1},
                {1,0,0,0,1,0,0,1,0,1},
                {1,1,1,1,1,1,1,1,0,1}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(9, 8);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(8, 1);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(9,8);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(8,8);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(7,8);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(7,7);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(7,6);
        GraphsAndMazes.Coordinate c6 = new GraphsAndMazes.Coordinate(7,5);
        GraphsAndMazes.Coordinate c7 = new GraphsAndMazes.Coordinate(7,4);
        GraphsAndMazes.Coordinate c8 = new GraphsAndMazes.Coordinate(7,3);
        GraphsAndMazes.Coordinate c9 = new GraphsAndMazes.Coordinate(8,3);
        GraphsAndMazes.Coordinate c10 = new GraphsAndMazes.Coordinate(8,2);
        GraphsAndMazes.Coordinate c11 = new GraphsAndMazes.Coordinate(8,1);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);
        l.add(c6);
        l.add(c7);
        l.add(c8);
        l.add(c9);
        l.add(c10);
        l.add(c11);

        Assert.assertEquals(l, path);
    }

    @Test
    public void test10by10b(){
        final int[][] exampleMaze = {
                {1,0,1,1,1,1,1,1,1,1},
                {1,0,1,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,1,1,0,1},
                {1,0,1,0,1,0,1,0,0,1},
                {1,0,1,0,0,0,1,1,1,1},
                {1,0,1,1,1,0,0,0,0,1},
                {1,0,0,0,1,1,1,1,0,1},
                {1,1,1,0,0,0,0,0,0,1},
                {1,0,0,0,1,0,0,1,0,1},
                {1,1,1,1,1,1,1,1,0,1}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(9, 8);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(5, 8);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(9,8);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(8,8);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(7,8);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(6,8);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(5,8);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);

        Assert.assertEquals(l, path);
    }

    @Test
    public void test10by10c(){
        final int[][] exampleMaze = {
                {1,0,1,1,1,1,1,1,1,1},
                {1,0,1,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,1,1,0,1},
                {1,0,1,0,1,0,1,0,0,1},
                {1,0,1,0,0,0,1,1,1,1},
                {1,0,1,1,1,0,0,0,0,1},
                {1,0,0,0,1,1,1,1,0,1},
                {1,1,1,0,0,0,0,0,0,1},
                {1,0,0,0,1,0,0,1,0,1},
                {1,1,1,1,1,1,1,1,0,1}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(9, 8);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 1);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        List<GraphsAndMazes.Coordinate> l = new LinkedList<>();
        GraphsAndMazes.Coordinate c1 = new GraphsAndMazes.Coordinate(9,8);
        GraphsAndMazes.Coordinate c2 = new GraphsAndMazes.Coordinate(8,8);
        GraphsAndMazes.Coordinate c3 = new GraphsAndMazes.Coordinate(7,8);
        GraphsAndMazes.Coordinate c4 = new GraphsAndMazes.Coordinate(7,7);
        GraphsAndMazes.Coordinate c5 = new GraphsAndMazes.Coordinate(7,6);
        GraphsAndMazes.Coordinate c6 = new GraphsAndMazes.Coordinate(7,5);
        GraphsAndMazes.Coordinate c7 = new GraphsAndMazes.Coordinate(7,4);
        GraphsAndMazes.Coordinate c8 = new GraphsAndMazes.Coordinate(7,3);
        GraphsAndMazes.Coordinate c9 = new GraphsAndMazes.Coordinate(6,3);
        GraphsAndMazes.Coordinate c10 = new GraphsAndMazes.Coordinate(6,2);
        GraphsAndMazes.Coordinate c11 = new GraphsAndMazes.Coordinate(6,1);
        GraphsAndMazes.Coordinate c12 = new GraphsAndMazes.Coordinate(5,1);
        GraphsAndMazes.Coordinate c13 = new GraphsAndMazes.Coordinate(4,1);
        GraphsAndMazes.Coordinate c14 = new GraphsAndMazes.Coordinate(3,1);
        GraphsAndMazes.Coordinate c15 = new GraphsAndMazes.Coordinate(2,1);
        GraphsAndMazes.Coordinate c16 = new GraphsAndMazes.Coordinate(1,1);
        GraphsAndMazes.Coordinate c17 = new GraphsAndMazes.Coordinate(0,1);
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);
        l.add(c6);
        l.add(c7);
        l.add(c8);
        l.add(c9);
        l.add(c10);
        l.add(c11);
        l.add(c12);
        l.add(c13);
        l.add(c14);
        l.add(c15);
        l.add(c16);
        l.add(c17);

        Assert.assertEquals(l, path);
    }

    @Test
    public void test50by50(){
        final int[][] exampleMaze = {
                {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
                {1,0,1,1,0,1,0,0,1,0,1,1,0,0,1,1,1,0,1,1,1,1,0,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,0,0,1,1,1,1,0,1},
                {1,0,0,1,0,1,1,1,1,0,0,1,1,0,0,0,1,1,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,1},
                {1,0,1,1,0,1,0,1,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,1,1,1,0,1,1,0,0,1,1,1,1,1,1,0,1},
                {1,0,0,1,0,1,0,1,1,1,1,0,0,1,1,0,1,1,0,0,1,1,1,0,0,1,1,0,0,1,1,0,1,0,1,0,1,0,0,1,0,0,0,0,0,0,0,1,0,1},
                {1,0,1,1,0,0,0,0,0,0,1,1,0,0,1,0,0,1,0,1,1,0,1,1,0,0,1,0,1,1,0,0,1,0,0,0,1,0,0,1,0,1,1,1,0,0,1,1,0,1},
                {1,0,1,0,0,0,1,1,0,1,1,0,0,0,1,1,1,1,0,0,1,0,0,1,1,0,1,1,1,0,0,0,1,1,0,1,1,0,1,1,0,1,0,1,0,1,1,0,0,1},
                {1,0,1,1,1,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,0,0,0,0,1,0,1,1,1,0,0,1,0,0,1,0,0,1,0,1,1,1,0,0,0,1},
                {1,0,0,0,1,0,1,1,0,1,0,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,0,1,1,0,1,0,0,0,1,1,0,1,1,1,1,1,0,1,0,0,0,1,1,1},
                {1,0,1,1,1,0,0,1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
                {1,0,1,0,0,0,1,1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,1,0,1,1,0,0,0,1,0,1,0,1,1,1,0,0,1,1,1,0,1,1,0,0,1},
                {1,0,1,1,0,0,1,0,0,0,1,0,1,0,1,1,0,0,0,1,0,0,1,0,0,0,0,0,1,0,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1,1,0,0,0,1},
                {1,0,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0,0,1,1,1,0,1,1,0,0,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,0,1,0,1,0,0,0,0,1,0,1,1,0,1,1,1,1,0,1,0,0,1,1,1,1,0,0,0,1,1,1,1,0,1,1,1,0,1,1,1,0,0,1,1,1,0,0,1},
                {1,0,1,1,0,1,1,1,0,1,1,0,1,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,1,1,1,0,1,0,1,1,0,1,1,0,1},
                {1,0,1,0,0,1,0,1,0,1,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,0,1,0,0,1,0,1,1,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1},
                {1,0,1,0,0,0,0,1,0,1,0,1,1,1,1,0,1,0,1,1,1,0,0,1,0,0,1,1,1,1,1,1,0,1,0,0,0,0,0,1,1,1,0,1,1,0,0,1,0,1},
                {1,0,1,1,0,0,1,1,0,1,0,1,0,0,0,0,1,0,1,0,1,1,0,0,0,1,1,0,1,0,1,0,0,1,0,1,1,1,0,1,0,1,1,1,0,0,1,1,0,1},
                {1,0,0,1,0,1,1,0,0,1,0,1,0,1,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,1,0,1,1,0,1,0,1,0,0,0,0,0,0,0,1,1,0,0,1},
                {1,0,0,1,0,1,0,0,0,1,0,1,0,1,1,1,1,0,1,0,0,0,0,1,0,0,1,1,1,1,1,0,1,0,0,1,0,1,1,0,0,1,1,1,0,0,0,0,0,1},
                {1,0,1,1,0,1,1,0,1,1,0,0,0,1,0,1,0,0,1,1,0,1,1,1,1,0,1,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,0,1,1,1,1,0,0,1},
                {1,0,1,0,0,0,1,0,1,0,0,0,1,1,0,1,1,0,0,1,0,1,0,0,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
                {1,0,1,0,1,1,1,0,1,0,0,1,1,0,0,0,1,0,1,1,0,1,0,0,0,0,0,1,1,0,1,1,0,0,1,0,0,1,1,1,1,1,0,0,1,0,1,0,0,1},
                {1,0,1,0,0,1,0,0,1,1,0,0,0,0,1,0,1,0,1,0,0,0,0,1,1,1,0,0,1,0,1,0,0,0,0,0,1,1,0,0,0,1,1,0,1,1,1,1,0,1},
                {1,0,1,0,0,1,0,0,0,1,0,1,1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,1,0,0,1,0,0,1,0,1,0,0,0,0,1},
                {1,0,1,1,0,1,0,1,1,1,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
                {1,0,0,1,1,1,0,1,0,1,0,1,0,1,1,1,1,1,0,0,1,0,1,1,1,0,0,1,1,1,1,0,0,1,1,0,0,1,1,1,0,0,0,0,0,0,0,1,0,1},
                {1,0,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,1,1,1,1,0,0,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,0,1,1,1,1,1,1,1,0,1,0,1},
                {1,1,1,0,0,1,1,1,1,1,0,1,0,1,0,1,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,1,1,0,0,1,1,1,0,0,0,0,0,0,0,1,0,1,0,1},
                {1,0,1,1,0,1,0,0,0,1,0,1,0,0,0,1,0,0,1,1,1,1,1,1,0,1,0,1,1,0,0,1,0,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1},
                {1,0,0,1,0,0,0,0,1,1,0,1,1,1,0,1,1,0,1,0,1,0,0,1,1,1,0,0,1,0,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
                {1,0,1,1,1,0,1,1,1,0,0,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,0,0,0,1,1,1},
                {1,0,1,0,0,0,1,0,0,0,0,0,0,1,1,0,1,1,1,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1,0,1,0,1,1,1,1,0,1,0,1},
                {1,0,1,1,0,1,1,0,1,1,1,1,1,1,0,0,0,0,1,0,1,1,0,0,0,0,1,1,1,1,1,0,0,1,1,0,0,0,1,1,1,0,0,1,0,0,0,1,0,1},
                {1,0,0,1,0,1,0,0,1,0,0,0,0,0,0,1,1,0,1,1,1,0,0,1,1,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,1,0,1},
                {1,0,1,1,0,1,0,1,1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,1,0,0,1,0,1,0,0,1,1,0,0,0,0,1,1,1,0,1,0,0,1,1,0,1,0,1},
                {1,0,1,0,0,1,0,1,0,1,0,1,1,0,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,0,1,1,1,1,0,0,0,1,1,1,1,0,0,1,1,1,0,1},
                {1,0,1,0,1,1,0,1,0,1,1,1,0,0,0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,1,0,1,1,1,0,0,1,1,0,0,0,0,0,1},
                {1,0,0,0,1,0,0,0,0,0,1,0,0,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1,0,0,1,1,1,0,0,1,0,0,0,0,0,0,0,1,1,0,1},
                {1,1,1,0,1,0,1,1,1,0,1,1,1,1,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,1,1,1,0,1,0,0,0,1,1,0,1,0,1,1,1,0,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,0,0,0,1,0,0,1,0,1,1,1,1,1,0,0,1,1,1,0,0,0,0,1,0,1,1,0,0,0,1,0,1,1,1,0,1,1,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,1,0,0,0,0,1,1,0,1,0,1,0,1,1,1,1,0,1,1,1,0,1,1,0,0,1,0,1,1,1,1,1,0,1,0,0,1,1,1,0,1},
                {1,0,1,1,1,0,1,0,0,1,1,1,1,1,1,0,0,1,0,0,0,0,0,1,0,0,1,0,1,0,1,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1},
                {1,0,0,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,1,0,0,0,0,0,0,1,0,0,1,0,0,1,1,0,1,1,1,0,1,0,1,1,1,1,1},
                {1,0,1,1,0,0,0,1,1,1,1,0,0,0,1,1,1,1,1,1,0,1,0,0,0,1,1,1,1,0,1,0,1,1,1,0,0,0,0,1,0,1,1,1,0,0,0,0,0,1},
                {1,0,0,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,1,1,0,1,1,0,0,1,0,1,0,1,0,0,0,0,1,1,1,0,0,0,1,0,1,1,1,0,1},
                {1,0,0,0,1,1,1,1,1,0,1,0,1,1,1,1,0,1,1,1,0,0,1,1,1,0,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1},
                {1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(0, 1);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(49, 48);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        Assert.assertEquals(start, path.get(0));
        Assert.assertEquals(end, path.get(path.size()-1));
    }

    @Test
    public void cycleTest10by10(){
        final int[][] exampleMaze = {
                {1,0,1,1,1,1,1,1,1,1},
                {1,0,1,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,1,1,0,1},
                {1,0,1,0,0,0,0,0,0,1},
                {1,0,1,0,1,1,1,1,1,1},
                {1,0,1,0,0,0,0,0,0,1},
                {1,0,0,0,1,1,1,1,0,1},
                {1,1,1,0,0,0,0,0,0,1},
                {1,0,0,0,1,0,0,1,0,1},
                {1,1,1,1,1,1,1,1,0,1}
        };

        final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(9, 8);
        final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, 1);
        final List<GraphsAndMazes.Coordinate> path = searchMaze(exampleMaze, start, end);

        int i = 0;
    }

    @Test
    public void timingTest(){

        System.out.println("Testing GraphsAndMazes Time Complexity");
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Trial number " + i + ":");
            for (int n = 10; n <= 12000; n *= 2){
                int[][] a = new int[n][n];
                for(int j = 0; j < n; j ++){
                    for (int k = 0; k < n; k++){
                        if (j == 0 || k == 0){
                            a[j][k] = 0;
                        }else{
                            a[j][k] = 1;
                        }
                    }
                }

                final GraphsAndMazes.Coordinate start = new GraphsAndMazes.Coordinate(n - 1, 0);
                final GraphsAndMazes.Coordinate end = new GraphsAndMazes.Coordinate(0, n - 1);

                double startClock = System.nanoTime();
                final List<GraphsAndMazes.Coordinate> path = searchMaze(a, start, end);
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