package me.janeldq.algorithms.sort;

public interface Sort {
	
	public <T extends Comparable<? super T>> void sort(T[] arr);
	
	public String name();

}
