package me.janeldq.algorithms.datastructures.queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue<T> implements Queue<T> {
	
	private Node<T> first;
	
	private Node<T> last;
	
	private int size;
	
	public LinkedQueue() {
		this.first = this.last = null;
		size = 0;
	}
	
	private static class Node<T> {
		T item;
		Node<T> next;
		public Node(T t, Node<T> next) {
			this.next = next;
			this.item = t;
		}
	}

	public void enqueue(T t) {
		Node<T> newNode = new Node<T>(t, null);
		if (last != null) { 
			last.next = newNode;
			last = newNode;
		} else {
			first = last = newNode;
		}
		size++;
	}

	public T dequeue() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		T item = first.item;
		first = first.next;
		size--;
		return item;
	}

	public T peek() {
		if (this.isEmpty()) 
			throw new NoSuchElementException();
		return last.item;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public Iterator<T> iterator() {
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<T> {

		private Node<T> current;
		
		public QueueIterator() {
			current = first;
		}
		
		public boolean hasNext() {
			return current != null;
		}

		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}	
	}
	
	
	public static void main(String[] args) {
		LinkedQueue<Integer> q = new LinkedQueue<Integer>();
		for(int i=0; i<10; i++) {
			q.enqueue(i);
		}
		System.out.println("peek: " + q.peek());
		System.out.println("size: " + q.size());
		q.enqueue(30);
		Iterator<Integer> itr = q.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next() + " ");
		}
		System.out.println();
		System.out.println("dequeue " + q.dequeue());
		while(!q.isEmpty()) {
			System.out.print(" " + q.dequeue());
		}
	}

}
