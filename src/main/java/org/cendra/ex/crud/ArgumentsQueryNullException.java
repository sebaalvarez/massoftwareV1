package org.cendra.ex.crud;


public class ArgumentsQueryNullException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2421566090653138439L;

	public ArgumentsQueryNullException() {
		super("Se intento buscar un objeto en base a un filtro nulo.");
	}

}
