package me.janeldq.algorithms.datastructures.trie;

import me.janeldq.algorithms.datastructures.queue.LinkedQueue;
import me.janeldq.algorithms.datastructures.queue.Queue;

/**
 * Tries are data structures composed of nodes that contain links
 * that are either null or references to other nodes.
 * Each node is pointed to by just one other node, which is called
 * its parent (except for the root), and each node has R links,
 * where R is the alphabet size.
 *
 * Often, tries have a substantial number of null links. Nodes with
 * null values exist to facilitate search in the trie and do not
 * correspond to keys.
 *
 * NOTE: Do NOT try to use this implementation(R-way tries) for large numbers of
 * long keys taken from large alphabets, Because it will require space
 * proportional to R times the total number of key characters.
 *
 * @param <T> type of value
 */
public class TrieST<T> {

    private static int R = 256; // radix = extend_ascii

    private Node root;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    /**
     * Return the value associated with the given key,
     * if not found, return null
     */
    public T get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (T)x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length())return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    /**
     * Put a value with given key
     */
    public void put(String key, T val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, T val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    /**
     * Return the size of the trie
     */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        int count = 0;
        if (x.val != null) count++;
        for (char c = 0; c < R; c++) {
            count += size(x.next[c]);
        }
        return count;
    }

    /**
     * Return true if the trie contains the specified key
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Delete the specified key
     */
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        if (x.val != null) return x;
        for (char c = 0; c < R; c++) {
            // if node x has a non-null link to child, no more work is required
            if (x.next[c] != null) return x;
        }
        // remove x if all the child links are null
        return null;
    }

    /**
     * Keys are represented implicitly in tries, so we need to create
     * explicit representations of all string keys.
     */
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new LinkedQueue<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre + c, q);
        }
    }

    /**
     * For wildcard, only consider '.'
     *
     */
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new LinkedQueue<>();
        collect(root, "", pat, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, Queue<String> q) {
        if (x == null) return;
        int d = pre.length();
        if (d == pat.length() && x.val != null) q.enqueue(pre);
        // no need to consider keys longer than pattern
        if (d == pat.length()) return;
        char next = pat.charAt(d);
        for (char c = 0; c < R; c++) {
            if (next == '.' || next == c) {
                collect(x.next[c], pre + c, pat, q);
            }
        }
    }

    /**
     * To find the longest key that is a prefix of a given string
     */
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d + 1, length);
    }

}
