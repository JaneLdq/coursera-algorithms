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
        Queue<String> q = new LinkedQueue<>();
        collect(root, "", pat, 0, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, int d, Queue<String> q) {
        if (x == null) return;
        if (pat.length() == 0) return;
        char c = pat.charAt(d);
        if (c == '.' || c > x.c) collect(x.right, pre, pat, d, q);
        if (c == '.' || c < x.c) collect(x.left, pre, pat, d, q);
        if (c == '.' || c == x.c) {
            if (d == pat.length() - 1 && x.val != null) q.enqueue(pre + x.c);
            if (d < pat.length() - 1) collect(x.mid, pre + x.c, pat, d + 1, q);
        }
        return;
    }

    public String longestPrefixOf(String s) {
        int len = search(root, s, 0, 0);
        return s.substring(0, len);
    }

    private int search(Node x, String s, int d, int len) {
        if (x == null) return len;
        char c = s.charAt(d);
        if (c > x.c) len = search(x.right, s, d, len);
        else if (c < x.c) len = search(x.left, s, d, len);
        else {
            if (d == s.length() - 1) return s.length();
            return search(x.mid, s, d + 1, ++len);
        }
        return len;
    }

}
