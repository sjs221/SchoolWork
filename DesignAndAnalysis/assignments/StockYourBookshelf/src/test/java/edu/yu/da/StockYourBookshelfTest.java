package edu.yu.da;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class StockYourBookshelfTest {

    @Test
    public void leffTest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 10;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(3);
        tanakhTypes.add(4);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(3);
        musserTypes.add(4);
        categories.put("Musser", musserTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }

    @Test
    public void altLeffTest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 10;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(3);
        tanakhTypes.add(4);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(5);
        musserTypes.add(10);
        categories.put("Musser", musserTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }

    @Test
    public void barelyATest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 4;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(1);
        tanakhTypes.add(2);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(2);
        musserTypes.add(4);
        categories.put("Musser", musserTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }

    @Test
    public void altBarelyATest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 4;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(1);
        tanakhTypes.add(4);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(2);
        musserTypes.add(4);
        categories.put("Musser", musserTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }

    @Test
    public void realLifeTest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 50;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(5);
        tanakhTypes.add(10);
        tanakhTypes.add(15);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(7);
        musserTypes.add(8);
        categories.put("Musser", musserTypes);

        LinkedList<Integer> gemaraTypes = new LinkedList<>();
        gemaraTypes.add(40);
        gemaraTypes.add(35);
        categories.put("Gemara", gemaraTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }

    @Test
    public void threeClassTest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 12;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(1);
        tanakhTypes.add(3);
        tanakhTypes.add(5);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(7);
        musserTypes.add(8);
        categories.put("Musser", musserTypes);

        LinkedList<Integer> gemaraTypes = new LinkedList<>();
        gemaraTypes.add(4);
        gemaraTypes.add(5);
        categories.put("Gemara", gemaraTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }

    @Test
    public void realLifeTest2(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 500;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(5);
        tanakhTypes.add(10);
        tanakhTypes.add(15);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(7);
        musserTypes.add(8);
        categories.put("Musser", musserTypes);

        LinkedList<Integer> gemaraTypes = new LinkedList<>();
        gemaraTypes.add(40);
        gemaraTypes.add(35);
        categories.put("Gemara", gemaraTypes);

        LinkedList<Integer> rambamTypes = new LinkedList<>();
        rambamTypes.add(400);
        rambamTypes.add(800);
        categories.put("Rambam", rambamTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }

    @Test(expected = IllegalStateException.class)
    public void errorTest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 12;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(1);
        tanakhTypes.add(3);
        tanakhTypes.add(5);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(7);
        musserTypes.add(8);
        categories.put("Musser", musserTypes);

        LinkedList<Integer> gemaraTypes = new LinkedList<>();
        gemaraTypes.add(4);
        gemaraTypes.add(5);
        categories.put("Gemara", gemaraTypes);

        System.out.println(syb.solution());
    }

    //Does not work - should adjust for 0
    @Test
    public void zeroTest(){
        StockYourBookshelf syb = new StockYourBookshelf();
        int budget = 10;
        HashMap<String, List<Integer>> categories = new HashMap<>();

        LinkedList<Integer> tanakhTypes = new LinkedList<>();
        tanakhTypes.add(3);
        tanakhTypes.add(4);
        categories.put("Tanakh", tanakhTypes);

        LinkedList<Integer> musserTypes = new LinkedList<>();
        musserTypes.add(3);
        musserTypes.add(4);
        categories.put("Musser", musserTypes);

        LinkedList<Integer> breslovTypes = new LinkedList<>();
        breslovTypes.add(0);
        categories.put("Breslov", breslovTypes);

        System.out.println(syb.maxAmountThatCanBeSpent(budget,categories));
        System.out.println(syb.solution());
    }
}