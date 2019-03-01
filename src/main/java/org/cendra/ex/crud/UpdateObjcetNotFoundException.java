package org.cendra.ex.crud;

public class UpdateObjcetNotFoundException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6027408769608062339L;

	public UpdateObjcetNotFoundException() {
		super(
				"Se intento actualizar un objeto que no existe en la base de datos.");
	}

	public UpdateObjcetNotFoundException(String condition) {
		super(
				"Se intento actualizar un objeto que no existe en la base de datos. Condición de búsqueda: "
						+ condition);
	}

}
