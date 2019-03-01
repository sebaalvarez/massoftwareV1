package com.massoftware.windows.cobranzas;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.windows.EliminarDialog;
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
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class WCobranzas extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	public final static String C_MODE = "Cobranzas";
	public final static String CT_MODE = "Cierre tesorería";
	public final static String CCC_MODE = "Cierre de cuentas corrientes";
	public final static String CC_MODE = "Cierre de cobranza";
	
	private String mode = C_MODE;

	// -------------------------------------------------------------

	private BeanItem<CobranzasFiltro> filterBI;
	private BeanItemContainer<Cobranzas> itemsBIC;

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

	private HorizontalLayout numeroCobranzaTXTHL;
	private HorizontalLayout detalleCobranzaTXTHL;

	// -------------------------------------------------------------

	public WCobranzas() {
		super();
		init(null);
	}

	public WCobranzas(Integer numero) {
		super();
		init(numero);
	}
	
	public WCobranzas(String mode) {
		super();
		this.mode = mode;
		init(null);
	}

	@SuppressWarnings({ "serial", "unchecked" })
	public void init(Integer numero) {
		try {

			buildContainersItems();

			filterBI.getItemProperty("numeroCobranza").setValue(numero);

			UtilUI.confWinList(this, "Cobranzas");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			// -----------

			numeroCobranzaTXTHL = UtilUI.buildTXTHLInteger(filterBI,
					"numeroCobranza", "Numero", false, 5, -1, 3, false, false,
					null, false, UtilUI.EQUALS, 0, 255);

			TextField numeroCobranzaTXT = (TextField) numeroCobranzaTXTHL
					.getComponent(0);

			numeroCobranzaTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroCobranzaTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroCobranzaBTN = (Button) numeroCobranzaTXTHL
					.getComponent(1);

			numeroCobranzaBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			detalleCobranzaTXTHL = UtilUI.buildTXTHL(filterBI,
					"detalleCobranza", "Detalle", false, 20, -1, 25, false,
					false, null, false, UtilUI.CONTAINS_WORDS_AND);

			TextField detalleCobranzaTXT = (TextField) detalleCobranzaTXTHL
					.getComponent(0);

			detalleCobranzaTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						detalleCobranzaTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button detalleCobranzaBTN = (Button) detalleCobranzaTXTHL
					.getComponent(1);

			detalleCobranzaBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(numeroCobranzaTXTHL,
					detalleCobranzaTXTHL, buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth(49f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "numeroCobranza",
					"detalleCobranza", "numeroVendedor", "nombreVendedor",
					"numeroZona", "nombreZona", "recepcion", "ticketInicio",
					"estado" });

			UtilUI.confColumn(itemsGRD.getColumn("numeroCobranza"), "Nro.",
					true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("detalleCobranza"), "Nombre",
					true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("numeroVendedor"), "Nro.",
					true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreVendedor"), "Nombre",
					true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("numeroZona"), "Nro.", true,
					50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreZona"), "Nombre", true,
					100);
			UtilUI.confColumn(itemsGRD.getColumn("recepcion"), "Recepción",
					true, 120);
			UtilUI.confColumn(itemsGRD.getColumn("ticketInicio"),
					"Ticket inicio", true, 120);
			UtilUI.confColumn(itemsGRD.getColumn("estado"), "Estado", true, -1);

			// Group headers by joining the cells
			HeaderRow groupingHeader = itemsGRD.prependHeaderRow();

			HeaderCell namesCellCobranza = groupingHeader.join(
					groupingHeader.getCell("numeroCobranza"),
					groupingHeader.getCell("detalleCobranza"));
			namesCellCobranza.setText("Cobranza");

			HeaderCell namesCellVendedor = groupingHeader.join(
					groupingHeader.getCell("numeroVendedor"),
					groupingHeader.getCell("nombreVendedor"));
			namesCellVendedor.setText("Vendedor");

			HeaderCell namesCellZona = groupingHeader.join(
					groupingHeader.getCell("numeroZona"),
					groupingHeader.getCell("nombreZona"));
			namesCellZona.setText("Zona");

			itemsGRD.setContainerDataSource(itemsBIC);

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
			itemsGRD.getColumn("recepcion").setRenderer(
					new DateRenderer(
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));
			itemsGRD.getColumn("ticketInicio").setRenderer(
					new DateRenderer(
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> order = new ArrayList<SortOrder>();

			order.add(new SortOrder("numeroCobranza", SortDirection.ASCENDING));

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
			agregarBTN.setVisible(C_MODE.equals(mode));
			modificarBTN = UtilUI.buildButtonModificar();
			modificarBTN.addClickListener(e -> {
				modificarBTNClick();
			});
			modificarBTN.setVisible(C_MODE.equals(mode));

			Button cierreTicketsBTN = UtilUI.buildButton(
					"Control y cierre de tickets",
					"Control y cierre de tickets");
			cierreTicketsBTN.setIcon(FontAwesome.ARCHIVE);
			cierreTicketsBTN.addClickListener(e -> {
				// modificarBTNClick();
				});
			cierreTicketsBTN.setVisible(CT_MODE.equals(mode));

			Button cierreTesoreriaBTN = UtilUI.buildButton(
					"Control y cierre de tesorería",
					"Control y cierre de tesorería");
			cierreTesoreriaBTN.setIcon(FontAwesome.ARCHIVE);
			cierreTesoreriaBTN.addClickListener(e -> {
				// modificarBTNClick();
				});
			cierreTesoreriaBTN.setVisible(CT_MODE.equals(mode));

			Button cierreCCBTN = UtilUI.buildButton("Cierre de ctas. ctes.",
					"Cierre de ctas. ctes.");
			cierreCCBTN.setIcon(FontAwesome.ARCHIVE);
			cierreCCBTN.addClickListener(e -> {
				// modificarBTNClick();
				});
			cierreCCBTN.setVisible(CCC_MODE.equals(mode));

			Button cierreCBTN = UtilUI.buildButton("Cierre de cobranza",
					"Cierre cobranza");
			cierreCBTN.setIcon(FontAwesome.ARCHIVE);
			cierreCBTN.addClickListener(e -> {
				// modificarBTNClick();
				});
			cierreCBTN.setVisible(CC_MODE.equals(mode));

			Button informeBTN = UtilUI.buildButton("Informe de cobranza",
					"Informe de cobranza");
			informeBTN.setIcon(FontAwesome.PRINT);
			informeBTN.addClickListener(e -> {
				// modificarBTNClick();
				});

			Button consultarBTN = UtilUI.buildButton("Consultar", "Consultar");
			consultarBTN.setIcon(FontAwesome.BOOK);
			consultarBTN.addClickListener(e -> {
				// modificarBTNClick();
				});

			filaBotoneraHL.addComponents(agregarBTN, modificarBTN,
					cierreTicketsBTN, cierreTesoreriaBTN, cierreCCBTN,
					cierreCBTN, informeBTN, consultarBTN);

			// -------------------------------------------------------
			// BOTONERA 2

			HorizontalLayout filaBotonera2HL = new HorizontalLayout();
			filaBotonera2HL.setSpacing(true);
			filaBotonera2HL.setVisible(C_MODE.equals(mode));

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

		filterBI = new BeanItem<CobranzasFiltro>(new CobranzasFiltro());
		itemsBIC = new BeanItemContainer<Cobranzas>(Cobranzas.class,
				new ArrayList<Cobranzas>());
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

													Cobranzas item = (Cobranzas) itemsGRD
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

				Cobranzas item = (Cobranzas) itemsGRD.getSelectedRow();

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

			((Validatable) numeroCobranzaTXTHL.getComponent(0)).validate();
			((Validatable) detalleCobranzaTXTHL.getComponent(0)).validate();

			List<Cobranzas> items = queryData();

			itemsBIC.removeAllItems();

			for (Cobranzas item : items) {
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

	// metodo que realiza la consulta a la base de datos
	private List<Cobranzas> queryData() {
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

			List<Cobranzas> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<Cobranzas>();
	}

	// metodo que realiza el delete en la base de datos
	private void deleteItem(Cobranzas item) {
		try {

			for (int i = 0; i < itemsMock.size(); i++) {
				if (itemsMock.get(i).getNumeroCobranza()
						.equals(item.getNumeroCobranza())) {
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

	List<Cobranzas> itemsMock = new ArrayList<Cobranzas>();

	private List<Cobranzas> mockData(int limit, int offset,
			CobranzasFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				Cobranzas item = new Cobranzas();

				item.setNumeroCobranza(i);
				item.setDetalleCobranza("Detalle " + i);
				item.setNumeroVendedor(i);
				item.setNombreVendedor("Vendedro " + i);
				item.setNumeroZona(i);
				item.setNombreZona("Zona " + i);
				item.setRecepcion(new Timestamp(System.currentTimeMillis()));
				item.setTicketInicio(new Timestamp(System.currentTimeMillis()));
				item.setEstado("Estado " + i);

				itemsMock.add(item);
			}
		}

		ArrayList<Cobranzas> arrayList = new ArrayList<Cobranzas>();

		for (Cobranzas item : itemsMock) {

			boolean passesFilterNumero = (filtro.getNumeroCobranza() == null || item
					.getNumeroCobranza().equals(filtro.getNumeroCobranza()));

			boolean passesFilterDetalle = (filtro.getDetalleCobranza() == null || item
					.getDetalleCobranza().toLowerCase()
					.contains(filtro.getDetalleCobranza().toLowerCase()));

			if (passesFilterNumero && passesFilterDetalle) {
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
