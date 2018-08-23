package me.janeldq.algorithms.sort;

import java.lang.reflect.Method;

/***
 * Sort Test
 * 
 * @author Jane
 *
 */
public class SortTester {
	
	private static Class[] sorters = {
			InsertionSort.class,
			SelectionSort.class,
			BubbleSort.class,
			ShellSort.class,
			MergeSort.class,
			QuickSort.class,
			HeapSort.class
	};
	
	public static void main(String[] args) throws Exception {
		Integer[] arr = {0,1,2,3,4,5,6,7,8,9};
		
		for (int k = 0; k < sorters.length; k++) {
			Class clazz = sorters[k];
			System.out.println(clazz.getName() + ":");
			
			// shuffle
			FisherYatesShuffle.shuffle(arr);
			
			Util.display(arr);
			
			// sort
			Method method = clazz.getDeclaredMethod("sort", Comparable[].class);
			method.invoke(null, new Object[]{arr});
			
			System.out.println();
			Util.display(arr);
			System.out.println("\n");
		}
	}
	
}
