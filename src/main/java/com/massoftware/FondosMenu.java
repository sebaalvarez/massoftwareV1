package com.massoftware;

import java.io.File;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.aperturas_cierres_cajas.WAperturasCierresCajas;
import com.massoftware.windows.bancos.WBancos;
import com.massoftware.windows.cajas.WCajas;
import com.massoftware.windows.captura_lotes_tickets.WCapturaLotesTickets;
import com.massoftware.windows.chequeras.WChequeras;
import com.massoftware.windows.cobranzas.WCobranzas;
import com.massoftware.windows.comprobante_de_fondo.WComprobanteDeFondo;
import com.massoftware.windows.comprobantes_emitidos.WComprobantesEmitidos;
import com.massoftware.windows.comprobantes_ventas_compras_pendientes_fondos.WComprobantesVentasComprasPendientesFondos;
import com.massoftware.windows.conciliaciones_bancarias.WConciliacionesBancarias;
import com.massoftware.windows.cuentas_fondo.WCuentasFondo;
import com.massoftware.windows.fecha_cierre_x_modulo.WFechaCierreXModulo;
import com.massoftware.windows.firmantes.WFirmantes;
import com.massoftware.windows.jurisdicciones_convenio_multilateral.WJurisdiccionesConvenioMultilateral;
import com.massoftware.windows.marcas_tickets.WMarcasTickets;
import com.massoftware.windows.modelos_cbtes_fondos.WModelosCbtesFondos;
import com.massoftware.windows.modelos_tickets.WModelosTickets;
import com.massoftware.windows.monedas.WMonedas;
import com.massoftware.windows.monedas_cotizaciones.WMonedasCotizaciones;
import com.massoftware.windows.sucursales.WSucursales;
import com.massoftware.windows.talonarios.WTalonarios;
import com.massoftware.windows.tipos_comprobantes.WTiposComprobantes;
import com.massoftware.windows.valores_propios.WValoresPropios;
import com.massoftware.windows.valores_terceros.WValoresTerceros;
import com.massoftware.windows.zonas.WZonas;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class FondosMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158479186080L;

	public FondosMenu() {
		super("Fondos");

		this.addShortcutListener(new ShortcutListener("F5", KeyCode.F5, new int[] {}) {

			private static final long serialVersionUID = -79140067012371655L;

			@Override
			public void handleAction(Object sender, Object target) {
				try {
					Window window = new WCuentasFondo();
					getUI().addWindow(window);
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
	}

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Archivos", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Editar", null);
		final MenuBar.MenuItem a3 = menubar.addItem("Comprobantes", null);
		final MenuBar.MenuItem a4 = menubar.addItem("Instrumentos de pago/cobro", null);
		final MenuBar.MenuItem a5 = menubar.addItem("Procesos", null);
		final MenuBar.MenuItem a6 = menubar.addItem("Informes", null);
		final MenuBar.MenuItem a7 = menubar.addItem("Ventana", null);
		final MenuBar.MenuItem a8 = menubar.addItem("Ayuda", null);

		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);
		a8.setEnabled(false);

		a1.addItem("Cuentas de fondo ...", openCuentasFondoCmd());
		a1.addItem("Rubros y grupos de cuentas ...", openCuentasFondoCmd());
		a1.addItem("Cobranzas ...", openTiposCobranzasCmd()).setEnabled(false);
		a1.addItem("Chequeras ...", openChequerasCmd()).setEnabled(false);
		a1.addItem("Bancos ...", openBancosCmd());
		a1.addItem("Firmantes (cheques propios) ...", openFirmantesCmd());
		a1.addItem("Cajas", openCajasCmd());
		a1.addItem("Monedas ...", openMonedasCmd()).setEnabled(false);
		a1.addItem("Cotizaciones de monedas ...", openMonedasCotizacionesCmd()).setEnabled(false);
		a1.addItem("Modelos de comprobantes", openModelosCbtesFondosCmd()).setEnabled(false);
		a1.addItem("Sucursales ...", openSucursalesCmd());
		a1.addItem("Juridicciones convenio multilateral", openJurisdiccionesConvenioMultilateralCmd())
				.setEnabled(false);
		a1.addSeparator();
		a1.addItem("Marcas de ticket's ...", openMarcasTicketsCmd()).setEnabled(false);
		a1.addItem("Series de ticket's ...", openModelosTicketsCmd()).setEnabled(false);
		a1.addItem("Ticket's denunciados ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobante", openTiposComprobantesCmd()).setEnabled(false);
		a1.addItem("Talonarios ...", openTalonariosCmd());
		a1.addSeparator();
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("Fechas de cierres por módulos", openFechaCierreXModuloCmd()).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Configurar impresora ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Salir", null).setEnabled(false);

		// a3.addItem("Comprobante de fondo ...", openComprobanteDeFondoCmd());
		// a3.addItem("Transferencia valores propios / terceros ...",
		// openComprobantesEmitidosCmd()).setEnabled(false);
		// a3.addItem("Revisión de comprobante ...", openComprobantesEmitidosCmd())
		// .setEnabled(false);
		// a3.addItem("Comprobantes pendientes de fondos ...",
		// openComprobantesVentasComprasPendientesFondosCmd());
		// a3.addSeparator();
		// a3.addItem("Comprobantes emitidos", openComprobantesEmitidosCmd());
		// a3.addSeparator();
		// a3.addItem("Captura de ticket's", openCapturaLotesTicketsCmd());
		//
		// a4.addItem("Valores propios ...", openValoresPropiosTicketsCmd());
		// a4.addItem("Valores de terceros ...", openValoresTercerosTicketsCmd());
		// a4.addItem("Tarjetas de crédito ...", openValoresPropiosTicketsCmd())
		//// .setEnabled(false);
		//
		// a5.addItem("Apertura y cierre de caja ...",
		// openAperturasCierresCajasCmd());
		// final MenuBar.MenuItem a5_1 = a5
		// .addItem("Cierre de cobranza ...", null);
		// a5_1.addItem("Cierre tesorería ...", openTiposCobranzasCTCmd());
		// a5_1.addItem("Cierre de cuentas corrientes ...",
		// openTiposCobranzasCCCCmd());
		// a5_1.addItem("Cierre de cobranza ...", openTiposCobranzasCCCmd());
		// a5.addItem("Conciliación bancaria ...",
		// openConciliacionesBancariasCmd());

		return menubar;
	}

	@SuppressWarnings("serial")
	protected HorizontalLayout getControlBar() throws Exception {

		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		row.setSpacing(true);

		Button cuentasDeFondosBTN = new Button("");
		cuentasDeFondosBTN.addStyleName(ValoTheme.BUTTON_HUGE);
		cuentasDeFondosBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		cuentasDeFondosBTN.setDescription("Cuentas de fondos (F5)");
		File fileImg = new File(BackendContextPG.get().getIconosPath() + File.separatorChar + "tree-table.png");
		cuentasDeFondosBTN.setIcon(new FileResource(fileImg));
		cuentasDeFondosBTN.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					Window window = new WCuentasFondo();
					getUI().addWindow(window);
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		row.addComponent(cuentasDeFondosBTN);

		// Button comprobantesDeFondos = new Button("");
		// comprobantesDeFondos.addStyleName(ValoTheme.BUTTON_HUGE);
		// comprobantesDeFondos.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		// comprobantesDeFondos.setDescription("Comprobantes de fondos (F6)");
		// comprobantesDeFondos.setIcon(FontAwesome.APPLE);
		// comprobantesDeFondos.addClickListener(new Button.ClickListener() {
		// public void buttonClick(ClickEvent event) {
		// Notification.show("Clicked "
		// + event.getButton().getDescription());
		// }
		// });
		// row.addComponent(comprobantesDeFondos);

		// Button mayor = new Button("");
		// mayor.addStyleName(ValoTheme.BUTTON_HUGE);
		// mayor.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		// mayor.setDescription("Consulta de mayor de cuenta (F7)");
		// mayor.setIcon(FontAwesome.APPLE);
		// mayor.addClickListener(new Button.ClickListener() {
		// public void buttonClick(ClickEvent event) {
		// Notification.show("Clicked "
		// + event.getButton().getDescription());
		// }
		// });
		// row.addComponent(mayor);

		return row;
	}

	protected Command openBancosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WBancos();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openFirmantesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WFirmantes();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openCajasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCajas();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openMonedasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WMonedas();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openMonedasCotizacionesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WMonedasCotizaciones();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openModelosCbtesFondosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WModelosCbtesFondos();
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

	protected Command openJurisdiccionesConvenioMultilateralCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WJurisdiccionesConvenioMultilateral();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openMarcasTicketsCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WMarcasTickets();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openModelosTicketsCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WModelosTickets();
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

	protected Command openTiposComprobantesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WTiposComprobantes();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openTiposCobranzasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCobranzas();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openChequerasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WChequeras();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openCuentasFondoCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCuentasFondo();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openComprobantesEmitidosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WComprobantesEmitidos();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openComprobantesVentasComprasPendientesFondosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WComprobantesVentasComprasPendientesFondos();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openCapturaLotesTicketsCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCapturaLotesTickets();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openValoresPropiosTicketsCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WValoresPropios();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openValoresTercerosTicketsCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WValoresTerceros();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openFechaCierreXModuloCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WFechaCierreXModulo();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openAperturasCierresCajasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WAperturasCierresCajas();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openTiposCobranzasCTCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCobranzas(WCobranzas.CT_MODE);
				getUI().addWindow(window);
			}
		};
	}

	protected Command openTiposCobranzasCCCCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCobranzas(WCobranzas.CCC_MODE);
				getUI().addWindow(window);
			}
		};
	}

	protected Command openTiposCobranzasCCCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCobranzas(WCobranzas.CC_MODE);
				getUI().addWindow(window);
			}
		};
	}

	protected Command openConciliacionesBancariasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WConciliacionesBancarias();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openComprobanteDeFondoCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WComprobanteDeFondo();
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

}
