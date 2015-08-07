package bno.swing2.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.SwingUtilities;

import bno.swing2.utils.Null;

/**
 * A dialog for displaying {@link Throwable}s
 */
public class ExceptionDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static String okText = "OK";
	private static String showDetailsText = "Show details";
	private static String hideDetailsText = "Hide details";

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

	@Override
	public void pack() {
		super.pack();

		Dimension maximumSize = getMaximumSize();
		Dimension size = getSize();

		setSize(Math.min(size.width, maximumSize.width),
				Math.min(size.height, maximumSize.height));
	}

	private void createGui() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(400, 0));
		setMaximumSize(new Dimension(600, 450));
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

		JLabel lblExceptionClass = new JLabel(
				throwable.getClass().getCanonicalName());
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

		btnOk = new JButton(okText);
		btnOk.addActionListener(this);
		buttonPanel.add(btnOk);

		btnDetails = new JToggleButton(showDetailsText);
		btnDetails.addActionListener(this);
		buttonPanel.add(btnDetails);

		return buttonPanel;
	}

	@Override
	public void setTitle(String title) {

		super.setTitle(Null.nvl(title, "An error occoured"));
	}

	public void setMessage(String message) {

		String msg = Null.nvl(Null.nvl(message, throwable.getMessage()),
				"Unknown Error");

		lblMessage.setText(msg);
	}

	public static void setTexts(String okText, String hideDetailsText,
			String showDetailsText) {

		ExceptionDialog.okText = Null.nvl(okText, ExceptionDialog.okText);
		ExceptionDialog.hideDetailsText = Null.nvl(hideDetailsText,
				ExceptionDialog.hideDetailsText);
		ExceptionDialog.showDetailsText = Null.nvl(showDetailsText,
				ExceptionDialog.showDetailsText);
	}

	private void fireSwitchDetailsEvent(boolean showDetails) {

		btnDetails.setText((showDetails) ? hideDetailsText : showDetailsText);
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

		dialog.setLocationRelativeTo(owner);

		dialog.setVisible(true);
	}

	public static void showExceptionDialog(Component owner,
			Throwable throwable) {

		showExceptionDialog(owner, null, throwable.getMessage(), throwable);
	}

	public static void showExceptionDialog(Component owner, String title,
			String message, Throwable throwable) {

		showExceptionDialog(
				(owner == null) ? null
						: SwingUtilities.windowForComponent(owner),
				title, message, throwable);
	}

}
