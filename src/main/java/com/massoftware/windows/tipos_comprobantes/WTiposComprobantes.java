package com.massoftware.windows.tipos_comprobantes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.sucursales.SucursalesOld;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;

public class WTiposComprobantes extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<TiposComprobantesFiltro> filterBI;
	private BeanItemContainer<TiposComprobantes> itemsBIC;

	// -------------------------------------------------------------

	protected int limit = 15;
	protected int offset = 0;

	// -------------------------------------------------------------

	public Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;
	private Button agregarBTN;
	private Button modificarBTN;
	private Button eliminarBTN;

	// -------------------------------------------------------------

	private HorizontalLayout numeroTXTHL;
	private HorizontalLayout nombreTXTHL;

	// -------------------------------------------------------------
	
	
	public WTiposComprobantes() {
		super();
		init(null);
	}
	
	
	public WTiposComprobantes(Integer numero) {
		super();
		init(numero);
	}

	@SuppressWarnings({ "serial", "unchecked" })
	public void init(Integer numero) {
		

		try {

			buildContainersItems();
			
			filterBI.getItemProperty("numero").setValue(numero);

			UtilUI.confWinList(this, "Tipos de comprobantes");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			// -----------

			HorizontalLayout paisesCBXHL = UtilUI.buildCBHL(filterBI,
					"sucursal", "Sucursal", false, true, SucursalesOld.class,
					queryDataSucursales());

			ComboBox paisesCBX = (ComboBox) paisesCBXHL.getComponent(0);

			paisesCBX.addValueChangeListener(e -> {
				this.loadDataResetPaged();
			});

			Button paisBTN = (Button) paisesCBXHL.getComponent(1);

			paisBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroTXTHL = UtilUI.buildTXTHLInteger(filterBI, "numero",
					"Numero", false, 5, -1, 3, false, false, null, false,
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

			nombreTXTHL = UtilUI.buildTXTHL(filterBI, "nombre", "Nombre",
					false, 20, -1, 25, false, false, null, false,
					UtilUI.CONTAINS_WORDS_AND);

			TextField nombreTXT = (TextField) nombreTXTHL.getComponent(0);

			nombreTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						nombreTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button nombreBTN = (Button) nombreTXTHL.getComponent(1);

			nombreBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(paisesCBXHL, numeroTXTHL, nombreTXTHL,
					buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			// itemsGRD.setWidth("390px");
			itemsGRD.setWidth(55.0f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "numeroSucursal", "numero",
					"nombre", "abreviatura", "clase", "concepto", "stk", "iva",
					"comision", "comLey", "esta" });

			UtilUI.confColumn(itemsGRD.getColumn("numeroSucursal"), "Sucursal",
					true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numero"), "Prov.", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombre"), "Nombre", true, 200);
			UtilUI.confColumn(itemsGRD.getColumn("abreviatura"), "Abreviatura",
					true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("clase"), "Clase", true, 150);
			UtilUI.confColumn(itemsGRD.getColumn("concepto"), "Concepto", true,
					150);
			UtilUI.confColumn(itemsGRD.getColumn("stk"), "stk", true, 30);
			UtilUI.confColumn(itemsGRD.getColumn("iva"), "iva", true, 30);
			UtilUI.confColumn(itemsGRD.getColumn("comision"), "comision", true,
					50);
			UtilUI.confColumn(itemsGRD.getColumn("comLey"), "comLey", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("esta"), "esta", true, -1);

			itemsGRD.setContainerDataSource(itemsBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			itemsGRD.getColumn("stk").setRenderer(
					new HtmlRenderer(),
					new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
							.getHtml(), FontAwesome.SQUARE_O.getHtml()));
			itemsGRD.getColumn("iva").setRenderer(
					new HtmlRenderer(),
					new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
							.getHtml(), FontAwesome.SQUARE_O.getHtml()));
			itemsGRD.getColumn("comision").setRenderer(
					new HtmlRenderer(),
					new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
							.getHtml(), FontAwesome.SQUARE_O.getHtml()));
			itemsGRD.getColumn("comLey").setRenderer(
					new HtmlRenderer(),
					new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
							.getHtml(), FontAwesome.SQUARE_O.getHtml()));
			itemsGRD.getColumn("esta").setRenderer(
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

			order.add(new SortOrder("numeroSucursal", SortDirection.ASCENDING));
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

		filterBI = new BeanItem<TiposComprobantesFiltro>(
				new TiposComprobantesFiltro());
		itemsBIC = new BeanItemContainer<TiposComprobantes>(
				TiposComprobantes.class, new ArrayList<TiposComprobantes>());
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

													TiposComprobantes item = (TiposComprobantes) itemsGRD
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

				TiposComprobantes item = (TiposComprobantes) itemsGRD
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

			((Validatable) numeroTXTHL.getComponent(0)).validate();
			((Validatable) nombreTXTHL.getComponent(0)).validate();

			List<TiposComprobantes> items = queryData();

			itemsBIC.removeAllItems();

			for (TiposComprobantes item : items) {
				itemsBIC.addBean(item);
			}

			boolean enabled = itemsBIC.size() > 0;

			itemsGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

			nextPageBTN
					.setEnabled(itemsBIC.size() > 0 && itemsBIC.size() >= limit);

			prevPageBTN.setEnabled(offset >= limit);

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	private List<SucursalesOld> queryDataSucursales() {
		try {

			return mockDataSucursales();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<SucursalesOld>();
	}

	// metodo que realiza la consulta a la base de datos
	private List<TiposComprobantes> queryData() {
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

			List<TiposComprobantes> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<TiposComprobantes>();
	}

	// metodo que realiza el delete en la base de datos
	private void deleteItem(TiposComprobantes item) {
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

	List<TiposComprobantes> itemsMock = new ArrayList<TiposComprobantes>();

	private List<TiposComprobantes> mockData(int limit, int offset,
			TiposComprobantesFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				TiposComprobantes item = new TiposComprobantes();

				item.setNumeroSucursal(i);
				item.setNumero(i);
				item.setNombre("Nombre " + i);
				item.setAbreviatura("Abreviatura " + i);
				item.setClase("Clase " + 1);
				item.setConcepto("Concepto " + i);
				item.setStk(i % 2 == 0);
				item.setIva(i % 2 == 0);
				item.setComision(i % 2 == 0);
				item.setComLey(i % 2 != 0);
				item.setEsta(i % 2 != 0);

				itemsMock.add(item);
			}
		}

		ArrayList<TiposComprobantes> arrayList = new ArrayList<TiposComprobantes>();

		for (TiposComprobantes item : itemsMock) {

			boolean passesFilterNumeroPais = (filtro.getSucursal() == null || item
					.getNumeroSucursal().equals(
							filtro.getSucursal().getNumero()));

			boolean passesFilterNumero = (filtro.getNumero() == null || item
					.getNumero().equals(filtro.getNumero()));

			boolean passesFilterNombre = (filtro.getNombre() == null || item
					.getNombre().toLowerCase()
					.contains(filtro.getNombre().toLowerCase()));

			if (passesFilterNumeroPais && passesFilterNumero
					&& passesFilterNombre) {
				arrayList.add(item);
			}
		}

		int end = offset + limit;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offset, end);
	}

	private List<SucursalesOld> mockDataSucursales() {

		List<SucursalesOld> itemsMock = new ArrayList<SucursalesOld>();

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				SucursalesOld item = new SucursalesOld();

				item.setNumero(i);
				item.setNombre("Nombre " + i);
				item.setTipoSucursal("Tipo sucursal " + i);				

				itemsMock.add(item);
			}
		}

		return itemsMock;
	}

	// =================================================================================

} // END CLASS
