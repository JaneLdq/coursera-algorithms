package me.janeldq.algorithms.sort;

/**
 * 3-way Radix Quicksort
 * Use first character to partition into "less", "equal", "greater subarrays,
 * recursively sort subarrays, excluding first character for middle subarray
 * (does not re-examine characters equal to the partitioning char)
 *
 *
 * 3-way string quicksort vs. MSD string sort
 *
 * MSD:     is cache-inefficient
 *          too much memory storing count[]
 *          too much overhead reinitializing count[] and aux[]
 *
 * 3-way:   has a short inner loop
 *          is cache-friendly (most of the time, it's partitioning the same way that normal quicksort is)
 *          is in-place
 *
 */
public class Quick3WayStringSort {

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    /**
     * 3-way partitioning using dth character
     *
     * @param a array of strings
     * @param lo start index in array
     * @param hi end index in array
     * @param d using dth character
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (lo >= hi) return;
        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exch(a, lt++, i++);
            else if (t > v)  exch(a, i, gt--);
            else i++;
        }
        // a[lo .. lt - 1] < v = a[lt .. gt] < a[gt + 1 .. hi]
        sort(a, lo, lt - 1, d);
        if (v >= 0) sort(a, lt, gt, d + 1);
        sort(a, gt + 1, hi, d);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        return -1;
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
