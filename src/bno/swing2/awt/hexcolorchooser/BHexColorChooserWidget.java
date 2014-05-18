package bno.swing2.awt.hexcolorchooser;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

public class BHexColorChooserWidget extends JComponent {

	private static final long serialVersionUID = 1L;
	private static final String uiClassID = "BHexColorChooserWidgetUI";

	Color selectedColor;

	public BHexColorChooserWidget(Color c) {
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

}
