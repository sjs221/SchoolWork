package edu.yu.da;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static edu.yu.da.GeneticAlgorithmConfig.SelectionType.ROULETTE;
import static edu.yu.da.GeneticAlgorithmConfig.SelectionType.TOURNAMENT;

public class DataCompressionTest {

    @Test
    public void barelyATest(){
        List<String> input = new LinkedList<>();
        input.add("Sam");
        input.add("Jake");
        input.add("Arieh");
        input.add("Greenfield");

        DataCompression dc = new DataCompression(input);
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(5,20,1.07,TOURNAMENT,0.4,0.4);
        DataCompression.SolutionI sol = dc.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest1(){
        List<String> input = new LinkedList<>();
        input.add("a");
        input.add("b");
        input.add("c");
        input.add("d");
        input.add("e");
        input.add("f");
        input.add("g");
        input.add("h");
        input.add("i");
        input.add("j");
        input.add("k");
        input.add("l");
        input.add("m");
        input.add("n");
        input.add("o");
        input.add("p");
        input.add("q");
        input.add("r");
        input.add("s");
        input.add("t");
        input.add("u");
        input.add("v");
        input.add("w");
        input.add("x");
        input.add("y");
        input.add("z");


        DataCompression dc = new DataCompression(input);
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(10,100,1.07,TOURNAMENT,0.4,0.4);
        DataCompression.SolutionI sol = dc.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest3(){
        List<String> input = new LinkedList<>();
        input.add("Sam");
        input.add("Jake");
        input.add("Arieh");
        input.add("Sam");

        DataCompression dc = new DataCompression(input);
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(5,20,1.07,TOURNAMENT,0.4,0.4);
        DataCompression.SolutionI sol = dc.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest4(){
        List<String> strings = new LinkedList<>();
        strings.add("Abe");
        strings.add("Benjamin");
        strings.add("Lewis");
        strings.add("Lewis");
        strings.add("Louis");
        strings.add("Sidney");
        strings.add("Sebastian");
        strings.add("Colton");
        strings.add("Jamison");
        strings.add("Christian");
        strings.add("Jamison");
        strings.add("George");
        strings.add("John");
        strings.add("James");
        strings.add("Martin");
        strings.add("Taylor");
        strings.add("Benjamin");
        strings.add("Lewis");
        strings.add("Lewis");
        strings.add("Louis");
        strings.add("Sidney");
        strings.add("Sebastian");
        strings.add("Colton");
        strings.add("Jamison");
        strings.add("Christian");
        strings.add("Colton");
        strings.add("Evgeni");
        strings.add("Justina");
        strings.add("Jamison");
        strings.add("Max");

        DataCompression dc = new DataCompression(strings);
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(5,20,1.07,TOURNAMENT,0.4,0.4);
        DataCompression.SolutionI sol = dc.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest5(){
         String[] x = new String[] {"jwimlbf", "ycgqivui", "ddanpyl", "iss", "xpknp", "hopgowv", "nhiih", "xchs", "yrzyju", "uednlapv", "qwto", "qovrhrlq", "svh", "atmsocwt", "ytprkwin", "qkzx", "ugpha", "chuylyh", "stle", "itzlrpud", "cikzgmcf", "rcu", "ngozp", "lkgw", "ifl", "wrljqj", "rivtqw", "mgx", "dehq", "mzvryylvc", "ekgq", "xnwc", "iobvdrr", "jbwpfdd", "ywtixspo", "wpng", "ozna", "vzskpoo", "scwpn", "amgzskzs", "ycn", "fuafn", "euj", "ofvdr", "upj", "zkzh", "zbiwg", "barmn", "ofdzg", "qhtczyvo", "efxdqhf", "jcjbog", "yixixw", "zepzyxqu", "cuqig", "xnrvhmvga", "whclbvgnj", "sqf", "dfc", "kvlcncbc", "xlw", "xojovrzux", "ughtu", "nvxfa", "ulymubl", "tijkki", "htxl", "wtc", "tntllycv", "qqfb", "nruy", "guhe", "rxuszmw", "enhgcwtsv", "vxpmhz"};
         List<String> input = Arrays.asList(x);

        DataCompression dc = new DataCompression(input);
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(5,20,1.07,TOURNAMENT,0.4,0.4);
        DataCompression.SolutionI sol = dc.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest5Roulette(){
        String[] x = new String[] {"jwimlbf", "ycgqivui", "ddanpyl", "iss", "xpknp", "hopgowv", "nhiih", "xchs", "yrzyju", "uednlapv", "qwto", "qovrhrlq", "svh", "atmsocwt", "ytprkwin", "qkzx", "ugpha", "chuylyh", "stle", "itzlrpud", "cikzgmcf", "rcu", "ngozp", "lkgw", "ifl", "wrljqj", "rivtqw", "mgx", "dehq", "mzvryylvc", "ekgq", "xnwc", "iobvdrr", "jbwpfdd", "ywtixspo", "wpng", "ozna", "vzskpoo", "scwpn", "amgzskzs", "ycn", "fuafn", "euj", "ofvdr", "upj", "zkzh", "zbiwg", "barmn", "ofdzg", "qhtczyvo", "efxdqhf", "jcjbog", "yixixw", "zepzyxqu", "cuqig", "xnrvhmvga", "whclbvgnj", "sqf", "dfc", "kvlcncbc", "xlw", "xojovrzux", "ughtu", "nvxfa", "ulymubl", "tijkki", "htxl", "wtc", "tntllycv", "qqfb", "nruy", "guhe", "rxuszmw", "enhgcwtsv", "vxpmhz"};
        List<String> input = Arrays.asList(x);

        DataCompression dc = new DataCompression(input);
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(5,20,1.07,ROULETTE,0.4,0.4);
        DataCompression.SolutionI sol = dc.solveIt(gac);
        System.out.println(sol);
    }

}