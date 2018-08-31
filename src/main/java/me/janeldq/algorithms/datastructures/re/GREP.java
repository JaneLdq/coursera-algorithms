package me.janeldq.algorithms.datastructures.re;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileInputStream;

/**
 * Classic Generalized Regular Expression Pattern-matching NFA client
 */
public class GREP {

    public static void main(String[] args) throws Exception {
        String regexp = "(.*" + args[0] + ".*)";
        System.setIn(new FileInputStream(args[1]));
        StdOut.println("Pattern: " + regexp);
        SimpleNFA nfa = new SimpleNFA(regexp);
        while (StdIn.hasNextLine()) {
            String txt = StdIn.readLine();
            if (nfa.recognizes(txt)) {
                StdOut.println(txt);
            }
        }
    }

}
