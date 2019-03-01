package com.massoftware.windows;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.DateField;
import com.vaadin.ui.themes.ValoTheme;

public class DateFieldEntity extends DateField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5847859604515789175L;

	@SuppressWarnings("rawtypes")
	public DateFieldEntity(BeanItem dtoBI, String attName, String mode) throws Exception {
		init(dtoBI, attName, mode);
	}

	@SuppressWarnings("rawtypes")
	private void init(BeanItem dtoBI, String attName, String mode) throws Exception {

		String label = ((Entity) dtoBI.getBean()).label(attName);
		String labelError = ((Entity) dtoBI.getBean()).labelError(attName);
		if (labelError == null || labelError.trim().length() == 0) {
			labelError = label;
		}
		boolean required = ((Entity) dtoBI.getBean()).required(attName);
		boolean readOnly = ((Entity) dtoBI.getBean()).readOnly(attName);
		boolean unique = ((Entity) dtoBI.getBean()).unique(attName);
		// int columns = (int) ((Entity) dtoBI.getBean()).columns(attName);
		// Integer maxLength = ((Entity) dtoBI.getBean()).maxLength(attName);
		// String mask = ((Entity) dtoBI.getBean()).mask(attName);

		// ----------------------------------------------------------------------------

		addStyleName(ValoTheme.DATEFIELD_TINY);		
		// txt.setHeight("-1px");
		setWidthUndefined();
		setHeightUndefined();
		setValidationVisible(true);
		setVisible(true);
		setEnabled(true);
		setReadOnly(false);
		setImmediate(true);

		setWidth(10f, Unit.EM );
		setLocale(new Locale("es", "AR"));
		setDateFormat("dd/MM/yyyy");		
		setLenient(true);

		setCaption(label);
		setRequiredError("El campo '" + labelError + "' es requerido. Es decir no debe estar vacio.");
		setRequired(required);

		if (unique) {
			addValidator(new UniqueValidator(dtoBI.getItemProperty(attName).getType(), mode, attName, dtoBI));
		}

		// ----------------------------------------------------------------------------

		setPropertyDataSource(dtoBI.getItemProperty(attName));

		// ----------------------------------------------------------------------------

		setReadOnly(readOnly);

		// ----------------------------------------------------------------------------

	}

	@Override
	protected Date handleUnparsableDateString(String dateString) throws Converter.ConversionException {

		return UtilDate.parseDate(dateString);
		// return new Timestamp(System.currentTimeMillis());
	}

	public void changeVariables(Object source, Map<String, Object> variables) {

		if (variables.containsKey("dateString") == false) {
			variables.put("dateString",
					variables.get("day") + "/" + variables.get("month") + "/" + variables.get("year"));
		}

		variables.put("day", -1);
		variables.put("year", -1);
		variables.put("month", -1);
		// variables.put("sec", -1);
		// variables.put("min", -1);
		// variables.put("hour", -1);
		
		super.changeVariables(source, variables);
	}

}
