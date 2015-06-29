package bno.swing2.widget.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Tab component that can be closed
 */
public class ClosableTabComponent extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel lblTitle;
	private JButton btnClose;

	private JTabbedPane tabbedPane;
	private ApplicationTab component;

	public ClosableTabComponent(JTabbedPane tabbedPane, ApplicationTab component) {

		this.tabbedPane = tabbedPane;
		this.component = component;

		createGui();
	}

	private void createGui() {

		setLayout(new BorderLayout());
		setOpaque(false);

		lblTitle = new JLabel();
		lblTitle.setOpaque(false);
		add(lblTitle, BorderLayout.CENTER);

		btnClose = new JButton("X");
		btnClose.setOpaque(false);
		btnClose.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));
		btnClose.setBorderPainted(true);
		btnClose.setContentAreaFilled(false);
		btnClose.addActionListener(this);
		btnClose.setForeground(Color.RED);
		add(btnClose, BorderLayout.EAST);
	}

	public void setTitle(String title) {

		lblTitle.setText(title);
	}

	public void close() {

		fireCloseEvent();
	}

	private void fireCloseEvent() {

		if (component instanceof ApplicationTab) {

			ApplicationTab appTab = (ApplicationTab) component;
			if (!appTab.isClosable()) {
				return;
			}

			appTab.closed();
		}

		tabbedPane.remove(component);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == btnClose) {

			fireCloseEvent();
		}
	}
}