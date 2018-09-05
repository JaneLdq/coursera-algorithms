package me.janeldq.algorithms.assignments.wordnet;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

public class WordNet {

    private final Map<Integer, String> map;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        int n = 0;
        map = new HashMap<>();
        In in = new In(synsets);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] arr = line.split(",");
            map.put(Integer.parseInt(arr[0].trim()), arr[1].trim());
            n++;
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
