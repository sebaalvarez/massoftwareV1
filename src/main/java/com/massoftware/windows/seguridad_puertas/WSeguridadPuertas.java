package com.massoftware.windows.seguridad_puertas;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.patrik.FastNavigation;

import com.massoftware.model.EntityId;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.SeguridadPuertasFiltro;
import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.massoftware.windows.WindowListado;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class WSeguridadPuertas extends WindowListado {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	BeanItem<SeguridadPuertasFiltro> filterBI;
	protected BeanItemContainer<SeguridadPuerta> itemsBIC;

	// -------------------------------------------------------------

	protected Button agregarTreeBTN;
	protected Button modificarTreeBTN;
	// protected Button copiarTreeBTN;
	protected Button eliminarTreeBTN;

	private Tree tree;

	private String itemTodas = "Todas las puertas";

	// -------------------------------------------------------------

	public WSeguridadPuertas() {
		super();
		filterBI = new BeanItem<SeguridadPuertasFiltro>(new SeguridadPuertasFiltro());
		init(false);
	}

	public WSeguridadPuertas(SeguridadPuertasFiltro filtro) {
		super();
		filterBI = new BeanItem<SeguridadPuertasFiltro>(filtro);
		init(true);
	}

	protected void buildContent() throws Exception {

		confWinList(this, "Módulos y puertas");

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

		Button buscarBTN = buildButtonBuscar();

		HorizontalLayout filaFiltroHL = new HorizontalLayout();
		filaFiltroHL.setWidth("100%");
		filaFiltroHL.setSpacing(true);

		filaFiltroHL.addComponents(buscarBTN);

		filaFiltroHL.setComponentAlignment(buscarBTN, Alignment.MIDDLE_RIGHT);

		return filaFiltroHL;
	}

	private Grid buildItemsGRD() throws Exception {

		itemsGRD = UtilUI.buildGrid();
		FastNavigation nav = UtilUI.initNavigation(itemsGRD);

		// ------------------------------------------------------------------

		// itemsGRD.setWidth(22f, Unit.EM);
		// itemsGRD.setWidth("100%");
		itemsGRD.setWidth(20.5f, Unit.EM);
		itemsGRD.setHeight(20.5f, Unit.EM);

		itemsGRD.setColumns(new Object[] { "id", "seguridadModulo", "numero", "nombre" });

		UtilUI.confColumn(itemsGRD.getColumn("id"), true, true, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("seguridadModulo"), true, true, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("numero"), true, false, false, true, 50);
		UtilUI.confColumn(itemsGRD.getColumn("nombre"), true, false, false, true, -1);

		SeguridadPuerta dto = new SeguridadPuerta();
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

		// order.add(new SortOrder("numeroRubro", SortDirection.ASCENDING));
		order.add(new SortOrder("seguridadModulo", SortDirection.ASCENDING));
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

		Panel seccionIzquierda = new Panel("Módulos");
		seccionIzquierda.setWidth(15f, Unit.EM);
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

			tree = new Tree("Módulos");

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
			if (item instanceof SeguridadModulo) {
				filterBI.getBean().setIdModulo(((SeguridadModulo) item).getId());
				// filterBI.getBean().setIdGrupo(null);
				this.loadDataResetPaged();
			} else {
				filterBI.getBean().setIdModulo(null);
				this.loadDataResetPaged();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void loadDataResetPagedTree() throws Exception {
		loadDataResetPagedTree(null);
	}

	public void loadDataResetPagedTree(SeguridadModulo selected) throws Exception {

		tree.removeAllItems();
		tree.addItem(itemTodas);
		tree.select(itemTodas);
		addCuentasContablesTree(selected);
		tree.expandItem(itemTodas);
	}

	private void addCuentasContablesTree(SeguridadModulo selected) throws Exception {

		List<SeguridadModulo> modulos = new SeguridadModulo().find();

		for (SeguridadModulo modulo : modulos) {

			tree.addItem(modulo);
			tree.setParent(modulo, itemTodas);

			if (selected != null && modulo.equals(selected)) {
				tree.expandItem(modulo);
				tree.select(modulo);
			}

		}
	}

	protected void eliminarTreeBTNClick() {
		try {

			if (tree.getValue() != null && tree.getValue() instanceof SeguridadModulo) {

				getUI().addWindow(new EliminarDialog(tree.getValue().toString(), new EliminarDialog.Callback() {
					public void onDialogResult(boolean yes) {

						try {
							if (yes) {

								Object item = tree.getValue();

								deleteItemTree(item);

								LogAndNotification.printSuccessOk("Se eliminó con éxito el ítem " + item);

								loadDataResetPagedTree();

								loadData();

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

				WindowForm window = new WSeguridadModulo(WindowForm.INSERT_MODE, null);
				window.setModal(true);
				window.center();
				window.setWindowListado(this);
				getUI().addWindow(window);

			} else if (tree.getValue() != null && tree.getValue() instanceof SeguridadModulo) {

				WindowForm window = new WSeguridadModulo(WindowForm.INSERT_MODE, null);
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

			if (tree.getValue() != null && tree.getValue() instanceof SeguridadModulo) {

				SeguridadModulo item = (SeguridadModulo) tree.getValue();

				WindowForm window = new WSeguridadModulo(WindowForm.UPDATE_MODE, item.getId());
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

	protected BeanItemContainer<SeguridadPuerta> getItemsBIC() {
		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<SeguridadPuerta>(SeguridadPuerta.class, new ArrayList<SeguridadPuerta>());
		}
		return itemsBIC;
	}

	protected void addBeansToItemsBIC() {

		List<SeguridadPuerta> items = queryData();

		for (SeguridadPuerta item : items) {
			getItemsBIC().addBean(item);
		}
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	// metodo que realiza la consulta a la base de datos
	private List<SeguridadPuerta> queryData() {

		try {

			return new SeguridadPuerta().find(limit, offset, buildOrderBy(), filterBI.getBean());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<SeguridadPuerta>();
	}

	// metodo que realiza el delete en la base de datos
	protected void deleteItem(Object item) throws Exception {
		((EntityId) item).delete();
	}

	// ------------------------------------------------------------------------------

	// metodo que realiza el delete en la base de datos
	private void deleteItemTree(Object item) throws Exception {

		if (item instanceof SeguridadModulo) {
			((SeguridadModulo) item).delete();
		}
	}

	@Override
	protected WindowForm buildWinddowForm(String mode, String id) throws Exception {
		
		if (tree.getValue() != null && tree.getValue() instanceof SeguridadModulo) {

			SeguridadModulo item = (SeguridadModulo) tree.getValue();
			
			return new WSeguridadPuerta(mode, id, item.getId());

		}

		return new WSeguridadPuerta(mode, id, null);
	}

	// =================================================================================

} // END CLASS