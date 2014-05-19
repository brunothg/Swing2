package bno.swing2.awt.hexcolorchooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import bno.swing2.awt.BGraphics2D;

public class BasicBHexColorChooserWidgetUI extends BHexColorChooserWidgetUI {

	private static final int numberOfHex = 13;
	private static final int borderSize = 3;

	//@formatter:off
	private static final Color[][] colors = new Color[][] {
			{ gc(0x00, 0x33, 0x66), gc(0x33, 0x66, 0x99), gc(0x33, 0x66, 0xCC), gc(0x00, 0x33, 0x99), gc(0x00, 0x00, 0x99), gc(0x00, 0x00, 0xCC), gc(0x00, 0x00, 0x66) },
			{ gc(0x00, 0x66, 0x66), gc(0x00, 0x66, 0x99), gc(0x00, 0x99, 0xCC), gc(0x00, 0x66, 0xCC), gc(0x00, 0x33, 0xCC), gc(0x00, 0x00, 0xFF), gc(0x33, 0x33, 0xFF), gc(0x33, 0x33, 0x99) }, 
			{ gc(0x66, 0x99, 0x99), gc(0x00, 0x99, 0x99), gc(0x33, 0xCC, 0xCC), gc(0x00, 0xCC, 0xFF), gc(0x00, 0x99, 0xFF), gc(0x00, 0x66, 0xFF), gc(0x33, 0x66, 0xFF), gc(0x33, 0x33, 0xCC), gc(0x66, 0x66, 0x99) }, 
			{ gc(0x33, 0x99, 0x66), gc(0x00, 0xCC, 0x99), gc(0x00, 0xFF, 0xCC), gc(0x00, 0xFF, 0xFF), gc(0x33, 0xCC, 0xFF), gc(0x33, 0x99, 0xFF), gc(0x66, 0x99, 0xFF), gc(0x66, 0x66, 0xFF), gc(0x66, 0x00, 0xFF), gc(0x66, 0x00, 0xCC) }, 
			{ gc(0x33, 0x99, 0x33), gc(0x00, 0xCC, 0x66), gc(0x00, 0xFF, 0x99), gc(0x66, 0xFF, 0xCC), gc(0x66, 0xFF, 0xFF), gc(0x66, 0xCC, 0xFF), gc(0x99, 0xCC, 0xFF), gc(0x99, 0x99, 0xFF), gc(0x99, 0x66, 0xFF), gc(0x99, 0x33, 0xFF), gc(0x99, 0x00, 0xFF) }, 
			{ gc(0x00, 0x66, 0x00), gc(0x00, 0xCC, 0x00), gc(0x00, 0xFF, 0x00), gc(0x66, 0xFF, 0x99), gc(0x99, 0xFF, 0xCC), gc(0xCC, 0xFF, 0xFF), gc(0xCC, 0xCC, 0xFF), gc(0xCC, 0x99, 0xFF), gc(0xCC, 0x66, 0xFF), gc(0xCC, 0x33, 0xFF), gc(0xCC, 0x00, 0xFF), gc(0x99, 0x00, 0xCC) }, 
			{ gc(0x00, 0x33, 0x00), gc(0x00, 0x99, 0x33), gc(0x33, 0xCC, 0x33), gc(0x66, 0xFF, 0x66), gc(0x99, 0xFF, 0x99), gc(0xCC, 0xFF, 0xCC), gc(0xFF, 0xFF, 0xFF), gc(0xFF, 0xCC, 0xFF), gc(0xFF, 0x99, 0xFF), gc(0xFF, 0x66, 0xFF), gc(0xFF, 0x00, 0xFF), gc(0xCC, 0x00, 0xCC), gc(0x66, 0x00, 0x66) }, 
			{ gc(0x33, 0x66, 0x00), gc(0x00, 0x99, 0x00), gc(0x66, 0xFF, 0x33), gc(0x99, 0xFF, 0x66), gc(0xCC, 0xFF, 0x99), gc(0xFF, 0xFF, 0xCC), gc(0xFF, 0xCC, 0xCC), gc(0xFF, 0x99, 0xCC), gc(0xFF, 0x66, 0xCC), gc(0xFF, 0x33, 0xCC), gc(0xCC, 0x00, 0x99), gc(0x99, 0x33, 0x99) }, 
			{ gc(0x33, 0x33, 0x00), gc(0x66, 0x99, 0x00), gc(0x99, 0xFF, 0x33), gc(0xCC, 0xFF, 0x66), gc(0xFF, 0xFF, 0x99), gc(0xFF, 0xCC, 0x99), gc(0xFF, 0x99, 0x99), gc(0xFF, 0x66, 0x99), gc(0xFF, 0x33, 0x99), gc(0xCC, 0x33, 0x99), gc(0x99, 0x00, 0x99) }, 
			{ gc(0x66, 0x66, 0x33), gc(0x99, 0xCC, 0x00), gc(0xCC, 0xFF, 0x33), gc(0xFF, 0xFF, 0x66), gc(0xFF, 0xCC, 0x66), gc(0xFF, 0x99, 0x66), gc(0xFF, 0x66, 0x66), gc(0xFF, 0x00, 0x66), gc(0xCC, 0x66, 0x99), gc(0x99, 0x33, 0x66) },
			{ gc(0x99, 0x99, 0x66), gc(0xCC, 0xCC, 0x00), gc(0xFF, 0xFF, 0x00), gc(0xFF, 0xCC, 0x00), gc(0xFF, 0x99, 0x33), gc(0xFF, 0x66, 0x00), gc(0xFF, 0x50, 0x50), gc(0xCC, 0x00, 0x66), gc(0x66, 0x00, 0x33) }, 
			{ gc(0x99, 0x66, 0x33), gc(0xCC, 0x99, 0x00), gc(0xFF, 0x99, 0x00), gc(0xCC, 0x66, 0x00), gc(0xFF, 0x33, 0x00), gc(0xFF, 0x00, 0x00), gc(0xCC, 0x00, 0x00), gc(0x99, 0x00, 0x33) }, 
			{ gc(0x66, 0x33, 0x00), gc(0x99, 0x66, 0x00), gc(0xCC, 0x33, 0x00), gc(0x99, 0x33, 0x00), gc(0x99, 0x00, 0x00), gc(0x80, 0x00, 0x00), gc(0x99, 0x33, 0x33) } 
			};
	//@formatter:on

