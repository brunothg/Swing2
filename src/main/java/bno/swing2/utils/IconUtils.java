package bno.swing2.utils;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import bno.swing2.BColor;

public class IconUtils {

	public static Icon getGrayScale(Icon ico) {

		BufferedImage img = new BufferedImage(ico.getIconWidth(),
				ico.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

		ico.paintIcon(null, img.getGraphics(), 0, 0);

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {

				img.setRGB(i, j, new BColor(img.getRGB(i, j), true).grayValue()
						.getRGB());
			}
		}

		return new ImageIcon(img);
	}

	public static Icon invertRGB(Icon ico) {

		BufferedImage img = new BufferedImage(ico.getIconWidth(),
				ico.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

		ico.paintIcon(null, img.getGraphics(), 0, 0);

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {

				img.setRGB(i, j, new BColor(img.getRGB(i, j), true).invertRGB()
						.getRGB());
			}
		}

		return new ImageIcon(img);
	}
}
