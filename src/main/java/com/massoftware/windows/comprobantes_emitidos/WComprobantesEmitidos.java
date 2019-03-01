package com.massoftware.windows.comprobantes_emitidos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilModel;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.aperturas_cierres_cajas.AperturasCierresCajas;
import com.massoftware.windows.aperturas_cierres_cajas.WAperturasCierresCajas;
import com.massoftware.windows.talonarios.TalonariosOld;
import com.massoftware.windows.talonarios.WTalonariosOld;
import com.massoftware.windows.tipos_comprobantes.TiposComprobantes;
import com.massoftware.windows.tipos_comprobantes.WTiposComprobantes;
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
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class WComprobantesEmitidos extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<ComprobantesEmitidosFiltro> filterBI;
	private BeanItemContainer<ComprobantesEmitidos> itemsBIC;

	// -------------------------------------------------------------

	protected int limit = 15;
	protected int offset = 0;

	// -------------------------------------------------------------

	private Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;
	private Button consultarBTN;
	private Button modificarBTN;

	// -------------------------------------------------------------

	private HorizontalLayout nroIdTXTHL;
	private HorizontalLayout detalleTXTHL;
	private HorizontalLayout fechaDedeDFHL;
	private HorizontalLayout fechaHastaDFHL;
	private HorizontalLayout comprobanteTXTHL;
	private HorizontalLayout numeroTipoComprobanteCBXHL;
	private HorizontalLayout numeroTalonarioCBXHL;
	private HorizontalLayout numeroCajaCBXHL;

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WComprobantesEmitidos() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Comprobantes emitidos");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			HorizontalLayout filaFiltro2HL = new HorizontalLayout();
			filaFiltro2HL.setSpacing(true);

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

			comprobanteTXTHL = UtilUI.buildTXTHL(filterBI, "comprobante",
					"Comprobante", false, 10, -1, 25, false, false, null,
					false, UtilUI.ENDS_WITCH);

			TextField comprobanteTXT = (TextField) comprobanteTXTHL
					.getComponent(0);

			comprobanteTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						comprobanteTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button comprobanteBTN = (Button) comprobanteTXTHL.getComponent(1);

			comprobanteBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			nroIdTXTHL = UtilUI.buildTXTHLInteger(filterBI, "nroId", "Nro. Id",
					false, 5, -1, 3, false, false, null, false, UtilUI.EQUALS,
					0, 255);

			TextField nroIdTXT = (TextField) nroIdTXTHL.getComponent(0);

			nroIdTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						nroIdTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button nroIdBTN = (Button) nroIdTXTHL.getComponent(1);

			nroIdBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			detalleTXTHL = UtilUI.buildTXTHL(filterBI, "detalle", "Detalle",
					false, 15, -1, 25, false, false, null, false,
					UtilUI.CONTAINS_WORDS_AND);

			TextField detalleTXT = (TextField) detalleTXTHL.getComponent(0);

			detalleTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						detalleTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button detalleBTN = (Button) detalleTXTHL.getComponent(1);

			detalleBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroTipoComprobanteCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroTipoComprobante", "nombreTipoComprobante", "Tipo",
					"numero", false, "Tipo comprobante", false);

			TextField numeroTipoComprobanteTXT = (TextField) numeroTipoComprobanteCBXHL
					.getComponent(0);

			numeroTipoComprobanteTXT
					.addTextChangeListener(new TextChangeListener() {
						public void textChange(TextChangeEvent event) {
							try {
								numeroTipoComprobanteTXT.setValue(event
										.getText());
								selectTipoComprobanteTXTShortcutEnter();
							} catch (Exception e) {
								LogAndNotification.print(e);
							}
						}

					});

			Button numeroTipoComprobanteBTN = (Button) numeroTipoComprobanteCBXHL
					.getComponent(2);

			numeroTipoComprobanteBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroTalonarioCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroTalonario", "nombreTalonario", "Talonario",
					"numero", false);

			TextField numeroTalonarioTXT = (TextField) numeroTalonarioCBXHL
					.getComponent(0);

			numeroTalonarioTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroTalonarioTXT.setValue(event.getText());
						selectTalonarioTXTShortcutEnter();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroTalonarioBTN = (Button) numeroTalonarioCBXHL
					.getComponent(2);

			numeroTalonarioBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroCajaCBXHL = UtilUI.buildSearchBox(filterBI, "numeroCaja",
					"nombreCaja", "Caja", "numero", false,
					"Apertura cierre caja", true);

			Button numeroCajaBTNOpen = (Button) numeroCajaCBXHL.getComponent(0);

			numeroCajaBTNOpen.addClickListener(e -> {
				try {
					selectAperturasCierresCajasTXTShortcutEnter();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			Button numeroCajaBTN = (Button) numeroCajaCBXHL.getComponent(3);

			numeroCajaBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(fechaDedeDFHL, fechaHastaDFHL,
					comprobanteTXTHL, nroIdTXTHL, detalleTXTHL, buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			filaFiltro2HL.addComponents(numeroTipoComprobanteCBXHL,
					numeroTalonarioCBXHL, numeroCajaCBXHL);

			// =======================================================
			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			itemsGRD.setWidth("100%");

			itemsGRD.setColumns(new Object[] { "cbte", "comprobante", "fecha",
					"detalle", "numeroTipoComprobante", "nroId", "numeroCaja",
					"importe" });

			UtilUI.confColumn(itemsGRD.getColumn("cbte"), "Cbte", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("comprobante"), "Comprobante",
					true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("fecha"), "Fecha", true, 80);
			UtilUI.confColumn(itemsGRD.getColumn("detalle"), "Detalle", true,
					-1);
			UtilUI.confColumn(itemsGRD.getColumn("numeroTipoComprobante"),
					"Tipo", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nroId"), "Nro. Id", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroCaja"), "Nro. caja",
					true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("importe"), "Importe", true,
					90);

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

			consultarBTN = UtilUI.buildButton("Consultar", "Consultar");
			consultarBTN.setIcon(FontAwesome.BOOK);
			consultarBTN.addClickListener(e -> {
				consultarBTNClick();
			});
			modificarBTN = UtilUI.buildButtonModificar();
			modificarBTN.addClickListener(e -> {
				modificarBTNClick();
			});

			filaBotoneraHL.addComponents(consultarBTN, modificarBTN);

			// -------------------------------------------------------
			// BOTONERA 2

			HorizontalLayout filaBotonera2HL = new HorizontalLayout();
			filaBotonera2HL.setSpacing(true);

			Label lbl = new Label(
					"<b>Cierre (Contable - Centralizado): </b><b>Sucursal: 00	Lote: 00</b>",
					ContentMode.HTML);

			filaBotonera2HL.addComponents(lbl);

			// -------------------------------------------------------

			content.addComponents(filaFiltroHL, filaFiltro2HL, itemsGRD,
					filaBotoneraPagedHL, filaBotonera2HL, filaBotoneraHL);

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
						consultarBTNClick();
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

		filterBI = new BeanItem<ComprobantesEmitidosFiltro>(
				new ComprobantesEmitidosFiltro());
		itemsBIC = new BeanItemContainer<ComprobantesEmitidos>(
				ComprobantesEmitidos.class,
				new ArrayList<ComprobantesEmitidos>());
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

	protected void consultarBTNClick() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				ComprobantesEmitidos item = (ComprobantesEmitidos) itemsGRD
						.getSelectedRow();

				Window window = new Window("Consultar ítem " + item);
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

	private void modificarBTNClick() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				ComprobantesEmitidos item = (ComprobantesEmitidos) itemsGRD
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
	protected void selectTipoComprobanteTXTShortcutEnter() {
		try {

			if (this.filterBI.getBean().getNumeroTipoComprobante() != null) {

				WTiposComprobantes window = new WTiposComprobantes(
						this.filterBI.getBean().getNumeroTipoComprobante());
				window.setModal(true);
				window.center();

				window.addCloseListener(new CloseListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent event) {
						setNumeroTipoComprobanteOnFilter(window);
					}
				});

				// -------------------------------------------------------
				// BOTONERA SELECCION

				HorizontalLayout filaBotoneraHL = new HorizontalLayout();
				filaBotoneraHL.setSpacing(true);

				Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
				seleccionarBTN.addClickListener(e -> {
					setNumeroTipoComprobanteOnFilter(window);
				});

				filaBotoneraHL.addComponents(seleccionarBTN);

				((VerticalLayout) window.getContent())
						.addComponent(filaBotoneraHL);

				((VerticalLayout) window.getContent()).setComponentAlignment(
						filaBotoneraHL, Alignment.MIDDLE_CENTER);

				getUI().addWindow(window);

			} else {
				this.filterBI.getItemProperty("nombreTipoComprobante")
						.setValue(null);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroTipoComprobanteOnFilter(WTiposComprobantes window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				TiposComprobantes item = (TiposComprobantes) window.itemsGRD
						.getSelectedRow();

				this.filterBI.getItemProperty("numeroTipoComprobante")
						.setValue(item.getNumero());
				this.filterBI.getItemProperty("nombreTipoComprobante")
						.setValue(item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroTipoComprobante")
						.setValue(null);
				this.filterBI.getItemProperty("nombreTipoComprobante")
						.setValue(null);
			}
		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	protected void selectAperturasCierresCajasTXTShortcutEnter() {
		try {

			// if (this.filterBI.getBean().getNumeroCaja() != null) {

			WAperturasCierresCajas window = new WAperturasCierresCajas();
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

			((VerticalLayout) window.getContent()).addComponent(filaBotoneraHL);

			((VerticalLayout) window.getContent()).setComponentAlignment(
					filaBotoneraHL, Alignment.MIDDLE_CENTER);

			getUI().addWindow(window);

			// } else {
			// this.filterBI.getItemProperty("nombreCaja").setValue(null);
			// }

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroCajaOnFilter(WAperturasCierresCajas window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				AperturasCierresCajas item = (AperturasCierresCajas) window.itemsGRD
						.getSelectedRow();

				this.filterBI.getItemProperty("numeroCaja").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreCaja").setValue(
						item.toString());

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

	@SuppressWarnings("unchecked")
	protected void selectTalonarioTXTShortcutEnter() {
		try {

			if (this.filterBI.getBean().getNumeroTalonario() != null) {

				WTalonariosOld window = new WTalonariosOld(this.filterBI.getBean()
						.getNumeroTalonario());
				window.setModal(true);
				window.center();

				window.addCloseListener(new CloseListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent event) {
						setNumeroTalonarioOnFilter(window);
					}
				});

				// -------------------------------------------------------
				// BOTONERA SELECCION

				HorizontalLayout filaBotoneraHL = new HorizontalLayout();
				filaBotoneraHL.setSpacing(true);

				Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
				seleccionarBTN.addClickListener(e -> {
					setNumeroTalonarioOnFilter(window);
				});

				filaBotoneraHL.addComponents(seleccionarBTN);

				((VerticalLayout) window.getContent())
						.addComponent(filaBotoneraHL);

				((VerticalLayout) window.getContent()).setComponentAlignment(
						filaBotoneraHL, Alignment.MIDDLE_CENTER);

				getUI().addWindow(window);

			} else {
				this.filterBI.getItemProperty("nombreTalonario").setValue(null);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroTalonarioOnFilter(WTalonariosOld window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				TalonariosOld item = (TalonariosOld) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroTalonario").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreTalonario").setValue(
						item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroTalonario").setValue(null);
				this.filterBI.getItemProperty("nombreTalonario").setValue(null);
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

			((Validatable) nroIdTXTHL.getComponent(0)).validate();
			((Validatable) detalleTXTHL.getComponent(0)).validate();
			((Validatable) fechaDedeDFHL.getComponent(0)).validate();
			((Validatable) fechaHastaDFHL.getComponent(0)).validate();
			((Validatable) comprobanteTXTHL.getComponent(0)).validate();
			((Validatable) numeroTipoComprobanteCBXHL.getComponent(0))
					.validate();
			((Validatable) numeroTalonarioCBXHL.getComponent(0)).validate();
			((Validatable) numeroCajaCBXHL.getComponent(1)).validate();

			List<ComprobantesEmitidos> items = queryData();

			itemsBIC.removeAllItems();

			for (ComprobantesEmitidos item : items) {
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
	private List<ComprobantesEmitidos> queryData() {
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

			List<ComprobantesEmitidos> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<ComprobantesEmitidos>();
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<ComprobantesEmitidos> itemsMock = new ArrayList<ComprobantesEmitidos>();

	private List<ComprobantesEmitidos> mockData(int limit, int offset,
			ComprobantesEmitidosFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				ComprobantesEmitidos item = new ComprobantesEmitidos();

				item.setCbte("CBTE " + i);
				item.setComprobante("X00000000000" + i);
				item.setFecha(new Date(System.currentTimeMillis()));
				item.setDetalle("Detalle " + i);
				item.setNumeroTipoComprobante(i);
				item.setNroId(i);
				item.setNumeroCaja(i);
				item.setImporte(new BigDecimal(i * 668 / 3.3));
				item.setNumeroTalonario(i);

				itemsMock.add(item);
			}
		}

		ArrayList<ComprobantesEmitidos> arrayList = new ArrayList<ComprobantesEmitidos>();

		for (ComprobantesEmitidos item : itemsMock) {

			boolean passesFilterFechaDesde = (filtro.getFechaDesde() == null || item
					.getFecha().after(filtro.getFechaDesde()));

			boolean passesFilterFechaHasta = (filtro.getFechaHasta() == null || item
					.getFecha()
					.before(UtilModel.sumarDiasAFecha(filtro.getFechaHasta(), 1)));

			boolean passesFilterComprobante = (filtro.getComprobante() == null || item
					.getComprobante().toLowerCase()
					.endsWith(filtro.getComprobante().toLowerCase()));

			boolean passesFilterNroId = (filtro.getNroId() == null || item
					.getNroId().equals(filtro.getNroId()));

			boolean passesFilterDetalle = (filtro.getDetalle() == null || item
					.getDetalle().toLowerCase()
					.contains(filtro.getDetalle().toLowerCase()));

			boolean passesFilterNumeroTipoComprobante = (filtro
					.getNumeroTipoComprobante() == null || item
					.getNumeroTipoComprobante().equals(
							filtro.getNumeroTipoComprobante()));

			boolean passesFilterNumeroTalonario = (filtro.getNumeroTalonario() == null || item
					.getNumeroTalonario().equals(filtro.getNumeroTalonario()));

			boolean passesFilterNumeroCaja = (filtro.getNumeroCaja() == null || item
					.getNumeroCaja().equals(filtro.getNumeroCaja()));

			if (passesFilterFechaDesde && passesFilterFechaHasta
					&& passesFilterComprobante && passesFilterNroId
					&& passesFilterDetalle && passesFilterNumeroTipoComprobante
					&& passesFilterNumeroTalonario && passesFilterNumeroCaja) {
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
