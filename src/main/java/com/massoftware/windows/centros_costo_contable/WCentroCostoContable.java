package com.massoftware.windows.centros_costo_contable;

import java.util.List;

import com.massoftware.model.CentroCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.EntityId;
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

@SuppressWarnings("serial")
public class WCentroCostoContable extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<CentroCostoContable> itemBI;

	// -------------------------------------------------------------

	private ComboBoxEntity ejercicioContableCBX;
	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private TextFieldEntity abreviaturaTXT;

	// -------------------------------------------------------------

	// public WPuntoEquilibrio(String mode, String id) {
	// super(mode, id);
	// }

	public WCentroCostoContable(String mode, String id, EjercicioContable ejercicioContable) {

		getItemBIC().getBean().setEjercicioContable(ejercicioContable);

		init(mode, id);
	}

	protected void buildContent() throws Exception {

		confWinForm(this.itemBI.getBean().labelSingular());
		// this.setWidth(28f, Unit.EM);

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
		List<EjercicioContable> ejrcicios = new EjercicioContable().find();
		ejercicioContableCBX = new ComboBoxEntity(this.itemBI, "ejercicioContable", this.mode, ejrcicios);
		ejercicioContableCBX.addValueChangeListener(e -> {
			try {
				validateEjercicioContableAndNumero();
				validateEjercicioContableAndNombre();
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
					validateEjercicioContableAndNumero();
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
					validateEjercicioContableAndNombre();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		// ---------------------------------------------------------------------------------------------------------
		abreviaturaTXT = new TextFieldEntity(this.itemBI, "abreviatura", this.mode);
		abreviaturaTXT.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				try {
					validateEjercicioContableAndAbreviatura();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		// ---------------------------------------------------------------------------------------------------------

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(ejercicioContableCBX, numeroTXT, nombreTXT, abreviaturaTXT);

		// ---------------------------------------------------------------------------------------------------------

		return generalVL;

		// ---------------------------------------------------------------------------------------------------------
	}

	// =================================================================================

	private void validateEjercicioContableAndNumero() throws Exception {
		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueEjercicioContableAndNumero();
	}

	private void validateEjercicioContableAndNombre() throws Exception {
		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueEjercicioContableAndNombre();
	}

	private void validateEjercicioContableAndAbreviatura() throws Exception {
		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueEjercicioContableAndAbreviatura();
	}

	protected void setMaxValues(EntityId item) throws Exception {
		// Al momento de insertar o copiar a veces se necesita el maximo valor de ese
		// atributo, + 1, esto es asi para hacer una especie de numero incremental de
		// ese atributo
		// Este metodo se ejecuta despues de consultar a la base de datos el bean en
		// base a su id

		CentroCostoContable centroCostoContable = (CentroCostoContable) item;

		Integer maxValue = (Integer) centroCostoContable.maxValue(new String[] { "ejercicioContable" }, "numero");

		centroCostoContable.setNumero(maxValue);
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((CentroCostoContable) obj);
	}

	protected BeanItem<CentroCostoContable> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<CentroCostoContable>(new CentroCostoContable());
		}
		return itemBI;
	}

	protected void validateForm() throws Exception {
		validateEjercicioContableAndNumero();
		validateEjercicioContableAndNombre();
		validateEjercicioContableAndAbreviatura();
		super.validateForm();
	}

	// =================================================================================

}
