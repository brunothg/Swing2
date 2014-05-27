package bno.swing2.test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bno.swing2.awt.BColor;
import bno.swing2.awt.BGraphics2D;
import bno.swing2.awt.ColorChangeEvent;
import bno.swing2.awt.ColorChangeListener;
import bno.swing2.awt.colorchooser.gradient.BGradientColorChooserWidget;
import bno.swing2.awt.colorchooser.hexcolorchooser.BHexColorChooserWidget;

public class TestJPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 6360279416330156160L;
	private BGradientColorChooserWidget gradient2;

	public TestJPanel() {
		super();
		setFocusable(true);
		addKeyListener(this);
		setLayout(new BorderLayout());

		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		add(top, BorderLayout.NORTH);

		comp = new BHexColorChooserWidget();

		final JLabel hexColorName = new JLabel(comp.getSelectedColor()
				.toHexString());
		top.add(hexColorName);

		final JLabel colorName = new JLabel(comp.getSelectedColor()
				.toRGBString());
		top.add(colorName);

		final JLabel color = new JLabel("Selected Color");
		color.setOpaque(true);
		color.setBackground(comp.getSelectedColor());
		color.setForeground(comp.getSelectedColor().invertRGB());
		top.add(color);

		comp.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void selectedColorChanged(ColorChangeEvent ev) {
				System.out.printf("Hex Color changed %s -> %s%n", ev
						.getOldColor().toString(), ev.getNewColor().toString());
				hexColorName.setText(ev.getNewColor().toHexString());
				colorName.setText(ev.getNewColor().toRGBString());
				color.setBackground(ev.getNewColor());
				color.setForeground(ev.getNewColor().invertRGB());
				gradient.setColor(ev.getNewColor());
				gradient2.setColor(ev.getNewColor());
			}

			@Override
			public void mouseOverColorChanged(ColorChangeEvent ev) {
				System.out.printf("Hex Mouse over Color changed %s -> %s%n",
						(ev.getOldColor() != null) ? ev.getOldColor()
								.toString() : "NULL",
						(ev.getNewColor() != null) ? ev.getNewColor()
								.toString() : "NULL");
			}
		});

		add(comp, BorderLayout.CENTER);

		gradient = new BGradientColorChooserWidget(new BColor(
				comp.getSelectedColor()), BGradientColorChooserWidget.Y_AXIS);
		add(gradient, BorderLayout.EAST);

		gradient2 = new BGradientColorChooserWidget(new BColor(
				comp.getSelectedColor()), BGradientColorChooserWidget.X_AXIS);
		gradient2.setMaximumSubdivisions(40);
		add(gradient2, BorderLayout.SOUTH);

		ColorChangeListener gccl = new ColorChangeListener() {

			@Override
			public void selectedColorChanged(ColorChangeEvent ev) {
				System.out.printf("Grad Color changed %s -> %s%n", ev
						.getOldColor().toString(), ev.getNewColor().toString());
				hexColorName.setText(ev.getNewColor().toHexString());
				colorName.setText(ev.getNewColor().toRGBString());
				color.setBackground(ev.getNewColor());
				color.setForeground(ev.getNewColor().invertRGB());

				gradient2.setColor(ev.getNewColor());
				gradient.setColor(ev.getNewColor());
			}

			@Override
			public void mouseOverColorChanged(ColorChangeEvent ev) {
				System.out.printf("Grad Mouse over Color changed %s -> %s%n",
						(ev.getOldColor() != null) ? ev.getOldColor()
								.toString() : "NULL",
						(ev.getNewColor() != null) ? ev.getNewColor()
								.toString() : "NULL");
			}
		};

		gradient.addColorChangeListener(gccl);
		gradient2.addColorChangeListener(gccl);
	}

	int zoom = 300;
	private BHexColorChooserWidget comp;
	private BGradientColorChooserWidget gradient;

	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());

		BGraphics2D.drawHexagon(g, getForeground(), getWidth() / 2 - zoom / 2,
				getHeight() / 2, zoom, 0);
		BGraphics2D.fillHexagon(g, getForeground(), getWidth() / 2 + zoom / 2,
				getHeight() / 2, zoom, 0);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_RIGHT:
			zoom++;
			repaint();
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_LEFT:
			zoom--;
			repaint();
			break;
		}

		comp.setSelectedColor(new BColor((int) (Math.random() * 255),
				(int) (Math.random() * 255), (int) (Math.random() * 255)));
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
