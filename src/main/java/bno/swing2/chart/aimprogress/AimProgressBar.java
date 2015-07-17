package bno.swing2.chart.aimprogress;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JProgressBar;

import bno.swing2.utils.Null;

public class AimProgressBar extends JProgressBar {

	private static final long serialVersionUID = 1L;

	private AimSection[] sections;
	private int aim;
	private Color aimColor;
	private boolean showFullAim = false;

	public AimProgressBar() {

		this(0, 100, 100);
	}

	public AimProgressBar(int min, int max, int aim) {

		this(min, max, null, aim);
	}

	public AimProgressBar(int min, int max, AimSection[] sections, int aim) {

		super(min, max);
		setSections(sections);
		setAim(aim);
		setValue(getMinimum());
	}

	@Override
	protected void paintComponent(Graphics g) {

		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		paintScala(g);
		paintAim(g);
		paintMarker(g);
	}

	private void paintMarker(Graphics g) {

		Rectangle inside = getInside();
		int length = (getOrientation() == HORIZONTAL) ? inside.width
				: inside.height;
		int[] xPoints, yPoints;

		int value = (int) (Math.max(
				0,
				Math.min((1.0 / (getMaximum() - getMinimum()))
						* (getValue() - getMinimum()), 1)) * length);

		int valueBorder;
		if (getOrientation() == HORIZONTAL) {

			int xMiddle = inside.x + value;
			int yTop = inside.y;
			int yMiddle = (int) (inside.y + inside.height * 0.5);

			xPoints = new int[] { xMiddle - yMiddle, xMiddle, xMiddle + yMiddle };
			yPoints = new int[] { yTop, yMiddle, yTop };

			valueBorder = (int) (yMiddle * 0.2);
		} else {

			int yMiddle = (int) (inside.y + inside.height - value);
			int xMiddle = (int) (inside.x + inside.width * 0.5);
			int xTop = (int) (inside.x + inside.width);

			yPoints = new int[] { yMiddle - xMiddle, yMiddle, yMiddle + xMiddle };
			xPoints = new int[] { xTop, xMiddle, xTop };

			valueBorder = (int) (xMiddle * 0.2);
		}
		valueBorder = Math.max(1, valueBorder);

		Color actualColor = Color.YELLOW;

		AimSection[] sections = getSections();
		int position = 0;
		for (int i = 0; i < sections.length; i++) {
			AimSection section = sections[i];

			if (i == sections.length - 1) {
				actualColor = section.getColor();
				break;
			}

			int newPosition = position + (int) (length * section.getSize());
			if (value >= position && value <= newPosition) {
				actualColor = section.getColor();
				break;
			}
			position = newPosition;
		}

		g.setColor(actualColor);
		g.fillPolygon(xPoints, yPoints, 3);

		g.setColor(Null.nvl(getAimColor(), Color.BLACK));
		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setStroke(new BasicStroke(valueBorder, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
		}
		g.drawPolygon(xPoints, yPoints, 3);
	}

	private void paintAim(Graphics g) {

		g.setColor(Null.nvl(getAimColor(), Color.BLACK));

		Rectangle inside = getInside();
		int length = (getOrientation() == HORIZONTAL) ? inside.width
				: inside.height;
		int[] xPoints, yPoints;

		int aim = (int) (Math.max(
				0,
				Math.min((1.0 / (getMaximum() - getMinimum()))
						* (getAim() - getMinimum()), 1)) * length);

		if (getOrientation() == HORIZONTAL) {

			int xMiddle = inside.x + aim;
			int yBottom = inside.y + inside.height;
			int yMiddle = (int) (inside.y + inside.height * 0.5);
			xPoints = new int[] { xMiddle - yMiddle, xMiddle, xMiddle + yMiddle };
			yPoints = new int[] { yBottom, yMiddle, yBottom };
		} else {

			int yMiddle = (int) (inside.y + inside.height - aim);
			int xMiddle = (int) (inside.x + inside.width * 0.5);
			int xBottom = (int) (inside.x);
			yPoints = new int[] { yMiddle - xMiddle, yMiddle, yMiddle + xMiddle };
			xPoints = new int[] { xBottom, xMiddle, xBottom };
		}

		g.fillPolygon(xPoints, yPoints, 3);
	}

	protected void paintScala(Graphics g) {

		Rectangle inside = getInside();

		g.setColor(getBackground());
		g.fillRect(inside.x, inside.y, inside.width, inside.height);

		AimSection[] sections = getSections();
		if (sections == null) {
			return;
		}
		int length = (getOrientation() == HORIZONTAL) ? inside.width
				: inside.height;

		int position = 0;
		for (int i = 0; i < sections.length; i++) {
			AimSection section = sections[i];

			int sectionAbsSize = (int) Math.round(length * section.getSize());
			if (i == sections.length - 1) {
				sectionAbsSize = length - position;
			}
			sectionAbsSize = Math.max(0,
					Math.min(sectionAbsSize, length - position));

			g.setColor(section.getColor());

			if (getOrientation() == HORIZONTAL) {
				g.fillRect(inside.x + position, inside.y, sectionAbsSize,
						inside.height);
			} else {
				g.fillRect(inside.x, inside.height + inside.y - position
						- sectionAbsSize, inside.width, sectionAbsSize);
			}
			position += sectionAbsSize;
		}
	}

	private Rectangle getInside() {

		Insets inset = getInsets();

		if (isShowFullAim()) {
			Rectangle inside = new Rectangle(inset.left, inset.top, getWidth()
					- inset.left - inset.right, getHeight() - inset.top
					- inset.bottom);
			if (getOrientation() == HORIZONTAL) {

				int yMiddle = (int) (inside.y + inside.height * 0.5);
				inset.set(inset.top, inset.left + yMiddle, inset.bottom,
						inset.right + yMiddle);
			} else {

				int xMiddle = (int) (inside.x + inside.width * 0.5);
				inset.set(inset.top + xMiddle, inset.left, inset.bottom
						+ xMiddle, inset.right);
			}
		}

		return new Rectangle(inset.left, inset.top, getWidth() - inset.left
				- inset.right, getHeight() - inset.top - inset.bottom);
	}

	public AimSection[] getSections() {
		return sections;
	}

	public void setSections(AimSection[] sections) {
		this.sections = sections;
		repaint();
	}

	public int getAim() {
		return aim;
	}

	public void setAim(int aim) {
		this.aim = aim;
		repaint();
	}

	public Color getAimColor() {
		return aimColor;
	}

	public void setAimColor(Color aimColor) {
		this.aimColor = aimColor;
	}

	public boolean isShowFullAim() {
		return showFullAim;
	}

	public void setShowFullAim(boolean showFullAim) {
		this.showFullAim = showFullAim;
	}
}