	Color preBackground;
	MouseListener mL;

	public static ComponentUI createUI(JComponent c) {
		return new BasicBHexColorChooserWidgetUI();
	}

	public void installUI(JComponent c) {
		installUI((BHexColorChooserWidget) c);
	}

	private void installUI(BHexColorChooserWidget c) {
		preBackground = c.getBackground();
		c.setBackground(Color.BLACK);

		c.addMouseListener(createMouseListener(c));
	}

	public void uninstallUI(JComponent c) {
		uninstallUI((BHexColorChooserWidget) c);
	}

	private void uninstallUI(BHexColorChooserWidget c) {
		c.setBackground(preBackground);
		c.removeMouseListener(mL);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		paint(g, (BHexColorChooserWidget) c);
	}

	private void paint(Graphics g, BHexColorChooserWidget c) {
		int[] selectedColorIndex = getIndex(c.getSelectedColor());

		int maxSizeWidth = (int) Math
				.floor((c.getWidth() / (double) numberOfHex)
						/ (Math.sqrt(3) / 2.0));
		int maxSizeHeight = (int) Math
				.floor((c.getHeight() / (double) numberOfHex) * (1.25));

		int sizeOfOneHex = Math.min(maxSizeHeight, maxSizeWidth);
		int sizeIOfOneHex = (int) (sizeOfOneHex * (Math.sqrt(3) / 2));
		int radiusOfOneHex = (int) Math.ceil(sizeOfOneHex / 2.0);
		int radiusIOfOneHex = (int) (radiusOfOneHex * (Math.sqrt(3) / 2));

		int pivotX = c.getWidth() / 2;
		int pivotY = c.getHeight() / 2;

		// Border
		int startX = (pivotX - (numberOfHex / 2) * sizeIOfOneHex)
				+ ((numberOfHex / 2) * radiusIOfOneHex);
		int startY = (int) (pivotY - (1.5 * radiusOfOneHex) * (numberOfHex / 2));
		int rowWidth = numberOfHex - (numberOfHex / 2);

		for (int y = 0; y < numberOfHex; y++) {
			for (int x = 0; x < rowWidth; x++) {

				if (y == 0 || y == numberOfHex - 1 || x == 0
						|| x == rowWidth - 1) {
					BGraphics2D.fillHexagon(g, c.getBackground(), startX + x
							* sizeIOfOneHex, startY, radiusOfOneHex
							+ borderSize, 0);
				}

			}
			startY += radiusOfOneHex * 1.5;
			startX -= (y < numberOfHex / 2) ? radiusIOfOneHex
					: -radiusIOfOneHex;
			rowWidth += (y < numberOfHex / 2) ? 1 : -1;
		}

		// Farbfelder
		startX = (pivotX - (numberOfHex / 2) * sizeIOfOneHex)
				+ ((numberOfHex / 2) * radiusIOfOneHex);
		startY = (int) (pivotY - (1.5 * radiusOfOneHex) * (numberOfHex / 2));
		rowWidth = numberOfHex - (numberOfHex / 2);

		for (int y = 0; y < numberOfHex; y++) {
			for (int x = 0; x < rowWidth; x++) {

				g.setColor(getColor(x, y));

				if (x == selectedColorIndex[0] && y == selectedColorIndex[1]) {
					BGraphics2D.fillHexagon(g, g.getColor(), startX + x
							* sizeIOfOneHex, startY, radiusOfOneHex, 0);
					BGraphics2D.fillHexagon(g, negate(g.getColor()), startX + x
							* sizeIOfOneHex, startY, radiusOfOneHex - 3, 0);
					BGraphics2D.fillHexagon(g, g.getColor(), startX + x
							* sizeIOfOneHex, startY, radiusOfOneHex - 5, 0);
				} else {
					BGraphics2D.fillHexagon(g, g.getColor(), startX + x
							* sizeIOfOneHex, startY, radiusOfOneHex, 0);
				}
			}
			startY += radiusOfOneHex * 1.5;
			startX -= (y < numberOfHex / 2) ? radiusIOfOneHex
					: -radiusIOfOneHex;
			rowWidth += (y < numberOfHex / 2) ? 1 : -1;
		}

	}

