package com.massoftware.windows.cuentas_fondo;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.patrik.FastNavigation;

import com.massoftware.model.CuentaFondo;
import com.massoftware.model.CuentaFondoGrupo;
import com.massoftware.model.CuentaFondoRubro;
import com.massoftware.model.CuentasFondoFiltro;
import com.massoftware.model.EntityId;
import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.OptionGroupEntityBoolean;
import com.massoftware.windows.SelectorBox;
import com.massoftware.windows.TextFieldBox;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.massoftware.windows.WindowListado;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

public class WCuentasFondo extends WindowListado {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	BeanItem<CuentasFondoFiltro> filterBI;
	protected BeanItemContainer<CuentaFondo> itemsBIC;

	// -------------------------------------------------------------

	protected Button agregarTreeBTN;
	protected Button modificarTreeBTN;
	// protected Button copiarTreeBTN;
	protected Button eliminarTreeBTN;

	private OptionGroupEntityBoolean activoOG;
	private TextFieldBox numeroIB;
	private TextFieldBox nombreTB;
	private SelectorBox bancoSB;
	private Tree tree;

	private String itemTodas = "Todas las cuentas";

	// -------------------------------------------------------------

	public WCuentasFondo() {
		super();
		filterBI = new BeanItem<CuentasFondoFiltro>(new CuentasFondoFiltro());
		init(false);
	}

	public WCuentasFondo(CuentasFondoFiltro filtro) {
		super();
		filterBI = new BeanItem<CuentasFondoFiltro>(filtro);
		init(true);
	}

	protected void buildContent() throws Exception {

		confWinList(this, new CuentaFondo().labelPlural());

		// =======================================================
		// FILTROS

		HorizontalLayout filtrosLayout = buildFiltros();

		// =======================================================
		// CUERPO

		VerticalLayout izquierda = UtilUI.buildVL();
		izquierda.addComponent(buildPanelTree());
		HorizontalLayout filaBotoneraTreeHL = buildBotonera1Tree();
		HorizontalLayout filaBotoneraTree2HL = buildBotonera2Tree();
		izquierda.addComponents(filaBotoneraTreeHL, filaBotoneraTree2HL);
		izquierda.setComponentAlignment(filaBotoneraTreeHL, Alignment.MIDDLE_LEFT);
		izquierda.setComponentAlignment(filaBotoneraTree2HL, Alignment.MIDDLE_RIGHT);

		// ------------------------------------------------------

		VerticalLayout derecha = UtilUI.buildVL();
		derecha.setWidth("100%");
		derecha.addComponent(buildItemsGRD());
		HorizontalLayout filaBotoneraHL = buildBotonera1();
		HorizontalLayout filaBotonera2HL = buildBotonera2();
		derecha.addComponents(filaBotoneraHL, filaBotonera2HL);
		derecha.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);
		derecha.setComponentAlignment(filaBotonera2HL, Alignment.MIDDLE_RIGHT);

		// ------------------------------------------------------

		HorizontalLayout cuerpo = new HorizontalLayout();
		cuerpo.setSpacing(false);
		cuerpo.setMargin(false);

		cuerpo.addComponents(izquierda, derecha);

		// =======================================================
		// CONTENT

		VerticalLayout content = UtilUI.buildWinContentVertical();

		content.addComponents(filtrosLayout, cuerpo);

		content.setComponentAlignment(filtrosLayout, Alignment.MIDDLE_CENTER);

