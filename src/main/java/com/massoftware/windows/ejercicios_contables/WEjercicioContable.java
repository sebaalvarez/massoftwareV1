package com.massoftware.windows.ejercicios_contables;

import com.massoftware.model.EjercicioContable;
import com.massoftware.model.EntityId;
import com.massoftware.windows.CheckBoxEntity;
import com.massoftware.windows.DateFieldEntity;
import com.massoftware.windows.TextAreaEntity;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WEjercicioContable extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<EjercicioContable> itemBI;

	// -------------------------------------------------------------

	private TextFieldEntity numeroTXT;
	private DateFieldEntity aperturaTXT;
	private DateFieldEntity cierreTXT;
	private CheckBoxEntity cerradoCHX;
	private CheckBoxEntity cerradoModulosCHX;
	private TextAreaEntity comentarioTXT;

	// -------------------------------------------------------------

	public WEjercicioContable(String mode, String id) {
		super(mode, id);
	}

	protected void buildContent() throws Exception {

		confWinForm(this.itemBI.getBean().labelSingular());
		this.setWidth(28f, Unit.EM);

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
		aperturaTXT = new DateFieldEntity(this.itemBI, "apertura", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		cierreTXT = new DateFieldEntity(this.itemBI, "cierre", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		cerradoCHX = new CheckBoxEntity(this.itemBI, "cerrado");
		// ---------------------------------------------------------------------------------------------------------
		cerradoModulosCHX = new CheckBoxEntity(this.itemBI, "cerradoModulos");
		// ---------------------------------------------------------------------------------------------------------
		comentarioTXT = new TextAreaEntity(this.itemBI, "comentario", this.mode, 4);
		comentarioTXT.setWidth("100%");
		// ---------------------------------------------------------------------------------------------------------

		HorizontalLayout fechasHL = UtilUI.buildHL();
		fechasHL.setMargin(false);
		fechasHL.addComponents(aperturaTXT, cierreTXT);
		
		HorizontalLayout cerradoHL = UtilUI.buildHL();
		cerradoHL.setMargin(false);
		cerradoHL.addComponents(cerradoCHX, cerradoModulosCHX);
		
		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(numeroTXT, fechasHL, cerradoHL, comentarioTXT);

		// ---------------------------------------------------------------------------------------------------------

		return generalVL;

		// ---------------------------------------------------------------------------------------------------------
	}

	// =================================================================================

	protected void setMaxValues(EntityId item) throws Exception {
		// Al momento de insertar o copiar a veces se necesita el maximo valor de ese
		// atributo, + 1, esto es asi para hacer una especie de numero incremental de
		// ese atributo
		// Este metodo se ejecuta despues de consultar a la base de datos el bean en
		// base a su id

//		itemBI.getBean().setNumero(this.itemBI.getBean().maxValueInteger("numero"));		
		((EjercicioContable) item).loadByMaxEjercicioContable();
		
		
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((EjercicioContable) obj);
	}

	protected BeanItem<EjercicioContable> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<EjercicioContable>(new EjercicioContable());
		}
		return itemBI;
	}

	// =================================================================================

}
