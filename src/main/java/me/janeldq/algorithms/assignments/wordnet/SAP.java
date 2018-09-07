package me.janeldq.algorithms.assignments.wordnet;


import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph G;

    private boolean[] markedFromV;

    private boolean[] markedFromW;

    private int[] distToFromV;

    private int[] distToFromW;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        // make a copy of G to be immutable
        this.G = new Digraph(G);
        markedFromV = new boolean[this.G.V()];
        markedFromW = new boolean[this.G.V()];
        distToFromV = new int[this.G.V()];
        distToFromW = new int[this.G.V()];
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return ancestorAndLength(v, w).shortestLen;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestorAndLength(v, w).ancestor;
    }

    private class Node {
        int ancestor;
        int shortestLen;
        public Node(int ancestor, int shortestLen) {
            this.ancestor = ancestor;
            this.shortestLen = shortestLen;
        }
    }

    private Node ancestorAndLength(int v, int w) {
        validate(v);
        validate(w);
        if (v == w) return new Node(v, 0);
        reset();
        Queue<Integer> q = new Queue<>();
        q.enqueue(v);
        markedFromV[v] = true;
        distToFromV[v] = 0;
        Node result = new Node(-1, -1);
        boolean stop = false;
        while (!q.isEmpty() && !stop) {
            int cur = q.dequeue();
            for (int vertex: G.adj(cur)) {
                if (!markedFromV[vertex]) {
                    if (vertex == w) {
                        result.ancestor = vertex;
                        result.shortestLen = distToFromV[cur] + 1;
                        distToFromV[vertex] = distToFromV[cur] + 1;
                        markedFromV[vertex] = true;
                        stop = true;
                        break;
                    }
                    q.enqueue(vertex);
                    markedFromV[vertex] = true;
                    distToFromV[vertex] = distToFromV[cur] + 1;
                }
            }
        }
        int minLen = Integer.MAX_VALUE;
        if (stop) {
            minLen = result.shortestLen;
        }
        q = new Queue<>();
        q.enqueue(w);
        markedFromW[w] = true;
        distToFromW[w] = 0;
        stop = false;
        while (!q.isEmpty() && !stop) {
            int cur = q.dequeue();
            for (int vertex: G.adj(cur)) {
                if (!markedFromW[vertex]) {
                    if (markedFromV[vertex]) {
                        int tempLen = distToFromV[vertex] + distToFromW[cur] + 1;
                        if (minLen > tempLen) {
                            minLen = tempLen;
                            result.ancestor = vertex;
                            result.shortestLen = minLen;
                        }
                    } else {
                        if (vertex == v) {
                            result.ancestor = vertex;
                            result.shortestLen = distToFromW[cur] + 1;
                            stop = true;
                            break;
                        }
                    }
                    q.enqueue(vertex);
                    markedFromW[vertex] = true;
                    distToFromW[vertex] = distToFromW[cur] + 1;
                }
            }
        }
        if (!stop) {
            result.shortestLen = minLen == Integer.MAX_VALUE ? -1 : minLen;
        }
        return result;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v);
        validate(w);
        return ancestorAndLength(v, w).shortestLen;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v);
        validate(w);
        return ancestorAndLength(v, w).ancestor;
    }

    private Node ancestorAndLength(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths vp = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wp = new BreadthFirstDirectedPaths(G, w);
        Node node = new Node(-1, -1);
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            if (vp.hasPathTo(i) && wp.hasPathTo(i)) {
                int vLen = vp.distTo(i);
                int wLen = wp.distTo(i);
                if (vLen + wLen < minLen) {
                    minLen = vLen + wLen;
                    node.ancestor = i;
                }
            }
        }
        if (minLen == Integer.MAX_VALUE) node.shortestLen = -1;
        else node.shortestLen = minLen;
        return node;
    }

    private void reset() {
        for (int i = 0; i < G.V(); i++) {
            markedFromV[i] = false;
            markedFromW[i] = false;
            distToFromV[i] = Integer.MAX_VALUE;
            distToFromW[i] = Integer.MAX_VALUE;
        }
    }

    private void validate(int v) {
        if (v < 0 || v >= G.V()) throw new IllegalArgumentException();
    }

    private void validate(Iterable<Integer> v) {
        if (v == null) throw new IllegalArgumentException();
        for (Integer i: v) {
            if (i == null || i < 0 || i >= G.V()) throw new IllegalArgumentException();
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}