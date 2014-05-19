package bno.swing2.awt.hexcolorchooser;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

public class BHexColorChooserWidget extends JComponent {

	private static final long serialVersionUID = 1L;
	private static final String uiClassID = "BHexColorChooserWidgetUI";

	public static final String SELECTED_COLOR_CHANGED_PROPERTY = "selectedColor";

	protected Color selectedColor;

	private BHexColorChooserWidget(Color c) {
		selectedColor = c;
		updateUI();
	}

	public BHexColorChooserWidget() {
		this(Color.WHITE);
	}

	public void setUI(BHexColorChooserWidgetUI ui) {
		super.setUI(ui);
	}

	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((BHexColorChooserWidgetUI) UIManager.getUI(this));
		} else {
			setUI(new BasicBHexColorChooserWidgetUI());
		}
	}

	public BHexColorChooserWidgetUI getUI() {
		return (BHexColorChooserWidgetUI) ui;
	}

	public String getUIClassID() {
		return uiClassID;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public synchronized void addColorChangeListener(ColorChangeListener listener) {
		listenerList.add(ColorChangeListener.class, listener);
	}

	public void firePropertyChange(String name, Color oldValue, Color newValue) {
		if (name.equals(SELECTED_COLOR_CHANGED_PROPERTY)) {
			fireSelectedColorChange(oldValue, newValue);
		}
	}

	private void fireSelectedColorChange(Color oldColor, Color newColor) {
		selectedColor = newColor;
		repaint();

		ColorChangeListener[] listeners = listenerList
				.getListeners(ColorChangeListener.class);

		for (ColorChangeListener listener : listeners) {
			listener.selectedColorChanged(newColor, oldColor);
		}
	}
}
