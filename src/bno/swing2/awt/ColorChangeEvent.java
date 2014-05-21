package bno.swing2.awt;

import java.awt.Component;

public class ColorChangeEvent {

	private BColor oldColor;
	private BColor newColor;

	private Component source;

	public ColorChangeEvent(Component source, BColor oldColor, BColor newColor) {
		this.source = source;
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	public BColor getOldColor() {
		return oldColor;
	}

	public BColor getNewColor() {
		return newColor;
	}

	public Component getSource() {
		return source;
	}

}
