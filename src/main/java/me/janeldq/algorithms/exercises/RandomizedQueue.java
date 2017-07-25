package me.janeldq.algorithms.exercises;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Algorithms Week 2 Programming Assignment: Deques and Randomized Queues
 * A randomized queue is similar to a stack or queue, except that the item 
 * removed is chosen uniformly at random from items in the data structure.
 * 
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * 
 * 
 * @author Jane
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int n = 0;
    
    private Node first = null;
    
    private class Node {
        private Item item;
        private Node next;
    }
    
    public RandomizedQueue() {}
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        if (first != null) {
            Node current = first;
            while(current.next != null) {
                current = current.next;
            }
            current.next = node;
        } else {
            first = node;
        }
        n++;
    }
    
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(n);
        Node current = first;
        Node pre = null;
        for (int i = 1; i <= idx; i++) {
            pre = current;
            current = current.next;
        }
        Item item = current.item;
        if (pre != null) {
            pre.next = current.next;
        }
        n--;
        return item;
    }
    
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(n);
        Node current = first;
        for (int i = 1; i <= idx; i++) {
            current = current.next;
        }
        Item item = current.item;
        return item;
    }
    
    private class RandomizedIterator implements Iterator<Item> {
        private int len;
        private Item[] items;
        @SuppressWarnings("unchecked")
		public RandomizedIterator() {
            len = n;
            items = (Item[]) new Object[n];
            Node current = first;
            for (int j = 0; j < n; j++) {
                items[j] = current.item;
                current = current.next;
            }
        }
        
        public boolean hasNext() {
            return len > 0;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int idx = StdRandom.uniform(len);
            Item item = items[idx];
            items[idx] = items[len-1];
            items[len-1] = item;
            len--;
            return item;
        }
    
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }
    
    public static void main(String[] args) {}
}