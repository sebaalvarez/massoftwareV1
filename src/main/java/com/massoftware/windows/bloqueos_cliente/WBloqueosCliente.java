package com.massoftware.windows.bloqueos_cliente;


import java.util.ArrayList;
import java.util.List;

import org.vaadin.patrik.FastNavigation;

import com.massoftware.model.BloqueoCliente;
import com.massoftware.model.BloqueosClienteFiltro;
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
public class WBloqueosCliente extends WindowListado {

	// -------------------------------------------------------------

	BeanItem<BloqueosClienteFiltro> filterBI;
	protected BeanItemContainer<BloqueoCliente> itemsBIC;

	// -------------------------------------------------------------
	@SuppressWarnings("unused")
	private TextFieldBox numeroIB;
	@SuppressWarnings("unused")
	private TextFieldBox nombreTB;

	// -------------------------------------------------------------

	public WBloqueosCliente() {
		super();
		filterBI = new BeanItem<BloqueosClienteFiltro>(new BloqueosClienteFiltro());
		init(false);
	}

	public WBloqueosCliente(BloqueosClienteFiltro filtro) {
		super();
		filterBI = new BeanItem<BloqueosClienteFiltro>(filtro);
		init(true);
	}

	protected void buildContent() throws Exception {

		confWinList(this, new BloqueoCliente().labelPlural());

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

		numeroIB = new TextFieldBox(this, filterBI, "motivoBloqueo");

		// --------------------------------------------------------

		nombreTB = new TextFieldBox(this, filterBI, "detalle");

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

		itemsGRD.setColumns(new Object[] { "motivoBloqueo", "detalle", "clasifCliente"});

		UtilUI.confColumn(itemsGRD.getColumn("motivoBloqueo"), true, 70);
		UtilUI.confColumn(itemsGRD.getColumn("detalle"), true, -1);
		UtilUI.confColumn(itemsGRD.getColumn("clasifCliente"), true, 70);
		
		BloqueoCliente dto = new BloqueoCliente();
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

		order.add(new SortOrder("motivoBloqueo", SortDirection.ASCENDING));

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

	protected BeanItemContainer<BloqueoCliente> getItemsBIC() {

		// -----------------------------------------------------------------
		// Crea el Container de la grilla, en base a al bean que queremos usar, y ademas
		// carga la grilla con una lista vacia

		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<BloqueoCliente>(BloqueoCliente.class, new ArrayList<BloqueoCliente>());
			
			
			
			List<BloqueoCliente> items = mockData(limit, offset,this.filterBI.getBean());
			
			itemsBIC = new BeanItemContainer<BloqueoCliente>(BloqueoCliente.class, items);
			
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
			List<BloqueoCliente> items = new ArrayList<BloqueoCliente>();

			// -----------------------------------------------------------------
			// Agrega los resultados a la grilla
			for (BloqueoCliente item : items) {
				getItemsBIC().addBean(item);
			}

			// -----------------------------------------------------------------

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected WindowForm buildWinddowForm(String mode, String id) {
		return new WBloqueoCliente(mode, id);
	}

	
	
	
	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<BloqueoCliente> itemsMock = new ArrayList<BloqueoCliente>();

	private List<BloqueoCliente> mockData(int limit, int offset, BloqueosClienteFiltro filtro) {

		if (itemsMock.size() == 0) {
			

			for (int i = 1; i < 500; i++) {

				BloqueoCliente item = new BloqueoCliente();
				
				item.setMotivoBloqueo(""+i);
				item.setDetalle("Nombre " + i);

				
				itemsMock.add(item);
				
				
			}
			
			
			
		}

		ArrayList<BloqueoCliente> arrayList = new ArrayList<BloqueoCliente>();

		for (BloqueoCliente item : itemsMock) {


			boolean passesFilterNumero = (filtro.getMotivoBloqueo() == null || item.getMotivoBloqueo().equals(filtro.getMotivoBloqueo()));

			boolean passesFilterNombre = (filtro.getDetalle() == null || item.getDetalle().toLowerCase()
					.contains(filtro.getDetalle().toLowerCase()));

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
