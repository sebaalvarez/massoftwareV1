package com.massoftware.windows.cuentas_contable;

import com.massoftware.model.Entity;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.TextFieldEntity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractValidator;

public class CuentaJerarquiaValidator extends AbstractValidator<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1435601608143548684L;

	@SuppressWarnings("rawtypes")
	private Class clazz;

	private String caption;

	private TextFieldEntity integraTXT;

	@SuppressWarnings("rawtypes")
	public CuentaJerarquiaValidator(TextFieldEntity integraTXT, Class clazz, String attName, BeanItem dtoBI) throws Exception {
		super("");

		this.clazz = clazz;
		this.integraTXT = integraTXT;
		this.caption = null;

		if (dtoBI.getBean() instanceof Entity) {
			String lbl = ((Entity) dtoBI.getBean()).label(attName);
			if (lbl != null) {
				caption = lbl;
			}
		}

	}

	@Override
	protected boolean isValidValue(Object value) {

		try {

			if (value != null && value.toString().trim().length() > 0) {
				char[] integraChars = integraTXT.getValue().toCharArray();

				for (int i = integraChars.length - 1; i > 0; i--) {
					if (integraChars[i] != '0') {
						break;
					} else {
						integraChars[i] = 'X';
					}
				}

				String prefix = new String(integraChars).replace("X", "").trim();

				if (value.toString().startsWith(prefix) == false) {
					throw new InvalidValueException("El campo '" + caption
							+ "' es incorrecto, el mismo debe comenzar con el valor " + format(prefix) + ".");
				}

			}

			return true;

		} catch (Exception e) {
			LogAndNotification.print(e);
			this.setErrorMessage(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Object> getType() {

		return clazz;
	}
	
	
	private String format(String codigoCuenta) {

		if (codigoCuenta == null) {
			return codigoCuenta;
		}

		String codigoCuenta2 = codigoCuenta.trim();
		char[] chars = codigoCuenta2.toCharArray();
		
		if(codigoCuenta.length() == 11) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c.%c%c.%c%c", chars[0],
					chars[1], chars[2], chars[3], chars[4], chars[5], chars[6],
					chars[7], chars[8], chars[9], chars[10]);
			
		} else if(codigoCuenta.length() == 10) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c.%c%c.%c", chars[0],
					chars[1], chars[2], chars[3], chars[4], chars[5], chars[6],
					chars[7], chars[8], chars[9]);
						
		} else if(codigoCuenta.length() == 9) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c.%c%c", chars[0],
					chars[1], chars[2], chars[3], chars[4], chars[5], chars[6],
					chars[7], chars[8]);
						
		} else if(codigoCuenta.length() == 8) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c.%c", chars[0],
					chars[1], chars[2], chars[3], chars[4], chars[5], chars[6],
					chars[7]);
						
		} else if(codigoCuenta.length() == 7) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c", chars[0],
					chars[1], chars[2], chars[3], chars[4], chars[5], chars[6]);
						
		} else if(codigoCuenta.length() == 6) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c%c.%c", chars[0],
					chars[1], chars[2], chars[3], chars[4], chars[5]);
						
		} else if(codigoCuenta.length() == 5) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c%c", chars[0],
					chars[1], chars[2], chars[3], chars[4]);
						
		} else if(codigoCuenta.length() == 4) {
			
			codigoCuenta2 = String.format("%c.%c%c.%c", chars[0],
					chars[1], chars[2], chars[3]);
						
		} else if(codigoCuenta.length() == 3) {
			
			codigoCuenta2 = String.format("%c.%c%c", chars[0],
					chars[1], chars[2]);
						
		} else if(codigoCuenta.length() == 2) {
			
			codigoCuenta2 = String.format("%c.%c", chars[0],
					chars[1]);
						
		} else if(codigoCuenta.length() == 1) {
			
			codigoCuenta2 = String.format("%c", chars[0]);
						
		}

		

		return codigoCuenta2;

	}

}
