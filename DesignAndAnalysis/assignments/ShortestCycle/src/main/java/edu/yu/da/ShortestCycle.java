package edu.yu.da;

/** Implements the ShortestCycleBase API.
 *
 */

import java.util.LinkedList;
import java.util.List;
import static edu.yu.da.ShortestCycleBase.Edge;

public class ShortestCycle extends ShortestCycleBase {

    private final List<Edge> edges;
    private final Edge eOi;
    private EdgeWeightedGraph ewg;


    /** Constructor
     *
     * @param edges List of edges that, in toto, represent a weighted undirected
     * graph.  The client maintains ownership of the List: the implementation may
     * not modify this input parameter.  The client guarantees that the List is
     * not null, and doesn't contains any null edges.
     * @param e One of the graph's edges, the "edge of interest" since we want to
     * determine the shortest cycle containing this edge.
     */
    public ShortestCycle(final List<Edge> edges, final Edge e) {
        // base class does nothing, but let's do it right
        super(edges, e);

        this.edges = edges;
        this.eOi = e;

        buildGraph();
    } // constructor

    private void buildGraph() {
        int vertices = countVertices();
        this.ewg = new EdgeWeightedGraph(vertices);
        for (Edge e : edges){
            if(!e.equals(this.eOi)){
                ewg.addEdge(e);
            }
//            ewg.addEdge(e);
        }
    }

    private int countVertices() {
        int highestV = 0;
        int highestW = 0;
        for (Edge e : edges){
            if (e.v() > highestV){
                highestV = e.v();
            }
            if (e.w() > highestW){
                highestW = e.w();
            }
        }
        return (Math.max(highestV, highestW) + 1);
    }

    /** Finds the shortest cycle in the graph with respect to the specified edge
     * as detailed by the requirements document.
     *
     * @return List of edges representing the shortest cyle containing the "edge
     * of interest".  The List can begin with any edge from the cycle, but must
     * be a sequence that begins and ends at the same vertex and contain the
     * "edge of interest".
     */
    @Override
    public List<Edge> doIt() {
        DijkstraUndirectedSP dusp = new DijkstraUndirectedSP(this.ewg, this.eOi.v());

        List<Edge> returnList = new LinkedList<Edge>(dusp.pathTo(this.eOi.w()));
        returnList.add(this.eOi);


        return returnList;
    } // doIt

}