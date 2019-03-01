package com.massoftware.windows;

import java.util.Locale;

import com.vaadin.data.util.converter.StringToLongConverter;

public class StringToLongConverterUnspecifiedLocale extends
		StringToLongConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5943612309620235994L;

	public String convertToPresentation(Long value,
			Class<? extends String> targetType, Locale locale)
			throws ConversionException {
		
		if (value == null) {
			return null;
		}

		// return getFormat(locale).format(value);
		return value.toString();
	}

}
