package bno.swing2.awt.Gradient;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class BasicBGradientColorChooserWidgetUI extends
		BGradientColorChooserWidgetUI {

	public static ComponentUI createUI(JComponent c) {
		return new BasicBGradientColorChooserWidgetUI();
	}

	public void installUI(JComponent c) {
		installUI((BGradientColorChooserWidget) c);
	}

	private void installUI(BGradientColorChooserWidget c) {
	}

	public void uninstallUI(JComponent c) {
		uninstallUI((BGradientColorChooserWidget) c);
	}

	private void uninstallUI(BGradientColorChooserWidget c) {
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		paint(g, (BGradientColorChooserWidget) c);
	}

	private void paint(Graphics g, BGradientColorChooserWidget c) {
		g.setColor(c.getColor());
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {

		return getPrefferedSize((BGradientColorChooserWidget) c);
	}

	private Dimension getPrefferedSize(BGradientColorChooserWidget c) {
		Dimension ret = new Dimension(100, c.getMaximumSubdivisions() * 5);

		return ret;
	}

	@Override
	public Dimension getMinimumSize(JComponent c) {
		return getMinimumSize((BGradientColorChooserWidget) c);
	}

	private Dimension getMinimumSize(BGradientColorChooserWidget c) {
		Dimension ret = new Dimension(100, c.getMaximumSubdivisions());

		return ret;
	}

	@Override
	public Dimension getMaximumSize(JComponent c) {
		Dimension ret = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

		return ret;
	}

}
