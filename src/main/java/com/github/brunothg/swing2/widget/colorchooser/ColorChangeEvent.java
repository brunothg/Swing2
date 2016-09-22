package com.github.brunothg.swing2.widget.colorchooser;

import java.awt.Component;

import com.github.brunothg.swing2.BColor;

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
