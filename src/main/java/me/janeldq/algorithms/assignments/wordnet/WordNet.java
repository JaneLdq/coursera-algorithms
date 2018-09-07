package me.janeldq.algorithms.assignments.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Algorithms Part 2 Week 1 Programming Assignment: WordNet
 * To build the WordNet digraph: each vertex v is an integer that represents a synset,
 * and each directed edge v→w represents that w is a hypernym of v.
 * The WordNet digraph is a rooted DAG: it is acyclic and has one vertex—the root
 * —that is an ancestor of every other vertex.

 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/seam.html
 *
 * @author Jane
 *
 */
public class WordNet {

    private final Map<Integer, String> idToSynsetMap;

    private final Map<String, List<Integer>> nounToIdMap;

    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        idToSynsetMap = new HashMap<>();
        nounToIdMap = new HashMap<>();
        In in = new In(synsets);
        int id = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] arr = line.split(",");
            // get all synonyms separated by space
            String[] set = arr[1].trim().split("\\s+");
            idToSynsetMap.put(id, arr[1]);
            for (String noun: set) {
                List<Integer> ids = nounToIdMap.getOrDefault(noun, new ArrayList<>());
                ids.add(id);
                nounToIdMap.put(noun, ids);
            }
            id++;
        }

        Digraph digraph = new Digraph(id);
        in = new In(hypernyms);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] arr = line.split(",");
            id = Integer.parseInt(arr[0]);
            for (int j = 1; j < arr.length; j++) {
                digraph.addEdge(id, Integer.parseInt(arr[j]));
            }
        }

        // check if it is acyclic
        DirectedCycle dc = new DirectedCycle(digraph);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }

        // check if it is rooted or not
        int roots = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (!digraph.adj(i).iterator().hasNext()) {
                roots++;
            }
        }
        if (roots > 1) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIdMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nounToIdMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        return sap.length(nounToIdMap.get(nounA), nounToIdMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        return idToSynsetMap.get(sap.ancestor(nounToIdMap.get(nounA), nounToIdMap.get(nounB)));
    }

}
