package org.cendra.ex.crud;

public class InsertNullException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7226702410623854272L;

	public InsertNullException() {
		super("Se intento insertar un objeto nulo.");
	}
	
	public InsertNullException(String label) {
		super("Se intento insertar un objeto " + label + "nulo.");
	}

}
