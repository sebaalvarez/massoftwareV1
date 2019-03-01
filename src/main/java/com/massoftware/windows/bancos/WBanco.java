package com.massoftware.windows.bancos;

import com.massoftware.model.Banco;
import com.massoftware.model.EntityId;
import com.massoftware.windows.CheckBoxEntity;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WBanco extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<Banco> itemBI;

	// -------------------------------------------------------------

	private TextFieldEntity numeroTXT;
	private TextFieldEntity nombreTXT;
	private TextFieldEntity cuitTXT;
	private CheckBoxEntity bloqueadoCHX;
	private TextFieldEntity hojaTXT;
	private TextFieldEntity primeraFilaTXT;
	private TextFieldEntity ultimaFilaTXT;
	private TextFieldEntity fechaTXT;
	private TextFieldEntity descripcionTXT;
	private TextFieldEntity referencia1TXT;
	private TextFieldEntity importeTXT;
	private TextFieldEntity referencia2TXT;
	private TextFieldEntity saldoTXT;

	// -------------------------------------------------------------

	public WBanco(String mode, String id) {
		super(mode, id);
	}

	protected void buildContent() throws Exception {

		confWinForm(this.itemBI.getBean().labelSingular());
		this.setWidth(28f, Unit.EM);

		// =======================================================
		// CUERPO

		TabSheet cuerpo = buildCuerpo();

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

	private TabSheet buildCuerpo() throws Exception {

		// ---------------------------------------------------------------------------------------------------------
		numeroTXT = new TextFieldEntity(this.itemBI, "numero", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		nombreTXT = new TextFieldEntity(this.itemBI, "nombre", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		cuitTXT = new TextFieldEntity(this.itemBI, "cuit", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		bloqueadoCHX = new CheckBoxEntity(this.itemBI, "bloqueado");
		// ---------------------------------------------------------------------------------------------------------
		hojaTXT = new TextFieldEntity(this.itemBI, "hoja", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		primeraFilaTXT = new TextFieldEntity(this.itemBI, "primeraFila", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		ultimaFilaTXT = new TextFieldEntity(this.itemBI, "ultimaFila", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		fechaTXT = new TextFieldEntity(this.itemBI, "fecha", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		descripcionTXT = new TextFieldEntity(this.itemBI, "descripcion", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		referencia1TXT = new TextFieldEntity(this.itemBI, "referencia1", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		importeTXT = new TextFieldEntity(this.itemBI, "importe", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		referencia2TXT = new TextFieldEntity(this.itemBI, "referencia2", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		saldoTXT = new TextFieldEntity(this.itemBI, "saldo", this.mode);
		// ---------------------------------------------------------------------------------------------------------

		HorizontalLayout formatoExtractoRow0HL = UtilUI.buildHL();
		formatoExtractoRow0HL.setMargin(false);
		formatoExtractoRow0HL.addComponents(hojaTXT, primeraFilaTXT, ultimaFilaTXT);

		HorizontalLayout formatoExtractoRow3HL = UtilUI.buildHL();
		formatoExtractoRow3HL.setMargin(false);
		formatoExtractoRow3HL.addComponents(referencia1TXT, referencia2TXT);

		HorizontalLayout formatoExtractoRow4HL = UtilUI.buildHL();
		formatoExtractoRow4HL.setMargin(false);
		formatoExtractoRow4HL.addComponents(importeTXT, saldoTXT);

		VerticalLayout formatoExtractoVL = UtilUI.buildVL();
		formatoExtractoVL.addComponents(formatoExtractoRow0HL, fechaTXT, descripcionTXT, formatoExtractoRow3HL,
				formatoExtractoRow4HL);

		VerticalLayout generalVL = UtilUI.buildVL();
		generalVL.addComponents(numeroTXT, nombreTXT, cuitTXT, bloqueadoCHX);

		TabSheet tabSheet = UtilUI.buildTS();

		tabSheet.addTab(generalVL, "General");
		tabSheet.addTab(formatoExtractoVL, "Formato extracto");

		// ---------------------------------------------------------------------------------------------------------

		return tabSheet;

		// ---------------------------------------------------------------------------------------------------------
	}

	// =================================================================================

	protected void setMaxValues(EntityId item) throws Exception {
		// Al momento de insertar o copiar a veces se necesita el maximo valor de ese
		// atributo, + 1, esto es asi para hacer una especie de numero incremental de
		// ese atributo
		// Este metodo se ejecuta despues de consultar a la base de datos el bean en
		// base a su id

		((Banco) item).setNumero(this.itemBI.getBean().maxValueInteger("numero"));
	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((Banco) obj);
	}

	protected BeanItem<Banco> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<Banco>(new Banco());
		}
		return itemBI;
	}

	// =================================================================================

}
