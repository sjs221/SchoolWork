package edu.yu.introtoalgs;

import java.util.HashMap;

/** Defines and implements the AnthroChassidus API per the requirements
 * documentation.
 *
 * @author Avraham Leff
 */

public class AnthroChassidus {
    private CustomUnionFind cUF;
    int lowerBoundChassidus;

    /**
     * Steps for problem:
     *  1. Make a custom UnionFind
     *  2. Take all unions of a[i] and b[i] in a Map<ID, Parent/Canonical Element>
     *  3. Traverse map
     *      3.1 Add each ID to a map<Canonical Element, size of chassidus>
     *  4. n - Map<ID> size + Cannonical Elements
     */

    private class CustomUnionFind {
        private HashMap<Integer, Integer> interviewedMap;
        private HashMap<Integer, Integer> chassidusQuantMap;

        public CustomUnionFind() {
            interviewedMap = new HashMap<>();
            chassidusQuantMap = new HashMap<>();
        }

        public int find (int i) {
            while (i != interviewedMap.get(i)) {
                interviewedMap.replace(interviewedMap.get(i),interviewedMap.get(interviewedMap.get(i)));
                i = interviewedMap.get(i);
            }
            return i;
        }

        public void union (int p , int q) {
            interviewedMap.putIfAbsent(p,p);
            interviewedMap.putIfAbsent(q,q);

            int root1 = find (p);
            int root2 = find (q);
            if ( root1 == root2 ) return ;

            //unsure about bellow
            chassidusQuantMap.putIfAbsent(root1, 1);
            chassidusQuantMap.putIfAbsent(root2, 1);

            // ensure that root1 is root
            // of smaller tree
            if (chassidusQuantMap.get(root1) >= chassidusQuantMap.get(root2)) {
                int temp = root1 ;
                root1 = root2 ;
                root2 = temp ;
            }
            // link root of smaller tree
            // to root of larger tree
            interviewedMap.replace(root1, root2);
            chassidusQuantMap.replace(root2, chassidusQuantMap.get(root2) + chassidusQuantMap.get(root1));

            if(interviewedMap.get(root1) == root2){
                chassidusQuantMap.remove(root1);
            }

        }
    }

    /** Constructor.  When the constructor completes, ALL necessary processing
     * for subsequent API calls have been made such that any subsequent call will
     * incur an O(1) cost.
     *
     * @param n the size of the underlying population that we're investigating:
     * need not correspond in any way to the number of people actually
     * interviewed (i.e., the number of elements in the "a" and "b" parameters).
     * Must be greater than 2.
     * @param a interviewed people, element value corresponds to a unique "person
     * id" in the range 0..n-1
     * @param b interviewed people, element value corresponds to a unique "person
     * id" in the range 0..n-1.  Pairs of a_i and b_i entries represent the fact
     * that the corresponding people follow the same Chassidus (without
     * specifying what that Chassidus is).
     */
    public AnthroChassidus(final int n, final int[] a, final int[] b) {
        if(a.length != b.length){
            throw new IllegalArgumentException("Input arrays must be of equal size. (All interviews must be done in pairs.)");
        }

        cUF = new CustomUnionFind();
        for (int i = 0; i < a.length; i++) {
            if(a[i] < 0 || a[i] > n || b[i] < 0 || b[i] > n){
                throw new IllegalArgumentException("The input must be within the range of 0 to n-1. (All ID numbers are withing this range.)");
            }

            cUF.union(a[i], b[i]);
        }
        lowerBoundChassidus = n - cUF.interviewedMap.size() + cUF.chassidusQuantMap.size();
    }

    /** Return the tightest value less than or equal to "n" specifying how many
     * types of Chassidus exist in the population: this answer is inferred from
     * the interviewers data supplied to the constructor
     *
     * @return tightest possible lower bound on the number of Chassidus in the
     * underlying population.
     */
    public int getLowerBoundOnChassidusTypes() {
        return lowerBoundChassidus;
    }

    /** Return the number of interviewed people who follow the same Chassidus as
     * this person.
     *
     * @param id uniquely identifies the interviewed person
     * @return the number of interviewed people who follow the same Chassidus as
     * this person.
     */
    public int nShareSameChassidus(final int id) {
        int root = cUF.find(id);
        return cUF.chassidusQuantMap.get(root);
    }




} // class