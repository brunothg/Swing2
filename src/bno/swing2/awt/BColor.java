package bno.swing2.awt;

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

}
