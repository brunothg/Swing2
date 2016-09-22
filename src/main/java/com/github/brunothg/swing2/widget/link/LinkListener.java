package com.github.brunothg.swing2.widget.link;

import java.util.EventListener;

public interface LinkListener extends EventListener {

	public void hover(Object Source, boolean hover);

	public void hactivated(Object Source);

}
