package me.janeldq.algorithms.exercises;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Algorithms Week 2 Programming Assignment: Deques and Randomized Queues
 * Deque is a double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue 
 * that supports adding and removing items from either the front or the back of the data structure.
 * 
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * 
 * @author Jane
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    
    private Node last;
    
    private int n;
    
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
            last = node;
        }
        first = node;
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
            first =  node;
        }
        last = node;
        n++;
    }
    
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (n == 1) {
            first = last = null;
        } else {
            Node newFirst = first.next;
            newFirst.pre = null;
            first = newFirst;
        }
        n--;
        return item;
    }
    
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (n == 1) {
            first = last = null;
        } else {
            Node newLast = last.pre;
            newLast.next = null;
            last = newLast;
        }
        n--;
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class Node {
        Item item;
        private Node next;
        private Node pre;
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public DequeIterator() {}
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {}
}