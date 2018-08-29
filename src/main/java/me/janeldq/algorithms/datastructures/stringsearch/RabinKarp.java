package me.janeldq.algorithms.datastructures.stringsearch;

import java.math.BigInteger;
import java.util.Random;

/**
 * Rabin-Karp fingerprint search
 *
 */
public class RabinKarp {

    private static final int R = 256;

    private final String pattern;

    private final long patternHash;

    private final int M;

    private final long Q;

    private long RM; // R^(M-1) % Q

    public RabinKarp(String pattern) {
        this.pattern = pattern;
        M = pattern.length();
        Q = longRandomPrime();
        RM = 1;
        for (int i = 1; i <= M - 1; i++) {
            RM = (R * RM) % Q;
        }
        patternHash = hash(pattern, M);
    }

    public int search(String txt) {
        int n = txt.length();
        long txtHash = hash(txt, M);
        if (patternHash == txtHash) return 0;
        for (int i = M; i < n; i++) {
            // remove leading digit
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            // add trailing digit
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            // check for match
            if (patternHash == txtHash) {
                if (check(txt, i - M + 1)) return i - M + 1;
            }
        }
        return n;
    }

    /**
     * Compute hash for key [0, M-1]
     * (computing the hash function for an M-digit base-R number represented
     * as a char array in time proportional M
     *
     */
    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++) {
            h = (R * h + key.charAt(j)) % Q;
        }
        return h;
    }

    /**
     * Monte Carlo version - no check
     * Las Vegas version - check pattern vs txt[i, i - M + 1]
     * @param i
     * @return
     */
    public boolean check(String txt, int i) {
        // Las Vegas
        for (int j = 0; j < M; j++) {
            if (pattern.charAt(j) != txt.charAt(i + j)) return false;
        }
        return true;  // Monte Carlo
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

}
