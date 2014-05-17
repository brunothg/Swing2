package bno.swing2.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TestFrame {

	private static JFrame disp;

	public static void main(String[] args) {
		disp = new JFrame("Swing2 - Test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setSize(800, 600);
		disp.setLocationByPlatform(true);

		disp.setLayout(new BorderLayout());

		TestJPanel comp = new TestJPanel();
		disp.add(comp);

		disp.setVisible(true);
	}

}
