package com.massoftware.windows.fecha_cierre_x_modulo;

import java.util.Date;

import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.ex.crud.UniqueException;

import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class WFechaCierreXModulo extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	public final static String INSERT_MODE = "INSERT_MODE";
	public final static String UPDATE_MODE = "UPDATE_MODE";
	public final static String COPY_MODE = "COPY_MODE";

	private String mode;

	// -------------------------------------------------------------

	private BeanItem<FechaCierreXModuloFiltro> filterBI;
	private BeanItem<FechaCierreXModulo> itemBI;

	// -------------------------------------------------------------

	private Button agregarBTN;
	private Button modificarBTN;

	// -------------------------------------------------------------

	private DateField ventasDF;
	private DateField stockDF;
	private DateField fondosDF;
	private DateField comprasDF;
	private DateField contabilidadDF;
	private DateField garantiasDevolucionesDF;
	private DateField tambosDF;
	private DateField rrhhDF;

	// -------------------------------------------------------------

	public WFechaCierreXModulo() {
		super();

		this.mode = UPDATE_MODE;
		filterBI = new BeanItem<FechaCierreXModuloFiltro>(
				new FechaCierreXModuloFiltro());
		itemBI = new BeanItem<FechaCierreXModulo>(new FechaCierreXModulo());
		init();
	}

	public void init() {

		try {

			UtilUI.confWinForm(this, "Fecha de cierre por módulo");
			this.setModal(false);

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FORM

			// -----------

			HorizontalLayout hl = UtilUI.buildHL();
			VerticalLayout column1 = UtilUI.buildVL();
			VerticalLayout column2 = UtilUI.buildVL();

			ventasDF = UtilUI.buildFieldDF(itemBI, "ventas", "Ventas", false,
					false);
			stockDF = UtilUI.buildFieldDF(itemBI, "stock", "Stock", false,
					false);
			fondosDF = UtilUI.buildFieldDF(itemBI, "fondos", "Fondos", false,
					false);
			comprasDF = UtilUI.buildFieldDF(itemBI, "compras", "Compras",
					false, false);
			contabilidadDF = UtilUI.buildFieldDF(itemBI, "contabilidad",
					"Contabilidad", false, false);
			garantiasDevolucionesDF = UtilUI.buildFieldDF(itemBI,
					"garantiasDevoluciones", "Garantias y devoluciones", false,
					false);
			tambosDF = UtilUI.buildFieldDF(itemBI, "tambos", "Tambos", false,
					false);
			rrhhDF = UtilUI.buildFieldDF(itemBI, "rrhh", "RRHH", false, false);

			column1.addComponents(ventasDF, stockDF, fondosDF, comprasDF);
			column2.addComponents(contabilidadDF, garantiasDevolucionesDF,
					tambosDF, rrhhDF);
			hl.addComponents(column1, column2);
			// -----------

			// =======================================================
			// -------------------------------------------------------
			// BOTONERA 1

			HorizontalLayout filaBotoneraHL = new HorizontalLayout();
			filaBotoneraHL.setSpacing(true);

			agregarBTN = UtilUI.buildButtonAgregar();
			agregarBTN.addClickListener(e -> {
				save();
			});
			modificarBTN = UtilUI.buildButtonModificar();
			modificarBTN.addClickListener(e -> {
				save();
			});

			agregarBTN.setVisible(INSERT_MODE.equals(mode));
			modificarBTN.setVisible(UPDATE_MODE.equals(mode));

			filaBotoneraHL.addComponents(agregarBTN, modificarBTN);

			// -------------------------------------------------------

			content.addComponents(hl, filaBotoneraHL);
			content.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);

			this.setContent(content);

			// =======================================================
			// -------------------------------------------------------
			// KEY EVENTs

			// Si tenemos la necesidad de un CBOX descomentamos este codigo
			// this.addShortcutListener(new ShortcutListener("ENTER",
			// KeyCode.ENTER, new int[] {}) {
			//
			// private static final long serialVersionUID = 1L;
			//
			// @Override
			// public void handleAction(Object sender, Object target) {
			// if (target.equals(control)) {
			//
			// }
			//
			// }
			// });

			// --------------------------------------------------

			this.addShortcutListener(new ShortcutListener("CTRL+S", KeyCode.S,
					new int[] { ModifierKey.CTRL }) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					save();
				}
			});

			this.addShortcutListener(new ShortcutListener("DELETE",
					KeyCode.DELETE, new int[] {}) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					if (target instanceof TextField
							&& ((TextField) target).isEnabled()
							&& ((TextField) target).isReadOnly() == false) {
						((TextField) target).setValue(null);
					} else if (target instanceof DateField
							&& ((DateField) target).isEnabled()
							&& ((DateField) target).isReadOnly() == false) {
						((DateField) target).setValue(null);
					}
				}
			});

			// =======================================================
			// -------------------------------------------------------

			loadData();

			if (INSERT_MODE.equalsIgnoreCase(mode)) {
				this.setCaption("Agregar " + getCaption().toLowerCase());
			} else if (UPDATE_MODE.equalsIgnoreCase(mode)) {
				this.setCaption("Modificar " + getCaption().toLowerCase()
						+ " : " + itemBI.getBean());
			} else if (COPY_MODE.equalsIgnoreCase(mode)) {
				this.setCaption("Copiar " + getCaption() + " : "
						+ itemBI.getBean());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================

	private void loadData() {
		try {

			FechaCierreXModulo item = queryData();
			if (item != null) {
				itemBI.setBean(item);
			} else {
				LogAndNotification.printError("No se encontro el item",
						"Se intento buscar un item en base a los siguientes parámetros de búsqueda, "
								+ filterBI.getBean());
			}
		} catch (IllegalArgumentException e) {
			LogAndNotification.print(e);
		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void save() {

		try {

			ventasDF.validate();
			stockDF.validate();
			fondosDF.validate();
			comprasDF.validate();
			contabilidadDF.validate();
			garantiasDevolucionesDF.validate();
			tambosDF.validate();
			rrhhDF.validate();

			String m = null;
			boolean ok = true;

			if (INSERT_MODE.equalsIgnoreCase(mode)) {
				ok = insert();
				m = "El item se agregó con éxito,  " + itemBI.getBean() + ".";
			} else if (COPY_MODE.equalsIgnoreCase(mode)) {
				ok = insert();
				m = "El item se copió con éxito, " + itemBI.getBean() + ".";
			} else if (UPDATE_MODE.equalsIgnoreCase(mode)) {
				ok = update();
				m = "El item se modificó con éxito, " + itemBI.getBean() + ".";
			}

			if (ok) {

				LogAndNotification.printSuccessOk(m);

				close();
			}

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);

		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);

		} catch (UniqueException e) {
			LogAndNotification.print(e);

		} catch (IllegalArgumentException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	// metodo que realiza la consulta a la base de datos
	private FechaCierreXModulo queryData() {
		try {

			System.out.println("Los filtros son "
					+ this.filterBI.getBean().toString());

			// Notification.show("Los filtros son "
			// + this.filterBI.getBean().toString());

			FechaCierreXModulo item = mockData(this.filterBI.getBean());

			return item;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new FechaCierreXModulo();
	}

	protected boolean insert() throws Exception {

		return true;
	}

	protected boolean update() throws Exception {

		return true;
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	private FechaCierreXModulo mockData(FechaCierreXModuloFiltro filtro) {

		FechaCierreXModulo item = new FechaCierreXModulo();

		item.setVentas(new Date(System.currentTimeMillis()));
		item.setStock(new Date(System.currentTimeMillis()));
		item.setFondos(new Date(System.currentTimeMillis()));
		item.setCompras(new Date(System.currentTimeMillis()));
		item.setContabilidad(new Date(System.currentTimeMillis()));
		item.setGarantiasDevoluciones(new Date(System.currentTimeMillis()));
		item.setTambos(new Date(System.currentTimeMillis()));
		item.setRrhh(new Date(System.currentTimeMillis()));

		return item;

	}

	// =================================================================================

} // END CLASS
