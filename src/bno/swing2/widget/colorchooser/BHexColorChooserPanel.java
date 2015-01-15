package bno.swing2.widget.colorchooser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import bno.swing2.widget.BColor;
import bno.swing2.widget.ColorChangeEvent;
import bno.swing2.widget.ColorChangeListener;
import bno.swing2.widget.colorchooser.gradient.BGradientColorChooserWidget;
import bno.swing2.widget.colorchooser.hexcolorchooser.BHexColorChooserWidget;

public class BHexColorChooserPanel extends AbstractColorChooserPanel implements
		ColorChangeListener {

	private static final long serialVersionUID = -7267034120468009348L;

	private BHexColorChooserWidget hexWidget;
	private BGradientColorChooserWidget gradientWidget;

	public int getMnemonic() {
		return KeyEvent.VK_X;
	}

	@Override
	public void updateChooser() {

		if (hexWidget != null) {
			hexWidget.setSelectedColor(new BColor(getColorFromModel()));

			if (gradientWidget != null) {
				gradientWidget.setColor(hexWidget.getSelectedColor());
			}
		}

	}

	@Override
	protected void buildChooser() {
		setLayout(new BorderLayout());

		hexWidget = new BHexColorChooserWidget();
		hexWidget.addColorChangeListener(this);
		add(hexWidget, BorderLayout.CENTER);

		gradientWidget = new BGradientColorChooserWidget(
				hexWidget.getSelectedColor());
		gradientWidget.addColorChangeListener(this);
		add(gradientWidget, BorderLayout.EAST);

	}

	@Override
	public Dimension getPreferredSize() {

		if (getParent() != null) {
			return new Dimension(getParent().getWidth(), getParent()
					.getHeight());
		} else {
			return super.getPreferredSize();
		}
	}

	@Override
	public String getDisplayName() {
		return "Hexagon";
	}

	@Override
	public Icon getSmallDisplayIcon() {
		return null;
	}

	@Override
	public Icon getLargeDisplayIcon() {
		return null;
	}

	@Override
	public void selectedColorChanged(ColorChangeEvent event) {

		if (event.getSource() == hexWidget) {
			gradientWidget.setColor(event.getNewColor());
			getColorSelectionModel().setSelectedColor(event.getNewColor());
		} else if (event.getSource() == gradientWidget) {
			gradientWidget.setColor(event.getNewColor());
			getColorSelectionModel().setSelectedColor(event.getNewColor());
		}

	}

	@Override
	public void mouseOverColorChanged(ColorChangeEvent event) {
	}

}
