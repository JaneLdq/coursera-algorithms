package me.janeldq.algorithms.stackAndQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Use linked node implements Stack
 * @author Jane
 *
 * @param <T>
 */
public class LinkedStack<T> implements Stack<T> {
	
	private Node<T> top;
	
	private int size;
	
	public LinkedStack() {
		top = null;
		size = 0;
	}
	
	private static class Node<T> {
		T item;
		Node<T> pre;
		public Node(T t, Node<T> next) {
			this.item = t;
			this.pre = next;
		}	
	}

	private class StackIterator implements Iterator<T> {
		
		private Node<T> current;

		public StackIterator() {
			current = top;
		}
		
		public boolean hasNext() {
			return current != null;
		}

		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T item = current.item;
			current = current.pre;
			return item;
		}
		
	}
	
	public Iterator<T> iterator() {
		return new StackIterator();
	}

	public T peek() {
		if (size() <= 0) {
			throw new NoSuchElementException();
		}
		T item = top.item;
		return item;
	}

	public T pop() {
		if (size() <= 0) {
			throw new NoSuchElementException();
		}
		T item = top.item;
		top = top.pre;
		size--;
		return item;
	}

	public void push(T t) {
		Node<T> newNode = new Node<T>(t, top);
		top = newNode;
		size++;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return size;
	}
	
	public static void main(String[] args) {
		LinkedStack<Integer> stk = new LinkedStack<Integer>();
		for(int i=0; i<10; i++) {
			stk.push(i);
		}
		System.out.println(stk.peek());
		System.out.println(stk.size());
		Iterator<Integer> itr = stk.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
