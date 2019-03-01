package com.massoftware.windows.tipos_Documento_AFIP;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.patrik.FastNavigation;

import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.TiposDocumentoAFIPFiltro;
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
public class WTiposDocumentoAFIP extends WindowListado {

	// -------------------------------------------------------------

	BeanItem<TiposDocumentoAFIPFiltro> filterBI;
	protected BeanItemContainer<TipoDocumentoAFIP> itemsBIC;

	// -------------------------------------------------------------

	private TextFieldBox numeroIB;
	private TextFieldBox nombreTB;

	// -------------------------------------------------------------

	public WTiposDocumentoAFIP() {
		super();
		filterBI = new BeanItem<TiposDocumentoAFIPFiltro>(new TiposDocumentoAFIPFiltro());
		init(false);
	}

	public WTiposDocumentoAFIP(TiposDocumentoAFIPFiltro filtro) {
		super();
		filterBI = new BeanItem<TiposDocumentoAFIPFiltro>(filtro);
		init(true);
	}

	protected void buildContent() throws Exception {

		confWinList(this, new TipoDocumentoAFIP().labelPlural());

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

	private HorizontalLayout buildFiltros() throws Exception {

		numeroIB = new TextFieldBox(this, filterBI, "tipo");

		// --------------------------------------------------------

		nombreTB = new TextFieldBox(this, filterBI, "descripcion");

		// --------------------------------------------------------

		Button buscarBTN = buildButtonBuscar();

		HorizontalLayout filaFiltroHL = new HorizontalLayout();
		filaFiltroHL.setSpacing(true);

		filaFiltroHL.addComponents(numeroIB, nombreTB, buscarBTN);
		filaFiltroHL.setComponentAlignment(buscarBTN, Alignment.MIDDLE_RIGHT);

		return filaFiltroHL;
	}

	private Grid buildItemsGRD() throws Exception {

		itemsGRD = UtilUI.buildGrid();
		FastNavigation nav = UtilUI.initNavigation(itemsGRD);

		// ------------------------------------------------------------------

		// itemsGRD.setWidth("100%");
		itemsGRD.setWidth(25f, Unit.EM);
		itemsGRD.setHeight(20.5f, Unit.EM);

		itemsGRD.setColumns(new Object[] { "tipo", "descripcion"});

		UtilUI.confColumn(itemsGRD.getColumn("tipo"), true, 70);
		UtilUI.confColumn(itemsGRD.getColumn("descripcion"), true, -1);
		
		TipoDocumentoAFIP dto = new TipoDocumentoAFIP();
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

		order.add(new SortOrder("tipo", SortDirection.ASCENDING));

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

	protected BeanItemContainer<TipoDocumentoAFIP> getItemsBIC() {

		// -----------------------------------------------------------------
		// Crea el Container de la grilla, en base a al bean que queremos usar, y ademas
		// carga la grilla con una lista vacia

		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<TipoDocumentoAFIP>(TipoDocumentoAFIP.class, new ArrayList<TipoDocumentoAFIP>());
			
			
			
			List<TipoDocumentoAFIP> items = mockData(limit, offset,this.filterBI.getBean());
			
			itemsBIC = new BeanItemContainer<TipoDocumentoAFIP>(TipoDocumentoAFIP.class, items);
			
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
			List<TipoDocumentoAFIP> items = new ArrayList<TipoDocumentoAFIP>();

			// -----------------------------------------------------------------
			// Agrega los resultados a la grilla
			for (TipoDocumentoAFIP item : items) {
				getItemsBIC().addBean(item);
			}

			// -----------------------------------------------------------------

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected WindowForm buildWinddowForm(String mode, String id) {
		return new WTipoDocumentoAFIP(mode, id);
	}

	
	
	
	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<TipoDocumentoAFIP> itemsMock = new ArrayList<TipoDocumentoAFIP>();

	private List<TipoDocumentoAFIP> mockData(int limit, int offset, TiposDocumentoAFIPFiltro filtro) {

		if (itemsMock.size() == 0) {
			
			for (int i = 1; i < 500; i++) {

				TipoDocumentoAFIP item = new TipoDocumentoAFIP();

				
				item.setTipo(i);
				item.setDescripcion("Nombre " + i);
				
				itemsMock.add(item);
				
				
			}
			
			
			
		}

		ArrayList<TipoDocumentoAFIP> arrayList = new ArrayList<TipoDocumentoAFIP>();

		for (TipoDocumentoAFIP item : itemsMock) {


//			boolean passesFilterNumero = (filtro.getNumero() == null || item.getNumero().equals(filtro.getNumero()));
//
//			boolean passesFilterNombre = (filtro.getNombre() == null || item.getNombre().toLowerCase()
//					.contains(filtro.getNombre().toLowerCase()));
//
//			if (passesFilterNumero && passesFilterNombre) {
				arrayList.add(item);
//			}
		}

//		int end = offset + limit;
//		if (end > arrayList.size()) {
//			return arrayList.subList(0, arrayList.size());
//		}

		return arrayList;//.subList(offset, end);
	}


	
	
	// =================================================================================

} // END CLASS
