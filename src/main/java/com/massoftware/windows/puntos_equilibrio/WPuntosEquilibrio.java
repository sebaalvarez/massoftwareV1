package com.massoftware.windows.puntos_equilibrio;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.patrik.FastNavigation;

import com.massoftware.model.PuntoEquilibrio;
import com.massoftware.model.PuntosEquilibrioFiltro;
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
public class WPuntosEquilibrio extends WindowListado {

	// -------------------------------------------------------------

	BeanItem<PuntosEquilibrioFiltro> filterBI;
	protected BeanItemContainer<PuntoEquilibrio> itemsBIC;

	// -------------------------------------------------------------

	private TextFieldBox numeroIB;
	private TextFieldBox nombreTB;

	// -------------------------------------------------------------

//	public WPuntosEquilibrio() {
//		super();
//		filterBI = new BeanItem<PuntosEquilibrioFiltro>(new PuntosEquilibrioFiltro());
//		init(false);
//	}

	public WPuntosEquilibrio(PuntosEquilibrioFiltro filtro) {
		super();
		filterBI = new BeanItem<PuntosEquilibrioFiltro>(filtro);
		init(true);
	}

	protected void buildContent() throws Exception {

		confWinList(this, new PuntoEquilibrio().labelPlural());

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

		numeroIB = new TextFieldBox(this, filterBI, "numero");

		// --------------------------------------------------------

		nombreTB = new TextFieldBox(this, filterBI, "nombre");

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
		itemsGRD.setWidth(29f, Unit.EM);
		itemsGRD.setHeight(20.5f, Unit.EM);

		itemsGRD.setColumns(new Object[] { "ejercicioContable", "numero", "nombre", "puntoEquilibrioTipo"  });

		UtilUI.confColumn(itemsGRD.getColumn("ejercicioContable"), true, 70);		
		UtilUI.confColumn(itemsGRD.getColumn("numero"), true, 70);
		UtilUI.confColumn(itemsGRD.getColumn("nombre"), true, 150);
		UtilUI.confColumn(itemsGRD.getColumn("puntoEquilibrioTipo"), true, 150);

		PuntoEquilibrio dto = new PuntoEquilibrio();
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

		order.add(new SortOrder("ejercicioContable", SortDirection.DESCENDING));
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

	protected BeanItemContainer<PuntoEquilibrio> getItemsBIC() {

		// -----------------------------------------------------------------
		// Crea el Container de la grilla, en base a al bean que queremos usar, y ademas
		// carga la grilla con una lista vacia

		if (itemsBIC == null) {
			itemsBIC = new BeanItemContainer<PuntoEquilibrio>(PuntoEquilibrio.class, new ArrayList<PuntoEquilibrio>());
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
			List<PuntoEquilibrio> items = new PuntoEquilibrio().find(limit, offset, buildOrderBy(), filterBI.getBean());

			// -----------------------------------------------------------------
			// Agrega los resultados a la grilla
			for (PuntoEquilibrio item : items) {
				getItemsBIC().addBean(item);
			}

			// -----------------------------------------------------------------

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected WindowForm buildWinddowForm(String mode, String id) {
		return new WPuntoEquilibrio(mode, id, filterBI.getBean().getEjercicioContable());
	}

	// =================================================================================

} // END CLASS
