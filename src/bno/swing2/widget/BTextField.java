package bno.swing2.widget;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	private static final BColor DEF_COLOR_HINT = BColor.color("#aaaaaa");

	private JLabel hint;

	private FocusListener focusLisener;

	public BTextField(int cols) {

		super(cols);

		create();
	}

	public BTextField(String txt) {

		super(txt);

		create();
	}

	public BTextField(String txt, int cols) {

		super(txt, cols);

		create();
	}

	public BTextField() {

		create();
	}

	private void create() {

		hint = new JLabel();
		hint.setOpaque(false);
		hint.setVisible(true);
		hint.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
		hint.setVerticalAlignment(JLabel.CENTER);
		hint.setHorizontalAlignment(JLabel.LEFT);
		hint.setForeground(DEF_COLOR_HINT);

		// addFocusListener(createFocusListener());
	}

	private FocusListener createFocusListener() {

		if (focusLisener != null) {
			return focusLisener;
		}

		focusLisener = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

				saveRepaint();
			}

			@Override
			public void focusGained(FocusEvent e) {

				saveRepaint();
			}
		};

		return focusLisener;
	}

	private void saveRepaint() {

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if ((getText() == null || getText().isEmpty()) /* && !isFocusOwner() */) {

			paintHint(g);
		}
	}

	private void paintHint(Graphics g) {

		Insets insets = getInsets();

		int width, height, x, y;

		width = getWidth() - insets.left - insets.right;
		height = getHeight() - insets.top - insets.bottom;

		x = insets.left;
		y = insets.top;

		g = g.create(x, y, width, height);

		hint.setSize(width, height);
		hint.setLocation(0, 0);
		hint.paint(g);
	}

	public String getHint() {
		return hint.getText();
	}

	public void setHint(String hint) {
		this.hint.setText(hint);
	}

	public static void main(String[] args) {
		JFrame disp = new JFrame();
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLayout(new BorderLayout());

		BTextField textField1 = new BTextField();
		textField1.setHint("Hint");
		disp.add(textField1, BorderLayout.CENTER);

		BTextField textField2 = new BTextField();
		textField2.setHint("Mal was anderes");
		disp.add(textField2, BorderLayout.SOUTH);

		disp.pack();
		disp.setVisible(true);
	}
}
