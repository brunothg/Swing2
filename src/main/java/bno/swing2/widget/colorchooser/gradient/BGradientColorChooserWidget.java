package bno.swing2.widget.colorchooser.gradient;

import javax.swing.JComponent;
import javax.swing.UIManager;

import bno.swing2.BColor;
import bno.swing2.widget.colorchooser.ColorChangeEvent;
import bno.swing2.widget.colorchooser.ColorChangeListener;

public class BGradientColorChooserWidget extends JComponent {

	private static final long serialVersionUID = 1L;
	private static final String uiClassID = "BGradientColorChooserWidgetUI";

	public static final String SELECTED_COLOR_CHANGED_PROPERTY = "selectedColor";
	public static final String MOUSE_OVER_COLOR_CHANGED_PROPERTY = "mouseOverColor";

	public static final int X_AXIS = 0x01;
	public static final int Y_AXIS = 0x02;

	private BColor color;
	private int orientation;
	private int max_sub;

	private BColor selectedColor;
	private BColor mouseOverColor;

	public BGradientColorChooserWidget(BColor c, int orientation) {
		this.color = c;
		this.selectedColor = this.color;
		this.orientation = orientation;
		this.max_sub = 21;
		updateUI();
	}

	public BGradientColorChooserWidget(BColor c) {
		this(c, Y_AXIS);
	}

	public void setUI(BGradientColorChooserWidgetUI ui) {
		super.setUI(ui);
	}

	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((BGradientColorChooserWidgetUI) UIManager.getUI(this));
		} else {
			setUI(new BasicBGradientColorChooserWidgetUI());
		}
	}

	public synchronized void addColorChangeListener(ColorChangeListener listener) {
		listenerList.add(ColorChangeListener.class, listener);
	}

	public BGradientColorChooserWidgetUI getUI() {
		return (BGradientColorChooserWidgetUI) ui;
	}

	public String getUIClassID() {
		return uiClassID;
	}

	public BColor getColor() {
		return color;
	}

	public void setColor(final BColor c) {
		this.color = c;

		repaint();
	}

	public int getOrientation() {
		return this.orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;

		revalidate();
		repaint();
	}

	public int getMaximumSubdivisions() {
		return max_sub;
	}

	public void setMaximumSubdivisions(int subs) {
		if (subs < 1) {
			return;
		}

		this.max_sub = subs;

		revalidate();
		repaint();
	}

	public BColor getSelectedColor() {
		return this.selectedColor;
	}

	public BColor getMouseOverColor() {
		return this.mouseOverColor;
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

	private void fireSelectedColorChange(BColor oldValue, BColor newValue) {
		selectedColor = newValue;

		ColorChangeListener[] listeners = listenerList
				.getListeners(ColorChangeListener.class);

		ColorChangeEvent event = new ColorChangeEvent(this, oldValue, newValue);

		for (ColorChangeListener listener : listeners) {
			listener.selectedColorChanged(event);
		}
	}
}
