package me.janeldq.algorithms.sort;

/**
 * Insertion Sort
 * 
 * @author Jane
 *
 */
public class InsertionSort implements Sort {

	private static InsertionSort instance;

	private InsertionSort() {}

	public static InsertionSort getInstance() {
		if (instance == null) {
			instance = new InsertionSort();
		}
		return instance;
	}
	
	public <T extends Comparable<? super T>> void sort(T[] arr) {
		int j;
		for (int i = 1; i < arr.length; i++) {
			T tmp = arr[i];
			for (j = i; j > 0 && tmp.compareTo(arr[j-1]) < 0; j--) {
				arr[j] = arr[j-1];
			}
			arr[j] = tmp;
		}
	}
}
