package me.janeldq.algorithms.sort;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Sort {
	
	static <T extends Comparable<? super T>> void sort(T[] arr) {
	    throw new NotImplementedException();
	}

}
