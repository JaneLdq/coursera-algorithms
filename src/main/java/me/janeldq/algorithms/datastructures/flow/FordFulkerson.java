package me.janeldq.algorithms.datastructures.flow;

import edu.princeton.cs.algs4.Queue;

/**
 * Ford-Fulkerson is an algorithm to find a max flow
 * steps:
 *      1. find an augmenting path
 *      2. compute the bottleneck capacity
 *      3. augment each edge and the total flow
 */
public class FordFulkerson {

    private final int V; // number of vertices

    private boolean[] marked; // marked[v] = true if path s->v in residual network

    private FlowEdge[] edgeTo; // edgeTo[v] = last edge on path s->v

    private double value; // value of max flow

    public FordFulkerson(FlowNetwork G, int s, int t) {
        V = G.V();
        validate(s);
        validate(t);
        value = 0.0;
        while(hasAugmentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            // compute bottleneck capacity
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

    // This implementation finds a shortest augmenting path (fewest number of edges),
    // which performs well both in theory and in practice
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        // breadth-first search
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        marked[s] = true;
        while(!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge e: G.adj(v)) {
                int w = e.other(v);
                // found path from s to w in the residual network
                // if residual capacity of v -> w > 0
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
        // is t reachable from s in residual network
        return marked[t];
    }

    public double value() {
        return value;
    }

    // Reture true if v is on the s side of min-cut
    public boolean inCut(int v) {
        validate(v);
        // If path s->v is in residual network, then v is in min-cut
        return marked[v];
    }

    private void validate(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException();
    }
}
