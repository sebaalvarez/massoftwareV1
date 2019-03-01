package com.massoftware.windows.comprobante_de_fondo;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.ex.crud.UniqueException;

import com.massoftware.model.Caja;
import com.massoftware.model.ComprobanteDeFondo;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class WComprobanteDeFondo extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	public final static String INSERT_MODE = "INSERT_MODE";
	public final static String UPDATE_MODE = "UPDATE_MODE";
	public final static String COPY_MODE = "COPY_MODE";

	private String mode;

	// -------------------------------------------------------------

	private BeanItem<ComprobanteDeFondoFiltro> filterBI;
	private BeanItem<ComprobanteDeFondo> itemBI;

	// -------------------------------------------------------------

	private Button agregarBTN;
	private Button modificarBTN;

	// -------------------------------------------------------------

	private ComboBox cajasCBX;
	private DateField aperturaDF;
	private ComboBox comprobantesCBX;
	private TextField codigoTXT;
	private DateField fechaDF;
	private TextField detalleTXT;
	private CheckBox conciliacionAutomaticaCHK;

	// -------------------------------------------------------------

	public WComprobanteDeFondo() {
		super();

		this.mode = INSERT_MODE;
		filterBI = new BeanItem<ComprobanteDeFondoFiltro>(
				new ComprobanteDeFondoFiltro());
		itemBI = new BeanItem<ComprobanteDeFondo>(new ComprobanteDeFondo());
		init();
	}

	public void init() {

		try {

			UtilUI.confWinForm(this, "Comprobante de fondo");
			this.setModal(false);

			VerticalLayout content = UtilUI.buildWinContentVertical();

			// =======================================================
			// -------------------------------------------------------
			// FORM

			// -----------

			HorizontalLayout hl = UtilUI.buildHL();

			cajasCBX = UtilUI.buildFieldCB(itemBI, "caja", "Caja", false,
					true, Caja.class, queryDataCajas());

			conciliacionAutomaticaCHK = UtilUI.buildFieldCHK(itemBI,
					"conciliacionAutomatica", "Conciliación automática", false);

			hl.addComponents(cajasCBX, conciliacionAutomaticaCHK);
			
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

			ComprobanteDeFondo item = queryData();
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

			cajasCBX.validate();
//			aperturaDF.validate();
//			comprobantesXBX.validate();
//			codigoTXT.validate();
//			fechaDF.validate();
//			detalleTXT.validate();
			conciliacionAutomaticaCHK.validate();

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
	
	private List<Caja> queryDataCajas() {
		try {

			return mockDataCajas();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<Caja>();
	}

	// metodo que realiza la consulta a la base de datos
	private ComprobanteDeFondo queryData() {
		try {

			System.out.println("Los filtros son "
					+ this.filterBI.getBean().toString());

			// Notification.show("Los filtros son "
			// + this.filterBI.getBean().toString());

			ComprobanteDeFondo item = mockData(this.filterBI.getBean());

			return item;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ComprobanteDeFondo();
	}

	protected boolean insert() throws Exception {
		
		System.out.println("Objeto a insertar " + this.itemBI.getBean());

		return true;
	}

	protected boolean update() throws Exception {
		
		System.out.println("Objeto a actualizar " + this.itemBI.getBean());

		return true;
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	private ComprobanteDeFondo mockData(ComprobanteDeFondoFiltro filtro) {

		ComprobanteDeFondo item = new ComprobanteDeFondo();

		// item.setVentas(new Date(System.currentTimeMillis()));
		// item.setStock(new Date(System.currentTimeMillis()));
		// item.setFondos(new Date(System.currentTimeMillis()));
		// item.setCompras(new Date(System.currentTimeMillis()));
		// item.setContabilidad(new Date(System.currentTimeMillis()));
		// item.setGarantiasDevoluciones(new Date(System.currentTimeMillis()));
		// item.setTambos(new Date(System.currentTimeMillis()));
		// item.setRrhh(new Date(System.currentTimeMillis()));

		return item;

	}
	
	private List<Caja> mockDataCajas() {

		List<Caja> itemsMock = new ArrayList<Caja>();

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				Caja item = new Caja();

				item.setNumero(i);
				item.setNombre("Nombre " + i);

				itemsMock.add(item);
			}
		}

		return itemsMock;
	}

	// =================================================================================

} // END CLASS
