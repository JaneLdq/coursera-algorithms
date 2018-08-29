package me.janeldq.algorithms.datastructures.stringsearch;

import org.junit.Assert;
import org.junit.Test;

public class SubstringSearchTest {

    @Test
    public void testBruteForce() {
        String pat = "was";
        String txt = "Alice was beginning to get very tired of sitting by her sister";
        int index = BruteForce.search(pat, txt);
        Assert.assertEquals(6, index);
        pat = "bro";
        index = BruteForce.search(pat, txt);
        Assert.assertEquals(62, index);
    }

    @Test
    public void testKMP() {
        String pattern = "AACADA";
        String txt = "AABRAACADABRAACAADABRA";
        KMP kmp = new KMP(pattern);
        int index = kmp.search(txt);
        Assert.assertEquals(4, index);
    }

    @Test
    public void testBoyerMoore() {
        String pattern = "AACADA";
        String txt = "AABRAACADABRAACAADABRA";
        BoyerMoore bm = new BoyerMoore(pattern);
        int index = bm.search(txt);
        Assert.assertEquals(4, index);
    }

    @Test
    public void testRabinKarp() {
        String pattern = "AACADA";
        String txt = "AABRAACADABRAACAADABRA";
        RabinKarp rk = new RabinKarp(pattern);
        int index = rk.search(txt);
        Assert.assertEquals(4, index);
    }
}
