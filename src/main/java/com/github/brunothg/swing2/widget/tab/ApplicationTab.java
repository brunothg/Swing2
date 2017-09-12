package com.github.brunothg.swing2.widget.tab;

import javax.swing.JPanel;

import com.github.brunothg.swing2.exception.NotClosableException;
import com.github.brunothg.swing2.utils.Null;

/**
 * Tab inside of {@link ApplicationTabPanel}.
 */
public class ApplicationTab extends JPanel {

	private static final long serialVersionUID = 1L;

	private boolean closable = true;
	private ClosableTabComponent tabComponent;

	private String name = "";

	/**
	 * Closes this tab. If {@link #setTabComponent(ClosableTabComponent)} was
	 * not called, the tab can not be closed an a {@link NotClosableException}
	 * will be thrown. If {@link #isClosable()} results in false value, this
	 * call will fail either.
	 * 
	 * @throws NotClosableException
	 *             if the tab cannot be closed
	 */
	public void closeTab() throws NotClosableException {

		if (!closable) {
			throw new NotClosableException(
					"This tab is set to is closable -> false");
		}

		if (tabComponent == null) {
			throw new NotClosableException("No ClosableTabComponent is set");
		}

		tabComponent.close();
	}

	/**
	 * The title of the tab or {@link #getName()}
	 * 
	 * @return The title of the tab
	 */
	public String getTitle() {

		return Null.nvl(name, getName());
	}

	/**
	 * Set the title of the tab
	 * 
	 * @param title
	 *            The new title
	 */
	public void setTitle(String title) {
		name = (Null.nvl(title, getName()));
		if (tabComponent != null) {
			tabComponent.setTitle(name);
		}
	}

	/**
	 * Check if this tab is closable
	 * 
	 * @return true, if this tab can be closed
	 */
	public boolean isClosable() {

		return closable;
	}

	public void setClosable(boolean closable) {

		this.closable = closable;

		if (tabComponent != null) {
			tabComponent.setClosable(closable);
		}
	}

	public ClosableTabComponent getTabComponent() {
		return tabComponent;
	}

	public void setTabComponent(ClosableTabComponent tabComponent) {
		this.tabComponent = tabComponent;
		tabComponent.setClosable(isClosable());
		tabComponent.setTitle(getTitle());
	}

}