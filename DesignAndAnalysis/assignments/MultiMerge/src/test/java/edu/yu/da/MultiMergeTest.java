package edu.yu.da;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class MultiMergeTest {

    @Test
    public void barelyATest(){

        int[][] arrays = {{2,5},{1,4}};
        MultiMerge mm = new MultiMerge();
        System.out.println(Arrays.toString(mm.merge(arrays)));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();

    }


    @Test
    public void barelyATest2(){

        int[][] arrays = {{2,5}, {3,6},{1,4}};
        MultiMerge mm = new MultiMerge();
        System.out.println(Arrays.toString(mm.merge(arrays)));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();
    }

    @Test
    public void barelyATest3(){

        int[][] arrays = {{2,5}, {3,6},{1,4},{8,9}};
        MultiMerge mm = new MultiMerge();
        System.out.println(Arrays.toString(mm.merge(arrays)));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();
    }

    @Test
    public void test1(){

        int[][] arrays = new int[2][10];

        Random randomGenerator = new Random();

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                arrays[i][j] = randomGenerator.nextInt(1000);
            }
            Arrays.sort(arrays[i]);
        }


        MultiMerge mm = new MultiMerge();

        int[] result = mm.merge(arrays);

        boolean good = true;

        for (int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i + 1]){
                good = false;
                break;
            }
        }

        Assert.assertTrue(good);

        System.out.println(Arrays.toString(result));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();
    }

    @Test
    public void test2(){

        int[][] arrays = new int[5][3];

        Random randomGenerator = new Random();

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                arrays[i][j] = randomGenerator.nextInt(1000);
            }
            Arrays.sort(arrays[i]);
        }


        MultiMerge mm = new MultiMerge();

        int[] result = mm.merge(arrays);

        boolean good = true;

        for (int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i + 1]){
                good = false;
                break;
            }
        }

        Assert.assertTrue(good);

        System.out.println(Arrays.toString(result));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();
    }

    @Test
    public void test5003(){

        int[][] arrays = new int[500][3];

        Random randomGenerator = new Random();

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                arrays[i][j] = randomGenerator.nextInt(1000);
            }
            Arrays.sort(arrays[i]);
            System.out.println(Arrays.toString(arrays[i]));
        }


        MultiMerge mm = new MultiMerge();

        int[] result = mm.merge(arrays);

        System.out.println(Arrays.toString(result));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();

        boolean good = true;

        for (int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i + 1]){
                good = false;
                break;
            }
        }

        Assert.assertTrue(good);
    }

    @Test
    public void test3500(){

        int[][] arrays = new int[3][500];

        Random randomGenerator = new Random();

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                arrays[i][j] = randomGenerator.nextInt(1000);
            }
            Arrays.sort(arrays[i]);
            System.out.println(Arrays.toString(arrays[i]));
        }


        MultiMerge mm = new MultiMerge();

        int[] result = mm.merge(arrays);

        System.out.println(Arrays.toString(result));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();

        boolean good = true;

        for (int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i + 1]){
                good = false;
                break;
            }
        }

        Assert.assertTrue(good);

    }

    @Test
    public void test100101(){

        int[][] arrays = new int[100][101];

        Random randomGenerator = new Random();

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                arrays[i][j] = randomGenerator.nextInt(1000);
            }
            Arrays.sort(arrays[i]);
            System.out.println(Arrays.toString(arrays[i]));
        }


        MultiMerge mm = new MultiMerge();

        int[] result = mm.merge(arrays);

        System.out.println(Arrays.toString(result));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();

        boolean good = true;

        for (int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i + 1]){
                good = false;
                break;
            }
        }

        Assert.assertTrue(good);
    }

    @Test
    public void test5051(){

        int[][] arrays = new int[50][51];

        Random randomGenerator = new Random();

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                arrays[i][j] = randomGenerator.nextInt(1000);
            }
            Arrays.sort(arrays[i]);
            System.out.println(Arrays.toString(arrays[i]));
        }


        MultiMerge mm = new MultiMerge();

        int[] result = mm.merge(arrays);

        System.out.println(Arrays.toString(result));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();

        boolean good = true;

        for (int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i + 1]){
                good = false;
                break;
            }
        }

        Assert.assertTrue(good);
    }


    @Test
    public void test2121(){

        int[][] arrays = new int[21][21];

        Random randomGenerator = new Random();

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                arrays[i][j] = randomGenerator.nextInt(1000000);
            }
            Arrays.sort(arrays[i]);
            System.out.println(Arrays.toString(arrays[i]));
        }


        MultiMerge mm = new MultiMerge();

        int[] result = mm.merge(arrays);

        System.out.println(Arrays.toString(result));
        System.out.println(mm.getNCombinedMerges());
        System.out.println();

        boolean good = true;

        for (int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i + 1]){
                good = false;
                break;
            }
        }

        Assert.assertTrue(good);
    }
}