		this.setContent(content);
	}

	private HorizontalLayout buildFiltros() throws Exception {

		bancoSB = new WCBancoSB(this);

		numeroIB = new TextFieldBox(this, filterBI, "numero");

		nombreTB = new TextFieldBox(this, filterBI, "nombre");

		activoOG = new OptionGroupEntityBoolean(this, filterBI, "bloqueado", "Todas", "Obsoletas", "Activas", true, 2);

		Button buscarBTN = buildButtonBuscar();

		HorizontalLayout filaFiltroHL = new HorizontalLayout();
		filaFiltroHL.setSpacing(true);

		filaFiltroHL.addComponents(bancoSB, numeroIB, nombreTB, activoOG, buscarBTN);

		filaFiltroHL.setComponentAlignment(buscarBTN, Alignment.MIDDLE_RIGHT);

		return filaFiltroHL;
	}

	private Grid buildItemsGRD() throws Exception {

		itemsGRD = UtilUI.buildGrid();
		FastNavigation nav = UtilUI.initNavigation(itemsGRD);

		// ------------------------------------------------------------------

		// itemsGRD.setWidth(22f, Unit.EM);
		itemsGRD.setWidth("100%");
		itemsGRD.setHeight(20.5f, Unit.EM);

		itemsGRD.setColumns(
				new Object[] { "id", "cuentaFondoGrupo", "banco", "numero", "nombre", "cuentaFondoTipo", "bloqueado" });

		UtilUI.confColumn(itemsGRD.getColumn("id"), true, true, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("cuentaFondoGrupo"), true, true, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("banco"), true, true, false, true, 100);
		UtilUI.confColumn(itemsGRD.getColumn("numero"), true, false, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("nombre"), true, false, false, true, 200);
		UtilUI.confColumn(itemsGRD.getColumn("cuentaFondoTipo"), true, false, false, true, -1);
		UtilUI.confColumn(itemsGRD.getColumn("bloqueado"), true, true, false, true, 30);

		CuentaFondo dto = new CuentaFondo();
		for (Column column : itemsGRD.getColumns()) {
			column.setHeaderCaption(dto.label(column.getPropertyId().toString()));
		}

		itemsGRD.setContainerDataSource(getItemsBIC());

		// .......

		// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
		itemsGRD.getColumn("bloqueado").setRenderer(new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
		// itemsGRD.getColumn("attName").setRenderer(
		// new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

		// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
		// itemsGRD.getColumn("attName").setRenderer(
		// new DateRenderer(
		// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

		// .......

		List<SortOrder> order = new ArrayList<SortOrder>();

		// order.add(new SortOrder("numeroRubro", SortDirection.ASCENDING));
		order.add(new SortOrder("cuentaFondoGrupo", SortDirection.ASCENDING));
		order.add(new SortOrder("numero", SortDirection.ASCENDING));

		itemsGRD.setSortOrder(order);

		// ------------------------------------------------------------------

		nav.addRowFocusListener(e -> {
			try {
				int row = e.getRow();

				if (row == offset + limit - 1) {
					nextPageBTNClick();
				}
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		// ------------------------------------------------------------------

		return itemsGRD;
	}

	// =================================================================================

	protected HorizontalLayout buildBotonera1Tree() {

		HorizontalLayout filaBotoneraHL = new HorizontalLayout();
		filaBotoneraHL.setSpacing(true);

		agregarTreeBTN = UtilUI.buildButtonAgregar();
		agregarTreeBTN.addClickListener(e -> {
			agregarTreeBTNClick();
		});
		modificarTreeBTN = UtilUI.buildButtonModificar();
		modificarTreeBTN.addClickListener(e -> {
			modificarTreeBTNClick();
		});
		// copiarTreeBTN = UtilUI.buildButtonCopiar();
		// copiarTreeBTN.addClickListener(e -> {
		// // copiarBTNClick();
		// });

		filaBotoneraHL.addComponents(agregarTreeBTN, modificarTreeBTN/* , copiarTreeBTN */);

		return filaBotoneraHL;
	}

	protected HorizontalLayout buildBotonera2Tree() {

		HorizontalLayout filaBotonera2HL = new HorizontalLayout();
		filaBotonera2HL.setSpacing(true);

		eliminarTreeBTN = UtilUI.buildButtonEliminar();
		eliminarTreeBTN.addClickListener(e -> {
			eliminarTreeBTNClick();
		});

		filaBotonera2HL.addComponents(eliminarTreeBTN);

		return filaBotonera2HL;
	}

	private Panel buildPanelTree() {

		Panel seccionIzquierda = new Panel("Estructura");
		seccionIzquierda.setWidth(20f, Unit.EM);
		seccionIzquierda.setHeight(20.5f, Unit.EM);
		seccionIzquierda.setContent(buildTree());

		return seccionIzquierda;
	}

	@SuppressWarnings("serial")
	private Tree buildTree() {
		try {

			Handler actionHandler = new Handler() {

				private final Action ACTION_ONE = new Action("Agregar");
				private final Action ACTION_TWO = new Action("Modificar");
				private final Action ACTION_THREE = new Action("Eliminar");
				private final Action[] ACTIONS = new Action[] { ACTION_ONE, ACTION_TWO, ACTION_THREE };

				@Override
				public void handleAction(Action action, Object sender, Object target) {

					if (action.getCaption().equals("Agregar")) {
						agregarTreeBTNClick();
					} else if (action.getCaption().equals("Modificar")) {
						modificarTreeBTNClick();
					} else if (action.getCaption().equals("Eliminar")) {
						eliminarTreeBTNClick();
					}

				}

				@Override
				public Action[] getActions(Object target, Object sender) {
					return ACTIONS;
				}
			};

			tree = new Tree("Estructura");

			loadDataResetPagedTree();

			tree.addValueChangeListener(event -> {
				if (event.getProperty() != null && event.getProperty().getValue() != null) {

					treeValueChangeListener(event.getProperty().getValue());

				}
			});

			tree.addActionHandler(actionHandler);

			return tree;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

	private void treeValueChangeListener(Object item) {
		try {
			if (item instanceof CuentaFondoRubro) {
				filterBI.getBean().getCuentaFondoRubro().setId(((CuentaFondoRubro) item).getId());
				filterBI.getBean().getCuentaFondoGrupo().setId(null);
				this.loadDataResetPaged();
			} else if (item instanceof CuentaFondoGrupo) {
				filterBI.getBean().getCuentaFondoRubro().setId(null);
				filterBI.getBean().getCuentaFondoGrupo().setId(((CuentaFondoGrupo) item).getId());
				this.loadDataResetPaged();
			} else {
				filterBI.getBean().getCuentaFondoRubro().setId(null);
				filterBI.getBean().getCuentaFondoGrupo().setId(null);
				this.loadDataResetPaged();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void loadDataResetPagedTree() throws Exception {
		loadDataResetPagedTree(null, null);
	}

	public void loadDataResetPagedTree(CuentaFondoRubro rubroSelected, CuentaFondoGrupo grupoSelected)
			throws Exception {

		tree.removeAllItems();
		tree.addItem(itemTodas);
		tree.select(itemTodas);
		addCuentasContablesTree(rubroSelected, grupoSelected);
		tree.expandItem(itemTodas);
	}

	// private void addCuentasContablesTree(Integer numeroRubro, Integer
	// numeroGrupo) throws Exception {
	//
	// List<CuentaFondoRubro> rubros = new CuentaFondoRubro().find();
	//
	// for (CuentaFondoRubro rubro : rubros) {
	//
	// tree.addItem(rubro);
	// tree.setParent(rubro, itemTodas);
	// // tree.setChildrenAllowed(cuentaContable, false);
	//
	// if (numeroRubro != null && rubro.getNumero() != null &&
	// rubro.getNumero().equals(numeroRubro)) {
	// tree.expandItem(rubro);
	// tree.select(rubro);
	// }
	//
	// List<CuentaFondoGrupo> grupos = new CuentaFondoGrupo().findByRubro(rubro);
	//
	// for (CuentaFondoGrupo grupo : grupos) {
	//
	// tree.addItem(grupo);
	// tree.setParent(grupo, rubro);
	// tree.setChildrenAllowed(grupo, false);
	// // tree.expandItem(grupo);
	//
	// if (numeroRubro != null && rubro.getNumero() != null &&
	// rubro.getNumero().equals(numeroRubro)
	// && numeroGrupo != null && grupo.getNumero() != null &&
	// grupo.getNumero().equals(numeroGrupo)) {
	// tree.expandItem(grupo);
	// tree.select(grupo);
	//
	// }
	//
	// }
	//
	// // tree.expandItem(rubro);
	//
	// }
	// }

	private void addCuentasContablesTree(CuentaFondoRubro rubroSelected, CuentaFondoGrupo grupoSelected)
			throws Exception {

		List<CuentaFondoRubro> rubros = new CuentaFondoRubro().find();

		for (CuentaFondoRubro rubro : rubros) {

			tree.addItem(rubro);
			tree.setParent(rubro, itemTodas);
			// tree.setChildrenAllowed(cuentaContable, false);

			if (rubroSelected != null && rubro.equals(rubroSelected)) {
				tree.expandItem(rubro);
				tree.select(rubro);
			}

			List<CuentaFondoGrupo> grupos = new CuentaFondoGrupo().findByRubro(rubro);

			for (CuentaFondoGrupo grupo : grupos) {

				tree.addItem(grupo);
				tree.setParent(grupo, rubro);
				tree.setChildrenAllowed(grupo, false);
				// tree.expandItem(grupo);

				if (grupoSelected != null && grupo.equals(grupoSelected)) {
					tree.expandItem(grupo);
					tree.select(grupo);
				}

			}

			// tree.expandItem(rubro);

		}
	}

	protected void eliminarTreeBTNClick() {
		try {

			if (tree.getValue() != null && tree.getValue() instanceof CuentaFondoRubro
					|| tree.getValue() instanceof CuentaFondoGrupo) {

				getUI().addWindow(new EliminarDialog(tree.getValue().toString(), new EliminarDialog.Callback() {
					public void onDialogResult(boolean yes) {

						try {
							if (yes) {
								// if (tree.getValue() != null) {

								Object item = tree.getValue();

								deleteItemTree(item);

								LogAndNotification.printSuccessOk("Se eliminó con éxito el ítem " + item);

								if (item instanceof CuentaFondoGrupo) {
									loadDataResetPagedTree(((CuentaFondoGrupo) item).getCuentaFondoRubro(), null);
								} else {
									loadDataResetPagedTree();
								}

								loadData();

								// }
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

	protected void agregarTreeBTNClick() {
		try {

			if (tree.getValue() != null && tree.getValue() instanceof String || tree.getValue().equals(itemTodas)) {

				WindowForm window = new WCuentaFondoRubro(WindowForm.INSERT_MODE, null);
				window.setModal(true);
				window.center();
				window.setWindowListado(this);
				getUI().addWindow(window);

			} else if (tree.getValue() != null && tree.getValue() instanceof CuentaFondoRubro) {

				WindowForm window = new WCuentaFondoGrupo(WindowForm.INSERT_MODE, null,
						((CuentaFondoRubro) tree.getValue()).getId());
				window.setModal(true);
				window.center();
				window.setWindowListado(this);
				getUI().addWindow(window);

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void modificarTreeBTNClick() {
		try {

			if (tree.getValue() != null && tree.getValue() instanceof CuentaFondoRubro) {

				CuentaFondoRubro item = (CuentaFondoRubro) tree.getValue();

				WindowForm window = new WCuentaFondoRubro(WindowForm.UPDATE_MODE, item.getId());
				window.setModal(true);
				window.center();
				window.setWindowListado(this);
				getUI().addWindow(window);

			} else if (tree.getValue() != null && tree.getValue() instanceof CuentaFondoGrupo) {

				CuentaFondoGrupo item = (CuentaFondoGrupo) tree.getValue();

				WindowForm window = new WCuentaFondoGrupo(WCuentaFondoGrupo.UPDATE_MODE, item.getId(), null);
				window.setModal(true);
				window.center();
				window.setWindowListado(this);
				getUI().addWindow(window);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================

	protected BeanItemContainer<CuentaFondo> getItemsBIC() {
		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<CuentaFondo>(CuentaFondo.class, new ArrayList<CuentaFondo>());
		}
		return itemsBIC;
	}

	protected void addBeansToItemsBIC() {

		List<CuentaFondo> items = queryData();

		for (CuentaFondo item : items) {
			getItemsBIC().addBean(item);
		}
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	// metodo que realiza la consulta a la base de datos
	private List<CuentaFondo> queryData() {

		try {

			return new CuentaFondo().find(limit, offset, buildOrderBy(), filterBI.getBean());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<CuentaFondo>();
	}

	// metodo que realiza el delete en la base de datos
	protected void deleteItem(Object item) throws Exception {
		((EntityId) item).delete();
	}

	// ------------------------------------------------------------------------------

	// metodo que realiza el delete en la base de datos
	private void deleteItemTree(Object item) throws Exception {
		if (item instanceof CuentaFondoRubro) {
			((CuentaFondoRubro) item).delete();
		} else if (item instanceof CuentaFondoGrupo) {
			((CuentaFondoGrupo) item).delete();
		}

	}

	@Override
	protected WindowForm buildWinddowForm(String mode, String id) {

		return null;
	}

	// =================================================================================

} // END CLASS