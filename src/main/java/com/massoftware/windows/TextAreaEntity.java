package com.massoftware.windows;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

public class TextAreaEntity extends TextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5847859604515789175L;

	@SuppressWarnings("rawtypes")
	public TextAreaEntity(BeanItem dtoBI, String attName, String mode, int rows) throws Exception {
		init(dtoBI, attName, mode, rows);
	}

	@SuppressWarnings("rawtypes")
	private void init(BeanItem dtoBI, String attName, String mode, int rows) throws Exception {

		String label = ((Entity) dtoBI.getBean()).label(attName);
		String labelError = ((Entity) dtoBI.getBean()).labelError(attName);
		if(labelError == null || labelError.trim().length() == 0) {
			labelError  = label;
		}
		boolean required = ((Entity) dtoBI.getBean()).required(attName);
		boolean readOnly = ((Entity) dtoBI.getBean()).readOnly(attName);
		boolean unique = ((Entity) dtoBI.getBean()).unique(attName);
		int columns = (int) ((Entity) dtoBI.getBean()).columns(attName);
		Integer maxLength = ((Entity) dtoBI.getBean()).maxLength(attName);
//		String mask = ((Entity) dtoBI.getBean()).mask(attName);

		// ----------------------------------------------------------------------------

		addStyleName(ValoTheme.TEXTFIELD_TINY);
		// txt.setWidth("-1px");
		// txt.setHeight("-1px");
		setWidthUndefined();
		setHeightUndefined();
		setValidationVisible(true);		
		setNullRepresentation("");
		setVisible(true);
		setEnabled(true);
		setReadOnly(false);
		setImmediate(true);

		setCaption(label);
		setRequiredError("El campo '" + labelError + "' es requerido. Es decir no debe estar vacio.");
		setRequired(required);
		setColumns(columns);
		setRows(rows);
		if (maxLength != null) {
			setMaxLength(maxLength);
		}
		if (unique) {
			addValidator(new UniqueValidator(dtoBI.getItemProperty(attName).getType(), mode, attName, dtoBI));
		}

		// ----------------------------------------------------------------------------

		setPropertyDataSource(dtoBI.getItemProperty(attName));

		// ----------------------------------------------------------------------------

		setReadOnly(readOnly);

		// ----------------------------------------------------------------------------

	}

}
