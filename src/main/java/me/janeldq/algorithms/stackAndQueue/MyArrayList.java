package me.janeldq.algorithms.stackAndQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Us array to implement list
 * @author Jane
 *
 * @param <T>
 */
public class MyArrayList<T> implements MyList<T> {
	
	private static final int DEFAULT_CAPACITY = 10;
	
	private int size;
	
	private T[] items;
	
	public MyArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public MyArrayList(int capacity) {
		size = 0;
		items = (T[]) new Object[capacity];
	}

	public int size() {
		return size;
	}

	public void trimToSize() {
		ensureCapacity(size());
	}

	public T get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return items[idx];
	}

	public T set(int idx, T newVal) {
		if (idx < 0 || idx >= size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		T old = items[idx];
		items[idx] = newVal;
		return old;
	}

	public T remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		T removed = items[idx];
		for (int i = idx; i < size-1; i++) {
			items[i] = items[i+1];
		}
		items[size-1] = null;
		size--;
		return removed;
	}

	public void add(int idx, T t) {
		if (size == items.length) {
			ensureCapacity(size * 2 + 1);
		}
		for (int i = size; i > idx; i--) {
			items[i] = items[i-1];
		}
		items[idx] = t;
		size++;		
	}

	public void add(T t) {
		add(size, t);
	}
	
	@SuppressWarnings("unchecked")
	public void ensureCapacity(int newCapacity) {
		if (newCapacity < size) {
			return;
		}
		T[] old = items;
		items = (T[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			items[i] = old[i];
		}
	}

	public Iterator<T> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<T> {
		
		private int current;
		
		public ListIterator() {
			current = 0;
		}

		public boolean hasNext() {
			if (current < size()) 
				return true;
			return false;
		}

		public T next() {
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			return (T) items[current++];
		}
		
		public void remove() {
			MyArrayList.this.remove(--current);
		}
	}
	
}
