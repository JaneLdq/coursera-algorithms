package me.janeldq.algorithms.assignments.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

/**
 * Algorithms Part 2 Week 5 Programming Assignment: Burrows–Wheeler Data Compression
 * The Burrows–Wheeler compression algorithm consists of 3 algorithmic components:
 *
 * 1.   Burrows–Wheeler transform. Given a typical English text file, transform it
 *      into a text file in which sequences of the same character occur near each
 *      other many times.
 *
 * 2.   Move-to-front encoding. Given a text file in which sequences of the same
 *      character occur near each other many times, convert it into a text file in
 *      which certain characters appear more frequently than others.
 *
 * 3.   Huffman compression. Given a text file in which certain characters appear
 *      more frequently than others, compress it by encoding frequently occurring
 *      characters with short codewords and infrequently occurring characters with
 *      long codewords.
 *
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/burrows.html
 *
 * @author Jane
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
            char c = BinaryStdIn.readChar();
            sb.append(c);
        }
        String s = sb.toString();
        char[] t = s.toCharArray();
        int[] next = new int[t.length];
        char[] sorted = sort(t, next);
        BinaryStdOut.write(sorted[first]);
        int pos = first;
        for (int i = 1; i < t.length; i++) {
            pos = next[pos];
            BinaryStdOut.write(sorted[pos]);
        }
        BinaryStdOut.close();
    }

    private static char[] sort(char[] a, int[] next) {
        int n = a.length;
        int R = 256;
        int[] count = new int[R + 1];
        ArrayList<Queue<Integer>> indices = new ArrayList<>(R);
        char[] aux = new char[n];
        for (int i = 0; i < R; i++) {
            indices.add(i, new Queue<>());
        }
        for (int i = 0; i < n; i++) {
            count[a[i] + 1]++;
            Queue<Integer> q = indices.get(a[i]);
            q.enqueue(i);
        }
        for (int r = 0; r < R; r++) count[r + 1] += count[r];
        for (int i = 0; i < n; i++) {
            aux[count[a[i]]] = a[i];
            next[count[a[i]]++] = indices.get(a[i]).dequeue();
        }
        return aux;
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        if (args[0].equals("+")) inverseTransform();
    }
}
