package me.janeldq.algorithms.assignments.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Move-to-front encoding and decoding
 *
 * The main idea of move-to-front encoding is to maintain an ordered
 * sequence of the characters in the alphabet, and repeatedly read a
 * character from the input message, print out the position in which
 * that character appears, and move that character to the front of
 * the sequence.
 *
 */
public class BurrowsWheeler {

    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        char[] chars = s.toCharArray();
        int n = s.length();
        int first = -1;
        char[] out = new char[n];
        for (int i = 0; i < n; i++) {
            int index = csa.index(i);
            out[i] = chars[(n + index - 1) % n];
            if (index == 0) first = i;
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(out[i]);
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        StringBuilder sb = new StringBuilder();
        while(!BinaryStdIn.isEmpty()) {
            sb.append(BinaryStdIn.readChar());
        }
        String s = sb.toString();
        char[] t = s.toCharArray();
        char[] sorted = sort(t);
        int[] next = next(t, sorted);
        BinaryStdOut.write(sorted[first]);
        int pos = first;
        for (int i = 1; i < t.length; i++) {
            pos = next[pos];
            BinaryStdOut.write(sorted[pos]);
        }
        BinaryStdOut.close();
    }

    private static int[] next(char[] t, char[] sorted) {
        int n = t.length;
        int[] next = new int[n];
        boolean[] marked = new boolean[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!marked[j] && sorted[i] == t[j]) {
                    next[i] = j;
                    marked[j] = true;
                    break;
                }
            }
        }
        return next;
    }

    private static char[] sort(char[] a) {
        int n = a.length;
        int R = 256;
        int[] count = new int[R + 1];
        char[] aux = new char[n];
        for (int i = 0; i < n; i++) count[a[i] + 1]++;
        for (int r = 0; r < R; r++) count[r + 1] += count[r];
        for (int i = 0; i < n; i++) aux[count[a[i]]++] = a[i];
        return aux;
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        if (args[0].equals("+")) inverseTransform();
    }
}
