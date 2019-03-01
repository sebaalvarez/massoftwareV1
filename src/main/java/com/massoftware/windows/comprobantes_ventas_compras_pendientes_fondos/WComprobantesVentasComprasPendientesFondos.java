package com.massoftware.windows.comprobantes_ventas_compras_pendientes_fondos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
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
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class WComprobantesVentasComprasPendientesFondos extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<ComprobantesVentasComprasPendientesFondosFiltro> filterBI;
	private BeanItemContainer<ComprobantesVentasComprasPendientesFondos> itemsBIC;

	// -------------------------------------------------------------

	protected int limit = 15;
	protected int offset = 0;

	// -------------------------------------------------------------

	private Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;
	private Button capturaFondoBTN;

	// -------------------------------------------------------------

	private HorizontalLayout numeroTXTHL;

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WComprobantesVentasComprasPendientesFondos() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this,
					"Comprobantes de ventas y compras pendientes de fondos");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			// -----------

			numeroTXTHL = UtilUI.buildTXTHLInteger(filterBI, "numeroDias",
					"Días", false, 5, -1, 3, false, false, null, false,
					UtilUI.EQUALS, 0, 255);

			TextField numeroTXT = (TextField) numeroTXTHL.getComponent(0);

			numeroTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroBTN = (Button) numeroTXTHL.getComponent(1);

			numeroBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(numeroTXTHL, buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth(35f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "modulo", "comprobante",
					"fecha", "clienteProveedor", "totalCbte" });

			UtilUI.confColumn(itemsGRD.getColumn("modulo"), "Módulo", true, 70);
			UtilUI.confColumn(itemsGRD.getColumn("comprobante"), "Comprobante",
					true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("fecha"), "Fecha", true, 90);
			UtilUI.confColumn(itemsGRD.getColumn("clienteProveedor"),
					"Cliente/Proveedor", true, 200);
			UtilUI.confColumn(itemsGRD.getColumn("totalCbte"), "Total cbte.",
					true, -1);

			itemsGRD.setContainerDataSource(itemsBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new HtmlRenderer(),
			// new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
			// .getHtml(), FontAwesome.SQUARE_O.getHtml()));

			// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
			itemsGRD.getColumn("fecha").setRenderer(
					new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

			// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(
			// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> order = new ArrayList<SortOrder>();

			order.add(new SortOrder("fecha", SortDirection.DESCENDING));

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

			capturaFondoBTN = UtilUI.buildButtonAgregar();
			capturaFondoBTN.setCaption("Captura fondo");
			capturaFondoBTN.addClickListener(e -> {
				capturaFondoBTNClick();
			});

			filaBotoneraHL.addComponents(capturaFondoBTN);

			// -------------------------------------------------------
			// BOTONERA 2

			HorizontalLayout filaBotonera2HL = new HorizontalLayout();
			filaBotonera2HL.setSpacing(true);

			Label lbl = new Label(
					"<b>Usuario:________________tIngreso: __/__ /____ </b>",
					ContentMode.HTML);

			filaBotonera2HL.addComponents(lbl);

			// -------------------------------------------------------

			content.addComponents(filaFiltroHL, itemsGRD, filaBotoneraPagedHL,
					filaBotoneraHL, filaBotonera2HL);

			content.setComponentAlignment(filaFiltroHL, Alignment.MIDDLE_CENTER);
			content.setComponentAlignment(filaBotoneraPagedHL,
					Alignment.MIDDLE_RIGHT);
			content.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);
			content.setComponentAlignment(filaBotonera2HL,
					Alignment.MIDDLE_RIGHT);

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

			this.addShortcutListener(new ShortcutListener("CTRL+A", KeyCode.A,
					new int[] { ModifierKey.CTRL }) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					capturaFondoBTNClick();
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

		filterBI = new BeanItem<ComprobantesVentasComprasPendientesFondosFiltro>(
				new ComprobantesVentasComprasPendientesFondosFiltro());
		itemsBIC = new BeanItemContainer<ComprobantesVentasComprasPendientesFondos>(
				ComprobantesVentasComprasPendientesFondos.class,
				new ArrayList<ComprobantesVentasComprasPendientesFondos>());
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

	protected void sort(SortEvent sortEvent) {
		try {

			if (itemsGRD.getSortOrder().size() == 1) {
				loadDataResetPaged();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void capturaFondoBTNClick() {
		try {

			itemsGRD.select(null);
			Window window = new Window("Captura fondo ítem ");
			window.setModal(true);
			window.center();
			window.setWidth("400px");
			window.setHeight("300px");
			getUI().addWindow(window);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void modificarBTNClick() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				ComprobantesVentasComprasPendientesFondos item = (ComprobantesVentasComprasPendientesFondos) itemsGRD
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

	// =================================================================================

	private void loadDataResetPaged() {
		this.offset = 0;
		loadData();
	}

	private void loadData() {
		try {

			((Validatable) numeroTXTHL.getComponent(0)).validate();

			List<ComprobantesVentasComprasPendientesFondos> items = queryData();

			itemsBIC.removeAllItems();

			for (ComprobantesVentasComprasPendientesFondos item : items) {
				itemsBIC.addBean(item);
			}

			boolean enabled = itemsBIC.size() > 0;

			itemsGRD.setEnabled(enabled);

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
	private List<ComprobantesVentasComprasPendientesFondos> queryData() {
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

			List<ComprobantesVentasComprasPendientesFondos> items = mockData(
					limit, offset, this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<ComprobantesVentasComprasPendientesFondos>();
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<ComprobantesVentasComprasPendientesFondos> itemsMock = new ArrayList<ComprobantesVentasComprasPendientesFondos>();

	private List<ComprobantesVentasComprasPendientesFondos> mockData(int limit,
			int offset, ComprobantesVentasComprasPendientesFondosFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				ComprobantesVentasComprasPendientesFondos item = new ComprobantesVentasComprasPendientesFondos();

				item.setModulo("Modulo " + i);
				item.setComprobante("X00000000000" + i);
				item.setFecha(new Date(System.currentTimeMillis()));
				item.setClienteProveedor((i % 2 == 0) ? "Cliente " + i
						: "Proveedor " + i);
				item.setTotalCbte(new BigDecimal("58963.3698"));

				itemsMock.add(item);
			}
		}

		ArrayList<ComprobantesVentasComprasPendientesFondos> arrayList = new ArrayList<ComprobantesVentasComprasPendientesFondos>();

		for (ComprobantesVentasComprasPendientesFondos item : itemsMock) {

			// boolean passesFilterNumeroDias = (filtro.getNumeroDias() == null
			// || item
			// .getNumeroDias().equals(filtro.getNumeroDias()));

			// if (passesFilterNumeroDias) {
			arrayList.add(item);
			// }
		}

		int end = offset + limit;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offset, end);
	}

	// =================================================================================

} // END CLASS
