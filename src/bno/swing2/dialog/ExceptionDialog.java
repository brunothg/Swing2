package bno.swing2.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

/**
 * Ein Dialog zum Anzeigen von geworfenen {@link Throwable}s
 */
public class ExceptionDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Throwable throwable;
	private JLabel lblMessage;
	private JButton btnOk;
	private JToggleButton btnDetails;
	private JPanel detailInfoPanel;

	private ExceptionDialog(Window owner, Throwable throwable) {
		super(owner);
		this.throwable = throwable;

		createGui();
		setMessage(null);
		pack();
	}

	private void createGui() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(400, 0));
		setTitle(null);

		setLayout(new BorderLayout());

		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(borderPanel, BorderLayout.CENTER);

		borderPanel.add(createDetailInformationPanel(), BorderLayout.CENTER);
		borderPanel.add(createBasicInformationPanel(), BorderLayout.NORTH);
	}

	private JPanel createDetailInformationPanel() {

		detailInfoPanel = new JPanel();
		detailInfoPanel.setVisible(false);
		detailInfoPanel.setLayout(new BorderLayout());

		JScrollPane spDetail = new JScrollPane();
		detailInfoPanel.add(spDetail, BorderLayout.CENTER);

		JTextArea taDetail = new JTextArea();
		taDetail.setEditable(false);
		taDetail.setText(createDetailMessageString(throwable));
		spDetail.setViewportView(taDetail);

		return detailInfoPanel;
	}

	private JPanel createBasicInformationPanel() {

		JPanel basicInfoPanel = new JPanel();
		basicInfoPanel.setLayout(new BorderLayout());

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		basicInfoPanel.add(infoPanel, BorderLayout.CENTER);

		lblMessage = new JLabel();
		lblMessage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lblMessage.setVerticalAlignment(JLabel.TOP);
		lblMessage.setHorizontalAlignment(JLabel.LEFT);
		infoPanel.add(lblMessage, BorderLayout.CENTER);

		JLabel lblExceptionClass = new JLabel(throwable.getClass()
				.getCanonicalName());
		lblExceptionClass
				.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lblExceptionClass.setVerticalAlignment(JLabel.TOP);
		lblExceptionClass.setHorizontalAlignment(JLabel.LEFT);
		infoPanel.add(lblExceptionClass, BorderLayout.SOUTH);

		basicInfoPanel.add(createButtonRow(), BorderLayout.SOUTH);

		return basicInfoPanel;
	}

	private JPanel createButtonRow() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		buttonPanel.add(btnOk);

		btnDetails = new JToggleButton("Show Details");
		btnDetails.addActionListener(this);
		buttonPanel.add(btnDetails);

		return buttonPanel;
	}

	@Override
	public void setTitle(String title) {

		super.setTitle((title == null) ? "An error occoured" : title);
	}

	public void setMessage(String message) {

		String msg = (message == null) ? ((throwable == null) ? "Unknown error"
				: throwable.getMessage()) : message;

		lblMessage.setText(msg);
	}

	private void fireSwitchDetailsEvent(boolean showDetails) {

		btnDetails.setText((showDetails) ? "Hide Details" : "Show Detials");
		detailInfoPanel.setVisible(showDetails);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (source == btnOk) {

			dispose();
		} else if (source == btnDetails) {

			fireSwitchDetailsEvent(btnDetails.isSelected());
		}
	}

	private static String createDetailMessageString(Throwable throwable) {

		if (throwable == null) {
			return "";
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		throwable.printStackTrace(pw);
		pw.close();

		return sw.toString();
	}

	public static void showExceptionDialog(Window owner, String title,
			String message, Throwable throwable) {

		ExceptionDialog dialog = new ExceptionDialog(owner, throwable);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setTitle(title);
		dialog.setMessage(message);

		dialog.setVisible(true);
	}

	public static void showExceptionDialog(Component owner, Throwable throwable) {

		showExceptionDialog(owner, null, throwable.getMessage(), throwable);
	}

	public static void showExceptionDialog(Component owner, String title,
			String message, Throwable throwable) {

		showExceptionDialog(getWindowOfComponent(owner), title, message,
				throwable);
	}

	private static Window getWindowOfComponent(Component owner) {

		if (owner == null) {
			return null;
		}

		Container parent;
		while (!(owner instanceof Window)
				&& (parent = owner.getParent()) != null) {
			owner = parent;
		}

		if (owner instanceof Window) {

			return (Window) owner;
		} else {

			return null;
		}
	}

}
