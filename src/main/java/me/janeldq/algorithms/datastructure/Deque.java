package me.janeldq.algorithms.datastructure;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    
    private Node last;
    
    private int n;
    
    private class Node {
        Item item;
        private Node next;
        private Node pre;
    }
    
    public Deque() {
        n = 0;
        first = last = null;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.next = first;
        node.pre = null;
        if (first != null) {
            first.pre = node;
        }
        if (n == 0) {
            first = last = node;
        }
        n++;
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.next = null;
        node.pre = last;
        if (last != null) {
            last.next = node;
        }
        if (n == 0) {
            first = last = node;
        }
        n++;
    }
    
    public Item removeFirst() {
    	return null;
    }
    
    public Iterator<Item> iterator() {
    	return null;
    }
    
    public static void main(String[] args) {}
}