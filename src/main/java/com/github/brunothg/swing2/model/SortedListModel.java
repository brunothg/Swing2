package com.github.brunothg.swing2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.SortOrder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.github.brunothg.swing2.utils.DefaultComparator;
import com.github.brunothg.swing2.utils.Null;

public class SortedListModel<E> extends DefaultListModel<E> implements
		ListDataListener {

	private static final long serialVersionUID = 1L;

	private SortOrder sortOrder;
	private Comparator<E> comparator;

	private boolean sorting = false;

	public SortedListModel(SortOrder sortOrder, Comparator<E> comparator) {

		this.sortOrder = Null.nvl(sortOrder, SortOrder.UNSORTED);
		this.comparator = Null.nvl(comparator, new DefaultComparator<E>());

		addListDataListener(this);
	}

	@Override
	public void intervalAdded(ListDataEvent e) {

		if (sortOrder != SortOrder.UNSORTED) {

			sort();
		}
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
	}

	@Override
	public void contentsChanged(ListDataEvent e) {

		if (sortOrder != SortOrder.UNSORTED) {

			sort();
		}
	}

	private void sort() {

		if (sorting || sortOrder == SortOrder.UNSORTED) {
			return;
		}

		sorting = true;

		List<E> elements = new ArrayList<E>(getSize());

		for (int i = 0; i < getSize(); i++) {

			elements.add(getElementAt(i));
		}

		Collections.sort(elements, comparator);

		if (sortOrder == SortOrder.ASCENDING) {
			Collections.reverse(elements);
		}

		clear();

		for (E element : elements) {

			add(0, element);
		}

		sorting = false;
	}

}