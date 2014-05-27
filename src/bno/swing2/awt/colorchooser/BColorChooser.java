package bno.swing2.awt.colorchooser;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import bno.swing2.awt.BColor;

public class BColorChooser extends JColorChooser {

	private static final long serialVersionUID = 2889510211457614799L;

	public BColorChooser() {
		super();
		addHex();
	}

	public BColorChooser(ColorSelectionModel model) {
		super(model);
		addHex();
	}

	public BColorChooser(BColor initialColor) {
		super(initialColor);
		addHex();
	}

	private void addHex() {

		AbstractColorChooserPanel[] panelsAr = getChooserPanels();

		List<AbstractColorChooserPanel> panels = new ArrayList<AbstractColorChooserPanel>(
				panelsAr.length + 1);

		for (AbstractColorChooserPanel pl : panelsAr) {
			panels.add(pl);
		}

		panels.add(0, new BHexColorChooserPanel());

		setChooserPanels(panels.toArray(new AbstractColorChooserPanel[panels
				.size()]));
	}

}
