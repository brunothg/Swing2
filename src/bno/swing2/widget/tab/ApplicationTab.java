package bno.swing2.widget.tab;

import javax.swing.JPanel;

import bno.swing2.exception.NotClosableException;
import bno.swing2.utils.Null;

/**
 * Tab innerhalb eines {@link ApplicationTabPanel}.
 */
public abstract class ApplicationTab extends JPanel {

	private static final long serialVersionUID = 1L;

	private String title;
	private boolean closable = true;
	private ClosableTabComponent tabComponent;

	/**
	 * Schließt den Tab. Wenn {@link #setTabComponent(ClosableTabComponent)}
	 * nicht aufgerufen wurde, kann der Tab nicht geschlossen werden und eine
	 * {@link NotClosableException} wird geworfen wird zurück gegeben. Wenn
	 * {@link #isClosable()} false liefert, schlägt dieser Aufrauf auch fehl.
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
	 * Der Titel des Tabs oder {@link #getName()}
	 */
	public String getTitle() {

		return Null.nvl(title, getName());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Überprüft, ob der Tab schließbar sein soll.
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
	 * Wird aufgerufen, wenn der Tab geschlossen wurde
	 */
	public void closed() {
	}

}