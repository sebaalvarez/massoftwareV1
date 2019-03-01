package org.cendra.ex.crud;

public class UpdateNullException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3990822778761403423L;

	public UpdateNullException() {
		super("Se intento actualizar un objeto nulo.");
	}

}
