package com.massoftware.windows;

import com.vaadin.data.validator.AbstractValidator;

@SuppressWarnings("rawtypes")
public class MinLengthValidator extends AbstractValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2815717402801785978L;

	private static String humanMsg = "El campo '%s' es incorrecto, el mismo debe contener %d caracteres como mínimo.";

	private Class type;
	private int minLength;
	private String label;

	public MinLengthValidator(Class type, String label, int minLength) {
		super("");
		this.label = label;
		this.minLength = minLength;

	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	public boolean isValidValue(Object value) {

		if (value != null) {
			try {

				if (value.toString().trim().length() < minLength == true) {
					this.setErrorMessage(String.format(humanMsg, label,
							minLength));

					return false;
				}

			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}

	@Override
	public Class getType() {
		return this.type;
	}

}
