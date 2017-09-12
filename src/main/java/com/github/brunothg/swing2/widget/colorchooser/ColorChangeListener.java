package com.github.brunothg.swing2.widget.colorchooser;

import java.util.EventListener;

public interface ColorChangeListener extends EventListener {

	/**
	 * Selected color has changed.
	 * 
	 * @param event
	 *            The {@link ColorChangeEvent}
	 */
	public void selectedColorChanged(ColorChangeEvent event);

	/**
	 * Targeted color changed. The color currently under the mouse cursor
	 * changed.
	 * 
	 * @param event
	 *            The {@link ColorChangeEvent}
	 */
	public void mouseOverColorChanged(ColorChangeEvent event);

}
