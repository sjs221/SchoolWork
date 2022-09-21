package edu.yu.introtoalgs;

import java.util.*;

/** Implements the "Add an Interval To a Set of Intervals" semantics defined in
 * the requirements document.
 *
 * @author Avraham Leff
 */

public class MergeAnInterval {

    /** An immutable class, holds a left and right integer-valued pair that
     * defines a closed interval
     *
     * IMPORTANT: students may not modify the semantics of the "left", "right"
     * instance variables, nor may they use any other constructor signature.
     * Students may (are encouraged to) add any other methods that they choose,
     * bearing in mind that my tests will ONLY DIRECTLY INVOKE the constructor
     * and the "merge" method.
     */
    public static class Interval implements Comparable<Interval>{
        public final int left;
        public final int right;

        /** Constructor
         *
         * @param left the left endpoint of the interval, may be negative
         * @param right the right endpoint of the interval, may be negative
         * @throws IllegalArgumentException if left is >= right
         */
        public Interval(int l, int r) {
            if(l >= r){
                throw new IllegalArgumentException("Left must be less than Right.");
            }
            this.left = l;
            this.right = r;
        }

        @Override
        public int compareTo(Interval o) {
            return (this.left - o.left);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return left == interval.left && right == interval.right;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    } // Interval class

    /** Merges the new interval into an existing set of disjoint intervals.
     *
     * @param intervals a set of disjoint intervals (may be empty)
     * @param newInterval the interval to be added
     * @return a new set of disjoint intervals containing the original intervals
     * and the new interval, merging the new interval if necessary into existing
     * interval(s), to preseve the "disjointedness" property.
     * @throws IllegalArgumentException if either parameter is null
     */
    public static Set<Interval> merge (final Set<Interval> intervals, Interval newInterval) {
        if(intervals == null || newInterval == null){
            throw new IllegalArgumentException("Neither parameter may be null.");
        }

        Set<Interval> returnSet = new HashSet<>();
        int mergedLeft = newInterval.left;
        int mergedRight = newInterval.right;

        for (Interval interval: intervals) {
            if(((interval.left < mergedLeft) && (interval.right < mergedLeft)) || ((interval.left > mergedRight) && (interval.right > mergedRight))){//Merged Interval does not overlap
                returnSet.add(interval);
            }else if((mergedLeft <= interval.left) && ((interval.left <= mergedRight) && (mergedRight <= interval.right))){//Merged Interval's left is lower and right is in between (Overlaps left)
                mergedRight = interval.right;
            }else if(((interval.left <= mergedLeft) && (mergedLeft <= interval.right)) && (mergedRight >= interval.right)){//Merged Interval's left is in between and right is higher (Overlaps right)
                mergedLeft = interval.left;
            }else if(((interval.left <= mergedLeft) && (mergedLeft <= interval.right)) && ((interval.left <= mergedRight) && (mergedRight <= interval.right))){//Merged Interval's left is in between and right is in between (Is entirely overlapped)
                mergedLeft = interval.left;
                mergedRight = interval.right;
            }
        }

        returnSet.add(new Interval(mergedLeft, mergedRight));

        return returnSet;
    }
}