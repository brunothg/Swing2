package bno.swing2.awt.Gradient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import bno.swing2.awt.BColor;

public class BasicBGradientColorChooserWidgetUI extends
		BGradientColorChooserWidgetUI {

	protected final static int BORDER_SIZE = 2;

	public static ComponentUI createUI(JComponent c) {
		return new BasicBGradientColorChooserWidgetUI();
	}

	public void installUI(JComponent c) {
		installUI((BGradientColorChooserWidget) c);
	}

	private void installUI(BGradientColorChooserWidget c) {
	}

	public void uninstallUI(JComponent c) {
		uninstallUI((BGradientColorChooserWidget) c);
	}

	private void uninstallUI(BGradientColorChooserWidget c) {
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		paint(g, (BGradientColorChooserWidget) c);
	}

	private void paint(Graphics g, BGradientColorChooserWidget c) {

		if (c.getOrientation() == BGradientColorChooserWidget.Y_AXIS) {
			paintY(g, c);
		} else {
			paintX(g, c);
		}

	}

	private void paintX(Graphics g, BGradientColorChooserWidget c) {
		int elementHeight = Math.max(c.getWidth() / c.getMaximumSubdivisions(),
				1);

		int anzElemente = c.getWidth() / elementHeight;

		int midHeight = c.getWidth() - ((anzElemente - 1) * elementHeight);

		int midPos = (int) (Math.round(anzElemente / 2.0) - 1);

		Color col = c.getColor();

		int aktHeight = 0;
		for (int x = 0; x < anzElemente; x++) {
			g.setColor(getColorAtPosition(x, anzElemente, midPos, anzElemente,
					col));

			if (x == midPos) {

				g.setColor(new BColor(g.getColor()).invertRGB());
				g.fillRect(aktHeight, 0, midHeight, c.getHeight());

				g.setColor(new BColor(g.getColor()).invertRGB());
				g.fillRect(aktHeight + BORDER_SIZE, 0 + BORDER_SIZE, midHeight
						- 2 * BORDER_SIZE, c.getHeight() - 2 * BORDER_SIZE);
				aktHeight += midHeight;
			} else {
				g.fillRect(aktHeight, 0, elementHeight, c.getHeight());
				aktHeight += elementHeight;
			}
		}
	}

	private void paintY(Graphics g, BGradientColorChooserWidget c) {
		int elementHeight = Math.max(
				c.getHeight() / c.getMaximumSubdivisions(), 1);

		int anzElemente = c.getHeight() / elementHeight;

		int midHeight = c.getHeight() - ((anzElemente - 1) * elementHeight);

		int midPos = (int) (Math.round(anzElemente / 2.0) - 1);

		Color col = c.getColor();

		int aktHeight = 0;
		for (int y = 0; y < anzElemente; y++) {
			g.setColor(getColorAtPosition(y, anzElemente, midPos, anzElemente,
					col));

			if (y == midPos) {

				g.setColor(new BColor(g.getColor()).invertRGB());
				g.fillRect(0, aktHeight, c.getWidth(), midHeight);

				g.setColor(new BColor(g.getColor()).invertRGB());
				g.fillRect(0 + BORDER_SIZE, aktHeight + BORDER_SIZE,
						c.getWidth() - 2 * BORDER_SIZE, midHeight - 2
								* BORDER_SIZE);
				aktHeight += midHeight;
			} else {
				g.fillRect(0, aktHeight, c.getWidth(), elementHeight);
				aktHeight += elementHeight;
			}
		}
	}

	private Color getColorAtPosition(int y, int anzElemente, int midPos,
			int ende, final Color color) {
		Color ret = color;
		if (midPos == y) {
			return ret;
		}

		int posDif;

		int[] dif = new int[3];

		if (midPos > y) {
			posDif = Math.abs(y - midPos);
			dif[0] = -(int) Math.ceil(color.getRed() / (double) (midPos));
			dif[1] = -(int) Math.ceil(color.getGreen() / (double) (midPos));
			dif[2] = -(int) Math.ceil(color.getBlue() / (double) (midPos));
		} else {
			int midPosEnde = Math.abs(ende - midPos) - 1;
			posDif = Math.abs(y - midPosEnde);
			dif[0] = (int) Math.ceil((255 - color.getRed())
					/ (double) (midPosEnde));
			dif[1] = (int) Math.ceil((255 - color.getGreen())
					/ (double) (midPosEnde));
			dif[2] = (int) Math.ceil((255 - color.getBlue())
					/ (double) (midPosEnde));
		}

		ret = new Color(Math.min(Math.max(ret.getRed() + dif[0] * posDif, 0),
				255), Math.min(Math.max(ret.getGreen() + dif[1] * posDif, 0),
				255), Math.min(Math.max(ret.getBlue() + dif[2] * posDif, 0),
				255));

		return ret;
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {

		return getPrefferedSize((BGradientColorChooserWidget) c);
	}

	private Dimension getPrefferedSize(BGradientColorChooserWidget c) {
		Dimension ret = new Dimension(
				(c.getOrientation() == BGradientColorChooserWidget.Y_AXIS) ? 100
						: (c.getMaximumSubdivisions() * 5),
				(c.getOrientation() == BGradientColorChooserWidget.Y_AXIS) ? (c
						.getMaximumSubdivisions() * 5) : 100);

		return ret;
	}

	@Override
	public Dimension getMinimumSize(JComponent c) {
		return getMinimumSize((BGradientColorChooserWidget) c);
	}

	private Dimension getMinimumSize(BGradientColorChooserWidget c) {
		Dimension ret = new Dimension(
				(c.getOrientation() == BGradientColorChooserWidget.Y_AXIS) ? 100
						: (c.getMaximumSubdivisions()),
				(c.getOrientation() == BGradientColorChooserWidget.Y_AXIS) ? (c
						.getMaximumSubdivisions()) : 100);

		return ret;
	}

	@Override
	public Dimension getMaximumSize(JComponent c) {
		Dimension ret = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

		return ret;
	}

}
