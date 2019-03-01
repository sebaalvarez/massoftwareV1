package org.cendra.ex.crud;

public class InsertDuplicateException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6027408769608062339L;

	public InsertDuplicateException() {
		super(
				"Se intento insertar un objeto que ya existe en la base de datos.");
	}

	public InsertDuplicateException(Object condition) {
		super(
				"Se intento insertar un objeto que ya existe en la base de datos. Condición de búsqueda: "
						+ condition);
	}

}
