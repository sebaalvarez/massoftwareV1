package com.massoftware.windows.cuentas_contable;

import java.util.Iterator;
import java.util.List;

import com.massoftware.model.CentroCostoContable;
import com.massoftware.model.CentrosCostoContableFiltro;
import com.massoftware.model.CostoVenta;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableEstado;
import com.massoftware.model.CuentasContableFiltro;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.EntityId;
import com.massoftware.model.PuntoEquilibrio;
import com.massoftware.model.PuntosEquilibrioFiltro;
import com.massoftware.windows.CheckBoxEntity;
import com.massoftware.windows.ComboBoxEntity;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.OptionGroupEntity;
import com.massoftware.windows.TextFieldEntity;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.WindowForm;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WCuentaContable extends WindowForm {

	// -------------------------------------------------------------

	private BeanItem<CuentaContable> itemBI;

	// -------------------------------------------------------------
	private ComboBoxEntity ejercicioContableCBX;
	private TextFieldEntity integraTXT;
	private TextFieldEntity cuentaJerarquiaTXT;
	private TextFieldEntity codigoTXT;
	private TextFieldEntity nombreTXT;
	// --------------------------------------------------------------------------
	private CheckBoxEntity imputableCHK;
	private CheckBoxEntity ajustaPorInflacionCHK;
	private OptionGroupEntity cuentaContableEstadoOG;
	private CheckBoxEntity cuentaConApropiacionCHK;
	// --------------------------------------------------------------------------
	private ComboBoxEntity centroCostoContableCBX;
	private TextFieldEntity cuentaAgrupadoraTXT;
	private TextFieldEntity porcentajeTXT;
	private ComboBoxEntity puntoEquilibrioCBX;
	private OptionGroupEntity costoVentaOG;
	private WCPuertaSB seguridadPuertaSB;
	// -------------------------------------------------------------

	// -------------------------------------------------------------

	public WCuentaContable(String mode, String id, EjercicioContable ejercicioContable, String integra) {

		getItemBIC().getBean().setEjercicioContable(ejercicioContable);

		if (INSERT_MODE.equals(mode)) {
			getItemBIC().getBean().setIntegra(integra);
		}

		init(mode, id);
		
		seguridadPuertaSB.setSelectedItem(itemBI.getBean().getSeguridadPuerta());
		
//		cuentaJerarquiaTXT.addTextChangeListener(new TextChangeListener() {
//
//			public void textChange(TextChangeEvent event) {
//				cuentaJerarquiaTXTTextChange(event.getText());
//			}
//		});
//		cuentaJerarquiaTXT.addValueChangeListener(new Property.ValueChangeListener() {
//			public void valueChange(ValueChangeEvent event) {
//				cuentaJerarquiaTXTTextChange((String) event.getProperty().getValue());
//			}
//		});
	}

	protected void buildContent() throws Exception {

		confWinForm(this.itemBI.getBean().labelSingular());
		this.setWidth(35f, Unit.EM);

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
		ejercicioContableCBX = new ComboBoxEntity(this.itemBI, "ejercicioContable", this.mode,
				new EjercicioContable().find());
		ejercicioContableCBX.addValueChangeListener(e -> {
			try {
				validateEjercicioContableAndCuentaJerarquia();
				validateEjercicioContableAndCodigo();
			} catch (Exception e1) {
				LogAndNotification.print(e1);
			}
		});
		// ---------------------------------------------------------------------------------------------------------
		integraTXT = new TextFieldEntity(this.itemBI, "integra", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		cuentaJerarquiaTXT = new TextFieldEntity(this.itemBI, "cuentaJerarquia", this.mode);
		
		cuentaJerarquiaTXT.addValidator(new CuentaJerarquiaValidator(integraTXT, String.class, "cuentaJerarquia", this.itemBI));
		
		cuentaJerarquiaTXT.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				try {
					validateEjercicioContableAndCuentaJerarquia();					
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		cuentaJerarquiaTXT.addBlurListener(e -> {
			try {
				cuentaJerarquiaTXTTextChange(cuentaJerarquiaTXT.getValue());
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});
		// ---------------------------------------------------------------------------------------------------------
		codigoTXT = new TextFieldEntity(this.itemBI, "codigo", this.mode);
		codigoTXT.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				try {
					validateEjercicioContableAndCodigo();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});
		// ---------------------------------------------------------------------------------------------------------
		nombreTXT = new TextFieldEntity(this.itemBI, "nombre", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		imputableCHK = new CheckBoxEntity(this.itemBI, "imputable");
		// ---------------------------------------------------------------------------------------------------------
		ajustaPorInflacionCHK = new CheckBoxEntity(this.itemBI, "ajustaPorInflacion");
		// ---------------------------------------------------------------------------------------------------------
		List<CuentaContableEstado> estados = new CuentaContableEstado().find();
		cuentaContableEstadoOG = new OptionGroupEntity(this.itemBI, "cuentaContableEstado", estados, false,
				estados.get(1));
		// ---------------------------------------------------------------------------------------------------------
		cuentaConApropiacionCHK = new CheckBoxEntity(this.itemBI, "cuentaConApropiacion");
		// ---------------------------------------------------------------------------------------------------------
		CentrosCostoContableFiltro centroCostoContableFiltro = new CentrosCostoContableFiltro();
		centroCostoContableFiltro.setEjercicioContable(itemBI.getBean().getEjercicioContable());

		centroCostoContableCBX = new ComboBoxEntity(this.itemBI, "centroCostoContable", this.mode,
				new CentroCostoContable().find(centroCostoContableFiltro));
		// ---------------------------------------------------------------------------------------------------------
		cuentaAgrupadoraTXT = new TextFieldEntity(this.itemBI, "cuentaAgrupadora", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		porcentajeTXT = new TextFieldEntity(this.itemBI, "porcentaje", this.mode);
		// ---------------------------------------------------------------------------------------------------------
		PuntosEquilibrioFiltro puntosEquilibrioFiltro = new PuntosEquilibrioFiltro();
		puntosEquilibrioFiltro.setEjercicioContable(itemBI.getBean().getEjercicioContable());

		puntoEquilibrioCBX = new ComboBoxEntity(this.itemBI, "puntoEquilibrio", this.mode,
				new PuntoEquilibrio().find(puntosEquilibrioFiltro));
		// ---------------------------------------------------------------------------------------------------------
		List<CostoVenta> costosVenta = new CostoVenta().find();
		costoVentaOG = new OptionGroupEntity(this.itemBI, "costoVenta", costosVenta, true, costosVenta.get(0));
		// ---------------------------------------------------------------------------------------------------------
		seguridadPuertaSB = new WCPuertaSB(this);
		// ---------------------------------------------------------------------------------------------------------

		// ---------------------------------------------------------------------------------------------------------

		//
		// HorizontalLayout formatoExtractoRow3HL = UtilUI.buildHL();
		// formatoExtractoRow3HL.setMargin(false);
		// formatoExtractoRow3HL.addComponents(referencia1TXT, referencia2TXT);
		//
		// HorizontalLayout formatoExtractoRow4HL = UtilUI.buildHL();
		// formatoExtractoRow4HL.setMargin(false);
		// formatoExtractoRow4HL.addComponents(importeTXT, saldoTXT);
		//
		// VerticalLayout formatoExtractoVL = UtilUI.buildVL();
		// formatoExtractoVL.addComponents(formatoExtractoRow0HL, fechaTXT,
		// descripcionTXT, formatoExtractoRow3HL,
		// formatoExtractoRow4HL);

		VerticalLayout generalAVL = UtilUI.buildVL();
		generalAVL.addComponents(ejercicioContableCBX, nombreTXT, integraTXT, cuentaJerarquiaTXT, codigoTXT, nombreTXT);

		VerticalLayout generalBVL = UtilUI.buildVL();
		generalBVL.addComponents(imputableCHK, ajustaPorInflacionCHK, cuentaConApropiacionCHK, cuentaContableEstadoOG);

		HorizontalLayout generalHL = UtilUI.buildHL();
		generalHL.setMargin(false);
		generalHL.addComponents(generalAVL, generalBVL);

		VerticalLayout centroCostoAVL = UtilUI.buildVL();
		centroCostoAVL.setMargin(false);
		centroCostoAVL.addComponents(centroCostoContableCBX, cuentaAgrupadoraTXT);

		VerticalLayout centroCostoBVL = UtilUI.buildVL();
		centroCostoBVL.setMargin(false);
		centroCostoBVL.addComponents(puntoEquilibrioCBX, porcentajeTXT);

		HorizontalLayout centroCostoHL = UtilUI.buildHL();
		centroCostoHL.setMargin(false);
		centroCostoHL.addComponents(centroCostoAVL, centroCostoBVL);

		VerticalLayout centroCostoVL = UtilUI.buildVL();
		// centroCostoVL.setMargin(false);
		centroCostoVL.addComponents(centroCostoHL, costoVentaOG);
		
		VerticalLayout seguridadVL = UtilUI.buildVL();
		// centroCostoVL.setMargin(false);
		seguridadVL.addComponents(seguridadPuertaSB);

		TabSheet tabSheet = UtilUI.buildTS();

		tabSheet.addTab(generalHL, "General");
		tabSheet.addTab(centroCostoVL, "Centro de costo");
		tabSheet.addTab(seguridadVL, "Seguridad");

		// ---------------------------------------------------------------------------------------------------------

		return tabSheet;

		// ---------------------------------------------------------------------------------------------------------
	}

	// =================================================================================

	private void validateEjercicioContableAndCuentaJerarquia() throws Exception {
		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueEjercicioContableAndCuentaJerarquia();
	}

	private void validateEjercicioContableAndCodigo() throws Exception {
		if (this.itemBI.getBean()._originalDTO != null && COPY_MODE.equals(mode)) {
			this.itemBI.getBean()._originalDTO.setterNull();
		}

		this.itemBI.getBean().checkUniqueEjercicioContableAndCodigo();
	}

	protected void setMaxValues(EntityId item) throws Exception {
		// Al momento de insertar o copiar a veces se necesita el maximo valor de ese
		// atributo, + 1, esto es asi para hacer una especie de numero incremental de
		// ese atributo
		// Este metodo se ejecuta despues de consultar a la base de datos el bean en
		// base a su id

	}

	protected void setBean(EntityId obj) throws Exception {

		// se utiliza para asignarle o cambiar el bean al contenedor del formulario

		itemBI.setBean((CuentaContable) obj);
	}

	protected BeanItem<CuentaContable> getItemBIC() {

		// -----------------------------------------------------------------
		// Crea el Container del form, en base a al bean que queremos usar, y ademas
		// carga el form con un bean vacio
		// como este metodo se llama muchas veces, inicializar el contenedor una sola
		// vez

		if (itemBI == null) {
			itemBI = new BeanItem<CuentaContable>(new CuentaContable());
		}
		return itemBI;
	}

	protected void validateForm() throws Exception {
		validateEjercicioContableAndCuentaJerarquia();
		validateEjercicioContableAndCodigo();
		super.validateForm();
	}

	protected Object copy() throws Exception {

		try {
			itemBI.getBean().insert();
			if (windowListado != null) {
				windowListado.loadDataResetPaged();
				// ((WCuentasFondo) windowListado).loadDataResetPagedTree(itemBI.getBean(),
				// null);

				Tree tree = ((WCuentasContable) windowListado).tree;

				if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {

					CuentaContable itemRama = (CuentaContable) tree.getValue();

					CuentasContableFiltro filtro = new CuentasContableFiltro();
					filtro.setEjercicioContable(itemBI.getBean().getEjercicioContable());
					filtro.setIntegra(itemBI.getBean().getIntegra());

					// itemRama.setNombre(itemBI.getBean().getNombre());
					// itemRama.setCuentaJerarquia(itemBI.getBean().getCuentaJerarquia());

					((WCuentasContable) windowListado).addCuentasContablesTree(filtro, itemRama);
				}

			}

			return itemBI.getBean();

		} catch (Exception e) {
			LogAndNotification.print(e);
			return null;
		}
	}

	protected Object insert() throws Exception {

		try {
			itemBI.getBean().insert();
			if (windowListado != null) {
				windowListado.loadDataResetPaged();
				// ((WCuentasFondo) windowListado).loadDataResetPagedTree(itemBI.getBean(),
				// null);

				Tree tree = ((WCuentasContable) windowListado).tree;

				if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {

					CuentaContable itemRama = (CuentaContable) tree.getValue();

					CuentasContableFiltro filtro = new CuentasContableFiltro();
					filtro.setEjercicioContable(itemBI.getBean().getEjercicioContable());
					filtro.setIntegra(itemBI.getBean().getIntegra());

					((WCuentasContable) windowListado).addCuentasContablesTree(filtro, itemRama);
				}

			}

			return itemBI.getBean();

		} catch (Exception e) {
			LogAndNotification.print(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected Object update() throws Exception {

		try {
			itemBI.getBean().update();
			if (windowListado != null) {
				windowListado.loadDataResetPaged();

				CuentaContable cuentaContable = itemBI.getBean();

				Tree tree = ((WCuentasContable) windowListado).tree;

				if (tree.getValue() != null && tree.getValue() instanceof CuentaContable) {

					CuentaContable itemRama = (CuentaContable) tree.getValue();

					// CuentasContableFiltro filtro = new CuentasContableFiltro();
					// filtro.setEjercicioContable(itemBI.getBean().getEjercicioContable());
					// filtro.setIntegra(itemBI.getBean().getIntegra());
					//
					// ((WCuentasContable) windowListado).addCuentasContablesTree(filtro, itemRama);

					if (itemRama.equals(cuentaContable)) {

						itemRama.setNombre(cuentaContable.getNombre());
						itemRama.setCuentaJerarquia(cuentaContable.getCuentaJerarquia());
						tree.setItemCaption(itemRama,
								cuentaContable.getCuentaJerarquia() + " " + cuentaContable.getNombre());

					} else { // -------------

						Iterator<CuentaContable> iterator = (Iterator<CuentaContable>) tree.getChildren(itemRama)
								.iterator();

						while (iterator.hasNext()) {

							CuentaContable itemHijaDeLaRama = iterator.next();

							if (itemHijaDeLaRama.equals(cuentaContable)) {

								itemHijaDeLaRama.setNombre(cuentaContable.getNombre());
								itemHijaDeLaRama.setCuentaJerarquia(cuentaContable.getCuentaJerarquia());
								tree.setItemCaption(itemHijaDeLaRama,
										((WCuentasContable) windowListado).format(cuentaContable.getCuentaJerarquia())
												+ " " + cuentaContable.getNombre());
							}

						}

					} // -------------

				}

				// ((WCuentasContable) windowListado).loadDataResetPagedTree(itemBI.getBean(),
				// null);
			}

			return itemBI.getBean();

		} catch (Exception e) {
			LogAndNotification.print(e);
			return null;
		}
	}

	protected void cuentaJerarquiaTXTTextChange(String value) {
		try {
			if (value == null) {
				return;
			}
			value = value.trim();
			String value2 = null;
			if (value.length() == 1) {								
				value2 = value.substring(0, 1) + "0000000000";				
			} else if (value.length() == 2) {
				value2 = value.substring(0, 2) + "000000000";
			} else if (value.length() == 3) {
				value2 = value.substring(0, 3) + "00000000";
			} else if (value.length() == 4) {
				value2 = value.substring(0, 4) + "0000000";
			} else if (value.length() == 5) {
				value2 = value.substring(0, 5) + "000000";
			} else if (value.length() == 6) {
				value2 = value.substring(0, 6) + "00000";
			} else if (value.length() == 7) {
				value2 = value.substring(0, 7) + "0000";
			} else if (value.length() == 8) {
				value2 = value.substring(0, 8) + "000";
			} else if (value.length() == 9) {
				value2 = value.substring(0, 9) + "00";
			} else if (value.length() == 10) {
				value2 = value.substring(0, 10) + "0";
			} else if (value.length() == 11) {
				value2 = value;
			} else {
				value2 = null;
			}

//			char[] integraChars = integraTXT.getValue().toCharArray();
//			
//			for(int i = integraChars.length -1; i > 0; i--) {
//				if(integraChars[i] != '0') {
//					break;
//				} else {				
//					integraChars[i] = 'X';
//				}
//			}
//			
//			String prefix = new String(integraChars).replace("X", "").trim();
//			
//			if(value2.startsWith(prefix) == false) {
//				LogAndNotification.print(new Exception(prefix));
//			}
			
			
			cuentaJerarquiaTXT.setValue(value2);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	// =================================================================================

}
