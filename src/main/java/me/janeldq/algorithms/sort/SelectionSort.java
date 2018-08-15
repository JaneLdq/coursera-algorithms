package me.janeldq.algorithms.sort;

/***
 * Selection Sort
 * 
 * @author Jane
 *
 */
public class SelectionSort implements Sort{

	private static SelectionSort instance;

	private SelectionSort() {}

	public static SelectionSort getInstance() {
		if (instance == null) {
			instance = new SelectionSort();
		}
		return instance;
	}

	public <T extends Comparable<? super T>> void sort(T[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int min = i;
			int j;
			for (j = i + 1; j < arr.length; j++) {
				if (arr[j].compareTo(arr[min]) < 0) {
					min = j;
				}
			}
			Util.swap(arr, min, i);
		}
	}

	public String name() {
		return "Selection Sort";
	}

}
