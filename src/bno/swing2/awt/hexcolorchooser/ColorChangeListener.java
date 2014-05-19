package bno.swing2.awt.hexcolorchooser;

import java.awt.Color;
import java.util.EventListener;

public interface ColorChangeListener extends EventListener {

	/**
	 * 
	 * @param newColor
	 *            Color that will be the new selected Color
	 */
	public void selectedColorChanged(Color newColor, Color oldColor);

}
