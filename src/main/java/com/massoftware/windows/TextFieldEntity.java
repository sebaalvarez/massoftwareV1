package com.massoftware.windows;

import java.math.BigDecimal;

import org.vaadin.inputmask.InputMask;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.LongRangeValidator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class TextFieldEntity extends TextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5847859604515789175L;

	@SuppressWarnings("rawtypes")
	public TextFieldEntity(BeanItem dtoBI, String attName, String mode) throws Exception {
		init(dtoBI, attName, mode);
	}

	@SuppressWarnings("rawtypes")
	private void init(BeanItem dtoBI, String attName, String mode) throws Exception {

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
		String mask = ((Entity) dtoBI.getBean()).mask(attName);

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
		if (maxLength != null) {
			setMaxLength(maxLength);
		}
		if (unique) {
			addValidator(new UniqueValidator(dtoBI.getItemProperty(attName).getType(), mode, attName, dtoBI));
		}

		if (mask != null) {

			InputMask im = new InputMask(mask);

			im.setAutoUnmask(true);
			im.setDigitsOptional(false);
			im.extend(this);

			if (dtoBI.getItemProperty(attName).getType() == Integer.class
					|| dtoBI.getItemProperty(attName).getType() == Long.class
					|| dtoBI.getItemProperty(attName).getType() == Double.class
					|| dtoBI.getItemProperty(attName).getType() == BigDecimal.class) {
				
				addStyleName("align-right");
			}

		} else if (dtoBI.getItemProperty(attName).getType() == Integer.class) {

			String minValueString = ((Entity) dtoBI.getBean()).minValue(attName);
			String maxValueString = ((Entity) dtoBI.getBean()).maxValue(attName);

			Integer minValue = Integer.MIN_VALUE;
			Integer maxValue = Integer.MAX_VALUE;

			if (minValueString != null) {
				minValue = new Integer(minValueString);
			}
			if (maxValueString != null) {
				maxValue = new Integer(maxValueString);
			}

			Integer maxLengthMin = minValue.toString().length();
			Integer maxLengthMax = maxValue.toString().length();

			if (maxLengthMax > maxLengthMin) {
				maxLengthMin = maxLengthMax;
			}

			// ----------------------------------------------------------------------------

			addStyleName("align-right");

			setColumns(maxLengthMin);

			setMaxLength(maxLengthMin);

			setConverter(new StringToIntegerConverterUnspecifiedLocale());

			String msg = "El campo " + labelError + " es inválido, se permiten sólo valores numéricos sin decimales, desde "
					+ minValue + " hasta " + maxValue + ".";

			addValidator(new IntegerRangeValidator(msg, minValue, maxValue));

			setConversionError(msg);

		} else if (dtoBI.getItemProperty(attName).getType() == Long.class) {

			String minValueString = ((Entity) dtoBI.getBean()).minValue(attName);
			String maxValueString = ((Entity) dtoBI.getBean()).maxValue(attName);

			Long minValue = Long.MIN_VALUE;
			Long maxValue = Long.MAX_VALUE;

			if (minValueString != null) {
				minValue = new Long(minValueString);
			}
			if (maxValueString != null) {
				maxValue = new Long(maxValueString);
			}

			Integer maxLengthMin = minValue.toString().length();
			Integer maxLengthMax = maxValue.toString().length();

			if (maxLengthMax > maxLengthMin) {
				maxLengthMin = maxLengthMax;
			}

			// ----------------------------------------------------------------------------

			addStyleName("align-right");

			setColumns(maxLengthMin);

			setMaxLength(maxLengthMin);

			setConverter(new StringToLongConverterUnspecifiedLocale());

			String msg = "El campo " + labelError + " es inválido, se permiten sólo valores numéricos sin decimales, desde "
					+ minValue + " hasta " + maxValue + ".";

			addValidator(new LongRangeValidator(msg, minValue, maxValue));

			setConversionError(msg);

		} else if (dtoBI.getItemProperty(attName).getType() == Double.class) {

			String minValueString = ((Entity) dtoBI.getBean()).minValue(attName);
			String maxValueString = ((Entity) dtoBI.getBean()).maxValue(attName);

			Double minValue = Double.MIN_VALUE;
			Double maxValue = Double.MAX_VALUE;

			if (minValueString != null) {
				minValue = new Double(minValueString);
			}
			if (maxValueString != null) {
				maxValue = new Double(maxValueString);
			}

			Integer maxLengthMin = minValue.toString().length();
			Integer maxLengthMax = maxValue.toString().length();

			if (maxLengthMax > maxLengthMin) {
				maxLengthMin = maxLengthMax;
			}

			// ----------------------------------------------------------------------------

			addStyleName("align-right");

			setColumns(maxLengthMin);

			setMaxLength(maxLengthMin);

			setConverter(new StringToDoubleConverterUnspecifiedLocale());
//			setConverter(new StringToDoubleConverter());						

			String msg = "El campo " + labelError + " es inválido, se permiten sólo valores numéricos con decimales, desde "
					+ minValue + " hasta " + maxValue + ".";

//			addValidator(new DoubleRangeValidator(msg, minValue, maxValue));

			setConversionError(msg);

		}else if (dtoBI.getItemProperty(attName).getType() == BigDecimal.class) {

			String minValueString = ((Entity) dtoBI.getBean()).minValue(attName);
			String maxValueString = ((Entity) dtoBI.getBean()).maxValue(attName);

			BigDecimal minValue = new BigDecimal(Double.MIN_VALUE);
			BigDecimal maxValue = new BigDecimal(Double.MAX_VALUE);

			if (minValueString != null) {
				minValue = new BigDecimal(minValueString);
			}
			if (maxValueString != null) {
				maxValue = new BigDecimal(maxValueString);
			}

			Integer maxLengthMin = minValue.toString().length();
			Integer maxLengthMax = maxValue.toString().length();

			if (maxLengthMax > maxLengthMin) {
				maxLengthMin = maxLengthMax;
			}

			// ----------------------------------------------------------------------------

			addStyleName("align-right");

			setColumns(maxLengthMin);

			setMaxLength(maxLengthMin);

			setConverter(new StringToBigDecimalConverterUnspecifiedLocale());

			String msg = "El campo " + labelError + " es inválido, se permiten sólo valores numéricos con decimales, desde "
					+ minValue + " hasta " + maxValue + ".";

			addValidator(new BigDecimalRangeValidator(msg, minValue, maxValue));

			setConversionError(msg);

		}

		// ----------------------------------------------------------------------------

		setPropertyDataSource(dtoBI.getItemProperty(attName));

		// ----------------------------------------------------------------------------

		setReadOnly(readOnly);

		// ----------------------------------------------------------------------------

	}

}
