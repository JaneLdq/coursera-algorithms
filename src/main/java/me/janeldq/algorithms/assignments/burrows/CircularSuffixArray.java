package me.janeldq.algorithms.assignments.burrows;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

    private final int n; // length of s

    private final char[] chars;

    private final int[] indices;

    private static final int CUTOFF = 6;

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        n = s.length();
        chars = s.toCharArray();
        indices = new int[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        sort(indices, 0, n - 1, 0);
    }

    private void sort(int[] indices, int lo, int hi, int d) {
        if (lo >= hi) return;
        if (hi - lo <= CUTOFF) {
            insertion(indices, lo, hi);
        } else {
            int lt = lo, gt = hi;
            int v = charAt(indices[lo], d);
            int i = lo + 1;
            while (i <= gt) {
                int t = charAt(indices[i], d);
                if (t < v) swap(indices, lt++, i++);
                else if (t > v) swap(indices, i, gt--);
                else i++;
            }
            // a[lo .. lt - 1] < v = a[lt .. gt] < a[gt + 1 .. hi]
            sort(indices, lo, lt - 1, d);
            if (v >= 0) sort(indices, lt, gt, d + 1);
            sort(indices, gt + 1, hi, d);
        }
    }

    private void insertion(int[] indices, int lo, int hi) {
        int j;
        for (int i = lo; i <= hi; i++) {
            int tmp = indices[i];
            for (j = i; j > lo && compare(tmp, indices[j - 1]) < 0; j--) {
                indices[j] = indices[j-1];
            }
            indices[j] = tmp;
        }
    }

    private int compare(int i, int j) {
        int count = 0;
        while (chars[i] == chars[j] && count < n) {
            i = (i + 1) % n;
            j = (j + 1) % n;
            count++;
        }
        return chars[i] - chars[j];
    }

    private int charAt(int idx, int d) {
        if (d >= n) return -1;
        return chars[(idx + d) % n];
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public int length() {
        return n;
    }

    public int index(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException();
        return indices[i];
    }

    public static void main(String[] args) {
        // unit testing
        String testStr = "BABABABABABABABABABA";
        CircularSuffixArray csa = new CircularSuffixArray(testStr);
        StdOut.println(csa.length());
        for (int i = 0; i < testStr.length(); i++) {
            StdOut.print(csa.index(i) + " ");
        }
    }
}
