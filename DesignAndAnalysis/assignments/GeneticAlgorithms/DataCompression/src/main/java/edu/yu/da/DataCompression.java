package edu.yu.da;

import java.util.*;

public class DataCompression implements DataCompressionI{

    public class Solution implements SolutionI{

        List<String> list;
        int g;
        int prevI = 0;
        int prevJ = 1;

        public Solution(List<String> list, int g) {
            this.list = list;
            this.g = g;
        }

        public void modList() {
            int i = prevI % (list.size()-1);
            int j = (prevJ) % (list.size()-1);
            String s1 = list.get(i);
            String s2 = list.get(j);
            list.remove(i);
            list.add(i,s2);
            list.remove(j);
            list.add(j,s1);
            this.prevI++;
            this.prevJ++;
        }

        public void modList(Solution o2) {
            List<String> sequence = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != o2.getList().get(i)){
                    sequence.add(list.remove(i));
                }
            }
            for (String s : sequence) {
                list.add(s);
            }
        }

        @Override
        public List<String> getList() {
            return list;
        }

        @Override
        public List<String> getOriginalList() {
            return original;
        }

        @Override
        public double relativeImprovement() {
            return ((double) nCompressedBytesInOriginalList())/((double) DataCompressionI.bytesCompressed(list));
        }

        /** Returns the fitness of this solution.
         */
        public double fitness(){
            return calculateFit();
        }

        private double calculateFit() {
            return relativeImprovement();
        }

        @Override
        public int nGenerations() {
            return g;
        }
    } // public inner class SolutionI

    private class Population {

        private Solution[] solutions;
        private int fittestIndex = -1;
        private int cG;
        private int s;
        private int c;
        private int m;
        private double threshold;
        private GeneticAlgorithmConfig.SelectionType st;


        public Population(int popSize, double threshold, double mp, double cp, GeneticAlgorithmConfig.SelectionType st) {
            this.threshold = threshold;
            this.solutions = new Solution[popSize];
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
            solutions[0] = new Solution(original,0);
            List<String> listCopy = new ArrayList<>(original);
            for (int i = 1; i < solutions.length; i++) {
                List<String> listI = new ArrayList<>(listCopy);
                Collections.shuffle(listI);
                solutions[i] = new Solution(listI,0);
                listCopy = listI;
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
            if (st == GeneticAlgorithmConfig.SelectionType.TOURNAMENT){
                if (fittestIndex == -1){
                    Arrays.sort(solutions, new Comparator<Solution>() {
                        @Override
                        public int compare(Solution o1, Solution o2) {
                            return (int) (o2.calculateFit() - o1.calculateFit());
                        }
                    });
                }
            }
        }

        //Selection
        public void select() {
            for (int i = 0; i < s; i++) {
                solutions[i].g++;
            }
        }

        //Crossover
        public void crossover() {
            for (int i = s; i <= c; i+=2) {
                Solution o1 = solutions[i];
                Solution o2 = solutions[i + 1];

                o1.modList(o2);
                o2.modList(o1);

                o1.g++;
                o2.g++;

            }
        }

        //Mutation
        public void mutation() {
            for (int i = c; i < m; i++) {
                Solution o = solutions[i];

                o.modList();

                if (o.g == cG){
                    o.g++;
                }

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

    private List<String> original;
    private int nCompressedBytesInOriginalList = 0;

    /** Constructor.
     *
     * @param original the list whose elements we want to reorder
     * to reduce the
     * number of bytes when compressing the list.
     */
    public DataCompression (final List<String> original) {
        this.original = original;
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

    @Override
    public int nCompressedBytesInOriginalList() {
        return DataCompressionI.bytesCompressed(original);
    }
}
