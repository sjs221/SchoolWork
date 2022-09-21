package edu.yu.da;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/** Implements the WaitNoMoreI API.
 *
 * Students MAY NOT change the provided constructor signature!
 *
 * @author Avraham Leff
 */

public class WaitNoMore implements WaitNoMoreI {

    private class Job{

        private int duration;
        private int weight;

        public Job(int duration, int weight) {
            this.duration = duration;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Job{" +
                    "duration=" + duration +
                    ", weight=" + weight +
                    '}';
        }
    }

    /** No-op constructor
     */
    public WaitNoMore() {
        // no-op, students may change the implementation
    }

    @Override
    public int minTotalWaitingTime(final int[] durations, final int[] weights) {
        LinkedList<Job> jobs = new LinkedList<>();
        for(int i = 0; i < durations.length; i++){
            jobs.add(new Job((durations[i]), (weights[i])));
        }

        System.out.println(jobs);

        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job j1, Job j2) {
                return (int) (j1.duration * j2.weight)- (j2.duration * j1.weight);
            }
        });

        System.out.println(jobs);

        int minWaitTime = 0;
        for (int i = 0; i < jobs.size() - 1; i++){
            minWaitTime += jobs.get(i).duration;
        }

        return minWaitTime;
    }
} // WaitNoMore