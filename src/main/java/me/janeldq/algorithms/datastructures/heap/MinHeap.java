package me.janeldq.algorithms.datastructures.heap;

public class MinHeap<T extends Comparable<? super T>>{
	
	private final static int CAPACITY = 10;
	
	private T[] data;
	
	private int size;
	
	@SuppressWarnings("unchecked")
	public MinHeap() {
		size = 0;
		this.data = (T[]) new Comparable[CAPACITY];
	}
	
	@SuppressWarnings("unchecked")
	public MinHeap(T[] data) {
		size = data.length;
		this.data = (T[]) new Comparable[size * 2 + 1];
		int i = 1;
		for (T item : data) {
			this.data[i++] = item;
		}
		buildHeap();
	}
	
	private void buildHeap() {
		for(int i = size/2; i>0; i--) {
			percolateDown(i);
		}
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	@SuppressWarnings("unchecked")
	public void resizeArray() {
		if (size == data.length - 1) {
			T[] newArray = (T[]) new Comparable[data.length * 2 + 1];
			for (int i = 1; i < data.length; i++) {
				newArray[i] = data[i];
			}
			data = newArray;
		}
	}
	
	public void insert(T item) {
		this.resizeArray();
		data[++size] = item;
		percolateUp(size);
	}
	
	public T findMin() {
		return data[1];
	}
	
	public T deleteMin() {
		T min = findMin();
		// 将最后一个元素移入空穴，然后做下滤
		data[1] = data[size--];
		percolateDown(1);
		return min;
	}
	
	private void percolateDown(int index) {
		int child;
		T tmp = data[index];
		for(; index * 2 <= size; index = child) {
			child = index * 2;
			if (child != size && data[child + 1].compareTo(data[child]) < 0) {
				child++;
			}
			if (data[child].compareTo(tmp) < 0) {
				data[index] = data[child];
			} else {
				break;
			}
		}
		data[index] = tmp;
	}

	private void percolateUp(int index) {
		int parent;
		T tmp = data[index];
		for(; index >1; index = parent) {
			parent = index / 2;
			if (data[parent].compareTo(data[index]) > 0) {
				data[index] = data[parent];
			} else {
				break;
			}
		}
		data[index] = tmp;
	}
}
