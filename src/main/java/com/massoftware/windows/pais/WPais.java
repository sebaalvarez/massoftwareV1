package com.massoftware.windows.pais;

import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.ex.crud.UniqueException;

import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.paises.WPaises;
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

public class WPais extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	public final static String INSERT_MODE = "INSERT_MODE";
	public final static String UPDATE_MODE = "UPDATE_MODE";
	public final static String COPY_MODE = "COPY_MODE";

	private String mode;

	// -------------------------------------------------------------

	private BeanItem<PaisFiltro> filterBI;
	private BeanItem<Pais> itemBI;

	// -------------------------------------------------------------

	private Button agregarBTN;
	private Button modificarBTN;

	// -------------------------------------------------------------

	private WPaises wtable;
	private TextField numeroTXT;
	private TextField nombreTXT;
	private TextField abreviaturaTXT;

	// -------------------------------------------------------------

	public WPais(WPaises wtable, String mode, Pais item) {
		super();
		this.wtable = wtable;
		this.mode = mode;
		filterBI = new BeanItem<PaisFiltro>(new PaisFiltro());
		itemBI = new BeanItem<Pais>(item);
		init();
	}

	public WPais(WPaises wtable, String mode, PaisFiltro filtro) {
		super();
		this.wtable = wtable;
		this.mode = mode;
		filterBI = new BeanItem<PaisFiltro>(filtro);
		itemBI = new BeanItem<Pais>(new Pais());
		init();
	}

	public WPais(WPaises wtable, String mode) {
		super();
		this.wtable = wtable;
		this.mode = mode;
		filterBI = new BeanItem<PaisFiltro>(new PaisFiltro());
		itemBI = new BeanItem<Pais>(new Pais());
		init();
	}

	public void init() {

		try {

			UtilUI.confWinForm(this, "País");

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FORM

			// -----------

			numeroTXT = UtilUI.buildTXT(itemBI, "numero", "Número", false, 5,
					-1, 5, true, false, null, true);

			nombreTXT = UtilUI.buildTXT(itemBI, "nombre", "Nombre", false, 20,
					-1, 5, true, false, null, true);

			abreviaturaTXT = UtilUI.buildTXT(itemBI, "abreviatura",
					"Abreviatura", false, 20, -1, 5, true, false, null, true);

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

			content.addComponents(numeroTXT, nombreTXT, abreviaturaTXT,
					filaBotoneraHL);
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

			Pais item = queryData();
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

			numeroTXT.validate();
			nombreTXT.validate();
			abreviaturaTXT.validate();

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

				wtable.loadData();

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
	private Pais queryData() {
		try {

			System.out.println("Los filtros son "
					+ this.filterBI.getBean().toString());

			// Notification.show("Los filtros son "
			// + this.filterBI.getBean().toString());

			Pais item = mockData(this.filterBI.getBean());

			return item;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new Pais();
	}

	protected boolean insert() throws Exception {

		return true;
	}

	protected boolean update() throws Exception {

		return true;
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	private Pais mockData(PaisFiltro filtro) {

		Pais item = new Pais();

		item.setNumero(1);
		item.setNombre("Nombre " + 1);
		item.setAbreviatura("Abreviatura " + 1);

		return item;

	}

	// =================================================================================

} // END CLASS
