package com.massoftware.windows.chequeras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.SortEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;

public class WChequeras extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<ChequerasFiltro> filterBI;
	private BeanItemContainer<CuentasFondoFiltro> itemsFiltroBIC;
	private BeanItemContainer<Chequeras> itemsBIC;

	// -------------------------------------------------------------

	protected int limit = 15;
	protected int offset = 0;

	protected int limitFiltro = limit;
	protected int offsetFiltro = 0;

	// -------------------------------------------------------------

	public Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;
	private Button agregarBTN;
	private Button modificarBTN;
	private Button eliminarBTN;

	// -------------------------------------------------------------

	private OptionGroup activoOG;
	private Grid itemsFiltroGRD;
	private Button prevPageFiltroBTN;
	private Button nextPageFiltroBTN;

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WChequeras() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Chequeras");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			// -----------

			// CheckBox bloqueadoCHK = UtilUI.buildFieldCHK(this.filterBI,
			// "bloqueado", "Bloqueado", false);
			//
			// bloqueadoCHK.addValueChangeListener(event ->
			// loadDataResetPaged());

			// -----------

			activoOG = UtilUI.buildBooleanOG(filterBI, "bloqueado", null,
					false, false, "Todas", "Activas", "No activas", true, 0);

			activoOG.addValueChangeListener(new ValueChangeListener() {

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

			filaFiltroHL.addComponents(activoOG, buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// =======================================================

			HorizontalLayout grillas = new HorizontalLayout();
			grillas.setSpacing(true);

			VerticalLayout columna1VL = new VerticalLayout();
			columna1VL.setSpacing(true);

			VerticalLayout columna2VL = new VerticalLayout();
			columna2VL.setSpacing(true);

			grillas.addComponents(columna1VL, columna2VL);

			// -------------------------------------------------------
			// GRILLA FILTRO

			itemsFiltroGRD = UtilUI.buildGrid();
			itemsFiltroGRD.setWidth(18f, Unit.EM);

			itemsFiltroGRD.setColumns(new Object[] { "numero", "nombre" });

			UtilUI.confColumn(itemsFiltroGRD.getColumn("numero"), "Cuenta",
					true, 50);
			UtilUI.confColumn(itemsFiltroGRD.getColumn("nombre"),
					"Nombre cuenta fondo", true, -1);

			// Group headers by joining the cells
			HeaderRow groupingHeader = itemsFiltroGRD.prependHeaderRow();

			HeaderCell namesCellCobranza = groupingHeader.join(
					groupingHeader.getCell("numero"),
					groupingHeader.getCell("nombre"));
			namesCellCobranza.setText("Filtrar por cuenta fondo");

			itemsFiltroGRD.setContainerDataSource(itemsFiltroBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new HtmlRenderer(),
			// new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
			// .getHtml(), FontAwesome.SQUARE_O.getHtml()));

			// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

			// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(
			// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> orderFiltro = new ArrayList<SortOrder>();

			orderFiltro.add(new SortOrder("numero", SortDirection.ASCENDING));

			itemsFiltroGRD.setSortOrder(orderFiltro);

			HorizontalLayout filaBotoneraPagedFiltroHL = new HorizontalLayout();
			filaBotoneraPagedFiltroHL.setSpacing(true);

			prevPageFiltroBTN = UtilUI.buildButtonPrev(limitFiltro,
					offsetFiltro);
			prevPageFiltroBTN.addClickListener(e -> {
				prevPageFiltroBTNClick();
			});

			nextPageFiltroBTN = UtilUI.buildButtonNext(limitFiltro,
					offsetFiltro);
			nextPageFiltroBTN.addClickListener(e -> {
				nextPageFiltroBTNClick();
			});

			filaBotoneraPagedFiltroHL.addComponents(prevPageFiltroBTN,
					nextPageFiltroBTN);

			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth(24f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "numero", "primerNumero",
					"ultimoNumero", "proximoNumero", "bloqueado" });

			UtilUI.confColumn(itemsGRD.getColumn("numero"), "Nro.", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("primerNumero"),
					"Primer nro.", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("ultimoNumero"),
					"Último nro.", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("proximoNumero"),
					"Próximo nro.", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("bloqueado"), "Bloqueado",
					true, -1);

			itemsGRD.setContainerDataSource(itemsBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			itemsGRD.getColumn("bloqueado").setRenderer(
					new HtmlRenderer(),
					new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
							.getHtml(), FontAwesome.SQUARE_O.getHtml()));

			// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

			// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(
			// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> order = new ArrayList<SortOrder>();

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

			filaBotoneraHL.addComponents(agregarBTN, modificarBTN);

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

			columna1VL.addComponents(itemsFiltroGRD, filaBotoneraPagedFiltroHL);

			columna1VL.setComponentAlignment(itemsFiltroGRD,
					Alignment.MIDDLE_CENTER);
			columna1VL.setComponentAlignment(filaBotoneraPagedFiltroHL,
					Alignment.MIDDLE_RIGHT);

			columna2VL.addComponents(itemsGRD, filaBotoneraPagedHL);

			columna2VL.setComponentAlignment(filaBotoneraPagedHL,
					Alignment.MIDDLE_RIGHT);

			content.addComponents(filaFiltroHL, grillas, filaBotoneraHL,
					filaBotonera2HL);

			content.setComponentAlignment(filaFiltroHL, Alignment.MIDDLE_CENTER);
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

			itemsFiltroGRD.addItemClickListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {

					itemsGRD.select(null);

					CuentasFondoFiltro itemCuentasFondoFiltro = (CuentasFondoFiltro) event
							.getItemId();
					filterBI.getBean().setNumeroCuentaFondo(
							itemCuentasFondoFiltro.getNumero());
					loadDataResetPaged();
				}
			});

			itemsGRD.addSortListener(e -> {
				sort(e);
			});

			// =======================================================
			// -------------------------------------------------------

			loadDataFiltro();

			if (this.itemsFiltroBIC.size() > 0) {
				CuentasFondoFiltro itemCuentasFondoFiltro = (CuentasFondoFiltro) this.itemsFiltroBIC
						.getIdByIndex(0);
				itemsFiltroGRD.select(itemCuentasFondoFiltro);
				filterBI.getBean().setNumeroCuentaFondo(
						itemCuentasFondoFiltro.getNumero());
			}

			loadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================

	private void buildContainersItems() throws Exception {

		filterBI = new BeanItem<ChequerasFiltro>(new ChequerasFiltro());
		itemsFiltroBIC = new BeanItemContainer<CuentasFondoFiltro>(
				CuentasFondoFiltro.class, new ArrayList<CuentasFondoFiltro>());
		itemsBIC = new BeanItemContainer<Chequeras>(Chequeras.class,
				new ArrayList<Chequeras>());
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

	private void nextPageFiltroBTNClick() {
		offsetFiltro = offsetFiltro + limitFiltro;
		prevPageFiltroBTN.setEnabled(offsetFiltro > 0);
		loadDataFiltro();
		if (this.itemsFiltroBIC.size() <= 0) {
			prevPageFiltroBTNClick();
		}
	}

	private void prevPageFiltroBTNClick() {
		offsetFiltro = offsetFiltro - limitFiltro;
		if (offsetFiltro < 0) {
			offsetFiltro = 0;
		}
		prevPageFiltroBTN.setEnabled(offsetFiltro > 0);
		loadDataFiltro();
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

													Chequeras item = (Chequeras) itemsGRD
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
			Window window = new Window("Agregar ítem ");
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

				Chequeras item = (Chequeras) itemsGRD.getSelectedRow();
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

			List<Chequeras> items = queryData();

			itemsBIC.removeAllItems();

			for (Chequeras item : items) {
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

	// private void loadDataResetPagedFilter() {
	// this.offsetFiltro = 0;
	// loadDataFiltro();
	// }

	private void loadDataFiltro() {
		try {

			List<CuentasFondoFiltro> items = queryDataCuentasFondoFiltro();

			itemsFiltroBIC.removeAllItems();

			for (CuentasFondoFiltro item : items) {
				itemsFiltroBIC.addBean(item);
			}

			boolean enabled = itemsFiltroBIC.size() > 0;

			itemsFiltroGRD.setEnabled(enabled);

			nextPageFiltroBTN.setEnabled(itemsFiltroBIC.size() > 0
					&& itemsFiltroBIC.size() >= limitFiltro);

			prevPageFiltroBTN.setEnabled(offsetFiltro >= limitFiltro);

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	private List<CuentasFondoFiltro> queryDataCuentasFondoFiltro() {
		try {

			return mockDataCuentasFondoFiltro();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<CuentasFondoFiltro>();
	}

	// metodo que realiza la consulta a la base de datos
	private List<Chequeras> queryData() {

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

			List<Chequeras> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<Chequeras>();
	}

	// metodo que realiza el delete en la base de datos
	private void deleteItem(Chequeras item) {
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

	List<Chequeras> itemsMock = new ArrayList<Chequeras>();

	private List<Chequeras> mockData(int limit, int offset,
			ChequerasFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				Chequeras item = new Chequeras();

				item.setNumeroCuentaFondo(i);
				item.setNumero(i);
				item.setPrimerNumero(i);
				item.setUltimoNumero(i + 30);
				item.setProximoNumero(i + 1);
				item.setBloqueado(i % 2 == 0);

				itemsMock.add(item);
			}
		}

		ArrayList<Chequeras> arrayList = new ArrayList<Chequeras>();

		for (Chequeras item : itemsMock) {

			boolean passesFilternumeroCuentaFondo = (filtro
					.getNumeroCuentaFondo() != null && filtro
					.getNumeroCuentaFondo().equals(item.getNumeroCuentaFondo()));

			boolean passesFilterBloqueado = (filtro.getBloqueado() == null
					|| filtro.getBloqueado() == 0
					|| (item.getBloqueado().equals(true) && filtro
							.getBloqueado().equals(1)) || (item.getBloqueado()
					.equals(false) && filtro.getBloqueado().equals(2)));

			if (passesFilternumeroCuentaFondo && passesFilterBloqueado) {
				arrayList.add(item);
			}
		}

		int end = offset + limit;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offset, end);
	}

	List<CuentasFondoFiltro> itemsFiltroMock = new ArrayList<CuentasFondoFiltro>();

	private List<CuentasFondoFiltro> mockDataCuentasFondoFiltro() {

		if (itemsFiltroMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				CuentasFondoFiltro item = new CuentasFondoFiltro();

				item.setNumero(i);
				item.setNombre("Nombre " + i);

				itemsFiltroMock.add(item);
			}
		}

		ArrayList<CuentasFondoFiltro> arrayList = new ArrayList<CuentasFondoFiltro>();

		for (CuentasFondoFiltro item : itemsFiltroMock) {

			arrayList.add(item);
		}

		int end = offsetFiltro + limitFiltro;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offsetFiltro, end);

	}

	// =================================================================================

} // END CLASS
