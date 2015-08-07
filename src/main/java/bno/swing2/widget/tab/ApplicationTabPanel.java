package bno.swing2.widget.tab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ContainerListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import bno.swing2.exception.NotClosableException;
import bno.swing2.utils.TabUtils;

/**
 * {@link JPanel} Component for displaying {@link ApplicationTab}s
 */
public class ApplicationTabPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tpInfo;

	public ApplicationTabPanel() {

		createGui();
	}

	private void createGui() {

		setLayout(new BorderLayout());

		tpInfo = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tpInfo, BorderLayout.CENTER);
	}

	public void addTabContainerListener(ContainerListener l) {
		tpInfo.addContainerListener(l);
	}

	public void removeTabContainerListener(ContainerListener l) {
		tpInfo.removeContainerListener(l);
	}

	public void openTab(ApplicationTab tab, boolean active) {

		TabUtils.openNewTab(tab, tpInfo, active);
	}

	public void closeTab(ApplicationTab tab, boolean force)
			throws NotClosableException {

		for (int i = 0; i < tpInfo.getTabCount(); i++) {

			Component tabComponent = tpInfo.getTabComponentAt(i);

			if (tabComponent instanceof ClosableTabComponent) {
				if (tabComponent.equals(tab.getTabComponent())) {

					if (!force) {

						tab.closeTab();
						return;
					} else {

						tpInfo.remove(tab);
						return;
					}
				}
			}
		}

		throw new NotClosableException(
				"This Application tab does not own the tab that should be closed");
	}
}