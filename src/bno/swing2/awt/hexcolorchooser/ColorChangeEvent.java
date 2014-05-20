package bno.swing2.awt.hexcolorchooser;

import java.awt.Color;
import java.awt.Component;

public class ColorChangeEvent {

	private Color oldColor;
	private Color newColor;

	private Component source;

	public ColorChangeEvent(Component source, Color oldColor, Color newColor) {
		this.source = source;
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	public Color getOldColor() {
		return oldColor;
	}

	public Color getNewColor() {
		return newColor;
	}

	public Component getSource() {
		return source;
	}

}
