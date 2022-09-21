package edu.yu.da;

/** Stubbed implementation of the SimpleEquationI interface.
 *
 * @author Avraham Leff
 */

import java.util.*;

import static edu.yu.da.SimpleEquationI.SolutionI;

public class SimpleEquation implements SimpleEquationI {

    public class SolutionI implements SimpleEquationI.SolutionI {
        private int x;
        private int y;
        private int gens;

        public SolutionI(int x, int y, int gens) {
            this.x = x;
            this.y = y;
            this.gens = gens;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setGens(int gens) {
            this.gens = gens;
        }

        /** Returns the value of x for the equation's solution.
         */
        public int getX(){
            return this.x;
        }

        /** Returns the value of y for the equation's solution.
         */
        public int getY(){
            return this.y;
        }

        /** Returns the fitness of this solution.
         */
        public double fitness(){
            return calculateFit();
        }

        private double calculateFit() {
            double fit = 0;

            //check x gene
            if (this.x == 0) {
                fit = 1;
            } else if (this.x == 1) {
                fit = 2;
            } else if (this.x == 2) {
                fit = 3;
            } else if (this.x == 3) {
                fit = 4;
            }

            //check y gene
            if (this.y == 0) {
                fit += 1;
            } else if (this.y == 1) {
                fit += 2;
            } else if (this.y == 2) {
                fit += 3;
            }

            return fit;
        }

        /** Returns the number of generations that the genetic algorithm ran to
         * produce this solution.
         */
        public int nGenerations(){
            return this.gens;
        }

        @Override
        public String toString() {
            return "SolutionI{" +
                    "x=" + x +
                    ", y=" + y +
                    ", gens=" + gens +
                    ", fit=" + this.calculateFit() +
                    '}';
        }
    } // public inner class SolutionI
    
    private class Population {

        private SolutionI[] solutions;
        private int fittestIndex = -1;
        private int cG;
        private int s;
        private int c;
        private int m;
        private double threshold;
        private GeneticAlgorithmConfig.SelectionType st;


        public Population(int popSize, double threshold, double mp, double cp, GeneticAlgorithmConfig.SelectionType st) {
            this.threshold = threshold;
            this.solutions = new SolutionI[popSize];
            this.cG = 0;
            this.st = st;
            initializePopulation();
            calculateNextGen(mp, cp);
        }

        //Calculate the next generations numbers s, c, and m
        private void calculateNextGen(double mp, double cp) {
            double p = solutions.length;
            m = (int) (mp * p);
            c = (int) (cp * p);
            s = (int) (p - m - c);

            m = (int) p;
        }

        //Initialize population
        private void initializePopulation() {
            Random random = new Random();
            for (int i = 0; i < solutions.length; i++) {
                solutions[i] = new SolutionI(random.nextInt(100),random.nextInt(100),0);
            }
            calculateFitness();
        }

        //Calculate fitness of each individual
        public void calculateFitness() {
            for (int i = 0; i < solutions.length; i++) {

                if (solutions[i].calculateFit() >= threshold){
                    fittestIndex = i;
                }

            }

            if (st == GeneticAlgorithmConfig.SelectionType.ROULETTE){
                if (fittestIndex == -1){
                    Arrays.sort(solutions, new Comparator<SolutionI>() {
                        @Override
                        public int compare(SolutionI o1, SolutionI o2) {
                            return (int) (o2.calculateFit() - o1.calculateFit());
                        }
                    });
                }
            }
        }

        //Selection
        public void select() {
            for (int i = 0; i < s; i++) {
                solutions[i].setGens(this.cG + 1);
            }
        }

        //Crossover
        public void crossover() {
            for (int i = s; i <= c; i+=2) {
                SolutionI o1 = solutions[i];
                SolutionI o2 = solutions[i + 1];

                int o1y = o1.getY();
                int o2y = o2.getY();

                o1.setY(o2y);
                o2.setY(o1y);

                o1.setGens(this.cG + 1);
                o2.setGens(this.cG + 1);

                solutions[i] = o1;
                solutions[i + 1] = o2;
            }
        }

        //Mutation
        public void mutation() {
            for (int i = c; i < m; i++) {
                SolutionI o = solutions[i];

                int oX = o.getX();
                if (oX > 10){
                    o.setX(oX - 10);
                } else if (oX < 3){
                    o.setX(oX + 1);
                }else{
                    o.setX(oX - 1);
                }

                int oY = o.getY();
                if (oY > 10){
                    o.setY(oY - 10);
                } else if (oY < 2){
                    o.setY(oY + 1);
                }else{
                    o.setY(oY - 1);
                }

                o.setGens(this.cG + 1);

                solutions[i] = o;
            }
        }

        //Evolve
        public SolutionI evolve(){
            if (fittestIndex != -1){
                return solutions[fittestIndex];
            }else{
                select();
                crossover();
                mutation();
                cG++;
                calculateFitness();

                return (fittestIndex != -1 ? solutions[fittestIndex] : null);
            }
        }
    }// private class Population

    /** Constructor.
     *
     * Students MAY NOT define any other constructor signature.  They
     * MAY change the stubbed implemention in any way they choose.
     */
    public SimpleEquation() {
    }

    @Override
    public SolutionI solveIt(final GeneticAlgorithmConfig gac) {
        Population p = new Population(gac.getInitialPopulationSize(), gac.getThreshold(), gac.getMutationProbability(),gac.getCrossoverProbability(), gac.getSelectionType());
        SolutionI returnSol = null;

        while (returnSol == null && p.cG < gac.getMaxGenerations()){
            returnSol = p.evolve();
            if (returnSol != null){
                break;
            }
        }

        return returnSol;
    }

} // public class SimpleEquation