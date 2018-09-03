package me.janeldq.algorithms.assignments.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class MoveToFront {

    public static void encode() {
        int R = 256;
        int[] ascii = new int[R];
        for (char i = 0; i < R; i++) {
            ascii[i] = i;
        }
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            for (char i = 0; i < R; i++) {
                if (ascii[i] == c) {
                    BinaryStdOut.write(i);
                    swap(ascii, 0, i);
                    System.out.println(Arrays.toString(ascii));
                    break;
                }
            }
        }
        BinaryStdOut.close();
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void decode() {
        int R = 256;
        int[] ascii = new int[R];
        for (char i = 0; i < R; i++) {
            ascii[i] = i;
        }
        while(!BinaryStdIn.isEmpty()) {
            char index = BinaryStdIn.readChar();
            BinaryStdOut.write(ascii[index]);
            swap(ascii, 0, index);
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        if (args[0].equals("+")) decode();
    }
}