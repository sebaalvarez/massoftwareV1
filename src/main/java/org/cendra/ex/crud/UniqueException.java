package org.cendra.ex.crud;

public class UniqueException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3834131844809016037L;

	private static String humanMsg1 = "El campo '%s' es incorrecto, en la base de datos ya existe un item con el mismo valor.";
	private static String humanMsg2 = "Los campos '%s' son incorrectos, en la base de datos ya existe un item con los mismos valores.";

	private String msg;

	public UniqueException(String attLabel) {
		this.msg = String.format(humanMsg1, attLabel);
	}

	public UniqueException(String... attsLabel) {
		String s = "";
		for (int i = 0; i < attsLabel.length; i++) {
			s += attsLabel[i];
			if (i < attsLabel.length - 1) {
				s += ", ";
			}
		}

		this.msg = String.format(humanMsg2, s);
	}

	public String getMessage() {
		return msg;
	}
	
	public void setMessage(String msg) {
		this.msg = msg;
	}


}
