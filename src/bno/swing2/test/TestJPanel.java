package bno.swing2.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import bno.swing2.awt.BGraphics2D;
import bno.swing2.awt.ColorChangeEvent;
import bno.swing2.awt.ColorChangeListener;
import bno.swing2.awt.Gradient.BGradientColorChooserWidget;
import bno.swing2.awt.hexcolorchooser.BHexColorChooserWidget;

public class TestJPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 6360279416330156160L;

	public TestJPanel() {
		super();
		setFocusable(true);
		addKeyListener(this);
		setLayout(new BorderLayout());

		comp = new BHexColorChooserWidget();
		comp.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void selectedColorChanged(ColorChangeEvent ev) {
				System.out.printf("Color changed %s -> %s%n", ev.getOldColor()
						.toString(), ev.getNewColor().toString());
			}

			@Override
			public void mouseOverColorChanged(ColorChangeEvent ev) {
				System.out.printf("Mouse over Color changed %s -> %s%n", (ev
						.getOldColor() != null) ? ev.getOldColor().toString()
						: "NULL", (ev.getNewColor() != null) ? ev.getNewColor()
						.toString() : "NULL");
			}
		});

		add(comp, BorderLayout.CENTER);

		BGradientColorChooserWidget gradient = new BGradientColorChooserWidget(
				Color.GREEN, BGradientColorChooserWidget.Y_AXIS);
		add(gradient, BorderLayout.EAST);
	}

	int zoom = 300;
	private BHexColorChooserWidget comp;

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

		comp.setSelectedColor(new Color((int) (Math.random() * 255),
				(int) (Math.random() * 255), (int) (Math.random() * 255)));
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
