package bno.swing2.widget;

import java.awt.Color;
import java.awt.Graphics;

public class BGraphics2D {
	private static final double RAD_60 = 1.0471975511965976;

	public static void fillHexagon(Graphics g, Color c, int x, int y, int size,
			double rotation) {
		Color tempColor = g.getColor();
		if (c != null) {
			g.setColor(c);
		}

		paintHexagon(g, x, y, size, rotation, true);

		g.setColor(tempColor);
	}

	public static void drawHexagon(Graphics g, Color c, int x, int y, int size,
			double rotation) {
		Color tempColor = g.getColor();
		if (c != null) {
			g.setColor(c);
		}

		paintHexagon(g, x, y, size, rotation, false);

		g.setColor(tempColor);
	}

	public static void paintHexagon(Graphics g, int x, int y, int size,
			double rotation, boolean fill) {
		int[] xPoints = new int[6];
		int[] yPoints = new int[6];

		for (int i = 0; i < 6; i++) {
			int x_c = x;
			int y_c = y - size;

			int[] new_c = rotate(x_c, y_c, x, y, RAD_60 * i + rotation);
			x_c = new_c[0];
			y_c = new_c[1];

			xPoints[i] = x_c;
			yPoints[i] = y_c;
		}

		if (fill) {
			g.fillPolygon(xPoints, yPoints, 6);
		} else {
			g.drawPolygon(xPoints, yPoints, 6);
		}
	}

	private static int[] rotate(int x_c, int y_c, int x_mid, int y_mid,
			double rotation) {
		int[] ret = new int[2];

		if (rotation == 0) {
			ret[0] = x_c;
			ret[1] = y_c;
		} else {
			int x = x_c - x_mid;
			int y = y_c - y_mid;

			ret[0] = (int) Math.round(Math.cos(rotation) * x
					- Math.sin(rotation) * y);
			ret[1] = (int) Math.round(Math.sin(rotation) * x
					+ Math.cos(rotation) * y);

			ret[0] += x_mid;
			ret[1] += y_mid;
		}

		return ret;
	}

}