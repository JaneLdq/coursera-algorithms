package me.janeldq.algorithms.sort;

/**
 * Merge Sort
 * 
 * @author Jane
 *
 */
public class MergeSort implements Sort {

	private static MergeSort instance;

	private MergeSort() {}

	public static MergeSort getInstance() {
	    if (instance == null) {
	        instance = new MergeSort();
        }
        return instance;
    }

	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> void sort(T[] arr) {
		T[] tmp = (T[]) new Comparable[arr.length];
		sort(arr, tmp, 0, arr.length-1);
	}

	public <T extends Comparable<? super T>> void sort(T[] arr, T[] tmp, int lo, int hi) {
		if (lo < hi) {
			int mid = (lo + hi) / 2;
			sort(arr, tmp, lo, mid);
			sort(arr, tmp, mid+1, hi);
			merge(arr, tmp, lo, mid+1, hi);
		}
	}
	
	private <T extends Comparable<? super T>> void merge(T[] arr, T[] tmp, int lo, int mid, int hi) {
		for (int i = lo; i <= hi; i++) {
			tmp[i] = arr[i];
		}
		int p = lo, q = mid;
		for (int j = lo; j <= hi; j++) {
			if (p >= mid) arr[j] = tmp[q++];
			else if (q > hi) arr[j] = tmp[p++];
			else if (tmp[p].compareTo(tmp[q]) < 0) {
				arr[j] = tmp[p++];
			} else {
				arr[j] = tmp[q++];
			}
		}
	}

}
