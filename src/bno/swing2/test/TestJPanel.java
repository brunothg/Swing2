package bno.swing2.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import bno.swing2.awt.BGraphics2D;
import bno.swing2.awt.colorchooser.BColorChooser;

public class TestJPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 6360279416330156160L;

	public TestJPanel() {
		super();
		setFocusable(true);
		addKeyListener(this);
		setLayout(new BorderLayout());
		setOpaque(true);

		BColorChooser cc = new BColorChooser();
		add(cc, BorderLayout.CENTER);

		cc.setColor(BColorChooser
				.showDialog(this, "Farbe aussuchen", Color.RED));
	}

	int zoom = 300;

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

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
