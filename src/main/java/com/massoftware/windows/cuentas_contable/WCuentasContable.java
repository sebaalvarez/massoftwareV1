package com.massoftware.windows.cuentas_contable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.patrik.FastNavigation;

import com.massoftware.model.CentroCostoContable;
import com.massoftware.model.CentrosCostoContableFiltro;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentasContableFiltro;
import com.massoftware.model.EntityId;
import com.massoftware.model.PuntoEquilibrio;
import com.massoftware.model.PuntosEquilibrioFiltro;
import com.massoftware.windows.ComboBoxBox;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.TextFieldBox;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.massoftware.windows.WindowListado;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class WCuentasContable extends WindowListado {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	BeanItem<CuentasContableFiltro> filterBI;
	protected BeanItemContainer<CuentaContable> itemsBIC;

	// -------------------------------------------------------------

	// protected Button agregarTreeBTN;
	// protected Button modificarTreeBTN;
	// protected Button copiarTreeBTN;
	// protected Button eliminarTreeBTN;

	private ComboBoxBox centroCostoContableCB;
	private ComboBoxBox puntoEquilibrioCB;
	private TextFieldBox codigoTB;
	private TextFieldBox nombreTB;
	protected Tree tree;

	private String itemTodas = "Todas las cuentas";

	// -------------------------------------------------------------

	public WCuentasContable() {
		super();
		filterBI = new BeanItem<CuentasContableFiltro>(new CuentasContableFiltro());
		init(false);
	}
	
	public WCuentasContable(CuentasContableFiltro filtro, boolean selectionMode) {
		super();
		filterBI = new BeanItem<CuentasContableFiltro>(filtro);
		init(selectionMode);
	}

	public WCuentasContable(CuentasContableFiltro filtro) {
		super();
		filterBI = new BeanItem<CuentasContableFiltro>(filtro);
		init(false);
	}

	protected void buildContent() throws Exception {

		confWinList(this, new CuentaContable().labelPlural());

		// =======================================================
		// FILTROS

		HorizontalLayout filtrosLayout = buildFiltros();

		// =======================================================
		// CUERPO

		VerticalLayout izquierda = UtilUI.buildVL();
		izquierda.addComponent(buildPanelTree());
		// HorizontalLayout filaBotoneraTreeHL = buildBotonera1Tree();
		// HorizontalLayout filaBotoneraTree2HL = buildBotonera2Tree();
		// izquierda.addComponents(filaBotoneraTreeHL, filaBotoneraTree2HL);
		// izquierda.setComponentAlignment(filaBotoneraTreeHL, Alignment.MIDDLE_LEFT);
		// izquierda.setComponentAlignment(filaBotoneraTree2HL, Alignment.MIDDLE_RIGHT);

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

		CentrosCostoContableFiltro centroCostoContableFiltro = new CentrosCostoContableFiltro();
		centroCostoContableFiltro.setEjercicioContable(filterBI.getBean().getEjercicioContable());

		centroCostoContableCB = new ComboBoxBox(this, filterBI, "centroCostoContable",
				new CentroCostoContable().find(centroCostoContableFiltro));

		PuntosEquilibrioFiltro puntosEquilibrioFiltro = new PuntosEquilibrioFiltro();
		puntosEquilibrioFiltro.setEjercicioContable(filterBI.getBean().getEjercicioContable());

		puntoEquilibrioCB = new ComboBoxBox(this, filterBI, "puntoEquilibrio",
				new PuntoEquilibrio().find(puntosEquilibrioFiltro));

		codigoTB = new TextFieldBox(this, filterBI, "codigo");

		nombreTB = new TextFieldBox(this, filterBI, "nombre");

		Button buscarBTN = buildButtonBuscar();

		HorizontalLayout filaFiltroHL = new HorizontalLayout();
		filaFiltroHL.setSpacing(true);

		filaFiltroHL.addComponents(codigoTB, nombreTB, centroCostoContableCB, puntoEquilibrioCB, buscarBTN);

		filaFiltroHL.setComponentAlignment(buscarBTN, Alignment.MIDDLE_RIGHT);

		return filaFiltroHL;
	}

	private Grid buildItemsGRD() throws Exception {

		itemsGRD = UtilUI.buildGrid();
		FastNavigation nav = UtilUI.initNavigation(itemsGRD);

		// ------------------------------------------------------------------

		itemsGRD.setWidth(43f, Unit.EM);
		// itemsGRD.setWidth("100%");
		itemsGRD.setHeight(20.5f, Unit.EM);

		itemsGRD.setColumns(new Object[] { "id", "ejercicioContable", "integra", "cuentaJerarquia", "codigo", "nombre",
				"centroCostoContable", "cuentaAgrupadora", "porcentaje" });

		UtilUI.confColumn(itemsGRD.getColumn("id"), true, true, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("ejercicioContable"), true, true, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("integra"), true, true, false, true, 90);
		UtilUI.confColumn(itemsGRD.getColumn("cuentaJerarquia"), true, false, false, true, 90);
		UtilUI.confColumn(itemsGRD.getColumn("codigo"), true, false, false, true, 90);
		UtilUI.confColumn(itemsGRD.getColumn("nombre"), true, false, false, true, -1);
		UtilUI.confColumn(itemsGRD.getColumn("centroCostoContable"), true, false, false, true, 150);
		UtilUI.confColumn(itemsGRD.getColumn("cuentaAgrupadora"), true, false, false, true, 100);
		UtilUI.confColumn(itemsGRD.getColumn("porcentaje"), true, false, false, true, 70);

		CuentaContable dto = new CuentaContable();
		for (Column column : itemsGRD.getColumns()) {
			column.setHeaderCaption(dto.label(column.getPropertyId().toString()));
		}

		itemsGRD.setContainerDataSource(getItemsBIC());

		// .......

		// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
		// itemsGRD.getColumn("bloqueado").setRenderer(new HtmlRenderer(),
		// new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O.getHtml(),
		// FontAwesome.SQUARE_O.getHtml()));

		// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
		// itemsGRD.getColumn("attName").setRenderer(
		// new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

		// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
		// itemsGRD.getColumn("attName").setRenderer(
		// new DateRenderer(
		// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

		// .......

		List<SortOrder> order = new ArrayList<SortOrder>();

		order.add(new SortOrder("cuentaJerarquia", SortDirection.ASCENDING));

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

	// protected HorizontalLayout buildBotonera1Tree() {
	//
	// HorizontalLayout filaBotoneraHL = new HorizontalLayout();
	// filaBotoneraHL.setSpacing(true);
	//
	// agregarTreeBTN = UtilUI.buildButtonAgregar();
	// agregarTreeBTN.addClickListener(e -> {
	// agregarTreeBTNClick();
	// });
	// modificarTreeBTN = UtilUI.buildButtonModificar();
	// modificarTreeBTN.addClickListener(e -> {
	// modificarTreeBTNClick();
	// });
	// copiarTreeBTN = UtilUI.buildButtonCopiar();
	// copiarTreeBTN.addClickListener(e -> {
	// copiarTreeBTNClick();
	// });
	//
	// filaBotoneraHL.addComponents(agregarTreeBTN, modificarTreeBTN,
	// copiarTreeBTN);
	//
	// return filaBotoneraHL;
	// }

	// protected HorizontalLayout buildBotonera2Tree() {
	//
	// HorizontalLayout filaBotonera2HL = new HorizontalLayout();
	// filaBotonera2HL.setSpacing(true);
	//
	// eliminarTreeBTN = UtilUI.buildButtonEliminar();
	// eliminarTreeBTN.addClickListener(e -> {
	// eliminarTreeBTNClick();
	// });
	//
	// filaBotonera2HL.addComponents(eliminarTreeBTN);
	//
	// return filaBotonera2HL;
	// }

	private Panel buildPanelTree() {

		Panel seccionIzquierda = new Panel("Estructura");
		seccionIzquierda.setWidth(25f, Unit.EM);
		seccionIzquierda.setHeight(20.5f, Unit.EM);
		seccionIzquierda.setContent(buildTree());

		return seccionIzquierda;
	}

	private Tree buildTree() {
		try {

			// Handler actionHandler = new Handler() {
			//
			// private final Action ACTION_ONE = new Action("Agregar");
			// private final Action ACTION_TWO = new Action("Modificar");
			// private final Action ACTION_FOR = new Action("Copiar");
			// private final Action ACTION_THREE = new Action("Eliminar");
			// private final Action[] ACTIONS = new Action[] { ACTION_ONE, ACTION_TWO,
			// ACTION_FOR, ACTION_THREE };
			//
			// @Override
			// public void handleAction(Action action, Object sender, Object target) {
			//
			// if (action.getCaption().equals("Agregar")) {
			// agregarTreeBTNClick();
			// } else if (action.getCaption().equals("Modificar")) {
			// modificarTreeBTNClick();
			// } else if (action.getCaption().equals("Copiar")) {
			// modificarTreeBTNClick();
			// } else if (action.getCaption().equals("Eliminar")) {
			// eliminarTreeBTNClick();
			// }
			//
			// }
			//
			// @Override
			// public Action[] getActions(Object target, Object sender) {
			// return ACTIONS;
			// }
			// };

			tree = new Tree("Estructura");

			loadDataResetPagedTree();

			tree.addValueChangeListener(event -> {
				if (event.getProperty() != null && event.getProperty().getValue() != null) {

					treeValueChangeListener(event.getProperty().getValue());

				}
			});

			// tree.addActionHandler(actionHandler);

			return tree;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

	private void treeValueChangeListener(Object item) {
		try {
			if (item instanceof CuentaContable) {

				CuentaContable cuentaContable = ((CuentaContable) item);

				filterBI.getBean().setIntegra(cuentaContable.getCuentaJerarquia());

				CuentasContableFiltro filtro = new CuentasContableFiltro();
				filtro.setEjercicioContable(this.filterBI.getBean().getEjercicioContable());
				filtro.setIntegra(cuentaContable.getCuentaJerarquia());

				this.addCuentasContablesTree(filtro, cuentaContable);

				this.loadDataResetPaged();

			} else {
				filterBI.getBean().setIntegra(null);
				this.loadDataResetPaged();
			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void loadDataResetPagedTree() throws Exception {
		CuentasContableFiltro filtro = new CuentasContableFiltro();
		filtro.setEjercicioContable(this.filterBI.getBean().getEjercicioContable());
		filtro.setIntegra("00000000000");

		loadDataResetPagedTree(filtro, null);
	}

	public void loadDataResetPagedTree(CuentasContableFiltro filtro, CuentaContable cuentaContableSelected)
			throws Exception {

		tree.removeAllItems();
		tree.addItem(itemTodas);
		tree.select(itemTodas);
		addCuentasContablesTree(filtro, cuentaContableSelected);
		tree.expandItem(itemTodas);
	}

	protected void addCuentasContablesTree(CuentasContableFiltro filtro, CuentaContable cuentaContableSelected)
			throws Exception {

		Map<String, Boolean> orderBy = new HashMap<String, Boolean>();
		orderBy.put("ejercicioContable", true);
		orderBy.put("cuentaJerarquia", true);

		List<CuentaContable> cuentasContables = new CuentaContable().find(filtro, orderBy, 0);

		for (CuentaContable cuentaContable : cuentasContables) {

			filtro = new CuentasContableFiltro();
			filtro.setEjercicioContable(this.filterBI.getBean().getEjercicioContable());
			filtro.setIntegra(cuentaContable.getCuentaJerarquia());

			// List<CuentaContable> cuentasContablesHijas = new
			// CuentaContable().find(filtro, orderBy);

			// if (cuentasContablesHijas.size() > 0) {

			tree.addItem(cuentaContable);
			tree.setItemCaption(cuentaContable, format(cuentaContable.getCuentaJerarquia()) + " " + cuentaContable.getNombre());
			if (cuentaContableSelected != null) {
				tree.setParent(cuentaContable, cuentaContableSelected);
				tree.expandItem(cuentaContableSelected);
			} else {
				tree.setParent(cuentaContable, itemTodas);
			}

			if (cuentaContableSelected != null && cuentaContable.equals(cuentaContableSelected)) {
				tree.expandItem(cuentaContable);
				tree.select(cuentaContable);
			}

			// } else {
			// tree.setChildrenAllowed(cuentaContable, false);
			// }

		}
	}

	// protected void eliminarTreeBTNClick() {
	// try {
	//
	// if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {
	//
	// getUI().addWindow(new EliminarDialog(tree.getValue().toString(), new
	// EliminarDialog.Callback() {
	// public void onDialogResult(boolean yes) {
	//
	// try {
	// if (yes) {
	//
	// Object item = tree.getValue();
	//
	// deleteItemTree(item);
	//
	// LogAndNotification.printSuccessOk("Se eliminó con éxito el ítem " + item);
	//
	// if (item instanceof CuentaContable) {
	//
	// CuentasContableFiltro filtro = new CuentasContableFiltro();
	// filtro.setIntegra(((CuentaContable) item).getIntegra());
	//
	//// loadDataResetPagedTree(null, null);
	// } else {
	// loadDataResetPagedTree();
	// }
	//
	// loadData();
	//
	// }
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	//
	// }
	// }));
	// }
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }

	// protected void agregarTreeBTNClick() {
	// try {
	//
	// if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {
	//
	// CuentaContable item = (CuentaContable) tree.getValue();
	// WindowForm window = buildWinddowForm(WindowForm.INSERT_MODE, item.getId());
	// window.setWindowListado(this);
	// getUI().addWindow(window);
	//
	// } else {
	//
	// WindowForm window = buildWinddowForm(WindowForm.INSERT_MODE, null);
	// window.setWindowListado(this);
	// getUI().addWindow(window);
	// }
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }
	//
	// protected void modificarTreeBTNClick() {
	// try {
	//
	// if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {
	//
	// CuentaContable item = (CuentaContable) tree.getValue();
	// WindowForm window = buildWinddowForm(WindowForm.UPDATE_MODE, item.getId());
	// window.setWindowListado(this);
	// getUI().addWindow(window);
	//
	// }
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }
	//
	// protected void copiarTreeBTNClick() {
	// try {
	//
	// if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {
	// CuentaContable item = (CuentaContable) tree.getValue();
	// WindowForm window = buildWinddowForm(WindowForm.COPY_MODE, item.getId());
	// window.setWindowListado(this);
	// getUI().addWindow(window);
	// }
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }

	// =================================================================================

	protected BeanItemContainer<CuentaContable> getItemsBIC() {
		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<CuentaContable>(CuentaContable.class, new ArrayList<CuentaContable>());
		}
		return itemsBIC;
	}

	protected void addBeansToItemsBIC() {

		List<CuentaContable> items = queryData();

		for (CuentaContable item : items) {
			getItemsBIC().addBean(item);
		}
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	// metodo que realiza la consulta a la base de datos
	private List<CuentaContable> queryData() {

		try {

			return new CuentaContable().find(limit, offset, buildOrderBy(), filterBI.getBean(), 1);
			// return new CuentaContable().find(-1, -1, buildOrderBy(), filterBI.getBean());
			// return new ArrayList<CuentaContable>();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<CuentaContable>();
	}

	// metodo que realiza el delete en la base de datos
	protected void deleteItem(Object item) throws Exception {
		((EntityId) item).delete();

		if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {

			CuentaContable itemRama = (CuentaContable) tree.getValue();

			CuentasContableFiltro filtro = new CuentasContableFiltro();
			filtro.setEjercicioContable(itemRama.getEjercicioContable());
			filtro.setIntegra(itemRama.getCuentaJerarquia());

			tree.removeItem(itemsGRD.getSelectedRow());

		}
	}

	// ------------------------------------------------------------------------------

	// metodo que realiza el delete en la base de datos
	// private void deleteItemTree(Object item) throws Exception {
	//
	// if (item instanceof CuentaContable) {
	// ((CuentaContable) item).delete();
	// }
	//
	// }

	@Override
	protected WindowForm buildWinddowForm(String mode, String id) {

		if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {
			return new WCuentaContable(mode, id, filterBI.getBean().getEjercicioContable(),
					((CuentaContable) tree.getValue()).getCuentaJerarquia());
		} else {
			return new WCuentaContable(mode, id, filterBI.getBean().getEjercicioContable(), "00000000000");
		}

	}
	
	String format(String codigoCuenta) {

		if (codigoCuenta == null || codigoCuenta.trim().length() != 11) {
			return codigoCuenta;
		}

		String codigoCuenta2 = codigoCuenta.trim();
		char[] chars = codigoCuenta2.toCharArray();

		codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c.%c%c.%c%c", chars[0],
				chars[1], chars[2], chars[3], chars[4], chars[5], chars[6],
				chars[7], chars[8], chars[9], chars[10]);

		return codigoCuenta2;

	}


	// =================================================================================

} // END CLASS