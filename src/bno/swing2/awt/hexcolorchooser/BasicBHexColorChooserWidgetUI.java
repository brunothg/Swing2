package bno.swing2.awt.hexcolorchooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import bno.swing2.awt.BGraphics2D;

public class BasicBHexColorChooserWidgetUI extends BHexColorChooserWidgetUI {

	private static final int numberOfHex = 13;

	public static ComponentUI createUI(JComponent c) {
		return new BasicBHexColorChooserWidgetUI();
	}

	public void installUI(JComponent c) {
	}

	public void uninstallUI(JComponent c) {
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);

		int maxSizeWidth = (int) Math.round((c.getWidth() / numberOfHex)
				/ (Math.sqrt(3) / 2.0));
		int maxSizeHeight = (int) Math
				.round((c.getHeight() / numberOfHex) * (1.29));

		int sizeOfOneHex = Math.min(maxSizeHeight, maxSizeWidth);
		int sizeIOfOneHex = (int) (sizeOfOneHex * (Math.sqrt(3) / 2));
		int radiusOfOneHex = sizeOfOneHex / 2;
		int radiusIOfOneHex = (int) (radiusOfOneHex * (Math.sqrt(3) / 2));

		int pivotX = c.getWidth() / 2;
		int pivotY = c.getHeight() / 2;

		int startX = (pivotX - (numberOfHex / 2) * sizeIOfOneHex)
				+ ((numberOfHex / 2) * radiusIOfOneHex);
		int startY = (int) (pivotY - (1.5 * radiusOfOneHex) * (numberOfHex / 2));

		int rowWidth = numberOfHex - (numberOfHex / 2);
		for (int y = 0; y < numberOfHex; y++) {
			for (int x = 0; x < rowWidth; x++) {
				g.setColor(getRandomColor());

				BGraphics2D.fillHexagon(g, g.getColor(), startX + x
						* sizeIOfOneHex, startY, radiusOfOneHex, 0);
			}
			startY += radiusOfOneHex * 1.5;
			startX -= (y < numberOfHex / 2) ? radiusIOfOneHex
					: -radiusIOfOneHex;
			rowWidth += (y < numberOfHex / 2) ? 1 : -1;
		}

	}

	private Color getRandomColor() {
		return new Color((int) (Math.random() * 255),
				(int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		Dimension ret = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

		return ret;
	}

	@Override
	public Dimension getMinimumSize(JComponent c) {
		Dimension ret = new Dimension(130, 130);

		return ret;
	}

	@Override
	public Dimension getMaximumSize(JComponent c) {
		Dimension ret = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

		return ret;
	}

}
