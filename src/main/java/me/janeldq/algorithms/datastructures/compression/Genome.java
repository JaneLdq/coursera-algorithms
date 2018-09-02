package me.janeldq.algorithms.datastructures.compression;

import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Genome
 */
public class Genome {

    public static void compress() {
        Alphabet DNA = new Alphabet("ACTG");
        String s = BinaryStdIn.readString();
        int n = s.length();
        BinaryStdOut.write(n);
        for (int i = 0; i < n; i++) {
            int d = DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, DNA.lgR());
        }
        BinaryStdOut.close();
    }

    public static void expand() {
        Alphabet DNA = new Alphabet("ACTG");
        int w = DNA.lgR();
        int n = BinaryStdIn.readInt();
        for (int i = 0; i < n; i++) {
            char c = BinaryStdIn.readChar(w);
            BinaryStdOut.write(DNA.toChar(c));
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) throws Exception {
        if (args[0].equals("-")) compress();
        if (args[0].equals("+")) expand();
    }
}
