package bno.swing2.widget.tab;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TabTest {

	public static void main(String[] args) {

		JFrame disp = new JFrame("Tab Test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLocationRelativeTo(null);
		disp.setSize(800, 600);
		disp.setLayout(new BorderLayout());

		ApplicationTabPanel tabPanel = new ApplicationTabPanel();
		disp.add(tabPanel, BorderLayout.CENTER);

		ApplicationTab closableTab = new ApplicationTab();
		closableTab.setClosable(true);
		closableTab.setTitle("Closable Tab");
		tabPanel.openTab(closableTab, true);

		ApplicationTab notClosableTab = new ApplicationTab();
		notClosableTab.setClosable(false);
		notClosableTab.setTitle("Not Closable Tab");
		tabPanel.openTab(notClosableTab, true);

		disp.setVisible(true);
	}
}
