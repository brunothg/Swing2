package com.github.brunothg.swing2.widget;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.brunothg.swing2.common.BColor;

/**
 * A text field that can show a hint inside the text field itself (Like
 * placeholder of HTML input).
 * 
 * @author Marvin Bruns
 *
 */
public class BTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	private static final BColor DEF_COLOR_HINT = BColor.color("#999999");

	private JLabel hint;
	private boolean ignoreHintFocus;

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

		setIgnoreHintFocus(false);
		setIgnoreRepaint(false);

		hint = new JLabel();
		hint.setOpaque(false);
		hint.setVisible(true);
		hint.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
		hint.setVerticalAlignment(JLabel.CENTER);
		hint.setHorizontalAlignment(JLabel.LEFT);
		hint.setForeground(DEF_COLOR_HINT);

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

		if ((getText() == null || getText().isEmpty())
				&& (!isFocusOwner() || isIgnoreHintFocus())) {

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

	public void setHintAlignment(int horizontalAlignment, int verticalAlignment) {

		hint.setHorizontalAlignment(horizontalAlignment);
		hint.setVerticalAlignment(verticalAlignment);
	}

	/**
	 * 
	 * @return int[horizontalAlignment, verticalAlignment]
	 */
	public int[] getHintAlignment() {

		return new int[] { hint.getHorizontalAlignment(),
				hint.getVerticalAlignment() };
	}

	public String getHint() {
		return hint.getText();
	}

	public void setHint(String hint) {
		this.hint.setText(hint);
	}

	public boolean isIgnoreHintFocus() {
		return ignoreHintFocus;
	}

	/**
	 * Influences the situations in which the hint is visible. If the focus is
	 * ignored, the hint is only invisible if there is some text in this text
	 * field. Otherwise the hint is also invisible if the text field accepts
	 * keyboard inputs. The default is true.
	 * 
	 * @param ignoreHintFocus
	 *            if true the hint is only invisible if there is some text in
	 *            this text field
	 */
	public void setIgnoreHintFocus(boolean ignoreHintFocus) {
		this.ignoreHintFocus = ignoreHintFocus;

		if (!this.ignoreHintFocus) {
			addFocusListener(createFocusListener());
		} else {
			removeFocusListener(focusLisener);
		}
	}
}
