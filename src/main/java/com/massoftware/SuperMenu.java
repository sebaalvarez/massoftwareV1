package com.massoftware;

import org.vaadin.patrik.demo.DemoUI;

import com.massoftware.windows.provincias.WProvincias;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class SuperMenu extends AbstractMenu {

	public SuperMenu() {
		super("Super menu");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158479186088L;

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Ventas-Archivo", null);

		a1.addItem("Demo", openPaisesCmd());
		a1.addItem("Provincias", openProvinciasCmd());

		return menubar;
	}

	protected Command openPaisesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new Window();
				window.setCaption("Demo");
				window.setClosable(true);
				window.setWidth("100%");
				window.setHeight("100%");
				window.setContent(new DemoUI());								 
				getUI().addWindow(window);
			}
		};
	}

	protected Command openProvinciasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WProvincias();
				getUI().addWindow(window);
			}
		};
	}

}
