package com.github.brunothg.swing2.widget.tab;

import java.util.EventListener;

public interface CloseListener extends EventListener {

	public void closing(Object source);

	public void closed(Object source);
}
