package me.janeldq.algorithms.sort;

/**
 * Quick Sort
 * 
 * @author Jane
 *
 */
public class QuickSort implements Sort {

	private static int N = 4;

	public static <T extends Comparable<? super T>> void sort(T[] arr) {
		quicksort(arr, 0, arr.length-1);
	}

	private static <T extends Comparable<? super T>> void quicksort(T[] arr, int lo, int hi) {
		if (hi - lo >= N) {
			if (lo < hi) {
				T pivot = findPivot(arr, lo, hi);
				int i = lo, j = hi-1;
				while(true) {
					while (arr[++i].compareTo(pivot) < 0) {}
					while (arr[--j].compareTo(pivot) > 0) {}
					if (i > j)
						break;
					Util.swap(arr, i, j);
				}
				Util.swap(arr, i, hi-1);
				quicksort(arr, lo, i-1);
				quicksort(arr, i+1, hi);
			}
		} else {
			insertion(arr, lo, hi);
		}
	}

	public static <T extends Comparable<? super T>> void insertion(T[] arr, int lo, int hi) {
		int j;
		for (int i = lo; i <= hi; i++) {
			T tmp = arr[i];
			for (j = i; j > lo && tmp.compareTo(arr[j-1]) < 0; j--) {
				arr[j] = arr[j-1];
			}
			arr[j] = tmp;
		}
	}
	
	private static <T extends Comparable<? super T>> T findPivot(T[] arr, int lo, int hi) {
		int mid = (lo + hi) / 2;
		if (arr[mid].compareTo(arr[lo]) < 0)
			Util.swap(arr, lo, mid);
		if (arr[hi].compareTo(arr[lo]) < 0)
			Util.swap(arr, lo, hi);
		if (arr[hi].compareTo(arr[mid]) < 0)
			Util.swap(arr, mid, hi);
		// 将倒数第二个元素与枢纽元替换，因为经过以上比较，arr[hi]一定大于枢纽元，arr[lo]一定小于枢纽元
		Util.swap(arr, mid, hi-1);
		return arr[hi-1];
	}

}
