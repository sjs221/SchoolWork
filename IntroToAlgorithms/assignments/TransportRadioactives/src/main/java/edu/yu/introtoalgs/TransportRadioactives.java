package edu.yu.introtoalgs;

/** Specifies the interface for generating a sequence of transportation states
 * that moves the radioactives from src to dest per the requirements doc.
 *
 * @author Avraham Leff
 */

import java.util.*;

import static edu.yu.introtoalgs.TransportationState.Location.DEST;
import static edu.yu.introtoalgs.TransportationState.Location.SRC;

public class TransportRadioactives {
    private int initM;
    private int initC;

    /** Computes a sequence of "transport radioactives" movements between the src
     * and the dest such that all of the initial methium and initial cathium are
     * transported safely from the src to the dest.  Each movement must respect
     * the constraints specified in the requirements doc.
     *
     * @param initialMithium initial amount of mithium (in kg) at the src
     * @param initialCathium initial amount of cathium (in kg) at the src
     * @return List of "transport radioactives" movements between the src and the
     * dest (if such a sequence can be computed), or an empty List if no such
     * sequence can be computed under the specified constraints.
     */
    public static List<TransportationState>
    transportIt(final int initialMithium, final int initialCathium) {
        if (initialCathium > initialMithium){
            return new ArrayList<>();
        }

        /* *
        * 1. Cathium must never exceed Mithium
        * 2. Max which can be transported is 2kg
        * 3. Min to transport is 1
        * 4. Need to end at DEST
        * 5. Make as many trips as needed to get everything across
        * */

        List<TransportationState> returnList = new ArrayList<>();

        returnList = buildGraph(new TransportationStateImpl(initialMithium, initialCathium, SRC, initialMithium, initialCathium), new TransportationStateImpl(0, 0, DEST, initialMithium, initialCathium));

        return returnList;
    } // transportIt

    private static List<TransportationState> buildGraph(TransportationState start, TransportationState end) {
        Queue<TransportationState> q = new LinkedList<>();
        Map<TransportationState,Boolean> marked = new HashMap<>();
        Map<TransportationState,TransportationState> edgeFrom = new HashMap<>();
        List<TransportationState> returnList = new LinkedList<>();
        boolean solved = false;
        q.add(start);
        marked.put(start, true);

        while(!q.isEmpty() && !solved){
            TransportationState ts = q.poll();
            for(TransportationState adjTS : getAdj(ts)){
                if((!marked.getOrDefault(adjTS,false)) && !solved){
                    q.add(adjTS);
                    marked.put(adjTS,true);
                    edgeFrom.put(adjTS,ts);
                    if(adjTS.equals(end)){
                        solved = true;
                        break;
                    }
                }
            }
        }

        if(solved){
            Stack<TransportationState> s = new Stack<>();
            boolean done = false;
            TransportationState ts = end;
            while(!done){
                s.add(ts);
                ts = edgeFrom.get(ts);
                if (ts != null && ts.equals(start)){
                    s.add(start);
                    done = true;
                }
                if (ts == null){
                    done = true;
                }
            }
            while(s.size() != 0){
                returnList.add(s.pop());
            }
        }

        return returnList;
    }

    private static List<TransportationState> getAdj(TransportationState ts) {
        List<TransportationState> returnList = new LinkedList<>();
        TransportationState.Location newLoc = (ts.truckLocation() == SRC ? DEST : SRC);

        if (newLoc == DEST){
        //SRC --> DEST

            //2m 0c
            if (ts.getMithiumSrc() - 2 >= 0){
                if (ts.getMithiumSrc() - 2 >= ts.getCathiumSrc() || ts.getMithiumSrc() - 2 == 0){
                    if (ts.getMithiumDest() + 2 >= ts.getCathiumDest()){
                        returnList.add(new TransportationStateImpl(ts.getMithiumSrc() - 2, ts.getCathiumSrc(), newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                    }
                }
            }

            //0m 2c
            if (ts.getCathiumSrc() - 2 >= 0){
                if (ts.getCathiumDest() + 2 <= ts.getMithiumDest() || ts.getMithiumDest() == 0){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc(), ts.getCathiumSrc() - 2, newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

            //1m 1c
            if (ts.getMithiumSrc() - 1 >= 0 && ts.getCathiumSrc() - 1>= 0){
                if (ts.getMithiumSrc() - 1 >= ts.getCathiumSrc() - 1 && ts.getCathiumDest() + 1 <= ts.getMithiumDest() + 1){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc() - 1, ts.getCathiumSrc() - 1, newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

            //1m 0c
            if (ts.getMithiumSrc() - 1 >= 0){
                if (ts.getMithiumSrc() - 1 >= ts.getCathiumSrc() || ts.getMithiumSrc() - 1 == 0){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc() - 1, ts.getCathiumSrc(), newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

            //0m 1c
            if (ts.getCathiumSrc() - 1 >= 0){
                if (ts.getCathiumDest() + 1 <= ts.getMithiumDest() || ts.getMithiumDest() == 0){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc(), ts.getCathiumSrc() - 1, newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

        }else{
        //DEST --> SRC

            //2m 0c
            if (ts.getMithiumDest() - 2 >= 0){
                if (ts.getMithiumDest() - 2 >= ts.getCathiumDest() || ts.getMithiumDest() - 2 == 0){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc() + 2, ts.getCathiumSrc(), newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

            //0m 2c
            if (ts.getCathiumDest() - 2 >= 0){
                if (ts.getCathiumSrc() + 2 <= ts.getMithiumSrc() || ts.getMithiumSrc() == 0){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc(), ts.getCathiumSrc() + 2, newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

            //1m 1c
            if (ts.getMithiumDest() - 1 >= 0 && ts.getCathiumDest() - 1 >= 0){
                if (ts.getMithiumSrc() + 1 >= ts.getCathiumSrc() + 1 && ts.getCathiumDest() - 1 <= ts.getMithiumDest() - 1){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc() + 1, ts.getCathiumSrc() + 1, newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

            //1m 0c
            if (ts.getMithiumDest() - 1 >= 0){
                if (ts.getMithiumDest() - 1 >= ts.getCathiumDest() || ts.getMithiumDest() - 1 == 0){
                    if (ts.getMithiumSrc() + 1 >= ts.getCathiumSrc()){
                        returnList.add(new TransportationStateImpl(ts.getMithiumSrc() + 1, ts.getCathiumSrc(), newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                    }
                }
            }

            //0m 1c
            if (ts.getCathiumDest() - 1 >= 0){
                if (ts.getCathiumSrc() + 1 <= ts.getMithiumSrc() || ts.getMithiumSrc() == 0){
                    returnList.add(new TransportationStateImpl(ts.getMithiumSrc(), ts.getCathiumSrc() + 1, newLoc, ts.getTotalMithium(), ts.getTotalCathium()));
                }
            }

        }

        return returnList;
    }
} // TransportRadioactives