package com.massoftware.windows.conciliaciones_bancarias;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilModel;
import com.massoftware.windows.UtilUI;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.SortEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class WConciliacionesBancarias extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<ConciliacionesBancariasFiltro> filterBI;
	private BeanItemContainer<ConciliacionesBancarias> itemsBIC;

	// -------------------------------------------------------------

	protected int limit = 15;
	protected int offset = 0;

	// -------------------------------------------------------------

	private Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;
	private Button agregarBTN;
	private Button modificarBTN;
	private Button eliminarBTN;

	// -------------------------------------------------------------

	private HorizontalLayout cuentasCBXHL;
	private HorizontalLayout desdeDFHL;
	private HorizontalLayout hastaDFHL;

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WConciliacionesBancarias() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Conciliaciones bancarias");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			// -----------

			cuentasCBXHL = UtilUI.buildCBHL(filterBI, "cuenta", "Cuenta",
					false, true, Cuentas.class, queryDataCuentas());

			ComboBox cuentasCBX = (ComboBox) cuentasCBXHL.getComponent(0);

			cuentasCBX.addValueChangeListener(e -> {
				this.loadDataResetPaged();
			});

			Button cuentasBTN = (Button) cuentasCBXHL.getComponent(1);

			cuentasBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			desdeDFHL = UtilUI.buildDFHL(filterBI, "desde", "Desde", false,
					false);

			DateField desdeDF = (DateField) desdeDFHL.getComponent(0);

			desdeDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			hastaDFHL = UtilUI.buildDFHL(filterBI, "hasta", "Hasta", false,
					false);

			DateField hastaDF = (DateField) hastaDFHL.getComponent(0);

			hastaDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(cuentasCBXHL, desdeDFHL, hastaDFHL,
					buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth("390px");
			itemsGRD.setWidth("100%");
			itemsGRD.setHeight(20.5f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "desde", "hasta",
					"numeroCuenta", "numero", "saldoInicial", "saldoFinal",
					"nombreEstado" });

			UtilUI.confColumn(itemsGRD.getColumn("numeroCuenta"), "Cuenta",
					true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numero"), "Nro.", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("desde"), "Desde", true, 90);
			UtilUI.confColumn(itemsGRD.getColumn("hasta"), "Hasta", true, 90);
			UtilUI.confColumn(itemsGRD.getColumn("saldoInicial"),
					"Saldo inicial", true, 70);
			UtilUI.confColumn(itemsGRD.getColumn("saldoFinal"), "Saldo final",
					true, 70);
			UtilUI.confColumn(itemsGRD.getColumn("nombreEstado"), "Estado",
					true, -1);

			itemsGRD.setContainerDataSource(itemsBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new HtmlRenderer(),
			// new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
			// .getHtml(), FontAwesome.SQUARE_O.getHtml()));

			// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
			itemsGRD.getColumn("desde").setRenderer(
					new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));
			itemsGRD.getColumn("hasta").setRenderer(
					new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

			// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(
			// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> order = new ArrayList<SortOrder>();

			order.add(new SortOrder("numeroCuenta", SortDirection.ASCENDING));
			order.add(new SortOrder("numero", SortDirection.ASCENDING));

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

			agregarBTN = UtilUI.buildButtonAgregar();
			agregarBTN.addClickListener(e -> {
				agregarBTNClick();
			});
			modificarBTN = UtilUI.buildButtonModificar();
			modificarBTN.addClickListener(e -> {
				modificarBTNClick();
			});

			Button papelA4BTN = UtilUI.buildButton("Papel: A4", "Papel: A4");
			papelA4BTN.setIcon(FontAwesome.PRINT);
			papelA4BTN.addClickListener(e -> {
				// seguimientoBTNClick();
				});

			filaBotoneraHL.addComponents(agregarBTN, modificarBTN, papelA4BTN);

			// -------------------------------------------------------
			// BOTONERA 2

			HorizontalLayout filaBotonera2HL = new HorizontalLayout();
			filaBotonera2HL.setSpacing(true);

			eliminarBTN = UtilUI.buildButtonEliminar();
			eliminarBTN.addClickListener(e -> {
				eliminarBTNClick();
			});

			filaBotonera2HL.addComponents(eliminarBTN);

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
					agregarBTNClick();
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

		filterBI = new BeanItem<ConciliacionesBancariasFiltro>(
				new ConciliacionesBancariasFiltro());
		itemsBIC = new BeanItemContainer<ConciliacionesBancarias>(
				ConciliacionesBancarias.class,
				new ArrayList<ConciliacionesBancarias>());
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

	private void eliminarBTNClick() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				getUI().addWindow(
						new EliminarDialog(
								itemsGRD.getSelectedRow().toString(),
								new EliminarDialog.Callback() {
									public void onDialogResult(boolean yes) {

										try {
											if (yes) {
												if (itemsGRD.getSelectedRow() != null) {

													ConciliacionesBancarias item = (ConciliacionesBancarias) itemsGRD
															.getSelectedRow();

													deleteItem(item);

													LogAndNotification
															.printSuccessOk("Se eliminó con éxito el ítem "
																	+ item);

													loadData();
												}
											}
										} catch (Exception e) {
											LogAndNotification.print(e);
										}

									}
								}));
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void agregarBTNClick() {
		try {

			itemsGRD.select(null);
			Window window = new Window("Agregar ítem");
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

				ConciliacionesBancarias item = (ConciliacionesBancarias) itemsGRD
						.getSelectedRow();
				item.getNumero();

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

			((Validatable) desdeDFHL.getComponent(0)).validate();
			((Validatable) hastaDFHL.getComponent(0)).validate();

			List<ConciliacionesBancarias> items = queryData();

			itemsBIC.removeAllItems();

			for (ConciliacionesBancarias item : items) {
				itemsBIC.addBean(item);
			}

			boolean enabled = itemsBIC.size() > 0;

			itemsGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

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

	private List<Cuentas> queryDataCuentas() {
		try {

			return mockDataPaises();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<Cuentas>();
	}

	// metodo que realiza la consulta a la base de datos
	private List<ConciliacionesBancarias> queryData() {
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

			List<ConciliacionesBancarias> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<ConciliacionesBancarias>();
	}

	// metodo que realiza el delete en la base de datos
	private void deleteItem(ConciliacionesBancarias item) {
		try {

			for (int i = 0; i < itemsMock.size(); i++) {
				if (itemsMock.get(i).getNumero().equals(item.getNumero())) {
					itemsMock.remove(i);
					return;
				}
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<ConciliacionesBancarias> itemsMock = new ArrayList<ConciliacionesBancarias>();

	private List<ConciliacionesBancarias> mockData(int limit, int offset,
			ConciliacionesBancariasFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				ConciliacionesBancarias item = new ConciliacionesBancarias();

				item.setNumeroCuenta(i);
				item.setNumero(i);
				item.setDesde(new Date(System.currentTimeMillis()));
				item.setHasta(new Date(System.currentTimeMillis()));
				item.setSaldoInicial(new BigDecimal("6548.688"));
				item.setSaldoFinal(new BigDecimal("6548.688"));
				item.setNombreEstado(i % 2 == 0 ? "Terminada" : "Pendiente");

				itemsMock.add(item);
			}
		}

		ArrayList<ConciliacionesBancarias> arrayList = new ArrayList<ConciliacionesBancarias>();

		for (ConciliacionesBancarias item : itemsMock) {

			boolean passesFilterNumeroCuenta = (filtro.getCuenta() == null || item
					.getNumeroCuenta().equals(filtro.getCuenta().getNumero()));

			boolean passesFilterDesde = (filtro.getDesde() == null || item
					.getDesde().after(filtro.getDesde()));

			boolean passesFilterHasta = (filtro.getHasta() == null || item
					.getHasta().before(
							UtilModel.sumarDiasAFecha(filtro.getHasta(), 1)));

			if (passesFilterNumeroCuenta && passesFilterDesde
					&& passesFilterHasta) {
				arrayList.add(item);
			}
		}

		int end = offset + limit;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offset, end);
	}

	private List<Cuentas> mockDataPaises() {

		List<Cuentas> itemsMock = new ArrayList<Cuentas>();

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				Cuentas item = new Cuentas();

				item.setNumero(i);
				item.setNombre("Nombre " + i);

				itemsMock.add(item);
			}
		}

		return itemsMock;
	}

	// =================================================================================

} // END CLASS
