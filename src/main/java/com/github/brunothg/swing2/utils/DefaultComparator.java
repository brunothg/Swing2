package com.github.brunothg.swing2.utils;

import java.util.Comparator;

/**
 * compares the object's string representation
 * 
 * @author Marvin Bruns
 *
 * @param <T>
 *            Class Type
 */
public class DefaultComparator<T> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {

		return o1.toString().compareTo(o2.toString());
	}

}