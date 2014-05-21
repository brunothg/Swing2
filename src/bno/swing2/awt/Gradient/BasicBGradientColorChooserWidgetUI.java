package bno.swing2.awt.Gradient;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import bno.swing2.awt.BColor;

public class BasicBGradientColorChooserWidgetUI extends
		BGradientColorChooserWidgetUI {

	protected final static int BORDER_SIZE = 2;

	private MouseListener mL;
	private MouseMotionListener mmL;

	public static ComponentUI createUI(JComponent c) {
		return new BasicBGradientColorChooserWidgetUI();
	}

	public void installUI(JComponent c) {
		installUI((BGradientColorChooserWidget) c);
	}

	private void installUI(BGradientColorChooserWidget c) {
		c.addMouseListener(createMouseListener(c));
		c.addMouseMotionListener(createMouseMotionListener(c));
	}

	public void uninstallUI(JComponent c) {
		uninstallUI((BGradientColorChooserWidget) c);
	}

	private void uninstallUI(BGradientColorChooserWidget c) {
		c.removeMouseListener(mL);
		c.removeMouseMotionListener(mmL);
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

		BColor col = c.getColor();

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

		BColor col = c.getColor();

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

	private BColor getColorAtPosition(int x, int y,
			BGradientColorChooserWidget c) {

		int elementHeight;
		int anzElemente;
		int midHeight;
		int soll;

		if (c.getOrientation() == BGradientColorChooserWidget.Y_AXIS) {
			elementHeight = Math.max(
					c.getHeight() / c.getMaximumSubdivisions(), 1);
			anzElemente = c.getHeight() / elementHeight;
			midHeight = c.getHeight() - ((anzElemente - 1) * elementHeight);
			soll = y;
		} else {
			elementHeight = Math.max(c.getWidth() / c.getMaximumSubdivisions(),
					1);
			anzElemente = c.getWidth() / elementHeight;
			midHeight = c.getWidth() - ((anzElemente - 1) * elementHeight);
			soll = x;
		}

		int midPos = (int) (Math.round(anzElemente / 2.0) - 1);
		int ende = anzElemente;
		BColor color = c.getColor();

		int y2;
		int ist = 0;

		for (y2 = 0; y2 < anzElemente; y2++) {
			int next = (y2 != midPos) ? elementHeight : midHeight;

			if (soll >= ist && soll <= ist + next) {
				break;
			}

			ist += next;
		}

		return getColorAtPosition(y2, anzElemente, midPos, ende, color);
	}

	private BColor getColorAtPosition(int y, int anzElemente, int midPos,
			int ende, final BColor color) {
		BColor ret = color;
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

		ret = new BColor(Math.min(Math.max(ret.getRed() + dif[0] * posDif, 0),
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

	static boolean equals(BColor c1, BColor c2) {
		if (c1 == c2 && c2 == null) {
			return true;
		}

		if (c1 == null || c2 == null) {
			return false;
		}

		return c1.equalsRGB(c2);
	}

	private MouseMotionListener createMouseMotionListener(
			final BGradientColorChooserWidget c) {
		mmL = new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

				BColor mouseOver = getColorAtPosition(e.getX(), c.getY(), c);

				if (!BasicBGradientColorChooserWidgetUI.equals(
						c.getMouseOverColor(), mouseOver)) {
					fireMouseOverColorChanged(c.getMouseOverColor(), mouseOver,
							c);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			}
		};

		return mmL;
	}

	private MouseListener createMouseListener(
			final BGradientColorChooserWidget c) {
		mL = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				BColor mouseOver = null;

				if (c.getMouseOverColor() != null) {
					fireMouseOverColorChanged(c.getMouseOverColor(), mouseOver,
							c);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				BColor clicked = getColorAtPosition(e.getX(), e.getY(), c);

				if (!BasicBGradientColorChooserWidgetUI.equals(
						c.getSelectedColor(), clicked)) {
					fireSelectedColorChanged(c.getSelectedColor(), clicked, c);
				}
			}
		};

		return mL;
	}

	private void fireSelectedColorChanged(BColor selectedColor, BColor clicked,
			BGradientColorChooserWidget c) {
		if (c == null) {
			return;
		}

		c.firePropertyChange(
				BGradientColorChooserWidget.SELECTED_COLOR_CHANGED_PROPERTY,
				selectedColor, clicked);
	}

	private void fireMouseOverColorChanged(BColor mouseOverColor,
			BColor newColor, BGradientColorChooserWidget c) {
		if (c == null) {
			return;
		}

		c.firePropertyChange(
				BGradientColorChooserWidget.MOUSE_OVER_COLOR_CHANGED_PROPERTY,
				mouseOverColor, newColor);
	}

}
