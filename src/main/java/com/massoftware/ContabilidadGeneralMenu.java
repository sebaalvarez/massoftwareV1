package com.massoftware;

import com.massoftware.model.AsientosModeloFiltro;
import com.massoftware.model.CentrosCostoContableFiltro;
import com.massoftware.model.CuentasContableFiltro;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntosEquilibrioFiltro;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.asientos_modelo.WAsientosModelo;
import com.massoftware.windows.centros_costo_contable.WCentrosCostoContable;
import com.massoftware.windows.cuentas_contable.WCuentasContable;
import com.massoftware.windows.ejercicios_contables.WEjerciciosContables;
import com.massoftware.windows.puntos_equilibrio.WPuntosEquilibrio;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ContabilidadGeneralMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8506821800939861972L;

	public ContabilidadGeneralMenu() {
		super("Contabilidad general");

		this.addShortcutListener(new ShortcutListener("F5", KeyCode.F5, new int[] {}) {

			private static final long serialVersionUID = -79140067012371655L;

			@Override
			public void handleAction(Object sender, Object target) {
				try {

				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});

		this.addShortcutListener(new ShortcutListener("F6", KeyCode.F6, new int[] {}) {

			private static final long serialVersionUID = 6542661935089665066L;

			@Override
			public void handleAction(Object sender, Object target) {
				try {

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
		final MenuBar.MenuItem a3 = menubar.addItem("Asientos", null);
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

		// a1.addItem("Plan de cuentas (orden -> cta de jerarquía) ...", null)
		// .setEnabled(false);
		a1.addItem("Plan de cuentas ...", openCuentasContableCmd());
		a1.addItem("Ejercicios contables ...", openEjerciciosContablesCmd());
		a1.addItem("Modelos de asientos", openAsientosModeloCmd());
		a1.addItem("Centros de costos ...", openCentrosCostoContableCmd());
		a1.addItem("Puntos de equilibrio ...", openPuntosEquilibrioCmd());
		a1.addSeparator();
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("Fecha de cierre por módulos", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Especificar impresora ...", null).setEnabled(false);

		// nuevoAsientoMI = a3.addItem("Nuevo asiento ...", null);
		a3.addItem("Asientos realizados ...", null);
		// asientos.addItem("Lotes de asientos importados ...", null);
		// asientos.addItem("Anulación de asientos ...", null);

		// procesos.addItem("Control de asientos ...", null);
		// procesos.addSeparator();
		// procesos.addItem("Cierre contabilidad", null);

		// informes.addItem("Balance general", null);
		// informes.addItem("Balance de comproboación de saldos", null);
		// informes.addItem("Informe estado de resultados", null);
		// informes.addSeparator();
		// informes.addItem("Libro diario", null);
		// informes.addItem("Mayor", null);
		// informes.addSeparator();
		// informes.addItem("Plan de cuenta ...", null);

		return menubar;
	}

	protected HorizontalLayout getControlBar() throws Exception {

		return UtilUI.buildHL();
	}

	protected Command openEjerciciosContablesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Window window = new WEjerciciosContables();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openPuntosEquilibrioCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				EjercicioContable ejercicioContable = new EjercicioContable();
				ejercicioContable.setId("2015");

				PuntosEquilibrioFiltro filtro = new PuntosEquilibrioFiltro();
				filtro.setEjercicioContable(ejercicioContable);

				Window window = new WPuntosEquilibrio(filtro);
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openCentrosCostoContableCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				EjercicioContable ejercicioContable = new EjercicioContable();
				ejercicioContable.setId("2015");

				CentrosCostoContableFiltro filtro = new CentrosCostoContableFiltro();
				filtro.setEjercicioContable(ejercicioContable);

				Window window = new WCentrosCostoContable(filtro);
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openCuentasContableCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				EjercicioContable ejercicioContable = new EjercicioContable();
				ejercicioContable.setId("2015");

				CuentasContableFiltro filtro = new CuentasContableFiltro();
				filtro.setEjercicioContable(ejercicioContable);

				Window window = new WCuentasContable(filtro);
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openAsientosModeloCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				EjercicioContable ejercicioContable = new EjercicioContable();
				ejercicioContable.setId("2017");

				AsientosModeloFiltro filtro = new AsientosModeloFiltro();
				filtro.setEjercicioContable(ejercicioContable);

				Window window = new WAsientosModelo(filtro);
				getUI().addWindow(window);
			}
		};
	}

}
