package me.janeldq.algorithms.stackAndQueue;

/**
 * This interface defines the basic operations of a stack
 * @author Jane
 *
 * @param <T>
 */
public interface Stack<T> extends Iterable<T> {

	T peek();
	
	T pop();
	
	void push(T t);
	
	boolean isEmpty();
	
	int size();
	
}
