package me.janeldq.algorithms.datastructures.compression;

import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Genome
 */
public class Genome {

    public static void compress() {
        String s = BinaryStdIn.readString();
        int n = s.length();
        BinaryStdOut.write(n);
        for (int i = 0; i < n; i++) {
            int d = Alphabet.DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, Alphabet.DNA.lgR());
        }
        BinaryStdOut.close();
    }

    public static void expand() {
        int w = Alphabet.DNA.lgR();
        int n = BinaryStdIn.readInt();
        for (int i = 0; i < n; i++) {
            char c = BinaryStdIn.readChar(w);
            BinaryStdOut.write(Alphabet.DNA.toChar(c));
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) throws Exception {
        if (args[0].equals("-")) compress();
        if (args[0].equals("+")) expand();
    }
}
