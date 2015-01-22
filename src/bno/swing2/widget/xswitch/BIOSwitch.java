package bno.swing2.widget.xswitch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.Border;

import bno.swing2.BColor;

public class BIOSwitch extends JCheckBox {

	private static final String uiClassID = "IOSwitchUI";
	private static final long serialVersionUID = 1L;

	private static final Color DEF_COLOR_BACKGROUND = BColor.color("#c2c2c2");
	private static final Color DEF_COLOR_FOREGROUND = Color.WHITE;
	private static final Color DEF_COLOR_ON = BColor.color("#27a1ca");
	private static final BColor DEF_COLOR_OFF = BColor.color("#a1a1a1");

	private Color onColor;
	private Color offColor;

	private String offString;

	public BIOSwitch() {

		super();
		create();
	}

	public BIOSwitch(Action a) {

		super(a);
		create();
	}

	public BIOSwitch(Icon icon) {

		super(icon);
		create();
	}

	public BIOSwitch(Icon icon, boolean selected) {

		super(icon, selected);
		create();
	}

	public BIOSwitch(String text) {

		super(text);
		create();
	}

	public BIOSwitch(String onString, String offString) {

		super(onString);

		create();
		setOffString(offString);
	}

	public BIOSwitch(String text, boolean selected) {

		super(text, selected);
		create();
	}

	public BIOSwitch(String text, Icon icon) {

		super(text, icon);
		create();
	}

	public BIOSwitch(String text, Icon icon, boolean selected) {

		super(text, icon, selected);
		create();
	}

	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI(UIManager.getUI(this));
		} else {
			setUI(new BasicIOSwitchUI());
		}
	}

	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	protected void create() {

		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		setForeground(DEF_COLOR_FOREGROUND);
		setBackground(DEF_COLOR_BACKGROUND);
		setOnColor(DEF_COLOR_ON);
		setOffColor(DEF_COLOR_OFF);
		setSelectedIcon(null);
		setDisabledSelectedIcon(null);
	}

	@Override
	protected void paintBorder(Graphics g) {

		Border border = getBorder();

		if (border == null) {
			return;
		}

		border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
	}

	public Color getOnColor() {
		return onColor;
	}

	/**
	 * Sets the background color for the switch in it's on state
	 * 
	 * @param offColor
	 */
	public void setOnColor(Color onColor) {
		this.onColor = onColor;
	}

	public Color getOffColor() {
		return offColor;
	}

	/**
	 * Sets the background color for the switch in it's off state
	 * 
	 * @param offColor
	 */
	public void setOffColor(Color offColor) {
		this.offColor = offColor;
	}

	public String getOnString() {
		return (getText() == null) ? "" : getText();
	}

	/**
	 * Sets the string for the switch in it's on state
	 * 
	 * @param onString
	 *            String to be shown in on state
	 */
	public void setOnString(String onString) {
		setText(onString);
	}

	public String getOffString() {
		return (offString == null) ? "" : offString;
	}

	/**
	 * Sets the string for the switch in it's off state
	 * 
	 * @param onString
	 *            String to be shown in off state
	 */
	public void setOffString(String offString) {
		this.offString = offString;
	}

	/**
	 * @see #setSelectedIcon(Icon)
	 */
	public void setIcon(Icon defaultIcon) {
		super.setSelectedIcon(defaultIcon);
	}

	/**
	 * @see #setDisabledSelectedIcon(Icon)
	 */
	public void setDisabledIcon(Icon disabledIcon) {
		super.setDisabledSelectedIcon(disabledIcon);
	}
}
