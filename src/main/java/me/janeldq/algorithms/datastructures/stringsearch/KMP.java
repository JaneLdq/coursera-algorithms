package me.janeldq.algorithms.datastructures.stringsearch;

/**
 * Knuth-Morris-Pratt substring search
 *
 * Basic idea: whenever we detect a mismatch, we already know some of
 * the characters in the text. Then we can take advantage of this
 * information to avoid backing up.
 *
 * How?
 *      1. never back up the text pointer i
 *      2. use an array dfa[][] to record how far to backup the pattern j
 *      when a mismatch is detected.
 *
 * KMP search accesses no more than M + N characters to search for a
 * pattern of length M in a text of length N.
 *
 */
public class KMP {

    /**
     * Alphabet Extended-ASCII
     */
    private static final int R = 256;

    /**
     * Deterministic finite-state automation(DFA)
     *
     * For every character c, dfa[c][j] is the pattern position to compare
     * against the next text position after comparing c with pat.charAt[j]
     * For a match, we want to just move on to the next character,
     * so dfa[pat.charAt(j)][j] is always j+1. (move RIGHT)
     * For a mismatch, backup is length of max overlap of beginning of pattern
     * with known text chars. The index of the pattern character to compare
     * with txt.charAt(i+1) (dfa[txt.charAt(i)][j]) is precisely the number
     * of overlapping characters. (move LEFT)
     *
     * Start the machine at state 0: if the machine reaches state M, then a
     * substring of the text matching the pattern has been found.
     *
     */
    private final int[][] dfa;

    private final String pattern;

    private final int M;

    public KMP(String pattern) {
        this.pattern = pattern;
        M = pattern.length();
        // construct dfa
        dfa = new int[R][M];
        dfa[pattern.charAt(0)][0] = 1;
        // X = restart position when working on column j of dfa[][]
        // the next value of X is dfa[pat.charAt(j)][X]
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++) {
                // copy mismatch cases
                dfa[c][j] = dfa[c][X];
            }
            // set match case
            dfa[pattern.charAt(j)][j] = j + 1;
            // update X
            X = dfa[pattern.charAt(j)][X];
        }
    }

    public int search(String txt) {
        int i, j, n = txt.length();
        for (i = 0, j = 0; i < n && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == M) return i - M;
        else return n;
    }
}
