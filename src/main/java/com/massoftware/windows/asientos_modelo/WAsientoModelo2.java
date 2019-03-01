package com.massoftware.windows.asientos_modelo;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.patrik.FastNavigation;
import org.vaadin.patrik.FastNavigation.EditorCloseListener;
import org.vaadin.patrik.events.EditorCloseEvent;

import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.AsientoModeloItemFiltro;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentasContableFiltro;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.EntityId;
import com.massoftware.windows.ComboBoxEntity;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.massoftware.windows.cuentas_contable.WCuentasContable;
import com.massoftware.windows.seguridad_puertas.WSeguridadPuertas;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WAsientoModelo2 extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<AsientoModelo> itemBI;
	protected BeanItemContainer<AsientoModeloItemGrid> itemsBIC;

	// -------------------------------------------------------------

	private ComboBoxEntity ejercicioContableCBX;
	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private Grid itemsGRD;

	// -------------------------------------------------------------

	// public WPuntoEquilibrio(String mode, String id) {
	// super(mode, id);
	// }

	public WAsientoModelo2(String mode, String id, EjercicioContable ejercicioContable) {

		getItemBIC().getBean().setEjercicioContable(ejercicioContable);

		init(mode, id);
	}

	protected void buildContent() throws Exception {

		confWinForm(this.itemBI.getBean().labelSingular());
		// this.setWidth(28f, Unit.EM);

		// =======================================================
		// CUERPO

		VerticalLayout cuerpo = buildCuerpo();

		// =======================================================
		// BOTONERAS

		HorizontalLayout filaBotoneraHL = buildBotonera1();

		// =======================================================
		// CONTENT

		VerticalLayout content = UtilUI.buildWinContentVertical();

		content.addComponents(cuerpo, filaBotoneraHL);

		content.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);

		this.setContent(content);
	}

	private VerticalLayout buildCuerpo() throws Exception {

		// ---------------------------------------------------------------------------------------------------------
		List<EjercicioContable> ejrcicios = new EjercicioContable().find();
		ejercicioContableCBX = new ComboBoxEntity(this.itemBI, "ejercicioContable", this.mode, ejrcicios);
		ejercicioContableCBX.addValueChangeListener(e -> {
			try {
				validateEjercicioContableAndNumero();
				validateEjercicioContableAndNombre();
			} catch (Exception e1) {
				LogAndNotification.print(e1);
			}
		});
		// ---------------------------------------------------------------------------------------------------------
		numeroTXT = new TextFieldEntity(this.itemBI, "numero", this.mode);
		numeroTXT.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				// String value = (String) event.getProperty().getValue();
				try {
					validateEjercicioContableAndNumero();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		// ---------------------------------------------------------------------------------------------------------
		nombreTXT = new TextFieldEntity(this.itemBI, "nombre", this.mode);
		nombreTXT.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				try {
					validateEjercicioContableAndNombre();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		// ---------------------------------------------------------------------------------------------------------

		HorizontalLayout cabeceraVL = UtilUI.buildHL();
		cabeceraVL.setMargin(false);
		cabeceraVL.addComponents(ejercicioContableCBX, numeroTXT, nombreTXT);

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(cabeceraVL, buildItemsGRD());

		// ---------------------------------------------------------------------------------------------------------

		return generalVL;

		// ---------------------------------------------------------------------------------------------------------
	}

	private Grid buildItemsGRD() throws Exception {

		itemsGRD = UtilUI.buildGrid();
		FastNavigation nav = UtilUI.initNavigation(itemsGRD);

		// ------------------------------------------------------------------

		itemsGRD.setWidth("100%");
		// itemsGRD.setWidth(20f, Unit.EM);
		itemsGRD.setHeight(20.5f, Unit.EM);

		itemsGRD.setColumns(new Object[] { "numero", "codigoCuentaContable", "nombreCuentaContable",
				"ejercicioContableCuentaContable" });

		// UtilUI.confColumn(itemsGRD.getColumn("asientoModelo"), true, 70);
		UtilUI.confColumn(itemsGRD.getColumn("numero"), true, 70);
		UtilUI.confColumn(itemsGRD.getColumn("codigoCuentaContable"), true, 70);
		UtilUI.confColumn(itemsGRD.getColumn("nombreCuentaContable"), true, -1);
		UtilUI.confColumn(itemsGRD.getColumn("ejercicioContableCuentaContable"), true, 50);

		AsientoModeloItemGrid dto = new AsientoModeloItemGrid();
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

		// order.add(new SortOrder("asientoModelo", SortDirection.DESCENDING));
		order.add(new SortOrder("numero", SortDirection.ASCENDING));

		itemsGRD.setSortOrder(order);

		// ------------------------------------------------------------------

		itemsGRD.setEditorBuffered(false);
		itemsGRD.setEditorEnabled(true);
		itemsGRD.getColumn("codigoCuentaContable").setEditable(true);
		itemsGRD.getColumn("nombreCuentaContable").setEditable(true);

		// nav.addEditorCloseShortcut(KeyCode.ENTER);

		// nav.addRowEditListener(new RowEditListener() {
		// @Override
		// public void onEvent(RowEditEvent event) {
		// int rowIndex = event.getRowIndex();
		// if (rowIndex >= 0) {
		// Indexed ds = itemsGRD.getContainerDataSource();
		// Object itemId = event.getItemId();
		// printChangedRow(rowIndex, ds, itemId);
		//
		// }
		// }
		// });

		// nav.addEditorOpenListener(new EditorOpenListener() {
		// @Override
		// public void onEvent(EditorOpenEvent event) {
		// int row = event.getRow();
		//
		// @SuppressWarnings("unchecked")
		// Collection<Object> prodIds = (Collection<Object>) ds.getItem(itemId)
		// .getItemPropertyIds();
		//
		// StringBuffer sb = new StringBuffer();
		// for (Object o : prodIds) {
		// sb.append(ds.getItem(itemId).getItemProperty(o).getValue() + "");
		// }
		//
		// System.out.println("Editor opened on row " + row + " at column " +
		// event.getColumn() + " , Value = " +
		// ((AsientoModeloItemGrid)itemsGRD.getSelectedRow()).getCodigoCuentaContable());
		//
		// }
		// });

		nav.addEditorCloseListener(new EditorCloseListener() {
			@Override
			public void onEvent(EditorCloseEvent event) {
				System.out.println("Editor closed on row " + event.getRow() + ", column " + event.getColumn() + ", "
						+ (event.wasCancelled() ? "user cancelled change" : "user saved change") + " , Value = "
						+ ((AsientoModeloItemGrid) itemsGRD.getSelectedRow()).getCodigoCuentaContable());

				blur();

			}
		});

		// nav.addRowFocusListener(e -> {
		// try {
		// int row = e.getRow();
		//
		// if (row == offset + limit - 1) {
		// nextPageBTNClick();
		// }
		// } catch (Exception ex) {
		// LogAndNotification.print(ex);
		// }
		// });

		// ------------------------------------------------------------------

		return itemsGRD;
	}

	// =================================================================================

	// private void printChangedRow(int rowIndex, Indexed ds, Object itemId) {
	// @SuppressWarnings("unchecked")
	// Collection<Object> prodIds = (Collection<Object>) ds.getItem(itemId)
	// .getItemPropertyIds();
	//
	// StringBuffer sb = new StringBuffer();
	// for (Object o : prodIds) {
	// sb.append(ds.getItem(itemId).getItemProperty(o).getValue() + " ");
	// }
	//
	// System.out.println("Row " + rowIndex + " changed to: " + sb.toString());
	// }

	protected String getValue() {

		String value = ((AsientoModeloItemGrid) itemsGRD.getSelectedRow()).getCodigoCuentaContable();

		if (value != null) {
			value = value.trim();
			if (value.length() == 0) {
				value = null;
			}
		}

		return value;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private void blur() {
		try {

			String value = getValue();

			if (value != null) {

				CuentasContableFiltro filtro = new CuentasContableFiltro();
				filtro.setEjercicioContable(this.itemBI.getBean().getEjercicioContable());
				filtro.setCodigo(value);

				List items = new CuentaContable().find(filtro);

				if (items.size() == 1) {

					setSelectedItem(items.get(0));

				} else {

					open();

				}
			} else {

				setSelectedItem(null);

			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void open() {
		try {

			CuentasContableFiltro filtro = new CuentasContableFiltro();
			filtro.setEjercicioContable(this.itemBI.getBean().getEjercicioContable());
			filtro.setCodigo(this.getValue());

			WCuentasContable windowPopup = new WCuentasContable(filtro, true);

			windowPopup.addCloseListener(e -> {
				try {

					setSelectedItem(windowPopup.itemsGRD.getSelectedRow());

				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			windowPopup.seleccionarBTN.addClickListener(e -> {
				try {

					if (windowPopup.itemsGRD.getSelectedRow() != null) {

						setSelectedItem(windowPopup.itemsGRD.getSelectedRow());

						windowPopup.close();

					}

				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			getUI().addWindow(windowPopup);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void setSelectedItem(Object item) {

		if (item == null) {
			item = new CuentaContable();
		}

		
		System.out.println("((CuentaContable) item).getNombre() " + ((CuentaContable) item).getNombre());
		
		
		
		
		AsientoModeloItemGrid asientoModeloItemGrid = getItemsBIC().getItem(((AsientoModeloItemGrid) itemsGRD.getSelectedRow())).getBean();
		
		asientoModeloItemGrid.setNombreCuentaContable(((CuentaContable) item).getNombre());
		
		System.out.println("asientoModeloItemGrid.getNombreCuentaContable() " + asientoModeloItemGrid.getNombreCuentaContable());
		
//		((AsientoModeloItemGrid) itemsGRD.getSelectedRow())
//				.setNombreCuentaContable(((CuentaContable) item).getNombre());
		
		itemsGRD.refreshAllRows();
		

	}

	// =================================================================================

	protected BeanItemContainer<AsientoModeloItemGrid> getItemsBIC() {

		// -----------------------------------------------------------------
		// Crea el Container de la grilla, en base a al bean que queremos usar, y ademas
		// carga la grilla con una lista vacia

		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<AsientoModeloItemGrid>(AsientoModeloItemGrid.class,
					new ArrayList<AsientoModeloItemGrid>());
		}
		return itemsBIC;
	}

	// =================================================================================

	private void validateEjercicioContableAndNumero() throws Exception {
		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueEjercicioContableAndNumero();
	}

	private void validateEjercicioContableAndNombre() throws Exception {
		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueEjercicioContableAndNombre();
	}

	protected void setMaxValues(EntityId item) throws Exception {
		// Al momento de insertar o copiar a veces se necesita el maximo valor de ese
		// atributo, + 1, esto es asi para hacer una especie de numero incremental de
		// ese atributo
		// Este metodo se ejecuta despues de consultar a la base de datos el bean en
		// base a su id

		AsientoModelo centroCostoContable = (AsientoModelo) item;

		Integer maxValue = (Integer) centroCostoContable.maxValue(new String[] { "ejercicioContable" }, "numero");

		centroCostoContable.setNumero(maxValue);
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((AsientoModelo) obj);
	}

	protected BeanItem<AsientoModelo> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<AsientoModelo>(new AsientoModelo());
		}
		return itemBI;
	}

	protected void validateForm() throws Exception {
		validateEjercicioContableAndNumero();
		validateEjercicioContableAndNombre();
		super.validateForm();
	}

	// metodo que realiza la consulta a la base de datos
	protected EntityId queryData() throws Exception {
		try {

			AsientoModelo item = (AsientoModelo) getItemBIC().getBean();
			item.loadById(id); // consulta a DB
			addBeansToItemsBIC(item);

			return item;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return (EntityId) getItemBIC().getBean();
	}

	protected void addBeansToItemsBIC(AsientoModelo asientoModelo) {

		// -----------------------------------------------------------------
		// Consulta a la base de datos y agrega los beans conseguidos al contenedor de
		// la grilla

		try {

			// -----------------------------------------------------------------

			AsientoModeloItemFiltro filtro = new AsientoModeloItemFiltro();
			filtro.setAsientoModelo(asientoModelo);

			// realiza la consulta a la base de datos
			List<AsientoModeloItem> items = new AsientoModeloItem().find(filtro);

			// -----------------------------------------------------------------
			// Agrega los resultados a la grilla
			for (AsientoModeloItem item : items) {
				AsientoModeloItemGrid itemGrid = new AsientoModeloItemGrid();
				itemGrid.setId(item.getId());
				itemGrid.setAsientoModelo(item.getAsientoModelo());
				itemGrid.setNumero(item.getNumero());
				itemGrid.setIdCuentaContable(item.getCuentaContable().getId());
				itemGrid.setEjercicioContableCuentaContable(item.getCuentaContable().getEjercicioContable());
				itemGrid.setCodigoCuentaContable(item.getCuentaContable().getCodigo());
				itemGrid.setNombreCuentaContable(item.getCuentaContable().getNombre());
				getItemsBIC().addBean(itemGrid);

			}

			// -----------------------------------------------------------------

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	// =================================================================================

}
