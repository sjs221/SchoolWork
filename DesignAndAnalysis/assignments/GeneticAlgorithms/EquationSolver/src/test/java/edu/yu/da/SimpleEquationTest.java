package edu.yu.da;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static edu.yu.da.GeneticAlgorithmConfig.SelectionType.ROULETTE;
import static edu.yu.da.GeneticAlgorithmConfig.SelectionType.TOURNAMENT;

public class SimpleEquationTest {

    @Test
    public void barelyATest(){
        SimpleEquation se = new SimpleEquation();
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(10,200,7,ROULETTE,0.4,0.4);
        SimpleEquation.SolutionI sol = se.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATestTourny(){
        SimpleEquation se = new SimpleEquation();
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(10,200,7,TOURNAMENT,0.4,0.4);
        SimpleEquation.SolutionI sol = se.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest2(){
        SimpleEquation se = new SimpleEquation();
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(10,10,7,ROULETTE,0.4,0.4);
        SimpleEquation.SolutionI sol = se.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest3(){
        SimpleEquation se = new SimpleEquation();
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(100,10,7,ROULETTE,0.7,0.2);
        SimpleEquation.SolutionI sol = se.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void barelyATest4(){
        SimpleEquation se = new SimpleEquation();
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(100,10,7,ROULETTE,0.6,0.2);
        SimpleEquation.SolutionI sol = se.solveIt(gac);
        System.out.println(sol);
    }

    @Test
    public void leffTest(){
        SimpleEquation se = new SimpleEquation();
        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(20,100,13.0,TOURNAMENT,0.1,0.7);
        SimpleEquation.SolutionI sol = se.solveIt(gac);
        System.out.println(sol);
    }

}