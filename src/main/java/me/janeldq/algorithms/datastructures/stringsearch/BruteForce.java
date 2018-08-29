package me.janeldq.algorithms.datastructures.stringsearch;

/**
 * Brute-force substring search requires ~NM character compares to
 * search for a pattern of length M in a text of length N, in the worst case.
 */
public class BruteForce {

    public static int search(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
//        // backup implicitly
//        for (int i = 0; i <= n - m; i++) {
//            int j;
//            // In a typical text-processing application, the j index rarely increments
//            // so the running time is proportional to N.
//            // Nearly all of the compares find a mismatch with the first character of the pattern.
//            for (j = 0; j < m; j++) {
//                if (txt.charAt(i + j) != pat.charAt(j)) break;
//            }
//            if (j == m) return i;
//        }

        // backup explicitly
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (txt.charAt(i) == pat.charAt(j)) j++;
            else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) return i - m; // found
        else return n; // not found
    }
}
