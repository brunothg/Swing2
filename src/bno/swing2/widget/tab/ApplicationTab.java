package bno.swing2.widget.tab;

import javax.swing.JPanel;

import bno.swing2.exception.NotClosableException;
import bno.swing2.utils.Null;

/**
 * Tab inside of {@link ApplicationTabPanel}.
 */
public abstract class ApplicationTab extends JPanel {

	private static final long serialVersionUID = 1L;

	private String title;
	private boolean closable = true;
	private ClosableTabComponent tabComponent;

	/**
	 * Closes this tab. If {@link #setTabComponent(ClosableTabComponent)} was
	 * not called, the tab can not be closed an a {@link NotClosableException}
	 * will be thrown. If {@link #isClosable()} results in false value, this
	 * call will fail either.
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
	 */
	public String getTitle() {

		return Null.nvl(title, getName());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Check if this tab is closable
	 */
	public boolean isClosable() {

		return closable;
	}

	public void setClosable(boolean closable) {

		this.closable = closable;
	}

	public ClosableTabComponent getTabComponent() {
		return tabComponent;
	}

	public void setTabComponent(ClosableTabComponent tabComponent) {
		this.tabComponent = tabComponent;
	}

	/**
	 * Is called when the tab is closed
	 */
	protected void closed() {
	}

}