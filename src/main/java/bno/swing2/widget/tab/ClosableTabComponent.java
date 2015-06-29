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

		btnClose = new JButton("\u2716");
		btnClose.setOpaque(false);
		btnClose.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));
		btnClose.setBorderPainted(true);
		btnClose.setContentAreaFilled(false);
		btnClose.addActionListener(this);
		add(btnClose, BorderLayout.EAST);
	}

	public void setTitle(String title) {

		lblTitle.setText(title);
	}

	public void setClosable(boolean closable) {

		btnClose.setEnabled(closable);
	}

	public boolean isClosabel() {

		return btnClose.isEnabled();
	}

	public void setCloseChar(char c) {

		btnClose.setText("" + c);
	}

	public char getCloseChar() {

		return btnClose.getText().charAt(0);
	}

	public void setCloseColor(Color c) {

		btnClose.setForeground(c);
	}

	public Color getCloseColor() {

		return btnClose.getForeground();
	}

	public void close() {

		fireCloseingEvent();

		tabbedPane.remove(component);

		fireClosedEvent();
	}

	public void addCloseListener(CloseListener l) {

		listenerList.add(CloseListener.class, l);
	}

	public void removeCloseListener(CloseListener l) {

		listenerList.remove(CloseListener.class, l);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == btnClose) {

			close();
		}
	}

	private void fireCloseingEvent() {

		CloseListener[] listeners = listenerList
				.getListeners(CloseListener.class);
		for (CloseListener listener : listeners) {

			listener.closing(this);
		}
	}

	private void fireClosedEvent() {

		CloseListener[] listeners = listenerList
				.getListeners(CloseListener.class);
		for (CloseListener listener : listeners) {

			listener.closed(this);
		}
	}
}