package me.janeldq.algorithms.assignments.boggle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private static final int[][] ADJ_POSITION = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
            {0, 1}, {1, -1}, {1, 0}, {1, 1}
    };

    private final MyTrieST<Integer> dictTrie;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();
        dictTrie = new MyTrieST<>();
        for (String s: dictionary) {
            dictTrie.put(s, 1);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException();
        Set<String> words = new HashSet<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                words.addAll(getValidWordsFrom(board, i, j));
            }
        }
        return words;
    }

    private Collection<String> getValidWordsFrom(BoggleBoard board, int i, int j) {
        Set<String> words = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        int[][] marked = new int[board.rows()][board.cols()];
        collect(board, marked, sb, i, j, words);
        return words;
    }

    private void collect(BoggleBoard board, int[][] marked, StringBuilder sb, int i, int j, Set<String> words) {
        if (i < 0 || j < 0 || i > board.rows() - 1 || j > board.cols() - 1) return;
        if (marked[i][j] == 1) return;
        marked[i][j] = 1;
        sb.append(board.getLetter(i, j));
        String str = sb.toString().replace("Q", "QU");
        // when the current path corresponds to a string that is not a prefix of any word in the dictionary,
        // there is no need to expand the path further.
        if (dictTrie.keysWithPrefix(str)) {
            // if length of current word >= 3 and is in dictionary then it is a valid word
            if (str.length() >= 3 && dictTrie.contains(str)) words.add(str);
            // add next character
            for (int[] a: ADJ_POSITION) {
                int adjI = i + a[0], adjJ = j + a[1];
                collect(board, marked, sb, adjI, adjJ, words);
            }
        }
        marked[i][j] = 0;
        sb.deleteCharAt(sb.length()-1);
    }

    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();
        int len = word.length();
        if (len <= 2) return 0;
        if (len <= 4) return 1;
        if (len == 5) return 2;
        if (len == 6) return 3;
        if (len == 7) return 5;
        return 11;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
