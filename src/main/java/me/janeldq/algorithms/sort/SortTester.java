package me.janeldq.algorithms.sort;

/***
 * Sort Test
 * 
 * @author Jane
 *
 */
public class SortTester {
	
	private static Sort[] sorters = {
			new InsertionSort(),
			new SelectionSort(),
			new BubbleSort(),
			new ShellSort(),
			new MergeSort(),
			new QuickSort(),
			new HeapSort()
	};
	
	public static void main(String[] args) {
		Integer[] arr = {0,1,2,3,4,5,6,7,8,9};
		
		for (int k = 0; k < sorters.length; k++) {
			Sort sorter = sorters[k];
			System.out.println(sorter.name() + ":");
			
			// shuffle
			FisherYatesShuffle.shuffle(arr);
			
			Util.display(arr);
			
			// sort
			sorter.sort(arr);
			
			System.out.println();
			Util.display(arr);
			System.out.println("\n");
		}
	}
	
}
