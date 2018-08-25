package me.janeldq.algorithms.sort;

/**
 * Most-significant-digit-first string sort
 *
 * MSD is much slower for small subarrays, so cutoff to insertion sort
 * for small subarrays. Why?
 *
 *      Each string eventually finds its way to its own subarray, so you
 *      will sort lots of subarrays of size 1, but each such sort involves
 *      initializing the R entries of count[] array to 0 and transforming
 *      them all to indices.
 *
 *      So the switch to insertion sort for small subarrays is necessary.
 *
 * Equal keys is the second pitfall for MSD.
 *      A worst-case input is one with all strings equal.
 *
 */
public class MSDStringSort {

    private static final int R = 256;

    private static final int CUTOFF = 15;

    public static void sort(String[] a) {
        // put the aux[] out of the recursion reduce extra space but sacrifice stability
        String[] aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) {

        if (hi <= lo) return;

        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        // the main defect: each recursion use a new count[] array
        // since we use -1 to represent the end of a string, this convention means
        // that we have R + 1 different possible character values at each string position:
        // 0 to signify end of string, 1 for the first alphabet character and so forth.
        // So the length of count[] should be R + 2
        int[] count = new int[R + 2];
        for (int i =lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }
        for (int r = 0; r < R+1; r++) {
            count[r+1] += count[r];
        }
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }
        // recursively sort for each character value
        for (int r = 0; r < R; r++) {
            sort(a, aux, lo + count[r], lo + count[r+1] - 1, d + 1);
        }
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        return -1;
    }

    /**
     * To avoid the cost of reexamining characters that we know to be equal,
     * this insertion sort takes an extra argument d and assumes that the
     * first d characters of all the strings to be sorted are known to be equal.
     */
    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--) {
                exch(a, j, j-1);
            }
        }
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    private static boolean less(String v, String w, int d) {
        // in Java, forming and comparing substrings is faster than directly comparing chars with charAt()
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

}
