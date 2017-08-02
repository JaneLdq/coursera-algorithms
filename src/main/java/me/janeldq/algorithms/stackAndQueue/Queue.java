package me.janeldq.algorithms.stackAndQueue;

/**
 * This interface defines the basic operations of a queue;
 * @author Jane
 *
 * @param <T>
 */
public interface Queue<T> extends Iterable<T> {
	
	void enqueue(T t);
	
	T dequeue();
	
	T peek();
	
	boolean isEmpty();
	
	int size();

}
