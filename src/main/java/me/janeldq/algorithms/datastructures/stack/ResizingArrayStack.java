package me.janeldq.algorithms.datastructures.stack;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Use resizing array to implement stack
 * @author Jane
 *
 * @param <T>
 */
public class ResizingArrayStack<T> implements Stack<T> {
	
	private static final int DEFAULT_CAPACITY = 10;
	
	private T[] items;
	
	private int top;
	
	private int size;
	
	public ResizingArrayStack() {
		this(DEFAULT_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public ResizingArrayStack(int capacity) {
		this.items = (T[]) new Object[capacity];
		top = -1;
		size = 0;
	}

	public Iterator<T> iterator() {
		return new StackIterator();
	}

	private class StackIterator implements Iterator<T> {
		
		private int current;

		public StackIterator() {
			this.current = top;
		}
		
		public boolean hasNext() {
			return current != -1;
		}

		public T next() {
			if (!hasNext()) 
				throw new NoSuchElementException();
			T item = items[current];
			current--;
			return item;
		}
	}
	
	public T peek() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		T item = items[top];
		return item;
	}

	public T pop() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		T item = items[top];
		top--;
		size--;
		if (top < this.items.length / 4 + 1) {
			this.shrink();
		}
		return item;
	}

	public void push(T t) {
		if (this.isFull()) {
			this.resize();
		}
		items[++top] = t;
		size++;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public boolean isFull() {
		return size == items.length;
	}
	
	private void resize() {
		@SuppressWarnings("unchecked")
		T[] newList = (T[]) new Object[items.length * 2];
		for (int i = 0; i < items.length; i++) {
			newList[i] = items[i];
		}
		items = newList;
	}
	
	@SuppressWarnings("unchecked")
	private void shrink() {
		T[] newList = (T[]) new Object[items.length / 2 + 1];
		for (int i = 0; i <= top; i++) {
			newList[i] = items[i];
		}
		items = newList;
	}
	
	public static void main(String[] args) {
		ResizingArrayStack<Integer> stk = new ResizingArrayStack<Integer>();
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
