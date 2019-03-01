package com.massoftware.windows.valores_propios;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.model.Banco;
import com.massoftware.model.BancosFiltro;
import com.massoftware.model.CuentaFondo;
import com.massoftware.model.CuentasFondoFiltro;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilModel;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.bancos.WBancos;
import com.massoftware.windows.chequeras.Chequeras;
import com.massoftware.windows.chequeras.WChequeras;
import com.massoftware.windows.cuentas_fondo.WCuentasFondo;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.SortEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class WValoresPropios extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<ValoresPropiosFiltro> filterBI;
	private BeanItemContainer<ValoresPropios> itemsBIC;

	// -------------------------------------------------------------

	protected int limit = 15;
	protected int offset = 0;

	// -------------------------------------------------------------

	private Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;

	private Button modificarBTN;

	// -------------------------------------------------------------

	private HorizontalLayout numeroInternoTXTHL;
	private HorizontalLayout numeroCuentaFondoCBXHL;
	private HorizontalLayout numeroBancoCBXHL;
	private HorizontalLayout numeroChequeraCBXHL;
	private HorizontalLayout numeroProveedorCBXHL;
	private HorizontalLayout emisionDesdeDFHL;
	private HorizontalLayout emisionHastaDFHL;
	private HorizontalLayout pagoDesdeDFHL;
	private HorizontalLayout pagoHastaDFHL;
	private OptionGroup numeroEstadoOG;

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WValoresPropios() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Valores propios");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			HorizontalLayout filaFiltro2HL = new HorizontalLayout();
			filaFiltro2HL.setSpacing(true);

			// -----------

			numeroInternoTXTHL = UtilUI.buildTXTHLInteger(filterBI, "numeroInterno",
					"Nro int", false, 5, -1, 3, false, false, null, false,
					UtilUI.EQUALS, 0, 255);

			TextField numeroInternoTXT = (TextField) numeroInternoTXTHL.getComponent(0);

			numeroInternoTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroInternoTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroInternoBTN = (Button) numeroInternoTXTHL.getComponent(1);

			numeroInternoBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroCuentaFondoCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroCuentaFondo", "nombreCuentaFondo", "Cta fondo",
					"numero", false, "Cuenta de fondo", true);

			Button numeroCuentaFondoBTNOpen = (Button) numeroCuentaFondoCBXHL
					.getComponent(0);

			numeroCuentaFondoBTNOpen.addClickListener(e -> {
				try {
					selectTalonarioTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroCuentaFondoBTN = (Button) numeroCuentaFondoCBXHL
					.getComponent(3);

			numeroCuentaFondoBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroBancoCBXHL = UtilUI.buildSearchBox(filterBI, "numeroBanco",
					"nombreBanco", "Banco", "numero", false, "Banco", true);

			Button numeroBancoBTNOpen = (Button) numeroBancoCBXHL
					.getComponent(0);

			numeroBancoBTNOpen.addClickListener(e -> {
				try {
					selectBancoTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroBancoBTN = (Button) numeroBancoCBXHL.getComponent(3);

			numeroBancoBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroChequeraCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroChequera", "nombreChequera", "Chequera", "numero",
					false, "Chequera", true);

			Button numeroChequeraBTNOpen = (Button) numeroChequeraCBXHL
					.getComponent(0);

			numeroChequeraBTNOpen.addClickListener(e -> {
				try {
					selectChequeraTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroChequeraBTN = (Button) numeroChequeraCBXHL
					.getComponent(3);

			numeroChequeraBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroProveedorCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroProveedor", "nombreProveedor", "Proveedor",
					"numero", false, "Proveedor", true);

			Button numeroProveedorBTNOpen = (Button) numeroProveedorCBXHL
					.getComponent(0);

			numeroProveedorBTNOpen.addClickListener(e -> {
				try {
					// selectProveedorTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroProveedorBTN = (Button) numeroProveedorCBXHL
					.getComponent(3);

			numeroProveedorBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			emisionDesdeDFHL = UtilUI.buildDFHL(filterBI, "emisionDesde",
					"Emisión desde", false, false);

			DateField emisionDesdeDF = (DateField) emisionDesdeDFHL
					.getComponent(0);

			emisionDesdeDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			emisionHastaDFHL = UtilUI.buildDFHL(filterBI, "emisionHasta",
					"Emisión hasta", false, false);

			DateField emisionHastaDF = (DateField) emisionHastaDFHL
					.getComponent(0);

			emisionHastaDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			pagoDesdeDFHL = UtilUI.buildDFHL(filterBI, "pagoDesde",
					"Pago desde", false, false);

			DateField pagoDesdeDF = (DateField) pagoDesdeDFHL.getComponent(0);

			pagoDesdeDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			pagoHastaDFHL = UtilUI.buildDFHL(filterBI, "pagoHasta",
					"Pago hasta", false, false);

			DateField pagoHastaDF = (DateField) pagoHastaDFHL.getComponent(0);

			pagoHastaDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			numeroEstadoOG = UtilUI.buildBooleanOG(filterBI, "numeroEstado",
					"Estado", false, false, new String[] { "Aplicado",
							"Diferido", "Todos" }, new Integer[] { 1, 2, 3 }, true, 2);

			numeroEstadoOG.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					try {
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(numeroInternoTXTHL, emisionDesdeDFHL,
					emisionHastaDFHL, pagoDesdeDFHL, pagoHastaDFHL,
					numeroEstadoOG, buscarBTN);

			filaFiltro2HL
					.addComponents(numeroCuentaFondoCBXHL, numeroBancoCBXHL,
							numeroChequeraCBXHL, numeroProveedorCBXHL);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// filaFiltroHL.setComponentAlignment(numeroEstadoOG,
			// Alignment.MIDDLE_CENTER);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth(61f, Unit.EM);
			// itemsGRD.setWidth("100%");
			itemsGRD.setHeight(20.5f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "numeroInterno", "numeroCheque",
					"numeroChequera", "nombreChequera", "emision", "pago",
					"numeroCuentaFondo", "nombreCuentaFondo", "numeroBanco",
					"nombreBanco", "numeroProveedor", "nombreProveedor",
					"numeroEstado", "nombreEstado", "importe" });

			UtilUI.confColumn(itemsGRD.getColumn("nombreChequera"), "Chequera",
					true, true, false, true, 200);
			UtilUI.confColumn(itemsGRD.getColumn("numeroEstado"), "Estado",
					true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroCuentaFondo"),
					"Cuenta fondo", true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroBanco"), "Banco", true,
					true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroProveedor"),
					"Proveedor", true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreEstado"), "Estado",
					true, true, false, true, 50);

			UtilUI.confColumn(itemsGRD.getColumn("numeroInterno"),
					"Nro. interno", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("numeroCheque"),
					"Nro. cheque", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("numeroChequera"), "Cheqera",
					true, 70);
			// UtilUI.confColumn(itemsGRD.getColumn("nombreChequera"),
			// "Cheqera",
			// true, 200);
			UtilUI.confColumn(itemsGRD.getColumn("emision"), "Emisión", true,
					90);
			UtilUI.confColumn(itemsGRD.getColumn("pago"), "Pago", true, 90);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroCuentaFondo"),
			// "Cta. fondo", true, 70);
			UtilUI.confColumn(itemsGRD.getColumn("nombreCuentaFondo"),
					"Cta. fondo", true, 150);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroBanco"), "Banco",
			// true,
			// 70);
			UtilUI.confColumn(itemsGRD.getColumn("nombreBanco"), "Banco", true,
					150);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroProveedor"),
			// "Proveedor", true,
			// 70);
			UtilUI.confColumn(itemsGRD.getColumn("nombreProveedor"),
					"Proveedor", true, 150);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroEstado"), "Estado",
			// true, 50);
			// UtilUI.confColumn(itemsGRD.getColumn("nombreEstado"), "Estado",
			// true, 70);
			UtilUI.confColumn(itemsGRD.getColumn("importe"), "Importe", true,
					-1);

			itemsGRD.setContainerDataSource(itemsBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new HtmlRenderer(),
			// new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
			// .getHtml(), FontAwesome.SQUARE_O.getHtml()));

			// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
			itemsGRD.getColumn("emision").setRenderer(
					new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));
			itemsGRD.getColumn("pago").setRenderer(
					new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

			// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(
			// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> order = new ArrayList<SortOrder>();

			order.add(new SortOrder("emision", SortDirection.DESCENDING));

			itemsGRD.setSortOrder(order);

			HorizontalLayout filaBotoneraPagedHL = new HorizontalLayout();
			filaBotoneraPagedHL.setSpacing(true);

			prevPageBTN = UtilUI.buildButtonPrev(limit, offset);
			prevPageBTN.addClickListener(e -> {
				prevPageBTNClick();
			});

			nextPageBTN = UtilUI.buildButtonNext(limit, offset);
			nextPageBTN.addClickListener(e -> {
				nextPageBTNClick();
			});

			filaBotoneraPagedHL.addComponents(prevPageBTN, nextPageBTN);

			// =======================================================
			// -------------------------------------------------------
			// BOTONERA 1

			HorizontalLayout filaBotoneraHL = new HorizontalLayout();
			filaBotoneraHL.setSpacing(true);

			modificarBTN = UtilUI.buildButtonModificar();
			modificarBTN.addClickListener(e -> {
				modificarBTNClick();
			});

			Button seguimientoBTN = UtilUI.buildButton("Seguimiento",
					"Seguimiento");
			seguimientoBTN.setIcon(FontAwesome.PRINT);
			seguimientoBTN.addClickListener(e -> {
				// seguimientoBTNClick();
				});

			Button masFiltrosBTN = UtilUI.buildButton("Búsqueda avanzada",
					"Búsqueda avanzada");
			masFiltrosBTN.setIcon(FontAwesome.SEARCH);
			masFiltrosBTN.addClickListener(e -> {
				// seguimientoBTNClick();
				});

			filaBotoneraHL.addComponents(modificarBTN, seguimientoBTN,
					masFiltrosBTN);

			// -------------------------------------------------------

			content.addComponents(filaFiltroHL, filaFiltro2HL, itemsGRD,
					filaBotoneraPagedHL, filaBotoneraHL);

			content.setComponentAlignment(filaFiltroHL, Alignment.MIDDLE_CENTER);
			content.setComponentAlignment(filaBotoneraPagedHL,
					Alignment.MIDDLE_RIGHT);
			content.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);

			this.setContent(content);

			// =======================================================
			// -------------------------------------------------------
			// KEY EVENTs

			this.addShortcutListener(new ShortcutListener("ENTER",
					KeyCode.ENTER, new int[] {}) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					if (target.equals(itemsGRD)) {
						modificarBTNClick();
					}

				}
			});

			// --------------------------------------------------

			this.addShortcutListener(new ShortcutListener("CTRL+M", KeyCode.M,
					new int[] { ModifierKey.CTRL }) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					modificarBTNClick();
				}
			});

			// --------------------------------------------------

			this.addShortcutListener(new ShortcutListener("CTRL+B", KeyCode.B,
					new int[] { ModifierKey.CTRL }) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					loadData();
				}
			});

			// =======================================================
			// -------------------------------------------------------

			itemsGRD.addSortListener(e -> {
				sort(e);
			});

			// =======================================================
			// -------------------------------------------------------

			loadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================

	private void buildContainersItems() throws Exception {

		filterBI = new BeanItem<ValoresPropiosFiltro>(
				new ValoresPropiosFiltro());
		itemsBIC = new BeanItemContainer<ValoresPropios>(ValoresPropios.class,
				new ArrayList<ValoresPropios>());
	}

	// =================================================================================

	private void nextPageBTNClick() {
		offset = offset + limit;
		prevPageBTN.setEnabled(offset > 0);
		loadData();
		if (this.itemsBIC.size() <= 0) {
			prevPageBTNClick();
		}
	}

	private void prevPageBTNClick() {
		offset = offset - limit;
		if (offset < 0) {
			offset = 0;
		}
		prevPageBTN.setEnabled(offset > 0);
		loadData();
	}

	private void sort(SortEvent sortEvent) {
		try {

			if (itemsGRD.getSortOrder().size() == 1) {
				loadDataResetPaged();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void modificarBTNClick() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				ValoresPropios item = (ValoresPropios) itemsGRD
						.getSelectedRow();

				Window window = new Window("Modificar ítem " + item);
				window.setModal(true);
				window.center();
				window.setWidth("400px");
				window.setHeight("300px");
				getUI().addWindow(window);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void selectTalonarioTXTShortcutEnter() {
		try {

			// if (this.filterBI.getBean().getNumeroCuentaFondo() != null) {
			
			CuentasFondoFiltro cuentasFondoFiltro = new CuentasFondoFiltro();
			cuentasFondoFiltro.setNumero(this.filterBI.getBean()
					.getNumeroCuentaFondo());

			WCuentasFondo window = null;// new WCuentasFondo(cuentasFondoFiltro); 77
			window.setModal(true);
			window.center();

			window.addCloseListener(new CloseListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void windowClose(CloseEvent event) {
					setNumeroCuentaFondoOnFilter(window);
				}
			});

			// -------------------------------------------------------
			// BOTONERA SELECCION

			HorizontalLayout filaBotoneraHL = new HorizontalLayout();
			filaBotoneraHL.setSpacing(true);

			Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
			seleccionarBTN.addClickListener(e -> {
				setNumeroCuentaFondoOnFilter(window);
			});

			filaBotoneraHL.addComponents(seleccionarBTN);

			((VerticalLayout) window.getContent()).addComponent(filaBotoneraHL);

			((VerticalLayout) window.getContent()).setComponentAlignment(
					filaBotoneraHL, Alignment.MIDDLE_CENTER);

			getUI().addWindow(window);

			// } else {
			// this.filterBI.getItemProperty("nombreCuentaFondo").setValue(null);
			// }

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroCuentaFondoOnFilter(WCuentasFondo window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				CuentaFondo item = (CuentaFondo) window.itemsGRD
						.getSelectedRow();

				this.filterBI.getItemProperty("numeroCuentaFondo").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreCuentaFondo").setValue(
						item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroCuentaFondo").setValue(
						null);
				this.filterBI.getItemProperty("nombreCuentaFondo").setValue(
						null);
			}
		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	protected void selectBancoTXTShortcutEnter() {
		try {

			// if (this.filterBI.getBean().getNumeroBanco() != null) {

			BancosFiltro bancosFiltro = new BancosFiltro();
			bancosFiltro.setNumero(this.filterBI.getBean().getNumeroBanco());

			WBancos window = new WBancos(bancosFiltro);
			window.setModal(true);
			window.center();

			window.addCloseListener(new CloseListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void windowClose(CloseEvent event) {
					setNumeroBancoOnFilter(window);
				}
			});

			// -------------------------------------------------------
			// BOTONERA SELECCION

			HorizontalLayout filaBotoneraHL = new HorizontalLayout();
			filaBotoneraHL.setSpacing(true);

			Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
			seleccionarBTN.addClickListener(e -> {
				setNumeroBancoOnFilter(window);
			});

			filaBotoneraHL.addComponents(seleccionarBTN);

			((VerticalLayout) window.getContent()).addComponent(filaBotoneraHL);

			((VerticalLayout) window.getContent()).setComponentAlignment(
					filaBotoneraHL, Alignment.MIDDLE_CENTER);

			getUI().addWindow(window);

			// } else {
			// this.filterBI.getItemProperty("nombreBanco").setValue(null);
			// }

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroBancoOnFilter(WBancos window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				Banco item = (Banco) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroBanco").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreBanco").setValue(
						item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroBanco").setValue(null);
				this.filterBI.getItemProperty("nombreBanco").setValue(null);
			}
		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	protected void selectChequeraTXTShortcutEnter() {
		try {

			// if (this.filterBI.getBean().getNumeroBanco() != null) {

			WChequeras window = new WChequeras();
			window.setModal(true);
			window.center();

			window.addCloseListener(new CloseListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void windowClose(CloseEvent event) {
					setNumeroChequeraOnFilter(window);
				}
			});

			// -------------------------------------------------------
			// BOTONERA SELECCION

			HorizontalLayout filaBotoneraHL = new HorizontalLayout();
			filaBotoneraHL.setSpacing(true);

			Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
			seleccionarBTN.addClickListener(e -> {
				setNumeroChequeraOnFilter(window);
			});

			filaBotoneraHL.addComponents(seleccionarBTN);

			((VerticalLayout) window.getContent()).addComponent(filaBotoneraHL);

			((VerticalLayout) window.getContent()).setComponentAlignment(
					filaBotoneraHL, Alignment.MIDDLE_CENTER);

			getUI().addWindow(window);

			// } else {
			// this.filterBI.getItemProperty("nombreBanco").setValue(null);
			// }

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroChequeraOnFilter(WChequeras window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				Chequeras item = (Chequeras) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroChequera").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreChequera").setValue(
						item.toString());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroChequera").setValue(null);
				this.filterBI.getItemProperty("nombreChequera").setValue(null);
			}
		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	// =================================================================================

	private void loadDataResetPaged() {
		this.offset = 0;
		loadData();
	}

	public void loadData() {
		try {

			((Validatable) numeroInternoTXTHL.getComponent(0)).validate();
			((Validatable) numeroCuentaFondoCBXHL.getComponent(1)).validate();
			((Validatable) numeroBancoCBXHL.getComponent(1)).validate();
			((Validatable) numeroChequeraCBXHL.getComponent(1)).validate();
			((Validatable) numeroProveedorCBXHL.getComponent(1)).validate();
			((Validatable) emisionDesdeDFHL.getComponent(0)).validate();
			((Validatable) emisionHastaDFHL.getComponent(0)).validate();
			((Validatable) pagoDesdeDFHL.getComponent(0)).validate();
			((Validatable) pagoHastaDFHL.getComponent(0)).validate();
			numeroEstadoOG.validate();

			List<ValoresPropios> items = queryData();

			itemsBIC.removeAllItems();

			for (ValoresPropios item : items) {
				itemsBIC.addBean(item);
			}

			boolean enabled = itemsBIC.size() > 0;

			itemsGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);

			nextPageBTN.setEnabled(itemsBIC.size() > 0
					&& itemsBIC.size() >= limit);

			prevPageBTN.setEnabled(offset >= limit);

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	// metodo que realiza la consulta a la base de datos
	private List<ValoresPropios> queryData() {
		try {

			System.out.println("Los filtros son "
					+ this.filterBI.getBean().toString());

			// Notification.show("Los filtros son "
			// + this.filterBI.getBean().toString());

			Map<String, Boolean> orderBy = new HashMap<String, Boolean>();

			for (SortOrder sortOrder : itemsGRD.getSortOrder()) {
				orderBy.put(sortOrder.getPropertyId().toString(), sortOrder
						.getDirection().toString().equals("ASCENDING"));
				System.err.println(sortOrder.getPropertyId() + " "
						+ sortOrder.getDirection());
			}

			List<ValoresPropios> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<ValoresPropios>();
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<ValoresPropios> itemsMock = new ArrayList<ValoresPropios>();

	private List<ValoresPropios> mockData(int limit, int offset,
			ValoresPropiosFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				ValoresPropios item = new ValoresPropios();

				item.setNumeroInterno(i);
				item.setNumeroCheque(i);
				item.setNumeroChequera(i);
				item.setNombreChequera("Chequera " + i);
				item.setEmision(new Timestamp(System.currentTimeMillis()));
				item.setPago(new Timestamp(System.currentTimeMillis()));
				item.setNumeroCuentaFondo(i);
				item.setNombreCuentaFondo("(" + i + ") Cuenta fondo");
				item.setNumeroBanco(i);
				item.setNombreBanco("(" + i + ") Banco");
				item.setNumeroProveedor(i);
				item.setNombreProveedor("(" + i + ") Proveedor");
				item.setNumeroEstado(i % 2 == 0 ? 1 : 2);
				item.setNombreEstado(i % 2 == 0 ? "Aplicado" : "Diferido");
				item.setImporte(new BigDecimal("569789.36987"));

				itemsMock.add(item);
			}
		}

		ArrayList<ValoresPropios> arrayList = new ArrayList<ValoresPropios>();

		for (ValoresPropios item : itemsMock) {

			boolean passesFilterNumeroInterno = (filtro.getNumeroInterno() == null || item
					.getNumeroInterno().equals(filtro.getNumeroInterno()));

			boolean passesFilterNumeroCuentaFondo = (filtro
					.getNumeroCuentaFondo() == null || item
					.getNumeroCuentaFondo().equals(
							filtro.getNumeroCuentaFondo()));

			boolean passesFilterNumeroBanco = (filtro.getNumeroBanco() == null || item
					.getNumeroBanco().equals(filtro.getNumeroBanco()));

			boolean passesFilterNumeroChequera = (filtro.getNumeroChequera() == null || item
					.getNumeroChequera().equals(filtro.getNumeroChequera()));

			boolean passesFilterEmisionDesde = (filtro.getEmisionDesde() == null || item
					.getEmision().after(filtro.getEmisionDesde()));

			boolean passesFilterEmisionHasta = (filtro.getEmisionHasta() == null || item
					.getEmision().before(
							UtilModel.sumarDiasAFecha(filtro.getEmisionHasta(),
									1)));

			boolean passesFilterPagoDesde = (filtro.getPagoDesde() == null || item
					.getPago().after(filtro.getPagoDesde()));

			boolean passesFilterPagoHasta = (filtro.getPagoHasta() == null || item
					.getPago()
					.before(UtilModel.sumarDiasAFecha(filtro.getPagoHasta(), 1)));

			boolean passesFilterNumeroEstado = (filtro.getNumeroEstado() == null || filtro.getNumeroEstado() == 3 ||item
					.getNumeroEstado().equals(filtro.getNumeroEstado()));

			if (passesFilterNumeroInterno && passesFilterNumeroCuentaFondo
					&& passesFilterNumeroBanco && passesFilterNumeroChequera
					&& passesFilterEmisionDesde && passesFilterEmisionHasta
					&& passesFilterPagoDesde && passesFilterPagoHasta
					&& passesFilterNumeroEstado) {
				arrayList.add(item);
			}
		}

		int end = offset + limit;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offset, end);
	}

	// =================================================================================

} // END CLASS
