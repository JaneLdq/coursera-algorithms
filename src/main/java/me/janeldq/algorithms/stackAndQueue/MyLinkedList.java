package me.janeldq.algorithms.stackAndQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Use linked nodes to implement list
 * @author Jane
 *
 * @param <T>
 */
public class MyLinkedList<T> implements MyList<T> {
	
	private int size;
	
	private Node<T> begin;
	
	private Node<T> end;
	
	public MyLinkedList() {
		size = 0;
		begin = new Node<T>(null, null, null);
		end = new Node<T>(null, null, begin);
		begin.next = end;
	}
	
	private static class Node<T> {
		public T item;
		public Node<T> next;
		public Node<T> pre;
		public Node(T item, Node<T> next, Node<T> pre) {
			this.item = item;
			this.next = next;
			this.pre = pre;
		}
	}

	private class ListIterator implements Iterator<T> {
		
		private Node<T> current;
		
		public ListIterator() {
			current = begin;
		}

		public boolean hasNext() {
			if (current.next != null && current.next.next != null)
				return true;
			return false;
		}

		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T item = current.next.item;
			current = current.next;
			return item;
		}
		
	}
	
	public Iterator<T> iterator() {
		return new ListIterator();
	}

	public int size() {
		return size;
	}

	public T get(int idx) {
		return getNode(idx).item;
	}
	
	private Node<T> getNode(int idx) {
		return getNode(idx, 0, size()-1);
	}
	
	private Node<T> getNode(int idx, int lo, int hi) {
		Node<T> p;
		if (idx < lo || idx > hi) {
			throw new IndexOutOfBoundsException();
		}
		if (idx < hi/2) {
			p = begin;
			for (int i = 0; i <= idx; i++) {
				p = p.next;
			}
		} else {
			p = end;
			for (int i = hi; i >= idx; i--) {
				p = p.pre;
			}
		}
		return p;
	}

	public T set(int idx, T newVal) {
		Node<T> p = this.getNode(idx);
		T old = p.item;
		p.item = newVal;
		return old;
	}

	public T remove(int idx) {
		Node<T> p = getNode(idx);
		T item = p.item;
		if (p.pre != null) {
			p.pre.next = p.next;
		}
		if (p.next != null) {
			p.next.pre = p.pre;
		}
		size--;
		return item;
	}

	public void add(int idx, T t) {
		Node<T> p = getNode(idx);
		Node<T> newNode = new Node<T>(t, p, p.pre);
		if (p.pre != null) {
			p.pre.next = newNode;
		}
		p.pre = newNode;
		size++;
	}

	public void add(T t) {
		Node<T> newNode = new Node<T>(t, end, end.pre);
		end.pre.next = newNode;
		end.pre = newNode;
		size++;
	}

}
