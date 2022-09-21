package edu.yu.da;

public class FordFulkerson {
    private boolean[] marked;     // marked[v] = true iff s->v path in residual graph
    private FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    private double value;         // current value of max flow

    public FordFulkerson(FlowNetwork G, int s, int t) {
        // while there exists an augmenting path, use it
        while (hasAugmentingPath(G, s, t)) {
            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }
            value += bottle;
        }
    }

    public double value()  {
        return value;
    }

    public boolean inCut(int v)  {
        return marked[v];
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];
        edgeTo = new FlowEdge[G.V()];
        Queue<Integer> queue = new Queue<Integer>();
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
        return marked[t];
    }

    private boolean isFeasible(FlowNetwork G, int s, int t) {
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if (e.flow() < 0 || e.flow() > e.capacity()){
                    return false;
                }
            }
        }
        for (int v = 0; v < G.V(); v++) {
            if (v != s && v != t && !localEq(G, v)){
                return false;
            }
        }
        return true;
    }

    private boolean localEq(FlowNetwork G, int v){
        double EPSILON = 1E-11;
        double netflow = 0.0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()){
                netflow -= e.flow();
            } else {
                netflow += e.flow();
            }
        }
        return Math.abs(netflow) < EPSILON;
    }
}
