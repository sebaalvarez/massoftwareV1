package com.massoftware;

import com.massoftware.windows.LogAndNotification;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractMenu extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1980557664437735354L;

	public AbstractMenu(String title) {

		try {

			preinit();

			setMargin(true);
			// setSpacing(true);

			Label h1 = new Label(title);
			h1.addStyleName(ValoTheme.LABEL_H1);
			h1.addStyleName(ValoTheme.LABEL_COLORED);
			addComponent(h1);

			addComponent(getMenuBar());

			HorizontalLayout hl = getControlBar();
			if (hl != null) {
				addComponent(hl);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void preinit() {

	}

	abstract protected MenuBar getMenuBar();

	protected HorizontalLayout getControlBar() throws Exception {
		return null;
	}

	protected AbstractMenu getThis() {
		return this;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
