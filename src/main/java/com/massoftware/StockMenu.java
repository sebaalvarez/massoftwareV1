package com.massoftware;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

public class StockMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092383121585372384L;

	public StockMenu() {
		super("Stock");
	}

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Archivos", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Editar", null);
		final MenuBar.MenuItem a3 = menubar.addItem("Comprobantes", null);
		final MenuBar.MenuItem a4 = menubar.addItem("Procesos", null);
		final MenuBar.MenuItem a5 = menubar.addItem("Informes", null);
		final MenuBar.MenuItem a6 = menubar.addItem("Ventana", null);
		final MenuBar.MenuItem a7 = menubar.addItem("Ayuda", null);

		a1.addItem("Productos ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Capítulos - Items de productos ...", null).setEnabled(false);
		a1.addItem("Marca - Gama de productos ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Productos conjunto ...", null).setEnabled(false);
		a1.addItem("Marcas ...", null).setEnabled(false);
		a1.addItem("Unidades de medida ...", null).setEnabled(false);
		a1.addItem("Códigos convenio multilateral ...", null);
		a1.addItem("Depósitos ...", null).setEnabled(false);
		a1.addItem("Sucursales ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("Fechas de cierre por módulos ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobantes ...", null).setEnabled(false);
		a1.addItem("Talonarios ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Configurar impresora ...", null).setEnabled(false);

		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);

		return menubar;
	}

}
