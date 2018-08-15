package me.janeldq.algorithms.sort;

/**
 * Fisher-Yates Shuffle
 * 
 * The basic method given for generating a random permutation of the numbers 1 through N goes as follows:
 * 1. Write down the numbers from 1 through N.
 * 2. Pick a random number k between one and the number of unstruck numbers remaining (inclusive).
 * 3. Counting from the low end, strike out the kth number not yet struck out, and write it down at the end of a separate list.
 * 4. Repeat from step 2 until all the numbers have been struck out.
 * 5. The sequence of numbers written down in step 3 is now a random permutation of the original numbers.
 *
 * Below is the modern version of the algorithm.
 * The algorithm described by Durstenfeld differs from that given by Fisher and Yates in a small but significant way.
 * Whereas a naive computer implementation of Fisher and Yates' method would spend needless time
 * counting the remaining numbers in step 3 above, Durstenfeld's solution is to move the "struck" numbers
 * to the end of the list by swapping them with the last unstruck number at each iteration.
 *
 * @author Jane
 *
 */
public class FisherYatesShuffle {

	public static <T extends Comparable<? super T>> void shuffle(T[] arr) {
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			int pos = (int) (Math.random() * (len - i));
			Util.swap(arr, pos, len-i-1);
		}
	}

}
