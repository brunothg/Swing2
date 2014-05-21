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

	public boolean equalsRGB(BColor c) {
		if (c == null) {
			return false;
		}

		return (getRed() == c.getRed() && getGreen() == c.getGreen() && getBlue() == c
				.getBlue());
	}

}
