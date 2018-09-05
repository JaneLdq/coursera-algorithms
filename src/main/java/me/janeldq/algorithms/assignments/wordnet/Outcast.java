package me.janeldq.algorithms.assignments.wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordNet;

    public Outcast(WordNet wordNet) {
        if (wordNet == null) throw new IllegalArgumentException();
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        double maxDist = 0;
        String outcast = null;
        for (String s: nouns) {
            double dist = 0;
            for (String other: nouns) {
                dist += wordNet.distance(s, other);
            }
            if (dist >= maxDist) {
                outcast = s;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
