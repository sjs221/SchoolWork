package edu.yu.da;

public class MultiMerge extends MultiMergeBase {

    public MultiMerge() {
        super();
    }

    /** Does a divide-and-conquer mergesort on the z integer-valued arrays (first
     * dimension is the ith array).
     *
     * @param arrays z (z >= 1) integer valued arrays, each of which is sorted,
     * and of identical size n where n>0 and need not be a power of two.  Results
     * are undefined if the arrays aren't sorted, or if they aren't the same
     * size.
     * @return result array of size "z * n": when the method completes, holds the
     * sorted contents of all input arrays.
     */
    @Override
    public int[] merge(int[][] arrays) {
        int z = arrays.length;
        int n = arrays[0].length;

        int[] sendArray = new int[z*n];
        int[] returnArray = sort(arrays, sendArray, 0,z-1);
        return returnArray;
    }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    private int[] merge(int[] a, int[] b) {
        int[] returnArray = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        boolean done = false;
        while(!done){
            if      (i == -1)       returnArray[k++] = b[j++];
            else if (j == -1)       returnArray[k++] = a[i++];
            else if (a[i] <= b[j])       returnArray[k++] = a[i++];
            else if (a[i] > b[j])       returnArray[k++] = b[j++];

            if (i >= a.length){
                i = -1;
            }
            if (j >= b.length){
                j = -1;
            }
            if (i == -1 && j == -1){
                done = true;
            }
        }

        combinedAMerge();
        return returnArray;
    }

    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private int[] sort(int[][] arrays, int[] aux, int lo, int hi) {
        if (hi - lo == 0){
            return arrays[hi];
        }
        int mid = lo + (hi - lo) / 2;
        int[] a = sort(arrays, aux, lo, mid);
        int[] b = (hi - lo == 2 ? arrays[hi] : sort(arrays, aux, mid + 1, hi));
        return merge(a, b);
    }

}
