package bno.swing2.widget.colorchooser.hexcolorchooser;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

import bno.swing2.widget.BColor;
import bno.swing2.widget.ColorChangeEvent;
import bno.swing2.widget.ColorChangeListener;

public class BHexColorChooserWidget extends JComponent {

	private static final long serialVersionUID = 2L;
	private static final String uiClassID = "BHexColorChooserWidgetUI";

	public static final String SELECTED_COLOR_CHANGED_PROPERTY = "selectedColor";
	public static final String MOUSE_OVER_COLOR_CHANGED_PROPERTY = "mouseOverColor";

	protected BColor selectedColor;
	protected BColor mouseOverColor;

	private BHexColorChooserWidget(BColor c) {
		selectedColor = c;
		mouseOverColor = null;
		updateUI();
	}

	public BHexColorChooserWidget() {
		this(new BColor(Color.WHITE));
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

	public BColor getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(final BColor c) {
		selectedColor = c;
		repaint();
	}

	public BColor getMouseOverColor() {
		return mouseOverColor;
	}

	public synchronized void addColorChangeListener(ColorChangeListener listener) {
		listenerList.add(ColorChangeListener.class, listener);
	}

	public void firePropertyChange(String propertyName, BColor oldValue,
			BColor newValue) {
		if (propertyName == null) {
			return;
		}

		if (propertyName.equals(SELECTED_COLOR_CHANGED_PROPERTY)) {
			fireSelectedColorChange(oldValue, newValue);
		} else if (propertyName.equals(MOUSE_OVER_COLOR_CHANGED_PROPERTY)) {
			fireMouseOverColorChange(oldValue, newValue);
		}
	}

	private void fireMouseOverColorChange(BColor oldValue, BColor newValue) {
		mouseOverColor = newValue;

		ColorChangeListener[] listeners = listenerList
				.getListeners(ColorChangeListener.class);

		ColorChangeEvent event = new ColorChangeEvent(this, oldValue, newValue);

		for (ColorChangeListener listener : listeners) {
			listener.mouseOverColorChanged(event);
		}
	}

	private void fireSelectedColorChange(BColor oldColor, BColor newColor) {
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
