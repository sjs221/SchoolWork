package edu.yu.introtoalgs;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool ;
import java.util.concurrent.ForkJoinTask ;

/** Implements the CountStringsFJ semantics specified in the requirements
 * document.
 *
 * @author Avraham Leff
 */

public class CountStringsFJ {


    private class ForkJoinSum extends RecursiveTask <Integer> {
        ForkJoinSum(int threshold, String[] array, String targetString, int low, int high) {
            // @fixme No error checking !
            this.low = low;
            this.high = high;
            this.array = array;
            this.targetString = targetString;
            // If array size is this small ,
            // don ’t process recursively
            this.threshold = threshold;
        }

        private final int low;
        private final int high;
        private final String[] array;
        private final String targetString;
        private final int threshold;

        public Integer compute() {
            if (high - low <= threshold) {
                return CountStringsFJ.countOccurrencesOfString(array, targetString, low , high);
            } // sequential processing
            else {
                ForkJoinSum left = new ForkJoinSum(threshold, array, targetString, low, (high + low) / 2);
                ForkJoinSum right = new ForkJoinSum(threshold, array, targetString, (high + low) / 2, high);
                left.fork();
                final Integer rightAnswer = right.compute();
                final Integer leftAnswer = left.join();
                return leftAnswer + rightAnswer;
            }
        }
    }

    private String[] inputArray;
    private String targetString;
    private int threshold;

    /** Constructor.
     *
     * @param arr the array to process, can't be null or empty
     * @param str the string to match, can't be null, may be empty
     * @param threshold when the length of arr is less than threshold, processing
     * must be sequential; otherwise, processing must use a fork/join, recursive
     * divide-and-conquer strategy.  The parameter must be greater than 0.
     *
     * IMPORTANT: Students must use this constructor, they MAY NOT add another
     * constructor.
     */
    public CountStringsFJ(final String[] arr, final String str, final int threshold) {
        if(arr == null || arr.length == 0 || str == null || threshold <= 0){
            throw new IllegalArgumentException();
        }
        this.inputArray = arr;
        this.targetString = str;
        this.threshold = threshold;
    }

    /** Returns the number of elements in arr that ".equal" the "str" parameter
     *
     * @return Using a strategy dictated by the relative values of threshold and
     * the size of arr, returns the number of times that str appears in arr
     */
    public int doIt() {
        if (this.inputArray.length < threshold){
            return countOccurrencesOfString(this.inputArray, this.targetString,0, this.inputArray.length);
        }else {
            int parallelism = Runtime.getRuntime().availableProcessors() * 1;
            ForkJoinTask<Integer> task = new ForkJoinSum(this.threshold, this.inputArray, this.targetString, 0, inputArray.length);
            final ForkJoinPool fjPool = new ForkJoinPool(parallelism);
            int parallelSum = fjPool.invoke(task);
            fjPool.shutdown();
            return parallelSum;
        }
    }

    private static Integer countOccurrencesOfString(String[] array, String targetString, int low, int high){
        int count = 0;
        for (int i = low; i < high; i++){
            if(array[i].equals(targetString)){
                count++;
            }
        }
        return count;
    }
}