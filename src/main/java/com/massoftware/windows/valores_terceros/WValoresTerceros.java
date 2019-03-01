package com.massoftware.windows.valores_terceros;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
import com.vaadin.data.util.converter.Converter;
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
import com.vaadin.ui.renderers.HtmlRenderer;

public class WValoresTerceros extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<ValoresTercerosFiltro> filterBI;
	private BeanItemContainer<ValoresTerceros> itemsBIC;

	// -------------------------------------------------------------

	private int limit = 15;
	private int offset = 0;

	// -------------------------------------------------------------

	private Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;

	private Button modificarBTN;

	// -------------------------------------------------------------

	private HorizontalLayout numeroChequeTXTHL;
	private HorizontalLayout numeroInternoTXTHL;
	private HorizontalLayout numeroCuentaFondoCBXHL;
	private HorizontalLayout numeroBancoCBXHL;
	private HorizontalLayout numeroClienteCBXHL;
	private HorizontalLayout numeroProveedorCBXHL;
	private HorizontalLayout emisionDesdeDFHL;
	private HorizontalLayout emisionHastaDFHL;
	private HorizontalLayout cobroDesdeDFHL;
	private HorizontalLayout cobroHastaDFHL;
	private OptionGroup numeroEstadoOG;

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WValoresTerceros() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Valores de terceros");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			HorizontalLayout filaFiltro2HL = new HorizontalLayout();
			filaFiltro2HL.setSpacing(true);

			// -----------

			numeroChequeTXTHL = UtilUI.buildTXTHLInteger(filterBI, "numeroCheque", "Cheque", false, 5, -1, 3, false,
					false, null, false, UtilUI.EQUALS, 0, 255);

			TextField numeroChequeTXT = (TextField) numeroChequeTXTHL.getComponent(0);

			numeroChequeTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroChequeTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroChequeBTN = (Button) numeroChequeTXTHL.getComponent(1);

			numeroChequeBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroInternoTXTHL = UtilUI.buildTXTHLInteger(filterBI, "numeroInterno", "Nro int", false, 5, -1, 3, false,
					false, null, false, UtilUI.EQUALS, 0, 255);

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

			numeroCuentaFondoCBXHL = UtilUI.buildSearchBox(filterBI, "numeroCuentaFondo", "nombreCuentaFondo",
					"Cta fondo", "numero", false, "Cuenta de fondo", true);

			Button numeroCuentaFondoBTNOpen = (Button) numeroCuentaFondoCBXHL.getComponent(0);

			numeroCuentaFondoBTNOpen.addClickListener(e -> {
				try {
					selectTalonarioTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroCuentaFondoBTN = (Button) numeroCuentaFondoCBXHL.getComponent(3);

			numeroCuentaFondoBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroBancoCBXHL = UtilUI.buildSearchBox(filterBI, "numeroBanco", "nombreBanco", "Banco", "numero", false,
					"Banco", true);

			Button numeroBancoBTNOpen = (Button) numeroBancoCBXHL.getComponent(0);

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

			numeroClienteCBXHL = UtilUI.buildSearchBox(filterBI, "numeroCliente", "nombreCliente", "Cliente", "numero",
					false, "Cliente", true);

			Button numeroClienteBTNOpen = (Button) numeroClienteCBXHL.getComponent(0);

			numeroClienteBTNOpen.addClickListener(e -> {
				try {
					// selectClienteTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroClienteBTN = (Button) numeroClienteCBXHL.getComponent(3);

			numeroClienteBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroProveedorCBXHL = UtilUI.buildSearchBox(filterBI, "numeroProveedor", "nombreProveedor", "Proveedor",
					"numero", false, "Proveedor", true);

			Button numeroProveedorBTNOpen = (Button) numeroProveedorCBXHL.getComponent(0);

			numeroProveedorBTNOpen.addClickListener(e -> {
				try {
					// selectProveedorTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroProveedorBTN = (Button) numeroProveedorCBXHL.getComponent(3);

			numeroProveedorBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			emisionDesdeDFHL = UtilUI.buildDFHL(filterBI, "emisionDesde", "Emisión desde", false, false);

			DateField emisionDesdeDF = (DateField) emisionDesdeDFHL.getComponent(0);

			emisionDesdeDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			emisionHastaDFHL = UtilUI.buildDFHL(filterBI, "emisionHasta", "Emisión hasta", false, false);

			DateField emisionHastaDF = (DateField) emisionHastaDFHL.getComponent(0);

			emisionHastaDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			cobroDesdeDFHL = UtilUI.buildDFHL(filterBI, "cobroDesde", "Cobro desde", false, false);

			DateField cobroDesdeDF = (DateField) cobroDesdeDFHL.getComponent(0);

			cobroDesdeDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			cobroHastaDFHL = UtilUI.buildDFHL(filterBI, "cobroHasta", "Cobro hasta", false, false);

			DateField cobroHastaDF = (DateField) cobroHastaDFHL.getComponent(0);

			cobroHastaDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			numeroEstadoOG = UtilUI.buildBooleanOG(filterBI, "numeroEstado", "Estado", false, false,
					new String[] { "Cartera", "Rechazados", "Aplicado", "Todos" }, new Integer[] { 0, 1, 2, 3 }, true,
					0);

			numeroEstadoOG.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
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

			filaFiltroHL.addComponents(numeroChequeTXTHL, numeroInternoTXTHL, emisionDesdeDFHL, emisionHastaDFHL,
					cobroDesdeDFHL, cobroHastaDFHL, numeroEstadoOG, buscarBTN);

			filaFiltro2HL.addComponents(numeroCuentaFondoCBXHL, numeroBancoCBXHL, numeroClienteCBXHL,
					numeroProveedorCBXHL);

			filaFiltroHL.setComponentAlignment(buscarBTN, Alignment.MIDDLE_RIGHT);

			// filaFiltroHL.setComponentAlignment(numeroEstadoOG,
			// Alignment.MIDDLE_CENTER);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			// itemsGRD.setWidth(61f, Unit.EM);
			itemsGRD.setWidth("100%");
			itemsGRD.setHeight(20.5f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "numeroCheque", "numeroInterno", "numeroCliente", "nombreCliente",
					"emision", "numeroBanco", "nombreBanco", "cobro", "numeroCuentaFondo", "nombreCuentaFondo",
					"numeroProveedor", "nombreProveedor", "numeroEstado", "nombreEstado", "importe" });

			UtilUI.confColumn(itemsGRD.getColumn("numeroCliente"), "Cliente", true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreCliente"), "Cliente", true, true, false, true, 200);
			UtilUI.confColumn(itemsGRD.getColumn("numeroEstado"), "Estado", true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroCuentaFondo"), "Cuenta fondo", true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroBanco"), "Banco", true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroProveedor"), "Proveedor", true, true, false, true, 50);
			// UtilUI.confColumn(itemsGRD.getColumn("nombreEstado"), "Estado",
			// true, true, false, true, 50);

			UtilUI.confColumn(itemsGRD.getColumn("numeroInterno"), "Nro. interno", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("numeroCheque"), "Nro. cheque", true, 80);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroCliente"), "Cliente",
			// true, 70);
			// UtilUI.confColumn(itemsGRD.getColumn("nombreCliente"),
			// "Cliente",
			// true, 200);
			UtilUI.confColumn(itemsGRD.getColumn("emision"), "Emisión", true, 90);
			UtilUI.confColumn(itemsGRD.getColumn("cobro"), "Cobro", true, 90);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroCuentaFondo"),
			// "Cta. fondo", true, 70);
			UtilUI.confColumn(itemsGRD.getColumn("nombreCuentaFondo"), "Cta. fondo", true, 150);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroBanco"), "Banco",
			// true,
			// 70);
			UtilUI.confColumn(itemsGRD.getColumn("nombreBanco"), "Banco", true, 150);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroProveedor"),
			// "Proveedor", true,
			// 70);
			UtilUI.confColumn(itemsGRD.getColumn("nombreProveedor"), "Proveedor", true, 150);
			// UtilUI.confColumn(itemsGRD.getColumn("numeroEstado"), "Estado",
			// true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreEstado"), "Estado", true, 70);
			UtilUI.confColumn(itemsGRD.getColumn("importe"), "Importe", true, -1);

			itemsGRD.setContainerDataSource(itemsBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new HtmlRenderer(),
			// new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
			// .getHtml(), FontAwesome.SQUARE_O.getHtml()));

			// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
			itemsGRD.getColumn("emision").setRenderer(new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));
			itemsGRD.getColumn("cobro").setRenderer(new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

			// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(
			// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			itemsGRD.getColumn("nombreEstado").setRenderer(new HtmlRenderer(), new Converter<String, String>() {
				@Override
				public String convertToModel(String value, Class<? extends String> targetType, Locale locale)
						throws Converter.ConversionException {
					return "not implemented";
				}

				@Override
				public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale)
						throws Converter.ConversionException {

					if (value != null && value.trim().equalsIgnoreCase("Cartera")) {
						return "<font color='blue'>" + value + "</font>";
					} else if (value != null && value.trim().equalsIgnoreCase("Rechazado")) {
						return "<font color='red'>" + value + "</font>";
					} else {
						return value;
					}

				}

				@Override
				public Class<String> getModelType() {
					return String.class;
				}

				@Override
				public Class<String> getPresentationType() {
					return String.class;
				}
			});

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

			Button seguimientoBTN = UtilUI.buildButton("Seguimiento", "Seguimiento");
			seguimientoBTN.setIcon(FontAwesome.PRINT);
			seguimientoBTN.addClickListener(e -> {
				// seguimientoBTNClick();
			});

			Button masFiltrosBTN = UtilUI.buildButton("Búsqueda avanzada", "Búsqueda avanzada");
			masFiltrosBTN.setIcon(FontAwesome.SEARCH);
			masFiltrosBTN.addClickListener(e -> {
				// seguimientoBTNClick();
			});

			filaBotoneraHL.addComponents(modificarBTN, seguimientoBTN, masFiltrosBTN);

			// -------------------------------------------------------

			content.addComponents(filaFiltroHL, filaFiltro2HL, itemsGRD, filaBotoneraPagedHL, filaBotoneraHL);

			content.setComponentAlignment(filaFiltroHL, Alignment.MIDDLE_CENTER);
			content.setComponentAlignment(filaBotoneraPagedHL, Alignment.MIDDLE_RIGHT);
			content.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);

			this.setContent(content);

			// =======================================================
			// -------------------------------------------------------
			// KEY EVENTs

			this.addShortcutListener(new ShortcutListener("ENTER", KeyCode.ENTER, new int[] {}) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					if (target.equals(itemsGRD)) {
						modificarBTNClick();
					}

				}
			});

			// --------------------------------------------------

			this.addShortcutListener(new ShortcutListener("CTRL+M", KeyCode.M, new int[] { ModifierKey.CTRL }) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					modificarBTNClick();
				}
			});

			// --------------------------------------------------

			this.addShortcutListener(new ShortcutListener("CTRL+B", KeyCode.B, new int[] { ModifierKey.CTRL }) {

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

		filterBI = new BeanItem<ValoresTercerosFiltro>(new ValoresTercerosFiltro());
		itemsBIC = new BeanItemContainer<ValoresTerceros>(ValoresTerceros.class, new ArrayList<ValoresTerceros>());
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

				ValoresTerceros item = (ValoresTerceros) itemsGRD.getSelectedRow();

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

	private void selectTalonarioTXTShortcutEnter() {
		try {

			// if (this.filterBI.getBean().getNumeroCuentaFondo() != null) {

			CuentasFondoFiltro cuentasFondoFiltro = new CuentasFondoFiltro();
			cuentasFondoFiltro.setNumero(this.filterBI.getBean().getNumeroCuentaFondo());

			WCuentasFondo window = null; //new WCuentasFondo(cuentasFondoFiltro); // 777
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

			((VerticalLayout) window.getContent()).setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_CENTER);

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

				CuentaFondo item = (CuentaFondo) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroCuentaFondo").setValue(item.getNumero());
				this.filterBI.getItemProperty("nombreCuentaFondo").setValue(item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroCuentaFondo").setValue(null);
				this.filterBI.getItemProperty("nombreCuentaFondo").setValue(null);
			}
		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	private void selectBancoTXTShortcutEnter() {
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

			((VerticalLayout) window.getContent()).setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_CENTER);

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

				this.filterBI.getItemProperty("numeroBanco").setValue(item.getNumero());
				this.filterBI.getItemProperty("nombreBanco").setValue(item.getNombre());

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

	private void selectClienteTXTShortcutEnter() {
		try {

			// if (this.filterBI.getBean().getNumeroBanco() != null) {

			WChequeras window = new WChequeras();
			window.setModal(true);
			window.center();

			window.addCloseListener(new CloseListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void windowClose(CloseEvent event) {
					setNumeroClienteOnFilter(window);
				}
			});

			// -------------------------------------------------------
			// BOTONERA SELECCION

			HorizontalLayout filaBotoneraHL = new HorizontalLayout();
			filaBotoneraHL.setSpacing(true);

			Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
			seleccionarBTN.addClickListener(e -> {
				setNumeroClienteOnFilter(window);
			});

			filaBotoneraHL.addComponents(seleccionarBTN);

			((VerticalLayout) window.getContent()).addComponent(filaBotoneraHL);

			((VerticalLayout) window.getContent()).setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_CENTER);

			getUI().addWindow(window);

			// } else {
			// this.filterBI.getItemProperty("nombreBanco").setValue(null);
			// }

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroClienteOnFilter(WChequeras window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				Chequeras item = (Chequeras) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroChequera").setValue(item.getNumero());
				this.filterBI.getItemProperty("nombreChequera").setValue(item.toString());

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

			((Validatable) numeroChequeTXTHL.getComponent(0)).validate();
			((Validatable) numeroInternoTXTHL.getComponent(0)).validate();
			((Validatable) numeroCuentaFondoCBXHL.getComponent(1)).validate();
			((Validatable) numeroBancoCBXHL.getComponent(1)).validate();
			((Validatable) numeroClienteCBXHL.getComponent(1)).validate();
			((Validatable) numeroProveedorCBXHL.getComponent(1)).validate();
			((Validatable) emisionDesdeDFHL.getComponent(0)).validate();
			((Validatable) emisionHastaDFHL.getComponent(0)).validate();
			((Validatable) cobroDesdeDFHL.getComponent(0)).validate();
			((Validatable) cobroHastaDFHL.getComponent(0)).validate();
			numeroEstadoOG.validate();

			List<ValoresTerceros> items = queryData();

			itemsBIC.removeAllItems();

			for (ValoresTerceros item : items) {
				itemsBIC.addBean(item);
			}

			boolean enabled = itemsBIC.size() > 0;

			itemsGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);

			nextPageBTN.setEnabled(itemsBIC.size() > 0 && itemsBIC.size() >= limit);

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
	private List<ValoresTerceros> queryData() {
		try {

			System.out.println("Los filtros son " + this.filterBI.getBean().toString());

			// Notification.show("Los filtros son "
			// + this.filterBI.getBean().toString());

			Map<String, Boolean> orderBy = new HashMap<String, Boolean>();

			for (SortOrder sortOrder : itemsGRD.getSortOrder()) {
				orderBy.put(sortOrder.getPropertyId().toString(),
						sortOrder.getDirection().toString().equals("ASCENDING"));
				System.err.println(sortOrder.getPropertyId() + " " + sortOrder.getDirection());
			}

			List<ValoresTerceros> items = mockData(limit, offset, this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<ValoresTerceros>();
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<ValoresTerceros> itemsMock = new ArrayList<ValoresTerceros>();

	private List<ValoresTerceros> mockData(int limit, int offset, ValoresTercerosFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				ValoresTerceros item = new ValoresTerceros();

				item.setNumeroInterno(i);
				item.setNumeroCheque(i);
				item.setNumeroCliente(i);
				item.setNombreCliente("Chequera " + i);
				item.setEmision(new Timestamp(System.currentTimeMillis()));
				item.setCobro(new Timestamp(System.currentTimeMillis()));
				item.setNumeroCuentaFondo(i);
				item.setNombreCuentaFondo("(" + i + ") Cuenta fondo");
				item.setNumeroBanco(i);
				item.setNombreBanco("(" + i + ") Banco");
				item.setNumeroProveedor(i);
				item.setNombreProveedor("(" + i + ") Proveedor");
				item.setNumeroEstado(new Random().nextInt(3));
				if (item.getNumeroEstado() == 0) {
					item.setNombreEstado("Cartera");
				} else if (item.getNumeroEstado() == 1) {
					item.setNombreEstado("Rechazado");
				} else if (item.getNumeroEstado() == 2) {
					item.setNombreEstado("Aplicado");
				}
				item.setImporte(new BigDecimal("569789.36987"));

				itemsMock.add(item);
			}
		}

		ArrayList<ValoresTerceros> arrayList = new ArrayList<ValoresTerceros>();

		for (ValoresTerceros item : itemsMock) {

			boolean passesFilterNumeroCheque = (filtro.getNumeroCheque() == null
					|| item.getNumeroCheque().equals(filtro.getNumeroCheque()));

			boolean passesFilterNumeroInterno = (filtro.getNumeroInterno() == null
					|| item.getNumeroInterno().equals(filtro.getNumeroInterno()));

			boolean passesFilterNumeroCuentaFondo = (filtro.getNumeroCuentaFondo() == null
					|| item.getNumeroCuentaFondo().equals(filtro.getNumeroCuentaFondo()));

			boolean passesFilterNumeroBanco = (filtro.getNumeroBanco() == null
					|| item.getNumeroBanco().equals(filtro.getNumeroBanco()));

			boolean passesFilterNumeroCliente = (filtro.getNumeroCliente() == null
					|| item.getNumeroCliente().equals(filtro.getNumeroCliente()));

			boolean passesFilterEmisionDesde = (filtro.getEmisionDesde() == null
					|| item.getEmision().after(filtro.getEmisionDesde()));

			boolean passesFilterEmisionHasta = (filtro.getEmisionHasta() == null
					|| item.getEmision().before(UtilModel.sumarDiasAFecha(filtro.getEmisionHasta(), 1)));

			boolean passesFilterCobroDesde = (filtro.getCobroDesde() == null
					|| item.getCobro().after(filtro.getCobroDesde()));

			boolean passesFilterCobroHasta = (filtro.getCobroHasta() == null
					|| item.getCobro().before(UtilModel.sumarDiasAFecha(filtro.getCobroHasta(), 1)));

			boolean passesFilterNumeroEstado = (filtro.getNumeroEstado() == null || filtro.getNumeroEstado() == 3
					|| item.getNumeroEstado().equals(filtro.getNumeroEstado()));

			if (passesFilterNumeroCheque && passesFilterNumeroInterno && passesFilterNumeroCuentaFondo
					&& passesFilterNumeroBanco && passesFilterNumeroCliente && passesFilterEmisionDesde
					&& passesFilterEmisionHasta && passesFilterCobroDesde && passesFilterCobroHasta
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
