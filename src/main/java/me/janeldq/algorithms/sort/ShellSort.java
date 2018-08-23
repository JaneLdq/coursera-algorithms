package me.janeldq.algorithms.sort;
/***
 * Shellsort
 * 
 * @author Jane
 *
 */
public class ShellSort implements Sort{

	public static <T extends Comparable<? super T>> void sort(T[] arr) {
		int h = arr.length / 2;
		while(h >= 1) {
			for (int i = h; i < arr.length; i++) {
				// using insertion sort to do h-sorting
				for (int j = i; j >= h && arr[j].compareTo(arr[j-h]) < 0; j-=h) {
					Util.swap(arr, j, j-h);
				}
			}
			h = h / 2;
		}
	}

}
