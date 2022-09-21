package edu.yu.da;

/** Implements the MaximizePayoutI API.
 *
 * Students MAY NOT change the provided constructor signature!
 *
 * @author Avraham Leff
 */

import java.util.*;

public class MaximizePayout implements MaximizePayoutI {

    /** No-op constructor
     */
    public MaximizePayout() {
        // no-op, students may change the implementation
    }

    @Override
    public long max(final List<Long> A, final List<Long> B) {
        if (A == null || B == null || A.isEmpty() || B.isEmpty() || A.size() != B.size()){
            throw new IllegalArgumentException();
        }
        List<Long> copyA = A;
        List<Long> copyB = B;
        return compute(copyA, copyB);
    }

    private long compute(List<Long> A, List<Long> B) {
        System.out.println(A + " " + B);

        Collections.sort(A, Collections.reverseOrder());
        Collections.sort(B, Collections.reverseOrder());

        System.out.println(A + " " + B);

        long result = 1;
        for (int i = 0; i < A.size(); i++) {
            result *= Math.pow(A.get(i), B.get(i));
        }
        return result;
    }

} // MaximizePayout