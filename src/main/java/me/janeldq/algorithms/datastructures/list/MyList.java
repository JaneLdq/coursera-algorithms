package me.janeldq.algorithms.datastructures.list;

/**
 * This interface defines the basic operations of a list
 * @author Jane
 *
 * @param <T>
 */
public interface MyList<T> extends Iterable<T> {
	
	int size();
	
	T get(int idx);
	
	T set(int idx, T newVal);
	
	T remove(int idx);
	
	void add(int idx, T t);
	
	void add(T t);
}
