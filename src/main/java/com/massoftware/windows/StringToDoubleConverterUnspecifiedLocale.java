package com.massoftware.windows;

import java.text.ParsePosition;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToDoubleConverter;

public class StringToDoubleConverterUnspecifiedLocale extends StringToDoubleConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5943612309620235994L;

	// public Double convertToPresentation(String value, Class<? extends Double>
	// targetType, Locale locale)
	// throws ConversionException {
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale)
			throws ConversionException {

		if (value == null) {
			return null;
		}

		Number n = convertToNumber(value, targetType, locale.US);
		return n == null ? null : n.doubleValue();

		// System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		// System.err.println("getFormat(locale.US).format(value) " +
		// getFormat(locale.US).format(value));
		// System.err.println("value.toString() " + value.toString());
		//
		// return getFormat(locale.US).format(value);
		// return value.toString();
	}

	protected Number convertToNumber(String value, Class<? extends Number> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return null;
		}

		// Remove leading and trailing white space
		value = value.trim();

		// Parse and detect errors. If the full string was not used, it is
		// an error.
		ParsePosition parsePosition = new ParsePosition(0);
		Number parsedValue = getFormat(locale).parse(value, parsePosition);
		if (parsePosition.getIndex() != value.length()) {
			throw new ConversionException("Could not convert '" + value + "' to " + getModelType().getName());
		}

		if (parsedValue == null) {
			// Convert "" to null
			return null;
		}

		return parsedValue;
	}

}
