package bno.swing2.awt.Gradient;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

import bno.swing2.awt.ColorChangeListener;

public class BGradientColorChooserWidget extends JComponent {

	private static final long serialVersionUID = 1L;
	private static final String uiClassID = "BGradientColorChooserWidgetUI";

	public static final String SELECTED_COLOR_CHANGED_PROPERTY = "selectedColor";
	public static final String MOUSE_OVER_COLOR_CHANGED_PROPERTY = "mouseOverColor";

	public static final int X_AXIS = 0x01;
	public static final int Y_AXIS = 0x02;

	private Color color;
	private int orientation;
	private int max_sub;

	private Color selectedColor;
	private Color mouseOverColor;

	public BGradientColorChooserWidget(Color c, int orientation) {
		this.color = c;
		this.selectedColor = this.color;
		this.orientation = orientation;
		this.max_sub = 21;
		updateUI();
	}

	public BGradientColorChooserWidget(Color c) {
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

	public Color getColor() {
		return color;
	}

	public void setColor(final Color c) {
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

	public Color getSelectedColor() {
		return this.selectedColor;
	}

	public Color getMouseOverColor() {
		return this.mouseOverColor;
	}

	public void firePropertyChange(String propertyName, Color oldValue,
			Color newValue) {
		if (propertyName == null) {
			return;
		}

		// TODO: fire Color Change
		if (propertyName.equals(SELECTED_COLOR_CHANGED_PROPERTY)) {

		} else if (propertyName.equals(MOUSE_OVER_COLOR_CHANGED_PROPERTY)) {

		}
	}
}
