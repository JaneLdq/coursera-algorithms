package me.janeldq.algorithms.datastructures.stringsearch;

/**
 * Boyer-Moore substring search
 * by scanning the pattern from right to left.
 *
 * This algorithm provides a linear-time worst-case guarantee.
 *
 * On typical inputs, substring search with this algorithm uses
 * ~N/M character compares to search for a pattern of length M
 * in a text of length N.
 *
 * -----------------------------------------------------------
 * Operation count for mismatched   |
 * char heuristic only              |
 * ---------------------------------|   Extra space
 * guarantee        | typical       |
 * -----------------------------------------------------------
 * MN               | N / M         | R
 * -----------------------------------------------------------
 *
 */
public class BoyerMoore {

    private int[] right;

    private String pattern;

    private final int M;

    private static final int R = 256;

    public BoyerMoore(String pattern) {
        this.pattern = pattern;
        M = pattern.length();
        // right[] tells how far to skip if mismatch is detected
        right = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        for (int j = 0; j < M; j++) {
            right[pattern.charAt(j)] = j; // rightmost position for chars in pattern
        }
    }

    public int search(String txt) {
        int n = txt.length();
        int skip;
        for (int i = 0; i <= n - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >=0; j--) {
                if (pattern.charAt(j) != txt.charAt(i + j)) {
                    // if the character cause mismatch is not found in the patter,
                    // slide the pattern j + 1 position, right[c] = -1, skip = j + 1
                    // else if the character is found in pattern, line up the pattern to
                    // let that character match ist rightmost occurrence in the pattern.
                    skip = j - right[txt.charAt(i + j)];
                    // if skip would not increase i, however stay or skip back makes no
                    // sense (all the txt start at less then i is already checked),
                    // we just increment i instead.
                    if (skip < 1) skip = 1;
                    break;
                }
            }
            if (skip == 0) return i;
        }
        return n;
    }
}
