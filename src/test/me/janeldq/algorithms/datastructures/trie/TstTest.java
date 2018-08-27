package me.janeldq.algorithms.datastructures.trie;

import org.junit.Assert;
import org.junit.Test;

public class TstTest {

    @Test
    public void testGetNPut() {
        TST<Integer> tst = new TST<>();
        tst.put("abc", 1);
        tst.put("aed", 2);
        tst.put("abc", 3);
        Assert.assertEquals(2, tst.size());
        Assert.assertEquals(new Integer(3), tst.get("abc"));
        Assert.assertEquals(new Integer(2), tst.get("aed"));
        Assert.assertEquals(null, tst.get("b"));
        Assert.assertEquals(true, tst.contains("abc"));
        Assert.assertEquals(false, tst.contains("d"));
    }

    @Test
    public void testKeys() {
        TST<Integer> tst = new TST<>();
        tst.put("apple", 1);
        tst.put("orange", 2);
        tst.put("lemon", 3);
        tst.put("olive", 4);
        Iterable<String> keys = tst.keys();
        for (String k: keys) {
            System.out.println(k + ", " + tst.get(k));
        }
        System.out.println();
        keys = tst.keysWithPrefix("o");
        for (String k: keys) {
            System.out.println(k + ", " + tst.get(k));
        }

        Assert.assertTrue(true);
    }

    @Test
    public void testKeysThatMatch() {
        TST<Integer> tst = new TST<>();
        tst.put("look", 1);
        tst.put("lock", 2);
        tst.put("rock", 3);
        Iterable<String> keys = tst.keysThatMatch(".o.k");
        for (String s: keys) {
            System.out.print(s + " ");
        }
        System.out.println();
        keys = tst.keysThatMatch(".ock");
        for (String s: keys) {
            System.out.print(s + " ");
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testLongestPrefix() {
        TST<Integer> tst = new TST<>();
        tst.put("she", 1);
        tst.put("shoe", 2);
        tst.put("sheep", 3);
        String pre = tst.longestPrefixOf("aow");
        Assert.assertEquals("", pre);
    }
}
