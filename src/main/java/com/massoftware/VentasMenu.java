package com.massoftware;

import com.massoftware.windows.alicuotas.WAlicuotas;
import com.massoftware.windows.bloqueos_cliente.WBloqueosCliente;
import com.massoftware.windows.clasificaciones_clientes.WClasificacionesClientes;
import com.massoftware.windows.sub_cta_cte.WSubCtasCte;
import com.massoftware.windows.sucursales.WSucursales;
import com.massoftware.windows.talonarios.WTalonarios;
import com.massoftware.windows.tipos_Documento_AFIP.WTiposDocumentoAFIP;
import com.massoftware.windows.zonas.WZonas;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

public class VentasMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092383121985372384L;

	public VentasMenu() {
		super("Ventas");
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

		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);

		a1.addItem("Clientes ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Productos ...", null).setEnabled(false);
		a1.addItem("Lista de precios ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Cobranzas ...", null).setEnabled(false);
		a1.addItem("condiciones de ventas ...", null).setEnabled(false);
		a1.addItem("Bonificaciones ...", null).setEnabled(false);
		a1.addItem("Vendedores y zonas de ventas ...", null).setEnabled(false);
		a1.addItem("Zonas ...", openZonasCmd());
		a1.addItem("Canales de comercialización ...", null).setEnabled(false);
		a1.addItem("Transportes", null).setEnabled(false);
		a1.addItem("Convenios de elaboración ...", null).setEnabled(false);

		MenuBar.MenuItem a11 = a1.addItem("Ciudades ...", null);

		// a11.addItem("Ciudades ...", openWindowCmd(Ciudad.class));
		// a11.addItem("Provincias ...", openWindowCmd(Provincia.class));
		// a11.addItem("Paises ...", openWindowCmd(Pais.class));
		a11.addItem("Ciudades ...", null).setEnabled(false);
		a11.addItem("Provincias ...", null).setEnabled(false);
		a11.addItem("Paises ...", null).setEnabled(false);

		a1.addItem("Tipos de clientes ...", null).setEnabled(false);
		a1.addItem("Sub ctas. ctes. ...", openSubCtasCteCmd());
		a1.addItem("Clasificación de clientes (cta. cte.) ...", openClasifClieCtasCteCmd());
		a1.addItem("Bloqueo de clientes ...", openBloqueoClienteCmd());
		a1.addItem("Alícuotas ...", openAlicuotasCmd());
		a1.addItem("Cargas ...", null).setEnabled(false);
		a1.addItem("Depósitos ...", null).setEnabled(false);		
		a1.addItem("Sucursales ...", openSucursalesCmd());
		a1.addItem("Tipos de documentos AFIP ...", openTiposDocAFIPCmd());
		a11.addItem("Motivos notas de creditos", null).setEnabled(false);
		a1.addItem("Motivos comentarios", null).setEnabled(false);
		a1.addItem("Motivos notas de crédito", null).setEnabled(false);
		a1.addItem("Perfil de facturación ...", null).setEnabled(false);
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("AFIP ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobante", null).setEnabled(false);
		a1.addItem("Talonarios ...", openTalonariosCmd());
		a1.addSeparator();
		a1.addItem("Configurar impresora ...", null).setEnabled(false);

		return menubar;
	}
	
	protected Command openClasifClieCtasCteCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WClasificacionesClientes();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openBloqueoClienteCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WBloqueosCliente();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openSubCtasCteCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WSubCtasCte();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openAlicuotasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WAlicuotas();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openTiposDocAFIPCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WTiposDocumentoAFIP();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openZonasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WZonas();
				getUI().addWindow(window);
			}
		};
	}
	
	
	protected Command openSucursalesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WSucursales();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openTalonariosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WTalonarios();
				getUI().addWindow(window);
			}
		};
	}

}
