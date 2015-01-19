package bno.swing2.widget;

import java.awt.Color;

public class BColor extends Color {

	private static final long serialVersionUID = 1L;

	public BColor(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	public BColor(int r, int g, int b) {
		super(r, g, b);
	}

	public BColor(Color c) {
		super(c.getRGB());
	}

	public BColor(int rgb) {
		super(rgb);
	}

	public BColor invertRGB() {
		return new BColor(255 - getRed(), 255 - getGreen(), 255 - getBlue(),
				getAlpha());
	}

	public BColor invert() {
		return new BColor(255 - getRed(), 255 - getGreen(), 255 - getBlue(),
				255 - getAlpha());
	}

	public boolean equalsRGB(BColor c) {
		if (c == null) {
			return false;
		}

		return (getRed() == c.getRed() && getGreen() == c.getGreen() && getBlue() == c
				.getBlue());
	}

	public boolean equalsARGB(BColor c) {
		return equalsRGB(c) && getAlpha() == c.getAlpha();
	}

	public String toHexString() {
		return String.format("#%02X%02X%02X", getRed(), getGreen(), getBlue());
	}

	public String toRGBString() {
		return String.format("r=%03d, g=%03d, b=%03d", getRed(), getGreen(),
				getBlue());
	}

	/**
	 * Creates a BColor based on a hexadecimal string. The syntax should follow:
	 * [#]rrggbb[aa]
	 * 
	 * @param hexCol
	 *            Hexadecimal color string
	 * @return The color represented by this string
	 */
	public static BColor color(String hexCol) {

		if (hexCol == null || hexCol.isEmpty()) {
			return null;
		}

		int[] rgba = new int[] { 0, 0, 0, 255 };

		if (hexCol.startsWith("#")) {
			hexCol = hexCol.substring(1);
		}

		int anzahl;

		if (hexCol.length() % 3 == 0) {

			anzahl = 3;
		} else if (hexCol.length() % 4 == 0) {

			anzahl = 4;
		} else {

			throw new ColorFormatException("Invalid color format: " + hexCol);
		}

		for (int i = 0; i < anzahl; i++) {

			rgba[i] = Integer.parseInt(hexCol.substring(0, 2), 16);
			hexCol = hexCol.substring(2);
		}

		return new BColor(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	public static class ColorFormatException extends RuntimeException {

		private static final long serialVersionUID = 8115734393484939671L;

		public ColorFormatException(String s) {
			super(s);
		}
	}

	public BColor grayValue() {

		int value = (getRed() + getGreen() + getBlue()) / 3;

		return new BColor(value, value, value, getAlpha());
	}
}
