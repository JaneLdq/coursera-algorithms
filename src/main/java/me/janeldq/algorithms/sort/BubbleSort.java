package me.janeldq.algorithms.sort;

/**
 * BubbleSort
 * 
 * @author Jane
 *
 */
public class BubbleSort implements Sort {

	private static BubbleSort instance;

	private BubbleSort() {}

	public static BubbleSort getInstance() {
		if (instance == null) {
			instance = new BubbleSort();
		}
		return instance;
	}

	public <T extends Comparable<? super T>> void sort(T[] arr) {
		for (int i = arr.length-1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (arr[j].compareTo(arr[j+1]) > 0) {
					Util.swap(arr, j, j+1);
				}
			}
		}
	}

}
