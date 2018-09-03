package me.janeldq.algorithms.assignments.burrows;

import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class CircularSuffixArray {

    private final int n; // length of s

    private final char[] chars;

    private final Integer[] indices;

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        n = s.length();
        chars = s.toCharArray();
        indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (i, j) -> {
            int count = 0;
            while (chars[i] == chars[j] && count < n) {
                i = (i + 1) % n;
                j = (j + 1) % n;
                count++;
            }
            return chars[i] - chars[j];
        });
    }

    /**
     * return the length of the string
     * @return
     */
    public int length() {
        return n;
    }

    /**
     * returns index of ith sorted suffix
     */
    public int index(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException();
        return indices[i];
    }

    public static void main(String[] args) {
        // unit testing
        String testStr = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(testStr);
        StdOut.println(csa.length());
        for (int i = 0; i < testStr.length(); i++) {
            StdOut.print(csa.index(i) + " ");
        }
    }
}
