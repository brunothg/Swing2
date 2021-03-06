package com.github.brunothg.swing2.widget.xswitch;

import static java.awt.Toolkit.getDefaultToolkit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.github.brunothg.swing2.common.BColor;
import com.github.brunothg.swing2.utils.IconUtils;

public class BasicIOSwitchUI extends IOSwitchUI {

	private static final int FONT_BORDER_X = (int) (getDefaultToolkit()
			.getScreenResolution() * 0.3);
	private static final int FONT_BORDER_Y = (int) (getDefaultToolkit()
			.getScreenResolution() * 0.1);

	private static final double SWITCH_WIDTH = 0.5;
	private static final double SWITCH_WIDTH_EXPANDED = 0.7;

	private MouseAdapter ml;

	public BasicIOSwitchUI() {

		create();
	}

	private void create() {

		createListener();
	}

	private void createListener() {

		ml = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				Object source = e.getSource();

				if (source instanceof BIOSwitch) {

					BIOSwitch c = (BIOSwitch) source;

					clickEvent(c);
				}
			}
		};
	}

	protected void clickEvent(BIOSwitch c) {

		if (!c.isEnabled()) {
			return;
		}
		c.setSelected(!c.isSelected());
	}

	public void installUI(BIOSwitch c) {

		c.addMouseListener(ml);
	}

	public void uninstallUI(BIOSwitch c) {

		c.removeMouseListener(ml);
	}

	@Override
	public void paint(Graphics g, JComponent c) {

		if (g instanceof Graphics2D) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		paint(g, (BIOSwitch) c);

	}

	private Color getColor(Color col, BIOSwitch c) {

		if (!c.isEnabled()) {
			return new BColor(col).grayValue().invertRGB();
		}

		return col;
	}

	private Icon getIcon(Icon ico, BIOSwitch c) {

		if (!c.isEnabled()) {
			return IconUtils.invertRGB(IconUtils.getGrayScale(ico));
		}

		return ico;
	}

	private void paint(Graphics g, BIOSwitch c) {

		Insets insets = c.getInsets();

		int width = c.getWidth();
		int height = c.getHeight();

		Color foreground = getColor(c.getForeground(), c);
		Color background = getColor(c.getBackground(), c);
		Color onColor = getColor(c.getOnColor(), c);
		Color offColor = getColor(c.getOffColor(), c);

		paintBackground(g, insets, width, height, background);
		paintSwitch(g, c, insets, width, height, foreground, onColor, offColor,
				c.isSelected());
	}

	private void paintSwitch(Graphics g, BIOSwitch c, Insets insets, int width,
			int height, Color foreground, Color onColor, Color offColor,
			boolean selected) {

		g.setColor((selected) ? onColor : offColor);

		int x_start;
		int x_end;
		int x_end2;

		int y_start = insets.top;
		int y_end = height - insets.bottom;

		if (selected) {

			x_start = insets.left;
			x_end = (int) (x_start + (width - insets.left - insets.right)
					* SWITCH_WIDTH);
			x_end2 = (int) (x_start + (width - insets.left - insets.right)
					* SWITCH_WIDTH_EXPANDED);
		} else {

			x_start = width - insets.right;
			x_end = (int) (x_start - (width - insets.left - insets.right)
					* SWITCH_WIDTH_EXPANDED);
			x_end2 = (int) (x_start - (width - insets.left - insets.right)
					* SWITCH_WIDTH);
		}

		g.fillPolygon(new int[] { x_start, x_end2, x_end, x_start }, new int[] {
				y_start, y_start, y_end, y_end }, 4);

		paintIcon(g, c, selected, x_start, x_end, x_end2, y_start, y_end);
		paintString(g, c, foreground, selected, x_start, x_end, x_end2,
				y_start, y_end);
	}

	private Icon paintIcon(Graphics g, BIOSwitch c, boolean selected,
			int x_start, int x_end, int x_end2, int y_start, int y_end) {

		Icon ico = getIcon(
				(selected) ? c.getSelectedIcon() : c.getDisabledSelectedIcon(),
				c);

		if (ico == null) {
			return null;
		}

		String text = ((selected) ? c.getOnString() : c.getOffString());
		boolean isText = text != null && !text.isEmpty();

		int x;
		if (isText) {

			x = (selected) ? x_start : x_start - ico.getIconWidth();
			x += ((selected) ? 1 : -1) * FONT_BORDER_X * 0.5;
		} else {

			x = (int) ((selected) ? (x_start + x_end) * 0.5
					: (x_start + x_end2) * 0.5);
			x -= ico.getIconWidth() * 0.5;
		}

		ico.paintIcon(c, g, x,
				(int) ((y_start + y_end) * 0.5 - ico.getIconHeight() * 0.5));

		return ico;
	}

	private void paintString(Graphics g, BIOSwitch c, Color foreground,
			boolean selected, int x_start, int x_end, int x_end2, int y_start,
			int y_end) {
		g.setColor(foreground);
		g.setFont(c.getFont());

		Icon ico = (selected) ? c.getSelectedIcon() : c
				.getDisabledSelectedIcon();

		int icoGap = (int) ((ico == null) ? 0 : ico.getIconWidth()
				+ c.getIconTextGap() + FONT_BORDER_X * 0.5);

		FontMetrics metrics = g.getFontMetrics(c.getFont());
		int ascent = metrics.getMaxAscent();
		int descent = metrics.getMaxDescent();

		if (selected) {

			int fontWidth = metrics.stringWidth(c.getOnString());

			int x_pos = (int) (x_start + (x_end - x_start - fontWidth) * 0.5);
			x_pos = (x_pos < icoGap) ? icoGap : x_pos;

			g.drawString(c.getOnString(), x_pos, (int) ((y_start
					+ ((y_end + 1 - y_start) * 0.5)
					- ((ascent + descent) * 0.5) + ascent)));
		} else {

			int fontWidth = metrics.stringWidth(c.getOffString());

			int x_pos = (int) (x_start + (x_end2 - x_start - fontWidth) * 0.5);
			x_pos = (x_pos + fontWidth > x_start - icoGap) ? x_start - icoGap
					- fontWidth : x_pos;

			g.drawString(c.getOffString(), x_pos, (int) ((y_start
					+ ((y_end + 1 - y_start) * 0.5)
					- ((ascent + descent) * 0.5) + ascent)));
		}
	}

	private void paintBackground(Graphics g, Insets insets, int width,
			int height, Color background) {
		g.setColor(background);
		g.fillRect(insets.left, insets.top, width - insets.left - insets.right,
				height - insets.top - insets.bottom);
	}

	public Dimension getMinimumSize(BIOSwitch c) {

		Insets insets = c.getInsets();
		FontMetrics metrics = c.getFontMetrics(c.getFont());

		Icon selectedIcon = c.getSelectedIcon();
		Icon deselectedIcon = c.getDisabledSelectedIcon();

		int iconWidth = Math.max(
				((selectedIcon != null) ? selectedIcon.getIconWidth() : 0),
				((deselectedIcon != null) ? deselectedIcon.getIconWidth() : 0));

		int width = Math.max(metrics.stringWidth(c.getOnString()),
				metrics.stringWidth(c.getOffString()));
		width += iconWidth;
		width += c.getIconTextGap();
		width *= 2;
		width += insets.left + insets.right + FONT_BORDER_X * 2;

		int height = insets.top + insets.bottom + metrics.getMaxAscent()
				+ metrics.getMaxDescent();
		height += FONT_BORDER_Y;

		return new Dimension(width, height);
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		return getMinimumSize(c);
	}

	@Override
	public Dimension getMinimumSize(JComponent c) {

		return getMinimumSize((BIOSwitch) c);
	}

	@Override
	public Dimension getMaximumSize(JComponent c) {
		return getPreferredSize(c);
	}

	@Override
	public void installUI(JComponent c) {
		installUI((BIOSwitch) c);
	}

	@Override
	public void uninstallUI(JComponent c) {
		uninstallUI((BIOSwitch) c);
	}
}
