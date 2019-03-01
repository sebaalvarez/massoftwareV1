package com.massoftware.windows.talonarios;

import com.massoftware.model.EntityId;
import com.massoftware.model.Talonario;
import com.massoftware.model.TalonarioControladorFizcal;
import com.massoftware.model.TalonarioLetra;
import com.massoftware.windows.CheckBoxEntity;
import com.massoftware.windows.DateFieldEntity;
import com.massoftware.windows.OptionGroupEntity;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WTalonario extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<Talonario> itemBI;

	// -------------------------------------------------------------

	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private OptionGroupEntity talonarioLetraOGE;
	private TextFieldEntity puntoVentaTXT;
	private CheckBoxEntity autonumeracionCHK;
	private CheckBoxEntity numeracionPreImpresaCHK;
	private CheckBoxEntity asociadoRG10098CHK;
	private OptionGroupEntity talonarioControladorFizcalOGE;
	private TextFieldEntity primerNumeroTXT;
	private TextFieldEntity proximoNumeroTXT;
	private TextFieldEntity ultimoNumeroTXT;
	private TextFieldEntity cantidadMinimaComprobantesTXT;
	private DateFieldEntity fechaDF;
	private TextFieldEntity numeroCAITXT;
	private DateFieldEntity vencimientoDF;
	private TextFieldEntity diasAvisoVencimientoTXT;

	// -------------------------------------------------------------

	public WTalonario(String mode, String id) {
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

		numeroTXT = new TextFieldEntity(this.itemBI, "numero", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		nombreTXT = new TextFieldEntity(this.itemBI, "nombre", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		TalonarioLetra talonarioLetra = new TalonarioLetra();
		talonarioLetra.setId("X");
		talonarioLetraOGE = new OptionGroupEntity(this.itemBI, "talonarioLetra", new TalonarioLetra().find(),
				true, talonarioLetra);
		// ---------------------------------------------------------------------------------------------------------
		puntoVentaTXT = new TextFieldEntity(this.itemBI, "puntoVenta", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		autonumeracionCHK = new CheckBoxEntity(this.itemBI, "autonumeracion");
		// ---------------------------------------------------------------------------------------------------------
		numeracionPreImpresaCHK = new CheckBoxEntity(this.itemBI, "numeracionPreImpresa");
		// ---------------------------------------------------------------------------------------------------------
		asociadoRG10098CHK = new CheckBoxEntity(this.itemBI, "asociadoRG10098");
		// ---------------------------------------------------------------------------------------------------------
		TalonarioControladorFizcal talonarioControladorFizcal = new TalonarioControladorFizcal();
		talonarioControladorFizcal.setId("S");
		talonarioControladorFizcalOGE = new OptionGroupEntity(this.itemBI, "talonarioControladorFizcal",
				new TalonarioControladorFizcal().find(), false, talonarioControladorFizcal);
		// ---------------------------------------------------------------------------------------------------------
		primerNumeroTXT = new TextFieldEntity(this.itemBI, "primerNumero", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		proximoNumeroTXT = new TextFieldEntity(this.itemBI, "proximoNumero", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		ultimoNumeroTXT = new TextFieldEntity(this.itemBI, "ultimoNumero", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		cantidadMinimaComprobantesTXT = new TextFieldEntity(this.itemBI, "cantidadMinimaComprobantes", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		fechaDF = new DateFieldEntity(this.itemBI, "fecha", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		numeroCAITXT = new TextFieldEntity(this.itemBI, "numeroCAI", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		vencimientoDF = new DateFieldEntity(this.itemBI, "vencimiento", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		diasAvisoVencimientoTXT = new TextFieldEntity(this.itemBI, "diasAvisoVencimiento", this.mode);
		// ---------------------------------------------------------------------------------------------------------

		HorizontalLayout fila0HL = UtilUI.buildHL();
		fila0HL.setMargin(false);
		fila0HL.addComponents(numeroTXT, nombreTXT);

		VerticalLayout fila2Columna0VL = UtilUI.buildVL();
		fila2Columna0VL.setMargin(false);
		fila2Columna0VL.addComponents(puntoVentaTXT, autonumeracionCHK, numeracionPreImpresaCHK, asociadoRG10098CHK);

		VerticalLayout fila2Columna1VL = UtilUI.buildVL();
		fila2Columna1VL.setMargin(false);
		fila2Columna1VL.addComponents(talonarioControladorFizcalOGE);

		HorizontalLayout fila2HL = UtilUI.buildHL();
		fila2HL.setMargin(false);
		fila2HL.addComponents(fila2Columna0VL, fila2Columna1VL);

		VerticalLayout fila3Columna0VL = UtilUI.buildVL();
		fila3Columna0VL.setMargin(false);
		fila3Columna0VL.addComponents(primerNumeroTXT, proximoNumeroTXT, ultimoNumeroTXT, cantidadMinimaComprobantesTXT
				);

		VerticalLayout fila3Columna1VL = UtilUI.buildVL();
		fila3Columna1VL.setMargin(false);
		fila3Columna1VL.addComponents(fechaDF, numeroCAITXT, vencimientoDF, diasAvisoVencimientoTXT);

		HorizontalLayout fila3HL = UtilUI.buildHL();
		fila3HL.setMargin(false);
		fila3HL.addComponents(fila3Columna0VL, fila3Columna1VL);

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(fila0HL, talonarioLetraOGE, fila2HL, fila3HL);

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

		((Talonario) item).setNumero(this.itemBI.getBean().maxValueInteger("numero"));
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((Talonario) obj);
	}

	protected BeanItem<Talonario> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<Talonario>(new Talonario());
		}
		return itemBI;
	}

	// =================================================================================

}
