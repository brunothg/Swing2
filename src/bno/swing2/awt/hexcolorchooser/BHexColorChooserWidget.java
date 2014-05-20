package bno.swing2.awt.hexcolorchooser;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

import bno.swing2.awt.ColorChangeEvent;
import bno.swing2.awt.ColorChangeListener;

public class BHexColorChooserWidget extends JComponent {

	private static final long serialVersionUID = 2L;
	private static final String uiClassID = "BHexColorChooserWidgetUI";

	public static final String SELECTED_COLOR_CHANGED_PROPERTY = "selectedColor";
	public static final String MOUSE_OVER_COLOR_CHANGED_PROPERTY = "mouseOverColor";

	protected Color selectedColor;
	protected Color mouseOverColor;

	private BHexColorChooserWidget(Color c) {
		selectedColor = c;
		mouseOverColor = null;
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

	public void setSelectedColor(final Color c) {
		Color selectedColorT = selectedColor;
		selectedColor = c;

		fireSelectedColorChange(selectedColorT, c);
	}

	public Color getMouseOverColor() {
		return mouseOverColor;
	}

	public synchronized void addColorChangeListener(ColorChangeListener listener) {
		listenerList.add(ColorChangeListener.class, listener);
	}

	public void firePropertyChange(String propertyName, Color oldValue,
			Color newValue) {
		if (propertyName == null) {
			return;
		}

		if (propertyName.equals(SELECTED_COLOR_CHANGED_PROPERTY)) {
			fireSelectedColorChange(oldValue, newValue);
		} else if (propertyName.equals(MOUSE_OVER_COLOR_CHANGED_PROPERTY)) {
			fireMouseOverColorChange(oldValue, newValue);
		}
	}

	private void fireMouseOverColorChange(Color oldValue, Color newValue) {
		mouseOverColor = newValue;

		ColorChangeListener[] listeners = listenerList
				.getListeners(ColorChangeListener.class);

		ColorChangeEvent event = new ColorChangeEvent(this, oldValue, newValue);

		for (ColorChangeListener listener : listeners) {
			listener.mouseOverColorChanged(event);
		}
	}

	private void fireSelectedColorChange(Color oldColor, Color newColor) {
		selectedColor = newColor;
		repaint();

		ColorChangeListener[] listeners = listenerList
				.getListeners(ColorChangeListener.class);

		ColorChangeEvent event = new ColorChangeEvent(this, oldColor, newColor);

		for (ColorChangeListener listener : listeners) {
			listener.selectedColorChanged(event);
		}
	}
}
