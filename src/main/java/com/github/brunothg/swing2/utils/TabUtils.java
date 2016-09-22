package com.github.brunothg.swing2.utils;

import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTabbedPane;

import com.github.brunothg.swing2.widget.tab.ApplicationTab;
import com.github.brunothg.swing2.widget.tab.ClosableTabComponent;

public class TabUtils {

	@SuppressWarnings("unchecked")
	public static <T> List<T> getTabs(Class<T> type, JTabbedPane tp) {

		List<T> tabs = new LinkedList<T>();

		int tabCount = tp.getTabCount();
		for (int i = 0; i < tabCount; i++) {

			Component tab = tp.getComponentAt(i);
			if (type.isInstance(tab)) {

				tabs.add((T) tab);
			}
		}

		return tabs;
	}

	/**
	 * active = true
	 *
	 * @see #openNewTab(ApplicationTab, JTabbedPane, boolean)
	 */
	public static void openNewTab(ApplicationTab tab, JTabbedPane tp) {

		openNewTab(tab, tp, true);
	}

	/**
	 * Open new Tab for {@link JTabbedPane}.
	 * 
	 * @param active
	 *            Tells, if the new tab should be the active one
	 */
	public static void openNewTab(ApplicationTab tab, JTabbedPane tp,
			boolean active) {

		ClosableTabComponent tabComponent = new ClosableTabComponent(tp, tab);
		tabComponent.setTitle(tab.getTitle());

		tp.addTab(tab.getTitle(), tab);

		int index = tp.indexOfComponent(tab);
		tp.setTabComponentAt(index, tabComponent);

		tab.setTabComponent(tabComponent);

		if (active) {
			tp.setSelectedIndex(index);
		}
	}
}