	private Color negate(Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(),
				255 - color.getBlue(), color.getAlpha());
	}

	private Color getColor(int x, int y) {
		return colors[y][x];
	}

	private int[] getIndex(Color c) {

		int x = 0;
		int y = 0;

		int dif = Integer.MAX_VALUE;

		for (int y2 = 0; y2 < colors.length; y2++) {
			for (int x2 = 0; x2 < colors[y2].length; x2++) {
				int tdif = calcDif(c, getColor(x2, y2));

				if (tdif < dif) {
					dif = tdif;
					x = x2;
					y = y2;
				}
			}
		}

		return new int[] { x, y };
	}

	private int calcDif(Color c1, Color c2) {
		int ret;

		int r1 = c1.getRed(), g1 = c1.getGreen(), b1 = c1.getBlue();
		int r2 = c2.getRed(), g2 = c2.getGreen(), b2 = c2.getBlue();

		ret = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
		ret = ret / 3;

		return ret;
	}

	private boolean existsColor(Color selected) {
		boolean ret = false;

		loop: for (int y2 = 0; y2 < colors.length; y2++) {
			for (int x2 = 0; x2 < colors[y2].length; x2++) {

				Color check = getColor(x2, y2);
				if (equals(check, selected)) {
					ret = true;
					break loop;
				}

			}
		}

		return ret;
	}

	static boolean equals(Color c1, Color c2) {

		return c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen()
				&& c1.getBlue() == c2.getBlue();
	}

	private static Color gc(int r, int g, int b) {
		return new Color(r, g, b);
	}

	private Color getColorAtLocation(int x, int y,
			final BHexColorChooserWidget c) {
		Color ret = c.getSelectedColor();

		BufferedImage img = new BufferedImage(c.getWidth(), c.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = img.createGraphics();
		paint(g, (JComponent) c);

		Color selected = new Color(img.getRGB(x, y));
		if (existsColor(selected)) {
			ret = selected;
		}

		return ret;
	}

	private MouseListener createMouseListener(final BHexColorChooserWidget c) {

		mL = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Color clicked = getColorAtLocation(e.getX(), e.getY(), c);

				if (!BasicBHexColorChooserWidgetUI.equals(c.getSelectedColor(),
						clicked)) {
					fireSelectedColorChanged(c.getSelectedColor(), clicked, c);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		};

		return mL;
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

	private void fireSelectedColorChanged(Color selectedColor, Color clicked,
			BHexColorChooserWidget c) {
		c.firePropertyChange(
				BHexColorChooserWidget.SELECTED_COLOR_CHANGED_PROPERTY,
				selectedColor, clicked);
	}
}
