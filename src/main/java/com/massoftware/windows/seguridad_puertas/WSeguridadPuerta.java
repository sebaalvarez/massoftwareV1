package com.massoftware.windows.seguridad_puertas;

import java.util.List;

import com.massoftware.model.EntityId;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.windows.ComboBoxEntity;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class WSeguridadPuerta extends WindowForm {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<SeguridadPuerta> itemBI;
	private List<SeguridadModulo> modulos;

	// -------------------------------------------------------------

	private ComboBoxEntity rubroCB;
	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private TextFieldEntity equateTXT;

	// -------------------------------------------------------------

	// public WSeguridadPuerta(String mode, String id) {
	//
	// super(mode, id);
	// }

	public WSeguridadPuerta(String mode, String id, String seguridadModuloId) throws Exception {

		modulos = new SeguridadModulo().find();

		if (seguridadModuloId != null) {
			SeguridadModulo seguridadModulo = new SeguridadModulo();
			seguridadModulo.setId(seguridadModuloId);

			getItemBIC().getBean().setSeguridadModulo(seguridadModulo);

		} else {
			getItemBIC().getBean().setSeguridadModulo(modulos.get(0));
		}

		init(mode, id);
	}

	protected void buildContent() throws Exception {

		confWinForm(itemBI.getBean().labelSingular());
		this.setWidth(31f, Unit.EM);

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

	@SuppressWarnings("serial")
	private VerticalLayout buildCuerpo() throws Exception {

		// ---------------------------------------------------------------------------------------------------------
		rubroCB = new ComboBoxEntity(this.itemBI, "seguridadModulo", this.mode, modulos);

		// rubroCB = UtilUI.buildFieldCB(itemBI, "cuentaFondoRubro", false, false,
		// CuentaFondoRubro.class,
		// new CuentaFondoRubro().find());
		rubroCB.addValueChangeListener(e -> {
			try {
				validateModuloAndNumero();
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
					validateModuloAndNumero();
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
					validateModuloAndNombre();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		// ---------------------------------------------------------------------------------------------------------
		equateTXT = new TextFieldEntity(this.itemBI, "equate", this.mode);

		// ---------------------------------------------------------------------------------------------------------

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(rubroCB, numeroTXT, nombreTXT, equateTXT);

		return generalVL;
	}

	// =================================================================================

	protected void setMaxValues(EntityId item) throws Exception {

		((SeguridadPuerta) item).setNumero((Integer) this.itemBI.getBean().maxValue(new String[] { "seguridadModulo" }, "numero"));
	}

	protected void setBean(EntityId obj) throws Exception {

		itemBI.setBean((SeguridadPuerta) obj);
	}

	protected BeanItem<SeguridadPuerta> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio

		if (itemBI == null) {
			itemBI = new BeanItem<SeguridadPuerta>(new SeguridadPuerta());
		}
		return itemBI;
	}

	// -----------------------------------------------------------------------------------

	private void validateModuloAndNumero() throws Exception {

		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueModuloAndNumero();
	}

	private void validateModuloAndNombre() throws Exception {

		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueRubroAndNombre();
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	// metodo que realiza la consulta a la base de datos
	protected SeguridadPuerta queryData() {
		try {

			SeguridadPuerta item = new SeguridadPuerta();
			item.loadById(id);
			if (COPY_MODE.equals(mode)) {
				item.setId(null);
			}

			return item;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new SeguridadPuerta();
	}

	// =================================================================================

	protected Object insert() throws Exception {

		try {
			itemBI.getBean().insert();
			if (windowListado != null) {
				((WSeguridadPuertas) windowListado).loadDataResetPagedTree(itemBI.getBean().getSeguridadModulo());
				windowListado.loadDataResetPaged();
			}

			return itemBI.getBean();

		} catch (Exception e) {
			LogAndNotification.print(e);
			return null;
		}
	}

	protected Object update() throws Exception {

		try {
			itemBI.getBean().update();
			if (windowListado != null) {
				((WSeguridadPuertas) windowListado).loadDataResetPagedTree(itemBI.getBean().getSeguridadModulo());
				windowListado.loadDataResetPaged();
			}

			return itemBI.getBean();

		} catch (Exception e) {
			LogAndNotification.print(e);
			return null;
		}
	}

	protected void validateForm() throws Exception {
		validateModuloAndNumero();
		validateModuloAndNombre();
		super.validateForm();
	}

}
