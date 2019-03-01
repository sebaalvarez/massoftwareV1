package com.massoftware.windows.aperturas_cierres_cajas;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.model.Caja;
import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.cajas.WCajas;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class WAperturasCierresCajas extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<AperturasCierresCajasFiltro> filterBI;
	private BeanItemContainer<AperturasCierresCajas> itemsBIC;

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

	private HorizontalLayout numeroCajaCBXHL;
	private HorizontalLayout usuarioCBXHL;

	// -------------------------------------------------------------

	public WAperturasCierresCajas() {
		super();
		init();
	}

	@SuppressWarnings("serial")
	public void init() {

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Aperturas y cierres de cajas");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			// -----------

			numeroCajaCBXHL = UtilUI.buildSearchBox(filterBI, "numeroCaja",
					"nombreCaja", "Caja", "numero", false);

			TextField numeroCajaTXT = (TextField) numeroCajaCBXHL
					.getComponent(0);

			numeroCajaTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroCajaTXT.setValue(event.getText());
						selectCajaTXTShortcutEnter();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroCajaBTN = (Button) numeroCajaCBXHL.getComponent(2);

			numeroCajaBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			usuarioCBXHL = UtilUI.buildCBHL(filterBI, "usuario", "Cajero",
					false, true, Usuarios.class, queryDataUsuarios());

			ComboBox usuarioCBX = (ComboBox) usuarioCBXHL.getComponent(0);

			usuarioCBX.addValueChangeListener(e -> {
				this.loadDataResetPaged();
			});

			Button usuarioBTN = (Button) usuarioCBXHL.getComponent(1);

			usuarioBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL
					.addComponents(numeroCajaCBXHL, usuarioCBXHL, buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth(38.5f, Unit.EM);

			itemsGRD.setColumns(new Object[] { "numeroCaja", "nombreCaja",
					"numero", "numeroUsuario", "nombreUsuario", "apertura",
					"cierre" });

			UtilUI.confColumn(itemsGRD.getColumn("numeroCaja"), "Nro. caja",
					true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreCaja"), "Caja", true,
					150);
			UtilUI.confColumn(itemsGRD.getColumn("numero"), "Caja", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroUsuario"), "Usuario",
					true, true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombreUsuario"), "Responsable",
					true, 150);
			UtilUI.confColumn(itemsGRD.getColumn("apertura"), "Apertura", true,
					120);
			UtilUI.confColumn(itemsGRD.getColumn("cierre"), "Cierre", true, 120);

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
			itemsGRD.getColumn("apertura").setRenderer(
					new DateRenderer(
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			itemsGRD.getColumn("cierre").setRenderer(
					new DateRenderer(
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> order = new ArrayList<SortOrder>();

			order.add(new SortOrder("numeroCaja", SortDirection.ASCENDING));
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
			
			Button cierreBTN = UtilUI.buildButton("Cierre", "Cierre");
			cierreBTN.setIcon(FontAwesome.CLOSE);
			cierreBTN.addClickListener(e -> {
//				modificarBTNClick();
			});

			filaBotoneraHL.addComponents(agregarBTN, modificarBTN, cierreBTN);

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

		filterBI = new BeanItem<AperturasCierresCajasFiltro>(
				new AperturasCierresCajasFiltro());
		itemsBIC = new BeanItemContainer<AperturasCierresCajas>(
				AperturasCierresCajas.class,
				new ArrayList<AperturasCierresCajas>());
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

													AperturasCierresCajas item = (AperturasCierresCajas) itemsGRD
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

				AperturasCierresCajas item = (AperturasCierresCajas) itemsGRD
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

	@SuppressWarnings("unchecked")
	protected void selectCajaTXTShortcutEnter() {
		try {

			if (this.filterBI.getBean().getNumeroCaja() != null) {

				WCajas window = new WCajas();

//				WCajas window = new WCajas(this.filterBI.getBean()
//						.getNumeroCaja());
				window.setModal(true);
				window.center();

				window.addCloseListener(new CloseListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent event) {
						setNumeroCajaOnFilter(window);
					}
				});

				// -------------------------------------------------------
				// BOTONERA SELECCION

				HorizontalLayout filaBotoneraHL = new HorizontalLayout();
				filaBotoneraHL.setSpacing(true);

				Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
				seleccionarBTN.addClickListener(e -> {
					setNumeroCajaOnFilter(window);
				});

				filaBotoneraHL.addComponents(seleccionarBTN);

				((VerticalLayout) window.getContent())
						.addComponent(filaBotoneraHL);

				((VerticalLayout) window.getContent()).setComponentAlignment(
						filaBotoneraHL, Alignment.MIDDLE_CENTER);

				getUI().addWindow(window);

			} else {
				this.filterBI.getItemProperty("nombreCaja").setValue(null);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroCajaOnFilter(WCajas window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				Caja item = (Caja) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroCaja").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreCaja").setValue(
						item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroCaja").setValue(null);
				this.filterBI.getItemProperty("nombreCaja").setValue(null);
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

			((Validatable) usuarioCBXHL.getComponent(0)).validate();
			((Validatable) numeroCajaCBXHL.getComponent(0)).validate();

			List<AperturasCierresCajas> items = queryData();

			itemsBIC.removeAllItems();

			for (AperturasCierresCajas item : items) {
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

	private List<Usuarios> queryDataUsuarios() {
		try {

			return mockDataUsuarios();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<Usuarios>();
	}

	// metodo que realiza la consulta a la base de datos
	private List<AperturasCierresCajas> queryData() {
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

			List<AperturasCierresCajas> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<AperturasCierresCajas>();
	}

	// metodo que realiza el delete en la base de datos
	private void deleteItem(AperturasCierresCajas item) {
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

	List<AperturasCierresCajas> itemsMock = new ArrayList<AperturasCierresCajas>();

	private List<AperturasCierresCajas> mockData(int limit, int offset,
			AperturasCierresCajasFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				AperturasCierresCajas item = new AperturasCierresCajas();

				item.setNumero(i);
				item.setNumeroCaja(i);
				item.setNombreCaja("Caja " + i);
				item.setNumeroUsuario(i);
				item.setNombreUsuario("Usuario " + 1);
				item.setApertura(new Timestamp(System.currentTimeMillis()));
				item.setCierre(new Timestamp(System.currentTimeMillis()));

				itemsMock.add(item);
			}
		}

		ArrayList<AperturasCierresCajas> arrayList = new ArrayList<AperturasCierresCajas>();

		for (AperturasCierresCajas item : itemsMock) {

			boolean passesFilterNumeroUsuario = (filtro.getUsuario() == null || item
					.getNumeroUsuario().equals(filtro.getUsuario().getNumero()));

			boolean passesFilterNumeroCaja = (filtro.getNumeroCaja() == null || item
					.getNumeroCaja().equals(filtro.getNumeroCaja()));

			if (passesFilterNumeroCaja && passesFilterNumeroUsuario) {
				arrayList.add(item);
			}
		}

		int end = offset + limit;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offset, end);
	}

	private List<Usuarios> mockDataUsuarios() {

		List<Usuarios> itemsMock = new ArrayList<Usuarios>();

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				Usuarios item = new Usuarios();

				item.setNumero(i);
				item.setNombre("Nombre " + i);

				itemsMock.add(item);
			}
		}

		return itemsMock;
	}

	// =================================================================================

} // END CLASS
