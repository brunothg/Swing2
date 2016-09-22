package com.github.brunothg.swing2.chart.aimprogress;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.brunothg.swing2.chart.aimprogress.AimProgressBar;
import com.github.brunothg.swing2.chart.aimprogress.AimSection;

public class AimProgressTest {

	public static void main(String[] args) {
		JFrame disp = new JFrame("AimProgress Test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLocationRelativeTo(null);
		disp.setSize(800, 100);
		disp.setLayout(new BorderLayout());

		final AimProgressBar aimBar = new AimProgressBar(0, 100, 80);
		aimBar.setSections(new AimSection[] { new AimSection(0.4, Color.RED),
				new AimSection(0.3, Color.YELLOW),
				new AimSection(0.3, Color.GREEN) });
		aimBar.setBackground(Color.LIGHT_GRAY);
		aimBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		aimBar.setValue(50);
		aimBar.setOrientation(JProgressBar.HORIZONTAL);
		disp.add(aimBar, BorderLayout.NORTH);

		final JSlider slider = new JSlider(0, 100);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				aimBar.setValue(slider.getValue());
			}
		});
		disp.add(slider, BorderLayout.CENTER);

		disp.setVisible(true);
	}
}
