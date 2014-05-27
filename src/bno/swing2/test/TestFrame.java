package bno.swing2.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class TestFrame {

	private static JFrame disp;

	public static void main(String[] args) {
		// sys();

		disp = new JFrame("Swing2 - Test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setSize(800, 600);
		disp.setLocationByPlatform(true);

		disp.setLayout(new BorderLayout());

		TestJPanel comp = new TestJPanel();
		disp.add(comp);

		disp.setVisible(true);
	}

	public static void sys() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void nimb() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
