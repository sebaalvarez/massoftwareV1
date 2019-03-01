package org.cendra.ex.crud;

public class DeleteForeingObjectConflictException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7368535778606688979L;

	private static String humanMsg = "Borrado no permitido. No está permitido que usted borre el item \"%s\", éste está siendo usado por %s \"%s\".";

	private static String humanMsg2 = "Borrado no permitido. No está permitido que usted borre el item \"%s\", éste está siendo usado por otros objetos en el sistema.";

	private String msg;

	public DeleteForeingObjectConflictException(Object item, String lblFK, Object itemFK) {

		this.msg = String.format(humanMsg, item, lblFK, itemFK);

	}

	public DeleteForeingObjectConflictException(Object item) {

		this.msg = String.format(humanMsg2, item);

	}

	public String getMessage() {
		return msg;
	}

}
