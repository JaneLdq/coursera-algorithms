package me.janeldq.algorithms.assignments.boggle;

import java.util.HashMap;
import java.util.Map;

public class MyTrieST<T> {

    private static int R = 26; // radix = extend_ascii

    private Node root;

    private Map<String, Node> prefixNodes;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public MyTrieST() {
        this.prefixNodes = new HashMap<>();
    }

    public T get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (T)x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length())return x;
        int c = key.charAt(d) - 65;
        return get(x.next[c], key, d + 1);
    }

    public void put(String key, T val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, T val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        int c = key.charAt(d) - 65;
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public boolean keysWithPrefix(String pre) {
        Node x = prefixNodes.get(pre);
        if (x != null) return true;
        if (pre.length() > 0) {
            x = prefixNodes.get(pre.substring(0, pre.length() - 1));
            if (x != null) return collect(x.next[pre.charAt(pre.length()-1)-65], pre);
        }
        return collect(get(root, pre, 0), pre);
    }

    private boolean collect(Node x, String pre) {
        if (x == null) return false;
        if (x.val != null) {
            prefixNodes.put(pre, x);
            return true;
        }
        boolean found = false;
        for (char c = 0; c < R; c++) {
            found = collect(x.next[c], pre + c);
            if (found) return found;
        }
        return found;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

}
