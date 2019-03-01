package com.massoftware.windows;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class PositiveUnFormatDoubleConverter implements Converter<String, Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6845323787594889683L;

	// Buscas tener exactamente una secuencia de 10 dígitos del 0 al 9
	@Override
	public Double convertToModel(String value,
			Class<? extends Double> targetType, Locale locale)
			throws ConversionException {

		if (value == null) {
			return null;
		}

		// Quitamos espacios en blanco y lo que no sea numero.
		value = value.trim();
		char[] chars = value.toCharArray();
		String newValue = "";
		for (char c : chars) {
			if (Character.isDigit(c) || c == '.') {
				newValue += c;
			}
		}

		value = newValue.trim();

		if (value.isEmpty()) {
			return 0.0;
		}

		if (value.length() == 1 && value.startsWith("[.]")) {
			return 0.0;
		}

		if (value.startsWith(".")) {
			value = 0 + value;
		}

		return new Double(value);
	}

	// La cadena en tu modelo (digamos, JavaBean) no necesita conversión.
	@Override
	public String convertToPresentation(Double value,
			Class<? extends String> targetType, Locale locale)
			throws ConversionException {
		if (value != null) {
			return value.toString();
		}
		return null;
	}

	@Override
	public Class<Double> getModelType() {
		return Double.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

	// protected java.text.NumberFormat getFormat(Locale locale) {
	// NumberFormat format = super.getFormat(locale);
	// format.setGroupingUsed(false);
	// return format;
	// };

}
