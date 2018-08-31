package me.janeldq.algorithms.datastructures.re;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;

public class SimpleNFA {

    // match transitions
    private final char[] re;

    // epsilon transitions
    private Digraph G;

    // number of states
    private final int M;

    /**
     * Building the NFA corresponding to an M-character RE takes time and space
     * proportional to M in the worst case.
     */
    public SimpleNFA(String regexp) {
        Stack<Integer> ops  = new Stack<>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);

        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {
                int or = ops.pop();
                /**
                 * For or(|) operation, we add 2 ϵ-transitions:
                 *  i)  one from the state corresponding to the left parenthesis to the state
                 *      corresponding to the first character of second RE.
                 *  ii) one from the state corresponding to the | operator to the state corresponding
                 *      to the right parenthesis.
                 *
                 * For example, for `(A | B)`, then we add `( -> B` and `| -> )`.
                 *
                 */
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else {
                    lp = or;
                }
            }
            if (i < M - 1 && re[i + 1] == '*') { // look ahead to check for closure
                /**
                 * For closure (*), we add two ϵ-transitions,
                 * to and from the corresponding left parenthesis.
                 */
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') { // 3 other ϵ-transitions
                G.addEdge(i, i + 1);
            }
        }
    }

    public boolean recognizes(String txt) {
        Bag<Integer> pc = new Bag<>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) pc.add(v);
        }

        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<>();
            for (int v : pc) {
                if (v < M) {
                    // if match, add the match transition
                    if (re[v] == txt.charAt(i) || re[v] == '.') {
                        match.add(v + 1);
                    }
                }
            }
            pc = new Bag<>();
            // dfs starts from all match states
            dfs = new DirectedDFS(G, match);
            for (int v = 0; v < G.V(); v++) {
                if (dfs.marked(v)) pc.add(v);
            }
        }
        for (int v : pc) {
            if (v == M) return true;
        }
        return false;
    }

}
