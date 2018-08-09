package me.janeldq.algorithms.sort;

public class Util {
	
	public static final <T extends Comparable<? super T>> void swap(T[] arr, int p, int q) {
		T tmp = arr[p];
		arr[p] = arr[q];
		arr[q] = tmp;
	}
	
	public static final <T> void display(T[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
