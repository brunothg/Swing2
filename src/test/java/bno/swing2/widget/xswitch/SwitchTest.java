package bno.swing2.widget.xswitch;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class SwitchTest {

	public static void main(String[] args) {

		JFrame disp = new JFrame("Tab Test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLocationRelativeTo(null);
		disp.setSize(800, 300);
		disp.setLayout(new BorderLayout());

		BIOSwitch bioSwitch = new BIOSwitch("ON", "OFF");
		bioSwitch.setSelected(true);
		disp.add(bioSwitch, BorderLayout.CENTER);

		disp.setVisible(true);
	}
}
