package bno.swing2.awt.blink;

import java.util.EventListener;

public interface LinkListener extends EventListener {

	public void hover(Object Source, boolean hover);

	public void hactivated(Object Source);

}
