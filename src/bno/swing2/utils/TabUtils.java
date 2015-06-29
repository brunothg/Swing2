package bno.swing2.utils;

import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTabbedPane;

import bno.swing2.widget.tab.ApplicationTab;
import bno.swing2.widget.tab.ClosableTabComponent;

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
	 * Öffnet einen neuen Tab in der angegebenen {@link JTabbedPane}. Active
	 * gibt an, ob der Tab der neue Sichtbare Tab werden soll.
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
