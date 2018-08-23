package me.janeldq.algorithms.sort;

/**
 * Heap Sort
 * 
 * @author Jane
 *
 */
public class HeapSort implements Sort {

	public static <T extends Comparable<? super T>> void sort(T[] arr) {

		for (int i = arr.length / 2; i >= 0; i--) {
			percolateDown(arr, i, arr.length);
		}
		for (int i = arr.length - 1; i > 0; i--) {
			Util.swap(arr, 0, i);
			percolateDown(arr, 0, i);
		}
	}
	
	private static <T extends Comparable<? super T>> void percolateDown(T[] data, int index, int n) {
		int child;
		T tmp = data[index];
		for(; index * 2 + 1 < n; index = child) {
			child = index * 2 + 1;
			if (child < n - 1 && data[child + 1].compareTo(data[child]) > 0) {
				child++;
			}
			if (data[child].compareTo(tmp) > 0) {
				data[index] = data[child];
			} else {
				break;
			}
		}
		data[index] = tmp;
	}

}
