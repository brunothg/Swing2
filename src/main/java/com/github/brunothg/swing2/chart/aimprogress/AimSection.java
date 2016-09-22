package com.github.brunothg.swing2.chart.aimprogress;

import java.awt.Color;

public class AimSection {

	private double size;
	private Color color;

	public AimSection(double size, Color color) {
		this.size = size;
		this.color = color;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
