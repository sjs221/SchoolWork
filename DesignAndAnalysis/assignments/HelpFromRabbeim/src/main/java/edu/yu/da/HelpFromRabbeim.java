package edu.yu.da;

/** Implements the HelpFromRabbeimI interface.
 *
 * Students MAY NOT change the provided constructor signature!
 *
 * @author Avraham Leff
 */

import java.util.*;

public class HelpFromRabbeim implements HelpFromRabbeimI {

    Map<Integer, Object> scheduleMap = new HashMap<>();

    /** No-op constructor
     */
    public HelpFromRabbeim() {
        // no-op, students may change the implementation
    }

    @Override
    public Map<Integer, HelpTopics> scheduleIt(List<Rebbe> rabbeim, Map<HelpTopics, Integer> requestedHelp) {
        int V = 2 + requestedHelp.size() + rabbeim.size();
        int s = 0, t = V - 1;
        FlowNetwork fn = buildFN(rabbeim,requestedHelp, V, s, t);
        FordFulkerson ff = new FordFulkerson(fn,s,t);
        Map<Integer, HelpTopics> schedule = buildSchedule(ff, fn);

        return schedule;
    }

    private Map<Integer, HelpTopics> buildSchedule(FordFulkerson ff, FlowNetwork fn) {
        Map<Integer,HelpTopics> returnMap = new HashMap<>();
        for (int v = 0; v < fn.V(); v++) {
            for (FlowEdge e : fn.adj(v)) {
                if ((v == e.from())){
                    if (v == 0) {
                        if (e.flow() != e.capacity()){
                            return Collections.emptyMap();
                        }
                    }
                    if (e.flow() > 0) {
                        if (scheduleMap.get(v) instanceof HelpTopics){
                            Rebbe r = (Rebbe) scheduleMap.get(e.to());
                            HelpTopics topic = (HelpTopics) scheduleMap.get(v);
                            returnMap.put(r._id, topic);
                        }
                    }else{
                        if (scheduleMap.get(v) instanceof HelpTopics){
                            Rebbe r = (Rebbe) scheduleMap.get(e.to());
                            HelpTopics topic = null;
                            returnMap.putIfAbsent(r._id, topic);
                        }
                    }
                }
            }
        }

        System.out.println(returnMap);
        return returnMap;
    }

    private FlowNetwork buildFN(List<Rebbe> rabbeim, Map<HelpTopics, Integer> requestedHelp, int V, int s, int t){
        Map<String, Integer> nodeMap = new HashMap<>();
        nodeMap.put("source", 0);
        nodeMap.put("sink", t);
        scheduleMap.put(0, "source");
        scheduleMap.put(t, "sink");
        FlowNetwork fn = new FlowNetwork(V);
        int i = 1;
        for (HelpFromRabbeimI.HelpTopics topic : requestedHelp.keySet()) {
            nodeMap.put(topic.toString(),i);
            scheduleMap.put(i, topic);
            fn.addEdge(new FlowEdge(s, i, requestedHelp.get(topic)));
            i++;
        }
        for (HelpFromRabbeimI.Rebbe r: rabbeim) {
            for (HelpFromRabbeimI.HelpTopics topic : r._helpTopics) {
                fn.addEdge(new FlowEdge(nodeMap.get(topic.toString()), i, 1));
            }
            nodeMap.put(r.toString(),i);
            scheduleMap.put(i, r);
            fn.addEdge(new FlowEdge(i, t, 1));
            i++;
        }
        return fn;
    }

} // HelpFromRabbeim