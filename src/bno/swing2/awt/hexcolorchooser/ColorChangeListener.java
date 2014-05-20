package bno.swing2.awt.hexcolorchooser;

import java.util.EventListener;

public interface ColorChangeListener extends EventListener {

	/**
	 * 
	 * 
	 * @param newColor
	 *            Color that will be the new selected color
	 * @param oldColor
	 *            Color that was selected before
	 */
	public void selectedColorChanged(ColorChangeEvent event);

	/**
	 * 
	 * @param newColor
	 *            Color that will be the new mouse over color
	 * @param oldColor
	 *            Color that was mouse over color before
	 */
	public void mouseOverColorChanged(ColorChangeEvent event);

}
