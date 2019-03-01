package com.massoftware.windows.clasificaciones_clientes;


import java.util.ArrayList;
import java.util.List;

import org.vaadin.patrik.FastNavigation;

import com.massoftware.model.ClasificacionCliente;
import com.massoftware.model.ClasificacionesClientesFiltro;
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
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WClasificacionesClientes extends WindowListado {

	// -------------------------------------------------------------

	BeanItem<ClasificacionesClientesFiltro> filterBI;
	protected BeanItemContainer<ClasificacionCliente> itemsBIC;

	// -------------------------------------------------------------
	@SuppressWarnings("unused")
	private TextFieldBox numeroIB;
	@SuppressWarnings("unused")
	private TextFieldBox nombreTB;

	// -------------------------------------------------------------

	public WClasificacionesClientes() {
		super();
		filterBI = new BeanItem<ClasificacionesClientesFiltro>(new ClasificacionesClientesFiltro());
		init(false);
	}

	public WClasificacionesClientes(ClasificacionesClientesFiltro filtro) {
		super();
		filterBI = new BeanItem<ClasificacionesClientesFiltro>(filtro);
		init(true);
	}

	protected void buildContent() throws Exception {

		confWinList(this, new ClasificacionCliente().labelPlural());

		// =======================================================
		// FILTROS

		HorizontalLayout filtrosLayout = buildFiltros();

		// =======================================================
		// CUERPO
		// solo cuando por ejemplo tenemos un arbol y una grilla, o cosas asi mas
		// complejas q solo la grilla

		// =======================================================
		// BOTONERAS

		HorizontalLayout filaBotoneraHL = buildBotonera1();
		HorizontalLayout filaBotonera2HL = buildBotonera2();

		// =======================================================
		// CONTENT

		VerticalLayout content = UtilUI.buildWinContentVertical();

		content.addComponents(filtrosLayout, buildItemsGRD(), filaBotoneraHL, filaBotonera2HL);

		content.setComponentAlignment(filtrosLayout, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);
		content.setComponentAlignment(filaBotonera2HL, Alignment.MIDDLE_RIGHT);

		this.setContent(content);
	}

	@SuppressWarnings("unused")
	private HorizontalLayout buildFiltros() throws Exception {

		numeroIB = new TextFieldBox(this, filterBI, "numero");

		// --------------------------------------------------------

		nombreTB = new TextFieldBox(this, filterBI, "nombre");

		// --------------------------------------------------------

		Button buscarBTN = buildButtonBuscar();

		HorizontalLayout filaFiltroHL = new HorizontalLayout();
		filaFiltroHL.setSpacing(true);

//		filaFiltroHL.addComponents(numeroIB, nombreTB, buscarBTN);
//		filaFiltroHL.setComponentAlignment(buscarBTN, Alignment.MIDDLE_RIGHT);

		return filaFiltroHL;
	}

	private Grid buildItemsGRD() throws Exception {

		itemsGRD = UtilUI.buildGrid();
		FastNavigation nav = UtilUI.initNavigation(itemsGRD);

		// ------------------------------------------------------------------

		// itemsGRD.setWidth("100%");
		itemsGRD.setWidth(25f, Unit.EM);
		itemsGRD.setHeight(20.5f, Unit.EM);

		itemsGRD.setColumns(new Object[] { "numero", "nombre", "color"});

		UtilUI.confColumn(itemsGRD.getColumn("numero"), true, 70);
		UtilUI.confColumn(itemsGRD.getColumn("nombre"), true, -1);
		UtilUI.confColumn(itemsGRD.getColumn("color"), true, 70);
		
		ClasificacionCliente dto = new ClasificacionCliente();
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

	protected BeanItemContainer<ClasificacionCliente> getItemsBIC() {

		// -----------------------------------------------------------------
		// Crea el Container de la grilla, en base a al bean que queremos usar, y ademas
		// carga la grilla con una lista vacia

		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<ClasificacionCliente>(ClasificacionCliente.class, new ArrayList<ClasificacionCliente>());
			
			
			
			List<ClasificacionCliente> items = mockData(limit, offset,this.filterBI.getBean());
			
			itemsBIC = new BeanItemContainer<ClasificacionCliente>(ClasificacionCliente.class, items);
			
		}
	
		
		return itemsBIC;
	}

	protected void addBeansToItemsBIC() {

		// -----------------------------------------------------------------
		// Consulta a la base de datos y agrega los beans conseguidos al contenedor de
		// la grilla

		try {

			// -----------------------------------------------------------------
			// realiza la consulta a la base de datos
			List<ClasificacionCliente> items = new ArrayList<ClasificacionCliente>();

			// -----------------------------------------------------------------
			// Agrega los resultados a la grilla
			for (ClasificacionCliente item : items) {
				getItemsBIC().addBean(item);
			}

			// -----------------------------------------------------------------

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected WindowForm buildWinddowForm(String mode, String id) {
		return new WClasificacionCliente(mode, id);
	}

	
	
	
	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<ClasificacionCliente> itemsMock = new ArrayList<ClasificacionCliente>();

	private List<ClasificacionCliente> mockData(int limit, int offset, ClasificacionesClientesFiltro filtro) {

		if (itemsMock.size() == 0) {
			

			for (int i = 1; i < 500; i++) {

				ClasificacionCliente item = new ClasificacionCliente();
				
				item.setNumero(i);
				item.setNombre("Nombre " + i);
				item.setColor(""+i);

				
				itemsMock.add(item);
				
				
			}
			
			
			
		}

		ArrayList<ClasificacionCliente> arrayList = new ArrayList<ClasificacionCliente>();

		for (ClasificacionCliente item : itemsMock) {


			boolean passesFilterNumero = (filtro.getNumero() == null || item.getNumero().equals(filtro.getNumero()));

			boolean passesFilterNombre = (filtro.getNombre() == null || item.getNombre().toLowerCase()
					.contains(filtro.getNombre().toLowerCase()));

			if (passesFilterNumero && passesFilterNombre) {
				arrayList.add(item);
			}
		}

//		int end = offset + limit;
//		if (end > arrayList.size()) {
//			return arrayList.subList(0, arrayList.size());
//		}

		return arrayList;//.subList(offset, end);
	}


	
	
	// =================================================================================

} // END CLASS
