package com.massoftware.windows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.vaadin.inputmask.InputMask;
import org.vaadin.patrik.FastNavigation;
import org.vaadin.patrik.FastNavigation.RowFocusListener;
import org.vaadin.patrik.events.RowFocusEvent;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.LongRangeValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UtilUI {

	public static String S_N = "S/N";
	public static String EQUALS = "EQUALS";
	public static String STARTS_WITCH = "STARTS_WITCH";
	public static String ENDS_WITCH = "ENDS_WITCH";
	public static String CONTAINS = "CONTAINS";
	public static String CONTAINS_WORDS_OR = "CONTAINS_WORDS_OR";
	public static String CONTAINS_WORDS_AND = "CONTAINS_WORDS_AND";

	public static Grid buildGrid() {
		Grid grd = new Grid();
		// initNavigation(grd);
		grd.addStyleName("small");
		grd.addStyleName("compact");
		grd.setWidth("100%");
		grd.setSelectionMode(SelectionMode.SINGLE);
		grd.setImmediate(true);

		return grd;
	}

	public static FastNavigation initNavigation(final Grid grid) {
		FastNavigation nav = new FastNavigation(grid, true, false);
		nav.setChangeColumnAfterLastRow(false);
		nav.setOpenEditorWithSingleClick(false);
		nav.setAllowArrowToChangeRow(true);

		// nav.addRowEditListener(new RowEditListener() {
		// @Override
		// public void onEvent(RowEditEvent event) {
		// int rowIndex = event.getRowIndex();
		// if (rowIndex >= 0) {
		// Indexed ds = grid.getContainerDataSource();
		// Object itemId = event.getItemId();
		// // printChangedRow(rowIndex, ds, itemId);
		//
		// // System.out.println("eeeeeeeeeeeeeeeee");
		//
		// }
		// }
		// });

		// Open with F2
		nav.addEditorOpenShortcut(KeyCode.F2);
		// writeOutput("Editor can also be opened with F2");

		// Close with F3
		nav.addEditorCloseShortcut(KeyCode.F3);
		// writeOutput("Editor can also be closed with F3");

		// Row focus change
		nav.addRowFocusListener(new RowFocusListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6975464630317608789L;

			@Override
			public void onEvent(RowFocusEvent event) {
				// int row = event.getRow();
				// writeOutput("Focus moved to row " + event.getRow());
				grid.select(event.getItemId());

			}
		});
		// writeOutput("Added row focus change listener");

		// Cell focus change
		// nav.addCellFocusListener(new CellFocusListener() {
		// @Override
		// public void onEvent(CellFocusEvent event) {
		// int row = event.getRow();
		// int col = event.getColumn();
		// // writeOutput("Focus moved to cell [" + row + ", " + col + " ]");
		// }
		// });
		// writeOutput("Added cell focus change listener");

		// Listening to opening of editor
		// nav.addEditorOpenListener(new EditorOpenListener() {
		// @Override
		// public void onEvent(EditorOpenEvent event) {
		// int row = event.getRow();
		// // writeOutput("Editor opened on row " + row + " at column "
		// // + event.getColumn());
		// }
		// });
		// writeOutput("Added editor open listener");

		// Listening to closing of editor
		// nav.addEditorCloseListener(new EditorCloseListener() {
		// @Override
		// public void onEvent(EditorCloseEvent event) {
		// // writeOutput("Editor closed on row "
		// // + event.getRow()
		// // + ", column "
		// // + event.getColumn()
		// // + ", "
		// // + (event.wasCancelled() ? "user cancelled change"
		// // : "user saved change"));
		// }
		// });
		// writeOutput("Added editor close listener");

		// nav.addClickOutListener(new ClickOutListener() {
		// @Override
		// public void onEvent(ClickOutEvent event) {
		// // writeOutput("User click outside Grid: "
		// // + event.getSource().toString());
		// }
		// });

		return nav;

	}
	
	public static Column confColumn(Column column, boolean sortable, double pixelWidth) {
		return confColumn(column, null, true, false, false, sortable, pixelWidth);
	}

	public static Column confColumn(Column column, String label, boolean sortable, double pixelWidth) {
		return confColumn(column, label, true, false, false, sortable, pixelWidth);
	}

	public static Column confColumn(Column column, boolean hidable, boolean hidden, boolean editable, boolean sortable,
			double pixelWidth) {

		return confColumn(column, null, hidable, hidden, editable, sortable, pixelWidth);
	}

	public static Column confColumn(Column column, String label, boolean hidable, boolean hidden, boolean editable,
			boolean sortable, double pixelWidth) {

		if (label == null) {
			label = column.getPropertyId().toString();
		}

		column.setHeaderCaption(label);
		column.setHidable(hidable);
		column.setHidden(hidden);
		column.setEditable(editable);
		if (pixelWidth > -1) {
			column.setWidth(pixelWidth);
		} else {

		}

		column.setSortable(sortable);

		return column;
	}

	public static VerticalLayout buildWinContentVertical() {

		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		content.setWidth("100%");

		return content;
	}

	public static HorizontalLayout buildWinContentHorizontal() {

		HorizontalLayout content = new HorizontalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		content.setWidth("100%");

		return content;
	}

	@Deprecated
	public static Button buildButtonPrev(int limit, int offset) {

		Button prevPageBTN = new Button();
		prevPageBTN.addStyleName(ValoTheme.BUTTON_TINY);
		prevPageBTN.setCaption("<");
		prevPageBTN.setEnabled(offset > 0);
		prevPageBTN.setDescription(limit + " registros anteriores.");
		prevPageBTN.addClickListener(e -> {
			// prevPageBTNClick();
		});

		return prevPageBTN;
	}

	@Deprecated
	public static Button buildButtonNext(int limit, int offset) {

		Button nextPageBTN = new Button();
		nextPageBTN.addStyleName(ValoTheme.BUTTON_TINY);
		nextPageBTN.setCaption(">");
		nextPageBTN.setDescription("Siguientes " + limit + " registros.");
		nextPageBTN.addClickListener(e -> {
			// nextPageBTNClick();
		});

		return nextPageBTN;
	}

	@Deprecated
	public static Button buildButtonBuscar() {

		Button buscarBTN = new Button();
		// buscarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		buscarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		buscarBTN.setIcon(FontAwesome.SEARCH);
		// buscarBTN.setWidth("5px");
		// agregarBTN.setCaption("Agregar");
		buscarBTN.setDescription("Buscar" + " (Ctrl+B)");
		// agregarBTN.addClickListener(e -> {
		// // agregarBTNClick();
		// });

		return buscarBTN;
	}

	public static Button buildButtonAgregar() {

		Button agregarBTN = new Button();
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		agregarBTN.setIcon(FontAwesome.PLUS);
		agregarBTN.setCaption("Agregar");
		agregarBTN.setDescription(agregarBTN.getCaption() + " (Ctrl+A)");
		// agregarBTN.addClickListener(e -> {
		// // agregarBTNClick();
		// });

		return agregarBTN;
	}

	public static Button buildButton(String label, String description) {

		Button btn = new Button();
		btn.addStyleName(ValoTheme.BUTTON_TINY);
		btn.setCaption(label);
		if (description != null) {
			btn.setDescription(description);
		}
		// modificarBTN.addClickListener(e -> {
		// // modificarBTNClick();
		// });

		return btn;
	}

	public static Button buildButtonModificar() {

		Button modificarBTN = new Button();
		modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		modificarBTN.setIcon(FontAwesome.PENCIL);
		modificarBTN.setCaption("Modificar");
		modificarBTN.setDescription(modificarBTN.getCaption() + " (Ctrl+M)");
		// modificarBTN.addClickListener(e -> {
		// // modificarBTNClick();
		// });

		return modificarBTN;
	}

	public static Button buildButtonCopiar() {

		Button modificarBTN = new Button();
		// modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		modificarBTN.setIcon(FontAwesome.COPY);
		modificarBTN.setCaption("Copiar");
		modificarBTN.setDescription(modificarBTN.getCaption() + " (Ctrl+C)");
		// modificarBTN.addClickListener(e -> {
		// // modificarBTNClick();
		// });

		return modificarBTN;
	}

	public static Button buildButtonEliminar() {

		Button eliminarBTN = new Button();
		eliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		eliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		eliminarBTN.setIcon(FontAwesome.TRASH);
		eliminarBTN.setCaption("Eliminar");

		// eliminarBTN.addClickListener(e -> {
		// // eliminarBTNClick();
		// });

		return eliminarBTN;
	}

	public static Button buildButtonSeleccionar() {

		Button seleccionarBTN = new Button();
		seleccionarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		seleccionarBTN.setIcon(FontAwesome.CHECK_SQUARE_O);
		seleccionarBTN.setCaption("Seleccionar");

		// eliminarBTN.addClickListener(e -> {
		// // eliminarBTNClick();
		// });

		return seleccionarBTN;
	}

	@Deprecated
	public static Window confWinList(Window window, String label) {

		window.setCaption(label);
		window.setImmediate(true);
		// window.setWidth("-1px");
		// window.setHeight("-1px");
		window.setWidthUndefined();
		window.setHeightUndefined();
		window.setClosable(true);
		window.setResizable(false);
		window.setModal(false);
		window.center();

		return window;
	}

	@Deprecated
	public static Window confWinForm(Window window, String label) {

		window.setCaption(label);
		window.setImmediate(true);
		// window.setWidth("-1px");
		// window.setHeight("-1px");
		window.setWidthUndefined();
		window.setHeightUndefined();
		window.setClosable(true);
		window.setResizable(false);
		window.setModal(true);
		window.center();
		// window.setContent((Component) this);
		// getUI().addWindow(window);

		return window;
	}

	public static Window confWinDeleteTextfieldsValue(Window window) {

		window.addShortcutListener(new ShortcutListener("DELETE", KeyCode.DELETE, new int[] {}) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				if (target instanceof TextField && ((TextField) target).isEnabled()
						&& ((TextField) target).isReadOnly() == false) {
					((TextField) target).setValue(null);
				}
			}
		});

		return window;
	}

	public static TabSheet buildTS() {

		TabSheet ts = new TabSheet();

		return ts;
	}

	public static HorizontalLayout buildHL() {
		HorizontalLayout hl = new HorizontalLayout();

		// hl.setWidth("-1px");
		// hl.setHeight("-1px");
		hl.setWidthUndefined();
		hl.setHeightUndefined();
		hl.setMargin(true);
		hl.setSpacing(true);

		return hl;
	}

	public static VerticalLayout buildVL() {
		VerticalLayout vl = new VerticalLayout();
		vl.setWidthUndefined();
		vl.setHeightUndefined();
		vl.setMargin(true);
		vl.setSpacing(true);

		return vl;
	}

	public static TextField buildTXT() {

		TextField txt = new TextField();

		txt.addStyleName(ValoTheme.TEXTFIELD_TINY);
		// txt.setWidth("-1px");
		// txt.setHeight("-1px");
		txt.setWidthUndefined();
		txt.setHeightUndefined();
		txt.setValidationVisible(true);
		txt.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txt.setNullRepresentation("");
		txt.setVisible(true);
		txt.setEnabled(true);
		txt.setReadOnly(false);
		txt.setImmediate(true);

		return txt;
	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public static HorizontalLayout buildTXTHL(BeanItem dtoBI, String attName, String label, boolean readOnly,
			int columns, int minLength, int maxLength, boolean required, boolean allowInputUnmask, String mask,
			boolean autoUnmask, String inputPrompt) throws Exception {

		HorizontalLayout txtHL = new HorizontalLayout();
		txtHL.setSpacing(false);

		TextField txt = buildTXT(dtoBI, attName, label, readOnly, columns, minLength, maxLength, required,
				allowInputUnmask, mask, autoUnmask);

		txt.setInputPrompt(buildWinFilterTXTInputPromptList(inputPrompt));
		txt.setDescription(txt.getInputPrompt());

		txtHL.addComponent(txt);

		// ----------------------------------------------

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.TIMES);
		btn.setDescription("Quitar filtro " + label + ".");

		btn.addClickListener(e -> {
			try {
				txt.setValue(null);
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		txtHL.addComponent(btn);
		txtHL.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		return txtHL;
	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public static HorizontalLayout buildTXTHLInteger(BeanItem dtoBI, String attName, String label, boolean readOnly,
			int columns, int minLength, int maxLength, boolean required, boolean allowInputUnmask, String mask,
			boolean autoUnmask, String inputPrompt, int minValue, int maxValue) throws Exception {

		HorizontalLayout txtHL = new HorizontalLayout();
		txtHL.setSpacing(false);

		TextField txt = buildTXTInteger(dtoBI, attName, label, readOnly, columns, minLength, maxLength, required,
				allowInputUnmask, mask, autoUnmask, minValue, maxValue);

		txt.setInputPrompt(buildWinFilterTXTInputPromptList(inputPrompt));
		txt.setDescription(txt.getInputPrompt());

		txtHL.addComponent(txt);

		// ----------------------------------------------

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.TIMES);
		btn.setDescription("Quitar filtro " + label + ".");

		btn.addClickListener(e -> {
			try {
				txt.setValue(null);
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		txtHL.addComponent(btn);
		txtHL.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		return txtHL;

	}

	public static String buildWinFilterTXTInputPromptList(String inputPrompt) {

		if (S_N.equals(inputPrompt)) {

			return "s/n o vacio para ver todos ..";

		} else if (CONTAINS.equals(inputPrompt)) {

			return "contiene ..";

		} else if (CONTAINS_WORDS_AND.equals(inputPrompt)) {

			return "contiene las palabras ..";

		} else if (CONTAINS_WORDS_OR.equals(inputPrompt)) {

			return "contiene las palabras opcionales ..";

		} else if (STARTS_WITCH.equals(inputPrompt)) {

			return "comienza con ..";

		} else if (ENDS_WITCH.equals(inputPrompt)) {

			return "termina con ..";
		} else if (EQUALS.equals(inputPrompt)) {

			return "igual a ..";
		}

		throw new RuntimeException(inputPrompt + " not found.");
	}

	/* Crea una caja con column 30 y de 100 como maximo numero de caracteres */
	@SuppressWarnings("rawtypes")
	public static TextField buildTXT30100(BeanItem dtoBI, String attName, boolean readOnly, boolean required)
			throws Exception {

		return buildTXT(dtoBI, attName, null, readOnly, 30, -1, 100, required, false, null, false);

	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXT(BeanItem dtoBI, String attName, boolean readOnly, int columns, int minLength,
			int maxLength, boolean required, boolean allowInputUnmask, String mask, boolean autoUnmask)
			throws Exception {

		if (required && minLength < 0) {
			minLength = 1;
		}

		return buildTXT(dtoBI, attName, null, readOnly, columns, minLength, maxLength, required, allowInputUnmask, mask,
				autoUnmask);

	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXT(BeanItem dtoBI, String attName, String label, Boolean readOnly, int columns,
			int minLength, int maxLength, Boolean required, boolean allowInputUnmask, String mask, boolean autoUnmask)
			throws Exception {

		TextField txt = buildTXT();

		if (label == null && dtoBI.getBean() instanceof Entity) {
			String lbl = ((Entity) dtoBI.getBean()).label(attName);
			if (lbl != null) {
				label = lbl;
			}
		}
		
		if (required == null && dtoBI.getBean() instanceof Entity) {			
			required = ((Entity) dtoBI.getBean()).required(attName);
		}
		

		if (required && minLength < 0) {
			minLength = 1;
		}
		
		if (readOnly == null && dtoBI.getBean() instanceof Entity) {			
			readOnly = ((Entity) dtoBI.getBean()).readOnly(attName);
		}

		txt.setCaption(label);

		txt.setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");
		txt.setColumns(columns);
		if (maxLength > -1) {
			txt.setMaxLength(maxLength);
		}
		txt.setRequired(required);

		if (minLength > 0) {

			// txt.addValidator(new MinLengthValidator(String.class, label, minLength));
		}

		if (allowInputUnmask == true) {
			InputMask im = new InputMask(mask);
			im.setAutoUnmask(autoUnmask);
			im.setDigitsOptional(false);
			im.extend(txt);
		}

		// if (unique == true) {
		//
		// String methodName = "get" + attName.substring(0, 1).toUpperCase()
		// + attName.substring(1, attName.length());
		//
		// @SuppressWarnings("unchecked")
		// Method method = clazz.getMethod(methodName);
		// Object originalValueDTO = method.invoke(originalDTO);
		//
		// txt.addValidator(new GenericUniqueValidator(field.getType(),
		// attName, true, true, cx.buildBO(clazz), originalValueDTO,
		// mode));
		// }

		txt.setPropertyDataSource(dtoBI.getItemProperty(attName));

		txt.setReadOnly(readOnly);

		return txt;
	}

	@Deprecated
	@SuppressWarnings("rawtypes")
	public static TextField buildTXTShortPlus(BeanItem dtoBI, String attName, String label, boolean readOnly,
			int minLength, boolean required) throws Exception {

		int length = (Short.MAX_VALUE + "").length();

		if (required && minLength < 0) {
			minLength = 1;
		}

		return buildTXTInteger(dtoBI, attName, label, readOnly, length, minLength, length, required, false, null, false,
				0, Short.MAX_VALUE);
	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXTIntegerPlus(BeanItem dtoBI, String attName, Boolean readOnly, Boolean required)
			throws Exception {
		return buildTXTIntegerPlus(dtoBI, attName, null, readOnly, -1, required);
	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXTIntegerPlus(BeanItem dtoBI, String attName, Boolean readOnly, int minLength,
			Boolean required) throws Exception {
		return buildTXTIntegerPlus(dtoBI, attName, null, readOnly, minLength, required);
	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXTIntegerPlus(BeanItem dtoBI, String attName, String label, Boolean readOnly,
			int minLength, Boolean required) throws Exception {

		int length = (Integer.MAX_VALUE + "").length();

		

		return buildTXTInteger(dtoBI, attName, label, readOnly, length, minLength, length, required, false, null, false,
				0, Integer.MAX_VALUE);
	}

	@Deprecated
	@SuppressWarnings("rawtypes")
	public static TextField buildTXTTinyintPlus(BeanItem dtoBI, String attName, String label, boolean readOnly,
			int minLength, boolean required) throws Exception {

		int length = 3;

		if (required && minLength < 0) {
			minLength = 1;
		}

		return buildTXTInteger(dtoBI, attName, label, readOnly, 6, minLength, length, required, false, null, false, 0,
				255);
	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXTInteger(BeanItem dtoBI, String attName, String label, boolean readOnly, int columns,
			int minLength, int maxLength, boolean required, boolean allowInputUnmask, String mask, boolean autoUnmask,
			int minValue, int maxValue) throws Exception {

		if (maxLength > (Integer.MAX_VALUE + "").length()) {
			maxLength = (Integer.MAX_VALUE + "").length();
		}
		

		TextField txt = buildTXT(dtoBI, attName, label, readOnly, columns, minLength, maxLength, required,
				allowInputUnmask, mask, autoUnmask);

		txt.setConverter(new StringToIntegerConverterUnspecifiedLocale());
		String msg = "El campo " + txt.getCaption()
				+ " es inválido, se permiten sólo valores numéricos sin decimales, desde " + minValue + " hasta "
				+ maxValue + ".";

		txt.addValidator(new IntegerRangeValidator(msg, minValue, maxValue));

		txt.setConversionError(msg);

		txt.addStyleName("align-right");

		return txt;

	}

	@Deprecated
	@SuppressWarnings("rawtypes")
	public static TextField buildTXTLong(BeanItem dtoBI, String attName, String label, boolean readOnly, int columns,
			int minLength, int maxLength, boolean required, boolean allowInputUnmask, String mask, boolean autoUnmask,
			long minValue, long maxValue) throws Exception {

		TextField txt = buildTXT(dtoBI, attName, label, readOnly, columns, minLength, maxLength, required,
				allowInputUnmask, mask, autoUnmask);

		txt.setConverter(new StringToIntegerConverterUnspecifiedLocale());
		String msg = "El campo " + txt.getCaption()
				+ " es inválido, se permiten sólo valores numéricos sin decimales, desde " + minValue + " hasta "
				+ maxValue + ".";

		txt.addValidator(new LongRangeValidator(msg, minValue, maxValue));

		txt.setConversionError(msg);

		txt.addStyleName("align-right");

		return txt;

	}

	public static TextArea buildTXA() {

		TextArea txa = new TextArea();

		txa.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txa.setWidthUndefined();
		txa.setHeightUndefined();
		txa.setValidationVisible(true);
		txa.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txa.setNullRepresentation("");
		txa.setVisible(true);
		txa.setEnabled(true);
		txa.setReadOnly(false);
		txa.setImmediate(true);

		return txa;
	}

	public static TextArea buildTXA(String label, boolean readOnly, int columns, int rows, int minLength, int maxLength,
			boolean required) {

		TextArea txt = buildTXA();

		txt.setCaption(label);
		txt.setReadOnly(readOnly);
		txt.setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");
		if (columns > 0) {
			txt.setColumns(columns);
		} else {
			txt.setWidth("100%");
		}
		txt.setRows(rows);
		txt.setMaxLength(maxLength);
		txt.setRequired(required);

		if (minLength > 0) {

			// txt.addValidator(new MinLengthValidator(String.class, label,
			// minLength));
		}

		// if (unique == true) {
		//
		// String methodName = "get" + attName.substring(0, 1).toUpperCase()
		// + attName.substring(1, attName.length());
		//
		// @SuppressWarnings("unchecked")
		// Method method = clazz.getMethod(methodName);
		// Object originalValueDTO = method.invoke(originalDTO);
		//
		// txt.addValidator(new GenericUniqueValidator(field.getType(),
		// attName, true, true, cx.buildBO(clazz), originalValueDTO,
		// mode));
		// }

		return txt;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Deprecated
	public static HorizontalLayout buildSearchBox(BeanItem dtoBI, String attName, String label, String label2,
			boolean readOnly, boolean required) {

		// HorizontalLayout hl = buildHL();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(false);
		hl.setSpacing(false);
		// hl.setCaption(label);

		// TextField txtSearch = ControlFactory.buildTXT();
		TextField txtSearch = new TextField();
		txtSearch.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtSearch.setNullRepresentation("");
		txtSearch.setCaption(label);
		txtSearch.setColumns(8);
		String searchFor = label2;
		if (searchFor != null) {
			searchFor = searchFor.toLowerCase();
			txtSearch.setDescription("Buscar por " + searchFor);
		} else {
			searchFor = "";
			txtSearch.setDescription("Buscar por " + label.toLowerCase());
		}
		txtSearch.setInputPrompt(searchFor);

		TextField txtValue = new TextField();
		txtValue.setValidationVisible(true);
		txtValue.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txtValue.setNullRepresentation("");
		txtValue.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtValue.setCaption("");
		txtValue.setEnabled(false);
		txtValue.setRequired(required);

		hl.addComponent(txtSearch);
		hl.setComponentAlignment(txtSearch, Alignment.MIDDLE_LEFT);

		hl.addComponent(txtValue);
		hl.setComponentAlignment(txtValue, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		Button removeFilterBTN = new Button();
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_TINY);
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Borrar valor");

		removeFilterBTN.addClickListener(e -> {
			txtSearch.setValue(null);
			dtoBI.getItemProperty(attName).setValue(null);
			txtValue.setValue(null);
		});

		hl.addComponent(removeFilterBTN);
		hl.setComponentAlignment(removeFilterBTN, Alignment.BOTTOM_LEFT);

		// valueCB.setPropertyDataSource(dtoBI.getItemProperty(attName));

		return hl;

	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public static HorizontalLayout buildSearchBox(BeanItem dtoBI, String attNameCode, String attName, String label,
			String label2, boolean required) throws SecurityException, ClassNotFoundException, NoSuchFieldException {

		return buildSearchBox(dtoBI, attNameCode, attName, label, label2, required, label, false);

	}

	@SuppressWarnings({ "rawtypes" })
	@Deprecated
	public static HorizontalLayout buildSearchBox(BeanItem dtoBI, String attNameCode, String attName, String label,
			String label2, boolean required, String label3, boolean onlyBtn)
			throws SecurityException, ClassNotFoundException, NoSuchFieldException {

		// HorizontalLayout hl = buildHL();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidthUndefined();
		hl.setMargin(false);
		hl.setSpacing(false);
		// hl.setCaption(label);

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.FOLDER_OPEN);
		btn.setDescription("Buscar " + label3);

		// TextField txtSearch = ControlFactory.buildTXT();
		TextField txtSearch = new TextField();
		txtSearch.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtSearch.setNullRepresentation("");
		txtSearch.setCaption(label);
		txtSearch.setColumns(6);
		String searchFor = label2;
		if (searchFor != null) {
			searchFor = searchFor.toLowerCase();
			txtSearch.setDescription("Buscar por " + searchFor);
		} else {
			searchFor = "";
			txtSearch.setDescription("Buscar por " + label.toLowerCase());
		}
		txtSearch.setInputPrompt(searchFor);

		Field field = getField(dtoBI.getBean().getClass(), attNameCode);

		if (field.getType() == Integer.class) {

			txtSearch.setConverter(new StringToIntegerConverterUnspecifiedLocale());

			String msg = "El campo " + txtSearch.getCaption()
					+ " es inválido, se permiten sólo valores numéricos sin decimales.";

			txtSearch.addStyleName("align-right");

			txtSearch.setConversionError(msg);

		}

		TextField txtValue = new TextField();
		txtValue.setValidationVisible(true);
		txtValue.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txtValue.setNullRepresentation("");
		txtValue.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtValue.setCaption("");
		// txtValue.setEnabled(false);
		txtValue.setRequired(required);
		txtValue.setInputPrompt(label3);
		txtValue.setDescription(label3);

		if (onlyBtn) {
			hl.addComponent(btn);
			hl.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);
			txtSearch.setVisible(false);
			txtValue.setCaption(label);
		}

		hl.addComponent(txtSearch);
		hl.setComponentAlignment(txtSearch, Alignment.MIDDLE_LEFT);

		hl.addComponent(txtValue);
		hl.setComponentAlignment(txtValue, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		Button removeFilterBTN = new Button();
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_TINY);
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Borrar valor");

		removeFilterBTN.addClickListener(e -> {
			txtSearch.setValue(null);
			// dtoBI.getItemProperty(attName).setValue(null);

			txtValue.setValue(null);
		});

		hl.addComponent(removeFilterBTN);
		hl.setComponentAlignment(removeFilterBTN, Alignment.BOTTOM_LEFT);

		txtSearch.setPropertyDataSource(dtoBI.getItemProperty(attNameCode));
		txtValue.setPropertyDataSource(dtoBI.getItemProperty(attName));

		return hl;

	}

	@SuppressWarnings({ "rawtypes" })
	@Deprecated
	public static HorizontalLayout buildSearchBox(BeanItem dtoBI, String attName, String label, boolean required,
			String label2) throws SecurityException, ClassNotFoundException, NoSuchFieldException {

		// HorizontalLayout hl = buildHL();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidthUndefined();
		hl.setMargin(false);
		hl.setSpacing(false);
		// hl.setCaption(label);

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.FOLDER_OPEN);
		btn.setDescription("Buscar " + label);

		TextField txtValue = new TextField();
		txtValue.setValidationVisible(true);
		txtValue.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txtValue.setNullRepresentation("");
		txtValue.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtValue.setCaption(label);
		// txtValue.setEnabled(false);
		txtValue.setRequired(required);
		txtValue.setInputPrompt(label2);
		txtValue.setDescription(label2);

		String searchFor = label;
		if (searchFor != null) {
			searchFor = searchFor.toLowerCase();
			txtValue.setDescription("Buscar por " + searchFor);
		} else {
			searchFor = "";
			txtValue.setDescription("Buscar por " + label.toLowerCase());
		}
		txtValue.setInputPrompt(searchFor);

		hl.addComponent(btn);
		hl.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);
		txtValue.setCaption(label);

		hl.addComponent(txtValue);
		hl.setComponentAlignment(txtValue, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		Button removeFilterBTN = new Button();
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_TINY);
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Borrar valor");

		removeFilterBTN.addClickListener(e -> {
			txtValue.setValue(null);
		});

		hl.addComponent(removeFilterBTN);
		hl.setComponentAlignment(removeFilterBTN, Alignment.BOTTOM_LEFT);

		txtValue.setPropertyDataSource(dtoBI.getItemProperty(attName));

		return hl;

	}

	public static ComboBox buildCB() {

		ComboBox cb = new ComboBox();
		cb.addStyleName(ValoTheme.COMBOBOX_TINY);

		cb.setWidth("100%");
		cb.setHeightUndefined();
		cb.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		cb.setValidationVisible(true);
		cb.setVisible(true);
		cb.setEnabled(true);
		cb.setReadOnly(false);
		cb.setImmediate(true);
		cb.setFilteringMode(FilteringMode.CONTAINS);

		// cb.addValueChangeListener(e -> {
		// try{
		// Object o = cb.getValue();
		// if(o != null){
		// cb.setDescription(o.toString());
		// } else {
		// cb.setDescription("");
		// }
		//
		// }catch(Exception ex){
		// LogAndNotification.print(ex);
		// }
		// });
		//
		//
		// Object o = cb.getValue();
		// if(o != null){
		// cb.setDescription(o.toString());
		// } else {
		// cb.setDescription("");
		// }

		return cb;
	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public static HorizontalLayout buildCBHL(BeanItem dtoBI, String attName, String label, boolean readOnly,
			boolean required, Class clazz, List options) throws Exception {

		HorizontalLayout txtHL = new HorizontalLayout();
		txtHL.setSpacing(false);

		ComboBox cb = buildFieldCB(dtoBI, attName, label, readOnly, required, clazz, options);

		txtHL.addComponent(cb);

		// ----------------------------------------------

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.TIMES);
		btn.setDescription("Quitar filtro " + label + ".");

		if (cb.isRequired() == false) {
			btn.addClickListener(e -> {
				try {
					cb.setValue(null);
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

		}

		btn.setVisible(cb.isRequired() == false);

		txtHL.addComponent(btn);
		txtHL.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		return txtHL;
	}

	@SuppressWarnings("rawtypes")
	public static ComboBox buildFieldCB(BeanItem dtoBI, String attName, boolean readOnly, boolean required, Class clazz,
			List options) throws Exception {

		return buildFieldCB(dtoBI, attName, null, readOnly, required, clazz, options);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ComboBox buildFieldCB(BeanItem dtoBI, String attName, String label, boolean readOnly,
			boolean required, Class clazz, List options) throws Exception {

		ComboBox cb = buildCB();

		if (label == null && dtoBI.getBean() instanceof Entity) {
			String lbl = ((Entity) dtoBI.getBean()).label(attName);
			if (lbl != null) {
				label = lbl;
			}
		}

		cb.setCaption(label);

		cb.setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");
		cb.setRequired(required);
		if (cb.isRequired()) {
			cb.setNullSelectionAllowed(false);
		}
		// cb.setItemCaptionPropertyId(propertyId);
		// cb.setWidth(width);
		// cb.setTextInputAllowed(textInputAllowed);

		// ----------------

		// BeanItemContainer<clazz> paisesBIC = new
		// BeanItemContainer<clazz>(
		// clazz, new ArrayList<clazz>());

		BeanItemContainer optionsBIC = new BeanItemContainer(clazz, new ArrayList());
		// optionsBIC.removeAllItems();
		for (Object option : options) {
			optionsBIC.addBean(option);
		}
		cb.setContainerDataSource(optionsBIC);

		// cb.setPropertyDataSource(dtoBI.getItemProperty(attName));

		if (cb.isRequired() && optionsBIC.size() > 0) {
			cb.setValue(optionsBIC.getIdByIndex(0));
		}

//		cb.setValue(optionsBIC.getIdByIndex(0));

		// ----------------

		cb.setPropertyDataSource(dtoBI.getItemProperty(attName));

		cb.setReadOnly(readOnly);

		return cb;

	}

	@Deprecated
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ComboBox buildFieldCB(BeanItem dtoBI, String attName, String label, String attName1, String caption,
			float widthEM, boolean readOnly, boolean required, boolean textInputAllowed, Class clazz, List options)
			throws Exception {

		ComboBox cb = buildCB();

		cb.setCaption(label);

		cb.setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");

		cb.setRequired(required);
		if (cb.isRequired()) {

			cb.setNullSelectionAllowed(false);

		}
		cb.setTextInputAllowed(textInputAllowed);

		// ----------------

		// BeanItemContainer<clazz> paisesBIC = new
		// BeanItemContainer<clazz>(
		// clazz, new ArrayList<clazz>());

		BeanItemContainer optionsBIC = new BeanItemContainer(clazz, new ArrayList());
		// optionsBIC.removeAllItems();
		for (Object option : options) {
			optionsBIC.addBean(option);
		}

		if (widthEM > 0) {
			cb.setWidth(widthEM, Unit.EM);
		}

		cb.setContainerDataSource(optionsBIC);

		if (!caption.isEmpty()) {
			cb.setItemCaptionPropertyId(caption);
		}

		if (cb.isRequired() && optionsBIC.size() > 0) {
			cb.setValue(optionsBIC.getIdByIndex(0));

		}

		cb.setPropertyDataSource(dtoBI.getItemProperty(attName1));

		// ----------------

		cb.setReadOnly(readOnly);

		return cb;
	}

	public static OptionGroup buildOG() {
		OptionGroup og = new OptionGroup();
		og.addStyleName(ValoTheme.OPTIONGROUP_SMALL);

		return og;
	}

	@SuppressWarnings("rawtypes")
	public static OptionGroup buildBooleanOG(BeanItem dtoBI, String attName, String label, boolean readOnly,
			boolean required, String labelAll, String labelTrue, String labelFalse, boolean horizontal, int value)
			throws Exception {

		OptionGroup og = buildOG();

		og.setCaption(label);

		og.setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");
		og.setRequired(required);

		og.addItem(0);
		og.addItem(1);
		og.addItem(2);

		og.setItemCaption(0, labelAll);
		og.setItemCaption(1, labelTrue);
		og.setItemCaption(2, labelFalse);

		if (horizontal) {
			og.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		}

		og.setPropertyDataSource(dtoBI.getItemProperty(attName));

		og.setValue(value);

		og.setReadOnly(readOnly);

		return og;
	}

	@SuppressWarnings("rawtypes")
	public static OptionGroup buildBooleanOG(BeanItem dtoBI, String attName, String label, boolean readOnly,
			boolean required, String[] labels, Integer[] values, boolean horizontal, int value) throws Exception {

		OptionGroup og = buildOG();

		og.setCaption(label);

		og.setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");
		og.setRequired(required);

		for (Integer item : values) {
			og.addItem(item);
		}

		for (int i = 0; i < values.length; i++) {
			og.setItemCaption(values[i], labels[i]);
		}

		if (horizontal) {
			og.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		}

		og.setPropertyDataSource(dtoBI.getItemProperty(attName));

		og.setValue(value);

		og.setReadOnly(readOnly);

		return og;
	}

	public static CheckBox buildCHK() {

		CheckBox chk = new CheckBox();

		chk.addStyleName(ValoTheme.CHECKBOX_SMALL);
		chk.setWidthUndefined();
		chk.setHeightUndefined();
		chk.setVisible(true);
		chk.setEnabled(true);
		chk.setReadOnly(false);
		chk.setImmediate(true);

		return chk;
	}

	@SuppressWarnings("rawtypes")
	public static CheckBox buildFieldCHK(BeanItem dtoBI, String attName, boolean readOnly) throws Exception {
		return buildFieldCHK(dtoBI, attName, null, readOnly);
	}

	@SuppressWarnings("rawtypes")
	public static CheckBox buildFieldCHK(BeanItem dtoBI, String attName, String label, boolean readOnly)
			throws Exception {

		CheckBox chk = buildCHK();

		if (label == null && dtoBI.getBean() instanceof Entity) {
			String lbl = ((Entity) dtoBI.getBean()).label(attName);
			if (lbl != null) {
				label = lbl;
			}
		}

		chk.setCaption(label);
		chk.setPropertyDataSource(dtoBI.getItemProperty(attName));

		chk.setReadOnly(readOnly);

		return chk;
	}

	@SuppressWarnings("rawtypes")
	public static HorizontalLayout buildDFHL(BeanItem dtoBI, String attName, String label, boolean readOnly,
			boolean required) throws SecurityException, ClassNotFoundException, NoSuchFieldException {

		HorizontalLayout txtHL = new HorizontalLayout();
		txtHL.setSpacing(false);

		DateField df = buildFieldDF(dtoBI, attName, label, readOnly, required);

		txtHL.addComponent(df);

		// ----------------------------------------------

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.TIMES);
		btn.setDescription("Quitar filtro " + label + ".");

		btn.addClickListener(e -> {
			try {
				df.setValue(null);
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		txtHL.addComponent(btn);
		txtHL.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		return txtHL;
	}

	@SuppressWarnings("rawtypes")
	public static DateField buildFieldDF(BeanItem dtoBI, String attName, String label, boolean readOnly,
			boolean required) throws SecurityException, ClassNotFoundException, NoSuchFieldException {

		DateField df = buildDF(false);

		df.setCaption(label);

		df.setRequired(required);
		df.setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");

		// df.setConverter(Timestamp.class);

		// .setConverter(new StringToTimestampConverterUnspecifiedLocale());

		df.setPropertyDataSource(dtoBI.getItemProperty(attName));

		df.setReadOnly(readOnly);

		return df;

	}

	public static DateField buildDF(boolean timestamp) {

		DateField df = null;

		if (timestamp) {
			df = new DateField();
			df.setConversionError("Se espera un valor dd/MM/yyyy HH:mm:ss ej. 12/11/1979 22:36:54");
		} else {

			df = new DateField() {

				private static final long serialVersionUID = -1814526872789903256L;

				@Override
				protected Date handleUnparsableDateString(String dateString) throws Converter.ConversionException {

					return UtilDate.parseDate(dateString);
					// return new Timestamp(System.currentTimeMillis());
				}

				public void changeVariables(Object source, Map<String, Object> variables) {

					if (variables.containsKey("dateString") == false) {
						variables.put("dateString",
								variables.get("day") + "/" + variables.get("month") + "/" + variables.get("year"));
					}

					variables.put("day", -1);
					variables.put("year", -1);
					variables.put("month", -1);
					// variables.put("sec", -1);
					// variables.put("min", -1);
					// variables.put("hour", -1);
					super.changeVariables(source, variables);
				}

			};
		}

		df.addStyleName(ValoTheme.DATEFIELD_TINY);
		df.setLocale(new Locale("es", "AR"));
		if (timestamp) {
			df.setDateFormat("dd/MM/yyyy HH:mm:ss");
			df.setResolution(Resolution.SECOND);
			// df.setResolution(DateResolution.DAY);
			df.setShowISOWeekNumbers(true);
		} else {
			df.setDateFormat("dd/MM/yyyy");
			df.setWidth("105px");
		}
		df.setLenient(true);
		// df.setReadOnly(false);
		df.setImmediate(true);

		return df;
	}

	public static Label buildLbl(String label, float widthEM) {
		Label lbl = new Label();

		lbl.setCaption(label);

		if (widthEM > 0) {
			lbl.setWidth(widthEM, Unit.EM);
		}

		return lbl;

	}

	@SuppressWarnings("rawtypes")
	private static Field getField(Class clazz, String attNamne)
			throws SecurityException, ClassNotFoundException, NoSuchFieldException {

		return Class.forName(clazz.getCanonicalName()).getDeclaredField(attNamne);

	}

}
