package org.cendra.ex.crud;

public class NullFieldException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3834131844809016037L;
	
	private static String humanMsg1 = "El campo '%s' es incorrecto, el campo es requerido.";
	private static String humanMsg2 = "Los campos '%s' son incorrectos, los campos son requeridos.";

	private String msg;

	public NullFieldException(String attLabel) {
		this.msg = String.format(humanMsg1, attLabel);
	}

	public NullFieldException(String... attsLabel) {
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
