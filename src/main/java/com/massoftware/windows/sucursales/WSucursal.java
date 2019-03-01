package com.massoftware.windows.sucursales;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.model.EntityId;
import com.massoftware.model.Sucursal;
import com.massoftware.model.SucursalTipo;
import com.massoftware.windows.CheckBoxEntity;
import com.massoftware.windows.ComboBoxEntity;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WSucursal extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<Sucursal> itemBI;

	// -------------------------------------------------------------
	private ComboBoxEntity sucursalTipoCBX;
	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private TextFieldEntity abreviaturaTXT;
	// ----------------------------------------------------------
	private TextFieldEntity cuentaClienteDesdeTXT;
	private TextFieldEntity cuentaClienteHasaTXT;
	private ComboBoxEntity cantidadCaracteresClienteCBX;
	private CheckBoxEntity identificacionNumericaClienteCHX;
	private CheckBoxEntity permiteCambiarClienteCHX;
	// ----------------------------------------------------------
	private TextFieldEntity clientesOcacionalesDesdeTXT;
	private TextFieldEntity clientesOcacionalesHasaTXT;
	// ----------------------------------------------------------
	private TextFieldEntity nroCobranzaDesdeTXT;
	private TextFieldEntity nroCobranzaHasaTXT;
	// ----------------------------------------------------------
	private TextFieldEntity proveedoresDesdeTXT;
	private TextFieldEntity proveedoresHasaTXT;
	private ComboBoxEntity cantidadCaracteresProveedorCBX;
	private CheckBoxEntity identificacionNumericaProveedorCHX;
	private CheckBoxEntity permiteCambiarProveedorCHX;

	// -------------------------------------------------------------

	public WSucursal(String mode, String id) {
		super(mode, id);
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
		sucursalTipoCBX = new ComboBoxEntity(this.itemBI, "sucursalTipo", this.mode, new SucursalTipo().find());
		// ---------------------------------------------------------------------------------------------------------
		numeroTXT = new TextFieldEntity(this.itemBI, "numero", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		nombreTXT = new TextFieldEntity(this.itemBI, "nombre", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		abreviaturaTXT = new TextFieldEntity(this.itemBI, "abreviatura", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		cuentaClienteDesdeTXT = new TextFieldEntity(this.itemBI, "cuentaClienteDesde", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		cuentaClienteHasaTXT = new TextFieldEntity(this.itemBI, "cuentaClienteHasa", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		List<Integer> cantidadCaracteresCliente = new ArrayList<Integer>();
		cantidadCaracteresCliente.add(3);
		cantidadCaracteresCliente.add(4);
		cantidadCaracteresCliente.add(5);
		cantidadCaracteresCliente.add(6);
		cantidadCaracteresClienteCBX = new ComboBoxEntity(this.itemBI, "cantidadCaracteresCliente", this.mode,
				cantidadCaracteresCliente, 6);
		// ---------------------------------------------------------------------------------------------------------
		identificacionNumericaClienteCHX = new CheckBoxEntity(this.itemBI, "identificacionNumericaCliente");
		// ---------------------------------------------------------------------------------------------------------
		permiteCambiarClienteCHX = new CheckBoxEntity(this.itemBI, "permiteCambiarCliente");
		// ---------------------------------------------------------------------------------------------------------
		clientesOcacionalesDesdeTXT = new TextFieldEntity(this.itemBI, "clientesOcacionalesDesde", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		clientesOcacionalesHasaTXT = new TextFieldEntity(this.itemBI, "clientesOcacionalesHasa", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		nroCobranzaDesdeTXT = new TextFieldEntity(this.itemBI, "nroCobranzaDesde", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		nroCobranzaHasaTXT = new TextFieldEntity(this.itemBI, "nroCobranzaHasa", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		proveedoresDesdeTXT = new TextFieldEntity(this.itemBI, "proveedoresDesde", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		proveedoresHasaTXT = new TextFieldEntity(this.itemBI, "proveedoresHasa", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		List<Integer> cantidadCaracteresProveedor = new ArrayList<Integer>();
		cantidadCaracteresProveedor.add(3);
		cantidadCaracteresProveedor.add(4);
		cantidadCaracteresProveedor.add(5);
		cantidadCaracteresProveedor.add(6);
		cantidadCaracteresProveedorCBX = new ComboBoxEntity(this.itemBI, "cantidadCaracteresProveedor", this.mode,
				cantidadCaracteresProveedor, 6);
		// ---------------------------------------------------------------------------------------------------------
		identificacionNumericaProveedorCHX = new CheckBoxEntity(this.itemBI, "identificacionNumericaProveedor");
		// ---------------------------------------------------------------------------------------------------------
		permiteCambiarProveedorCHX = new CheckBoxEntity(this.itemBI, "permiteCambiarProveedor");
		// ---------------------------------------------------------------------------------------------------------

		HorizontalLayout cabeceraHL = UtilUI.buildHL();
		cabeceraHL.setMargin(false);		
		cabeceraHL.addComponents(numeroTXT, abreviaturaTXT, nombreTXT,sucursalTipoCBX);

		Label label1 = new Label("Cuenta clientes:");
		label1.addStyleName("v-labeltext");

		Label label2 = new Label("Clientes ocacionales:");
		label2.addStyleName("v-labeltext");

		Label label3 = new Label("Nº cobranza:");
		label3.addStyleName("v-labeltext");

		Label label4 = new Label("Proveedores:");
		label4.addStyleName("v-labeltext");

		Label label5 = new Label("Desde");
		label5.addStyleName("v-labeltext");

		Label label6 = new Label("Hasta");
		label6.addStyleName("v-labeltext");

		Label label7 = new Label("");
		label7.addStyleName("v-labeltext");

		Label label8 = new Label("Cantidad de caracteres");
		label8.addStyleName("v-labeltext");

		Label label9 = new Label("");
		label9.addStyleName("v-labeltext");

		Label label10 = new Label("");
		label10.addStyleName("v-labeltext");

		Label label11 = new Label("Identificación numérica");
		label11.addStyleName("v-labeltext");

		Label label12 = new Label("");
		label12.addStyleName("v-labeltext");

		Label label13 = new Label("");
		label13.addStyleName("v-labeltext");

		Label label14 = new Label("Permite cambiar");
		label14.addStyleName("v-labeltext");

		Label label15 = new Label("");
		label15.addStyleName("v-labeltext");

		Label label16 = new Label("");
		label16.addStyleName("v-labeltext");

		VerticalLayout columnalVL = UtilUI.buildVL();
		columnalVL.setMargin(false);
		columnalVL.addComponents(label7, label1, label2, label3, label4);

		VerticalLayout columna2VL = UtilUI.buildVL();
		columna2VL.setMargin(false);
		columna2VL.addComponents(label5, cuentaClienteDesdeTXT, clientesOcacionalesDesdeTXT, nroCobranzaDesdeTXT,
				proveedoresDesdeTXT);
		columna2VL.setComponentAlignment(label5, Alignment.BOTTOM_LEFT);

		VerticalLayout columna3VL = UtilUI.buildVL();
		columna3VL.setMargin(false);
		columna3VL.addComponents(label6, cuentaClienteHasaTXT, clientesOcacionalesHasaTXT, nroCobranzaHasaTXT,
				proveedoresHasaTXT);
		columna3VL.setComponentAlignment(label6, Alignment.BOTTOM_LEFT);

		VerticalLayout columna4VL = UtilUI.buildVL();
		columna4VL.setMargin(false);
		columna4VL.addComponents(label8, cantidadCaracteresClienteCBX, label9, label10, cantidadCaracteresProveedorCBX);
		columna4VL.setComponentAlignment(label8, Alignment.BOTTOM_LEFT);
		columna4VL.setComponentAlignment(cantidadCaracteresClienteCBX, Alignment.MIDDLE_CENTER);
		columna4VL.setComponentAlignment(cantidadCaracteresProveedorCBX, Alignment.MIDDLE_CENTER);

		VerticalLayout columna5VL = UtilUI.buildVL();
		columna5VL.setMargin(false);
		columna5VL.addComponents(label11, identificacionNumericaClienteCHX, label12, label13,
				identificacionNumericaProveedorCHX);
		columna5VL.setComponentAlignment(identificacionNumericaClienteCHX, Alignment.MIDDLE_CENTER);
		columna5VL.setComponentAlignment(identificacionNumericaProveedorCHX, Alignment.MIDDLE_CENTER);
		columna5VL.setComponentAlignment(label11, Alignment.BOTTOM_LEFT);

		VerticalLayout columna6VL = UtilUI.buildVL();
		columna6VL.setMargin(false);
		columna6VL.addComponents(label14, permiteCambiarClienteCHX, label15, label16, permiteCambiarProveedorCHX);
		columna6VL.setComponentAlignment(permiteCambiarClienteCHX, Alignment.MIDDLE_CENTER);
		columna6VL.setComponentAlignment(permiteCambiarProveedorCHX, Alignment.MIDDLE_CENTER);
		columna6VL.setComponentAlignment(label14, Alignment.BOTTOM_LEFT);

		HorizontalLayout cuerpoHL = UtilUI.buildHL();
		cuerpoHL.setMargin(false);
		cuerpoHL.addComponents(columnalVL, columna2VL, columna3VL, columna4VL, columna5VL, columna6VL);

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(cabeceraHL, cuerpoHL);

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

		((Sucursal) item).setNumero(this.itemBI.getBean().maxValueInteger("numero"));
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((Sucursal) obj);
	}

	protected BeanItem<Sucursal> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<Sucursal>(new Sucursal());
		}
		return itemBI;
	}

	// =================================================================================

}
