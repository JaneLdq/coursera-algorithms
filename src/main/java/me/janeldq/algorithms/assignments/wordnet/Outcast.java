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
        int outcast = -1;
        int maxDist = -1;
        int n = nouns.length;
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = wordNet.distance(nouns[i], nouns[j]);
                dist[i] += tmp;
                dist[j] += tmp;
            }
        }
        for (int i = 0; i < n; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                outcast = i;
            }
        }
        return nouns[outcast];
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
