package me.janeldq.algorithms.sort;

/**
 * Least-significant-digit-first string sort
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
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }
            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args) {
        String[] testArr = new String[] {
          "abc", "cea", "abe", "etd", "ehc", "ade", "etg", "gak", "eqk"
        };
        LSDStringSort.sort(testArr, 3);
        for (String s: testArr) {
            System.out.println(s);
        }
    }
}
