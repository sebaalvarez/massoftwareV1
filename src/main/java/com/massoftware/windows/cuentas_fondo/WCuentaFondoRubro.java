package com.massoftware.windows.cuentas_fondo;

import com.massoftware.model.CuentaFondoRubro;
import com.massoftware.model.EntityId;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class WCuentaFondoRubro extends WindowForm {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<CuentaFondoRubro> itemBI;

	// -------------------------------------------------------------

	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;

	// -------------------------------------------------------------

	public WCuentaFondoRubro(String mode, String id) {
		super(mode, id);
	}

	protected void buildContent() throws Exception {

		confWinForm(this.itemBI.getBean().labelSingular());
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

	private VerticalLayout buildCuerpo() throws Exception {

		// ---------------------------------------------------------------------------------------------------------
		numeroTXT = new TextFieldEntity(this.itemBI, "numero", this.mode);		
		// ---------------------------------------------------------------------------------------------------------
		nombreTXT = new TextFieldEntity(this.itemBI, "nombre", this.mode);		
		// ---------------------------------------------------------------------------------------------------------

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(numeroTXT, nombreTXT);

		return generalVL;
	}

	// =================================================================================

	protected void setMaxValues(EntityId item) throws Exception {
		((CuentaFondoRubro) item).setNumero((Integer) this.itemBI.getBean().maxValueInteger("numero"));
	}

	protected void setBean(EntityId obj) throws Exception {

		itemBI.setBean((CuentaFondoRubro) obj);
	}

	protected BeanItem<CuentaFondoRubro> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio

		if (itemBI == null) {
			itemBI = new BeanItem<CuentaFondoRubro>(new CuentaFondoRubro());
		}
		return itemBI;
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	// metodo que realiza la consulta a la base de datos
	protected CuentaFondoRubro queryData() {
		try {

			CuentaFondoRubro item = new CuentaFondoRubro();
			item.loadById(id);
			if (COPY_MODE.equals(mode)) {
				item.setId(null);
			}

			return item;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new CuentaFondoRubro();
	}

	// =================================================================================

	protected Object insert() throws Exception {

		try {
			itemBI.getBean().insert();
			if (windowListado != null) {
				windowListado.loadDataResetPaged();
				((WCuentasFondo) windowListado).loadDataResetPagedTree(itemBI.getBean(), null);
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
				windowListado.loadDataResetPaged();
				((WCuentasFondo) windowListado).loadDataResetPagedTree(itemBI.getBean(), null);
			}

			return itemBI.getBean();

		} catch (Exception e) {
			LogAndNotification.print(e);
			return null;
		}
	}

}
