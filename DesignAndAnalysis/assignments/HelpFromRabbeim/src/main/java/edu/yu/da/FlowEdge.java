package edu.yu.da;

public class FlowEdge {
    private final int v;             // from
    private final int w;             // to
    private final double capacity;   // capacity
    private double flow;             // flow

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public int other(int vertex) {
        if (vertex == v){
            return w;
        }
        return v;
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v) {
            return flow;              // backward edge
        }
        return capacity - flow;   // forward edge;
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == v){
            flow -= delta;           // backward edge
        }
        flow += delta;           // forward edge
    }

    public String toString() {
        return String.format("%d->%d %.2f %.2f", v, w, capacity, flow);
    }


//    /**
//     * Unit tests the {@code FlowEdge} data type.
//     *
//     * @param args the command-line arguments
//     */
//    public static void main(String[] args) {
//        FlowEdge e = new FlowEdge(12, 23, 4.56);
//        StdOut.println(e);
//    }

}