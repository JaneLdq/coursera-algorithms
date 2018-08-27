package me.janeldq.algorithms.datastructures.trie;

import me.janeldq.algorithms.datastructures.queue.LinkedQueue;
import me.janeldq.algorithms.datastructures.queue.Queue;

/**
 * Ternary search tries.
 * In a TST, each node has a character, three links, and a value.
 * The three links correspond to keys whose current characters
 * are less than, equal to, or greater than the node’s character.
 *
 * In the corresponding TST, characters appear explicitly in nodes,
 * we find characters corresponding to keys only when we are
 * traversing the middle links.
 *
 */
public class TST<T> {

    private Node root;

    private int n;

    private class Node {
        char c;
        Node left, mid, right;
        T val;
    }

    public T get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length())return x;
        char c = key.charAt(d);
        if (c > x.c) return get(x.right, key, d);
        else if (c < x.c) return get(x.left, key, d);
        else if (d < key.length() - 1) return get(x.mid, key, d + 1);
        else return x;
    }

    public void put(String key, T val) {
        if (!contains(key)) n++;
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, T val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c > x.c) x.right = put(x.right, key, val, d);
        else if (c < x.c) x.left = put(x.left, key, val, d);
        else if (d < key.length() - 1) x.mid = put(x.mid, key, val, d + 1);
        else x.val = val;
        return x;
    }

    public int size() {
        return n;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Iterable<String> keys() {
        Queue<String> q = new LinkedQueue<>();
        collect(root, "", q);
        return q;
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new LinkedQueue<>();
        Node x = get(root, pre, 0);
        if (x == null) return q;
        if (x.val != null) q.enqueue(pre);
        collect(x.mid, pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre + x.c);
        collect(x.left, pre, q);
        collect(x.right, pre, q);
        collect(x.mid, pre + x.c, q);
    }

    public Iterable<String> keysThatMatch(String pat) {
        // TODO
        return null;
    }

    public String longestPrefixOf(String s) {
        // TODO
        return null;
    }

}
