package com.massoftware.windows.captura_lotes_tickets;

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
import com.massoftware.windows.cobranzas.Cobranzas;
import com.massoftware.windows.cobranzas.WCobranzas;
import com.massoftware.windows.vendedores.Vendedores;
import com.massoftware.windows.vendedores.WVendedores;
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
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class WCapturaLotesTickets extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<CapturaLotesTicketsFiltro> filterBI;
	private BeanItemContainer<CapturaLotesTickets> itemsBIC;

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

	private HorizontalLayout numeroLoteTXTHL;
	private HorizontalLayout nombreClienteTXTHL;
	private HorizontalLayout fechaDedeDFHL;
	private HorizontalLayout fechaHastaDFHL;

	private HorizontalLayout numeroCobranzaCBXHL;
	private HorizontalLayout numeroVendedorCBXHL;

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WCapturaLotesTickets() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Captura de tickets");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			HorizontalLayout filaFiltro2HL = new HorizontalLayout();
			filaFiltro2HL.setSpacing(true);

			// -----------

			numeroLoteTXTHL = UtilUI.buildTXTHLInteger(filterBI, "numeroLote",
					"Lote", false, 5, -1, 3, false, false, null, false,
					UtilUI.EQUALS, 0, 255);

			TextField numeroTXT = (TextField) numeroLoteTXTHL.getComponent(0);

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

			Button numeroBTN = (Button) numeroLoteTXTHL.getComponent(1);

			numeroBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			nombreClienteTXTHL = UtilUI.buildTXTHL(filterBI, "nombreCliente",
					"Cliente", false, 20, -1, 25, false, false, null, false,
					UtilUI.CONTAINS_WORDS_AND);

			TextField nombreTXT = (TextField) nombreClienteTXTHL
					.getComponent(0);

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

			Button nombreBTN = (Button) nombreClienteTXTHL.getComponent(1);

			nombreBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});
			// -----------

			fechaDedeDFHL = UtilUI.buildDFHL(filterBI, "fechaDesde", "Desde",
					false, false);

			DateField fechaDesdeDF = (DateField) fechaDedeDFHL.getComponent(0);

			fechaDesdeDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			fechaHastaDFHL = UtilUI.buildDFHL(filterBI, "fechaHasta", "Hasta",
					false, false);

			DateField fechaHastaDF = (DateField) fechaHastaDFHL.getComponent(0);

			fechaHastaDF.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					loadDataResetPaged();
				}
			});

			// -----------

			numeroCobranzaCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroCobranza", "nombreCobranza", "Cobranza", "numero",
					false, "Cobranza", false);

			TextField numeroCobranzaTXT = (TextField) numeroCobranzaCBXHL
					.getComponent(0);

			numeroCobranzaTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroCobranzaTXT.setValue(event.getText());
						selectNumeroCobranzaTXTShortcutEnter();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroCobranzaBTN = (Button) numeroCobranzaCBXHL
					.getComponent(2);

			numeroCobranzaBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroVendedorCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroVendedor", "nombreVendedor", "Vendedor", "numero",
					false, "Vendedor", false);

			TextField numeroVendedorTXT = (TextField) numeroVendedorCBXHL
					.getComponent(0);

			numeroVendedorTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroVendedorTXT.setValue(event.getText());
						selectNumeroVendedorTXTShortcutEnter();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroVendedorBTN = (Button) numeroVendedorCBXHL
					.getComponent(2);

			numeroVendedorBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(fechaDedeDFHL, fechaHastaDFHL,
					numeroLoteTXTHL, nombreClienteTXTHL, buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			filaFiltro2HL.addComponents(numeroCobranzaCBXHL,
					numeroVendedorCBXHL);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth(43f, Unit.EM);
			// itemsGRD.setWidth("100%");

			itemsGRD.setColumns(new Object[] { "numeroLote", "fecha",
					"numeroCobranza", "nombreCobranza", "numeroVendedor",
					"nombreVendedor", "cuentaCliente", "nombreCliente",
					"importeTotal", "ecl" });

			UtilUI.confColumn(itemsGRD.getColumn("numeroLote"), "Lote", true,
					50);
			UtilUI.confColumn(itemsGRD.getColumn("fecha"), "Fecha", true, 90);
			UtilUI.confColumn(itemsGRD.getColumn("numeroCobranza"), "Cobranza",
					true, true, false, true, 50);

			UtilUI.confColumn(itemsGRD.getColumn("nombreCobranza"), "Cobranza",
					true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("numeroVendedor"), "Nro",
					true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreVendedor"), "Nombre",
					true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("cuentaCliente"), "Nro.",
					true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreCliente"), "Nombre",
					true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("importeTotal"),
					"Importe total", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("ecl"), "Ecl", true, -1);

			// Group headers by joining the cells
			HeaderRow groupingHeader = itemsGRD.prependHeaderRow();

			HeaderCell namesCellCobranza = groupingHeader.join(
					groupingHeader.getCell("numeroVendedor"),
					groupingHeader.getCell("nombreVendedor"));
			namesCellCobranza.setText("Vendedor");

			HeaderCell namesCellVendedor = groupingHeader.join(
					groupingHeader.getCell("cuentaCliente"),
					groupingHeader.getCell("nombreCliente"));
			namesCellVendedor.setText("Cliente");

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

			agregarBTN = UtilUI.buildButtonAgregar();
			agregarBTN.addClickListener(e -> {
				agregarBTNClick();
			});
			modificarBTN = UtilUI.buildButtonModificar();
			modificarBTN.addClickListener(e -> {
				modificarBTNClick();
			});
			Button consultarBTN = UtilUI.buildButton("Consultar", "Consultar");
			consultarBTN.setIcon(FontAwesome.BOOK);
			consultarBTN.addClickListener(e -> {
				// consultarBTNClick();
				});

			filaBotoneraHL
					.addComponents(agregarBTN, modificarBTN, consultarBTN);

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

			content.addComponents(filaFiltroHL, filaFiltro2HL, itemsGRD,
					filaBotoneraPagedHL, filaBotoneraHL, filaBotonera2HL);

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

		filterBI = new BeanItem<CapturaLotesTicketsFiltro>(
				new CapturaLotesTicketsFiltro());
		itemsBIC = new BeanItemContainer<CapturaLotesTickets>(
				CapturaLotesTickets.class, new ArrayList<CapturaLotesTickets>());
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

													CapturaLotesTickets item = (CapturaLotesTickets) itemsGRD
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

				CapturaLotesTickets item = (CapturaLotesTickets) itemsGRD
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

	@SuppressWarnings("unchecked")
	protected void selectNumeroCobranzaTXTShortcutEnter() {
		try {

			if (this.filterBI.getBean().getNumeroCobranza() != null) {

				WCobranzas window = new WCobranzas(this.filterBI.getBean()
						.getNumeroCobranza());
				window.setModal(true);
				window.center();

				window.addCloseListener(new CloseListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent event) {
						setNumeroCobranzaOnFilter(window);
					}
				});

				// -------------------------------------------------------
				// BOTONERA SELECCION

				HorizontalLayout filaBotoneraHL = new HorizontalLayout();
				filaBotoneraHL.setSpacing(true);

				Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
				seleccionarBTN.addClickListener(e -> {
					setNumeroCobranzaOnFilter(window);
				});

				filaBotoneraHL.addComponents(seleccionarBTN);

				((VerticalLayout) window.getContent())
						.addComponent(filaBotoneraHL);

				((VerticalLayout) window.getContent()).setComponentAlignment(
						filaBotoneraHL, Alignment.MIDDLE_CENTER);

				getUI().addWindow(window);

			} else {
				this.filterBI.getItemProperty("nombreCobranza").setValue(null);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroCobranzaOnFilter(WCobranzas window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				Cobranzas item = (Cobranzas) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroCobranza").setValue(
						item.getNumeroCobranza());
				this.filterBI.getItemProperty("nombreCobranza").setValue(
						item.getDetalleCobranza());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroCobranza").setValue(null);
				this.filterBI.getItemProperty("nombreCobranza").setValue(null);
			}
		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected void selectNumeroVendedorTXTShortcutEnter() {
		try {

			if (this.filterBI.getBean().getNumeroVendedor() != null) {

				WVendedores window = new WVendedores(this.filterBI.getBean()
						.getNumeroCobranza());
				window.setModal(true);
				window.center();

				window.addCloseListener(new CloseListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent event) {
						setNumeroVendedorOnFilter(window);
					}
				});

				// -------------------------------------------------------
				// BOTONERA SELECCION

				HorizontalLayout filaBotoneraHL = new HorizontalLayout();
				filaBotoneraHL.setSpacing(true);

				Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
				seleccionarBTN.addClickListener(e -> {
					setNumeroVendedorOnFilter(window);
				});

				filaBotoneraHL.addComponents(seleccionarBTN);

				((VerticalLayout) window.getContent())
						.addComponent(filaBotoneraHL);

				((VerticalLayout) window.getContent()).setComponentAlignment(
						filaBotoneraHL, Alignment.MIDDLE_CENTER);

				getUI().addWindow(window);

			} else {
				this.filterBI.getItemProperty("nombreVendedor").setValue(null);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroVendedorOnFilter(WVendedores window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				Vendedores item = (Vendedores) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroVendedor").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreVendedor").setValue(
						item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroVendedor").setValue(null);
				this.filterBI.getItemProperty("nombreVendedor").setValue(null);
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

	private void loadData() {
		try {

			((Validatable) numeroLoteTXTHL.getComponent(0)).validate();
			((Validatable) nombreClienteTXTHL.getComponent(0)).validate();

			List<CapturaLotesTickets> items = queryData();

			itemsBIC.removeAllItems();

			for (CapturaLotesTickets item : items) {
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
	private List<CapturaLotesTickets> queryData() {
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

			List<CapturaLotesTickets> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<CapturaLotesTickets>();
	}

	// metodo que realiza el delete en la base de datos
	private void deleteItem(CapturaLotesTickets item) {
		try {

			for (int i = 0; i < itemsMock.size(); i++) {
				if (itemsMock.get(i).getNumeroLote()
						.equals(item.getNumeroLote())) {
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

	List<CapturaLotesTickets> itemsMock = new ArrayList<CapturaLotesTickets>();

	private List<CapturaLotesTickets> mockData(int limit, int offset,
			CapturaLotesTicketsFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				CapturaLotesTickets item = new CapturaLotesTickets();

				item.setNumeroLote(i);
				item.setFecha(new Date(System.currentTimeMillis()));
				item.setNumeroCobranza(i);
				item.setNombreCobranza("Cobranza " + i);
				item.setNumeroVendedor(i);
				item.setNombreVendedor("Vendedor " + i);
				item.setCuentaCliente(i);
				item.setNombreCliente("Cliente " + i);
				item.setImporteTotal(new BigDecimal("569897.36985"));
				item.setEcl("ecl " + i);

				itemsMock.add(item);
			}
		}

		ArrayList<CapturaLotesTickets> arrayList = new ArrayList<CapturaLotesTickets>();

		for (CapturaLotesTickets item : itemsMock) {

			boolean passesFilterNumeroLote = (filtro.getNumeroLote() == null || item
					.getNumeroLote().equals(filtro.getNumeroLote()));

			boolean passesFilterNombreCliente = (filtro.getNombreCliente() == null || item
					.getNombreCliente().toLowerCase()
					.contains(filtro.getNombreCliente().toLowerCase()));

			boolean passesFilterFechaDesde = (filtro.getFechaDesde() == null || item
					.getFecha().after(filtro.getFechaDesde()));

			boolean passesFilterFechaHasta = (filtro.getFechaHasta() == null || item
					.getFecha()
					.before(UtilModel.sumarDiasAFecha(filtro.getFechaHasta(), 1)));

			boolean passesFilterNumeroCobranza = (filtro.getNumeroCobranza() == null || item
					.getNumeroCobranza().equals(filtro.getNumeroCobranza()));

			boolean passesFilterNumeroVendedor = (filtro.getNumeroVendedor() == null || item
					.getNumeroVendedor().equals(filtro.getNumeroVendedor()));

			if (passesFilterNumeroLote && passesFilterNombreCliente
					&& passesFilterFechaDesde && passesFilterFechaHasta
					&& passesFilterNumeroCobranza && passesFilterNumeroVendedor) {
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
