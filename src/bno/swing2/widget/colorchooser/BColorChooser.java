package bno.swing2.widget.colorchooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import bno.swing2.BColor;

public class BColorChooser extends JColorChooser {

	private static final long serialVersionUID = 2889510211457614799L;

	public BColorChooser() {
		super();
		addHex();
	}

	public BColorChooser(ColorSelectionModel model) {
		super(model);
		addHex();
	}

	public BColorChooser(BColor initialColor) {
		super(initialColor);
		addHex();
	}

	private void addHex() {

		AbstractColorChooserPanel[] panelsAr = getChooserPanels();

		List<AbstractColorChooserPanel> panels = new ArrayList<AbstractColorChooserPanel>(
				panelsAr.length + 1);

		for (AbstractColorChooserPanel pl : panelsAr) {
			panels.add(pl);
		}

		panels.add(0, new BHexColorChooserPanel());

		setChooserPanels(panels.toArray(new AbstractColorChooserPanel[panels
				.size()]));
	}

	/**
	 * @see javax.swing.JColorChooser#showDialog
	 * 
	 * @param component
	 *            the parent Component for the dialog
	 * @param title
	 *            the String containing the dialog's title
	 * @param initialColor
	 *            the initial Color set when the color-chooser is shown
	 * @return the selected color or null if the user opted out
	 * @throws HeadlessException
	 */
	public static Color showDialog(Component component, String title,
			Color initialColor) throws HeadlessException {

		final BColorChooser pane = new BColorChooser(new BColor(
				initialColor != null ? initialColor : Color.WHITE));

		ColorTracker ok = new ColorTracker(pane);
		JDialog dialog = createDialog(component, title, true, pane, ok, null);

		dialog.setVisible(true);

		return ok.getColor();
	}

	/**
	 * 
	 * @see javax.swing.JColorChooser#createDialog
	 * 
	 * @param c
	 *            the parent component for the dialog
	 * @param title
	 *            the title for the dialog
	 * @param modal
	 *            a boolean. When true, the remainder of the program is inactive
	 *            until the dialog is closed.
	 * @param chooserPane
	 *            the color-chooser to be placed inside the dialog
	 * @param okListener
	 *            the ActionListener invoked when "OK" is pressed
	 * @param cancelListener
	 *            the ActionListener invoked when "Cancel" is pressed
	 * @return a new dialog containing the color-chooser pane
	 * @exception HeadlessException
	 *                if GraphicsEnvironment.isHeadless() returns true.
	 */
	public static JDialog createDialog(Component c, String title,
			boolean modal, final JColorChooser chooserPane,
			ActionListener okListener, ActionListener cancelListener)
			throws HeadlessException {

		// Window window = JOptionPane.getWindowForComponent(c);
		// ColorChooserDialog dialog;
		// if (window instanceof Frame) {
		// dialog = new ColorChooserDialog((Frame) window, title, modal, c,
		// chooserPane, okListener, cancelListener);
		// } else {
		// dialog = new ColorChooserDialog((Dialog) window, title, modal, c,
		// chooserPane, okListener, cancelListener);
		// }
		// dialog.getAccessibleContext().setAccessibleDescription(title);
		// return dialog;

		final Color initialColor = chooserPane.getColor();

		Window window = getWindowForComponent(c);
		final JDialog ret;
		if (window instanceof Frame) {
			ret = new JDialog((Frame) window, title, modal);
		} else {
			ret = new JDialog((Dialog) window, title, modal);
		}

		ret.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		Locale locale = window.getLocale();
		String okString = UIManager.getString("ColorChooser.okText", locale);
		String cancelString = UIManager.getString("ColorChooser.cancelText",
				locale);
		String resetString = UIManager.getString("ColorChooser.resetText",
				locale);

		ret.setLayout(new BorderLayout());
		ret.add(chooserPane, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ret.add(buttons, BorderLayout.SOUTH);

		JButton okB = new JButton(okString);
		okB.getAccessibleContext().setAccessibleDescription(okString);
		okB.setActionCommand("OK");
		okB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ret.setVisible(false);
			}
		});
		if (okListener != null) {
			okB.addActionListener(okListener);
		}
		buttons.add(okB);

		final JButton cancelB = new JButton(cancelString);
		cancelB.getAccessibleContext().setAccessibleDescription(cancelString);

		// The following few lines are used to register esc to close the dialog
		Action cancelKeyAction = new AbstractAction() {
			private static final long serialVersionUID = 6313146026842426365L;

			public void actionPerformed(ActionEvent e) {
				cancelB.doClick();
			}
		};
		KeyStroke cancelKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0);
		InputMap inputMap = cancelB
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = cancelB.getActionMap();
		if (inputMap != null && actionMap != null) {
			inputMap.put(cancelKeyStroke, "cancel");
			actionMap.put("cancel", cancelKeyAction);
		}
		// end esc handling

		cancelB.setActionCommand("cancel");
		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret.setVisible(false);
			}
		});
		if (cancelListener != null) {
			cancelB.addActionListener(cancelListener);
		}
		buttons.add(cancelB);

		JButton resetB = new JButton(resetString);
		resetB.getAccessibleContext().setAccessibleDescription(resetString);
		resetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooserPane.setColor(initialColor);
			}
		});
		buttons.add(resetB);

		if (JDialog.isDefaultLookAndFeelDecorated()) {
			boolean supportsWindowDecorations = UIManager.getLookAndFeel()
					.getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				ret.getRootPane().setWindowDecorationStyle(
						JRootPane.COLOR_CHOOSER_DIALOG);
			}
		}
		ret.applyComponentOrientation(((c == null) ? ret.getRootPane() : c)
				.getComponentOrientation());

		ret.pack();
		ret.setLocationRelativeTo(c);
		ret.getRootPane().setDefaultButton(okB);
		ret.getAccessibleContext().setAccessibleDescription(title);

		return ret;
	}

	static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException {
		if (parentComponent == null)
			return JOptionPane.getRootFrame();
		if (parentComponent instanceof Frame
				|| parentComponent instanceof Dialog)
			return (Window) parentComponent;
		return getWindowForComponent(parentComponent.getParent());
	}

}

class ColorTracker implements ActionListener, Serializable {
	private static final long serialVersionUID = 5196860353241491749L;
	JColorChooser chooser;
	Color color;

	public ColorTracker(JColorChooser c) {
		chooser = c;
	}

	public void actionPerformed(ActionEvent e) {
		color = chooser.getColor();
	}

	public Color getColor() {
		return color;
	}
}