package me.janeldq.algorithms.sort;

/**
 * Least-significant-digit-first string sort
 * (examining the characters in a right-to-left order)
 *
 * LSD stably sorts fixed-length strings based on the fact that
 * the key-indexed counting implementation being stable.
 *
 * Applicable situation: telephone numbers, bank account numbers,
 * IP addresses and so forth.
 *
 */
public class LSDStringSort {

    /**
     * LSD string sort
     * @param a array of strings
     * @param W fixed-length W of string
     */
    public static void sort(String[] a, int W) {
        // radix R - extended ascii character
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R+1];

            // compute frequency counts
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }

            // sum the frequency counts of smaller values to get
            // the starting index for item with any given key value
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            // distribute the data:
            // for each key value r, count[r] is the index of the position
            // in aux[] where the net item with key value r should be placed
            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            // copy back
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }

}
