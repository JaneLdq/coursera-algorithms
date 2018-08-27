package me.janeldq.algorithms.datastructures.trie;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TrieTest {

    @Test
    public void testPutNGet() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("abc", 1);
        trie.put("acd", 2);
        Assert.assertEquals(2, trie.size());
        Assert.assertEquals(new Integer(1), trie.get("abc"));
        Assert.assertEquals(new Integer(2), trie.get("acd"));
        Assert.assertEquals(null, trie.get("ccc"));
        Assert.assertEquals(true, trie.contains("abc"));
        Assert.assertEquals(false, trie.contains("ed"));
    }

    @Test
    public void testDelete() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("abc", 1);
        trie.put("acd", 2);
        trie.delete("abc");
        Assert.assertEquals(null, trie.get("abc"));
        Assert.assertEquals(1, trie.size());
    }

    @Test
    public void testKeys() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("abc", 1);
        trie.put("acd", 2);
        Iterable<String> keys = trie.keys();
        Set<String> keySet = new HashSet<>();
        for(String k: keys) keySet.add(k);
        Assert.assertEquals(2, keySet.size());
    }

    @Test
    public void testKeyWithPrefix() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("she", 1);
        trie.put("should", 3);
        trie.put("shell", 2);
        Iterable<String> keys = trie.keysWithPrefix("she");
        Set<String> keySet = new HashSet<>();
        for(String k: keys) keySet.add(k);
        Assert.assertEquals(new HashSet<String>() {
            {add("she"); add("shell");}
        }, keySet);
    }

    @Test
    public void testKeyThatMatch() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("shot", 1);
        trie.put("shoe", 2);
        trie.put("show", 3);
        Iterable<String> keys = trie.keysThatMatch("sho.");
        Set<String> keySet = new HashSet<>();
        for(String k: keys) {
            keySet.add(k);
        }
        Assert.assertEquals(new HashSet<String>() {
            {add("shot"); add("shoe"); add("show");}
        }, keySet);
    }

    @Test
    public void testLongestPrefixOf() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("olive", 1);
        trie.put("ol", 2);
        String prefix = trie.longestPrefixOf("olympics");
        Assert.assertEquals("ol", prefix);
    }
}
