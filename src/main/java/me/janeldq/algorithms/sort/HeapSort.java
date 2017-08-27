package me.janeldq.algorithms.sort;

import me.janeldq.algorithms.datastructures.heap.MinHeap;

/**
 * Heap Sort
 * 
 * @author Jane
 *
 */
public class HeapSort implements Sort {

	public <T extends Comparable<? super T>> void sort(T[] arr) {
		MinHeap<T> heap = new MinHeap<T>(arr);
		for(int i = 0; i < arr.length; i++) {
			arr[i] = heap.deleteMin();
		}
	}

	public String name() {
		return "Heap Sort";
	}

}
