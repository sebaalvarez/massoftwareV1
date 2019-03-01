package com.massoftware.windows.bloqueos_cliente;

import com.massoftware.model.EntityId;
import com.massoftware.model.BloqueoCliente;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;



@SuppressWarnings("serial")
public class WBloqueoCliente extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<BloqueoCliente> itemBI;

	// -------------------------------------------------------------

	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private WCClasificacionClientesSB clasifClieSB;


	// -------------------------------------------------------------

	public WBloqueoCliente(String mode, String id) {
		super(mode, id);
		clasifClieSB.setSelectedItem(itemBI.getBean().getClasifCliente());
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

		// --------------------------------------------------------

				this.addShortcutListener(new ShortcutListener("ENTER", KeyCode.ENTER, new int[] {}) {

					private static final long serialVersionUID = 1L;

					@Override
					public void handleAction(Object sender, Object target) {

						try {

							if (target instanceof TextField && ((TextField) target).getParent().equals(clasifClieSB)) {
								clasifClieSB.blur();
							}

						} catch (Exception e) {
							LogAndNotification.print(e);
						}

					}
				});
		
		// ---------------------------------------------------------------------------------------------------------
		numeroTXT = new TextFieldEntity(this.itemBI, "motivoBloqueo", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		nombreTXT = new TextFieldEntity(this.itemBI, "detalle", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		clasifClieSB = new WCClasificacionClientesSB(this.itemBI);
		// ---------------------------------------------------------------------------------------------------------

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(numeroTXT, nombreTXT, clasifClieSB);

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

		itemBI.getBean().setMotivoBloqueo(this.itemBI.getBean().maxValue("motivoBloqueo"));
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((BloqueoCliente) obj);
	}

	protected BeanItem<BloqueoCliente> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<BloqueoCliente>(new BloqueoCliente());
		}
		return itemBI;
	}



	// =================================================================================

}
