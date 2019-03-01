package com.massoftware.windows.clasificaciones_clientes;

import com.massoftware.model.EntityId;
import com.massoftware.model.ClasificacionCliente;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WClasificacionCliente extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<ClasificacionCliente> itemBI;

	// -------------------------------------------------------------

	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private ColorPicker colorPCK;
	// -------------------------------------------------------------

	public WClasificacionCliente(String mode, String id) {
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
		nombreTXT = new TextFieldEntity(this.itemBI, "nombre", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		colorPCK = new ColorPicker();
		colorPCK.setSwatchesVisibility(false);
		colorPCK.setHistoryVisibility(false);
		colorPCK.setTextfieldVisibility(false);
		colorPCK.setHSVVisibility(false);
		
		colorPCK.addColorChangeListener(event -> Notification.show(
                "Color changed: " + event.getColor().getRGB(),Type.TRAY_NOTIFICATION));
		
		colorPCK.getColor().getRGB();
		colorPCK.setColor(new Color(255));
        

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(numeroTXT, nombreTXT, colorPCK);

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

		itemBI.getBean().setNumero(this.itemBI.getBean().maxValueInteger("numero"));
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((ClasificacionCliente) obj);
	}

	protected BeanItem<ClasificacionCliente> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<ClasificacionCliente>(new ClasificacionCliente());
		}
		return itemBI;
	}



	// =================================================================================

}
