package com.massoftware.windows;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;
import org.cendra.ex.crud.NullFieldException;
import org.cendra.ex.crud.UniqueException;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class LogAndNotification {

	public static void printError(String title, String msg) {
		Notification notification = new Notification(title, msg, Type.WARNING_MESSAGE);
		// notification.setStyleName("warning failure");
		notification.setStyleName("tray failure");
		notification.setPosition(Position.BOTTOM_LEFT);
		// notification.setDelayMsec(10000);
		notification.show(Page.getCurrent());
	}

	public static void print(Exception e) {

	
		
		if (e instanceof UniqueException) {						
			
			printUniqueException((UniqueException) e);
			
		} else if (e instanceof InvalidValueException) {
			
			printInvalidValueException((InvalidValueException) e);
			
		} else if (e instanceof NullFieldException) {
			
			printNullFieldException((NullFieldException) e);
			
		} else if (e instanceof DeleteForeingObjectConflictException) {
			
			printDeleteForeingObjectConflictException((DeleteForeingObjectConflictException) e);
			
		} else if (e instanceof IllegalArgumentException) {
			
			printIllegalArgumentException((IllegalArgumentException) e);
			
		} else {
			
			Notification notification = new Notification("Error Interno del Sistema", e.toString(), Type.ERROR_MESSAGE);
			notification.setStyleName("error bar small closable");
			notification.setPosition(Position.BOTTOM_LEFT);
			Integer delayMsec = -1;
			notification.setDelayMsec(delayMsec);

			notification.show(Page.getCurrent());

//			e.printStackTrace();

		}

		 e.printStackTrace();

	}

	// private static void print(DeleteForeingObjectConflictException e, String
	// humanObject) {
	// Notification notification = new Notification("Objeto no borrable",
	// e.getMessage(), Type.WARNING_MESSAGE);
	// // notification.setStyleName("warning failure");
	// notification.setStyleName("tray failure");
	// notification.setPosition(Position.BOTTOM_LEFT);
	// // notification.setDelayMsec(10000);
	// notification.show(Page.getCurrent());
	//
	// // e.printStackTrace();
	// }

	private static void printIllegalArgumentException(IllegalArgumentException e) {
		Notification notification = new Notification("Argumento inválido", e.getMessage(), Type.WARNING_MESSAGE);
		// notification.setStyleName("warning failure");
		notification.setStyleName("tray failure");
		notification.setPosition(Position.BOTTOM_LEFT);
		// notification.setDelayMsec(10000);
		notification.show(Page.getCurrent());

		// e.printStackTrace();
	}

	private static void printInvalidValueException(InvalidValueException e) {
		Notification notification = new Notification("Campo inválido", e.getMessage(), Type.WARNING_MESSAGE);
		// notification.setStyleName("warning failure");
		notification.setStyleName("tray failure");
		notification.setPosition(Position.BOTTOM_LEFT);
		// notification.setDelayMsec(10000);
		notification.show(Page.getCurrent());

		// e.printStackTrace();
	}

	// private static void print(InsertDuplicateException e) {
	// Notification notification = new Notification("Duplicación de datos",
	// e.getMessage(), Type.WARNING_MESSAGE);
	// // notification.setStyleName("warning failure");
	// notification.setStyleName("tray failure");
	// notification.setPosition(Position.BOTTOM_LEFT);
	// // notification.setDelayMsec(10000);
	// notification.show(Page.getCurrent());
	//
	// // e.printStackTrace();
	// }

	private static void printUniqueException(UniqueException e) {
		Notification notification = new Notification("Duplicación de datos", e.getMessage(), Type.WARNING_MESSAGE);
		// notification.setStyleName("warning failure");
		notification.setStyleName("tray failure");
		notification.setPosition(Position.BOTTOM_LEFT);
		// notification.setDelayMsec(10000);
		notification.show(Page.getCurrent());

		 e.printStackTrace();
	}

	private static void printNullFieldException(NullFieldException e) {
		Notification notification = new Notification("Campo vacio y requerido", e.getMessage(), Type.WARNING_MESSAGE);
		// notification.setStyleName("warning failure");
		notification.setStyleName("tray failure");
		notification.setPosition(Position.BOTTOM_LEFT);
		// notification.setDelayMsec(10000);
		notification.show(Page.getCurrent());

		// e.printStackTrace();
	}

	private static void printDeleteForeingObjectConflictException(DeleteForeingObjectConflictException e) {
		Notification notification = new Notification("Borrado no permitido - Objeto con relaciones", e.getMessage(),
				Type.WARNING_MESSAGE);
		// notification.setStyleName("warning failure");
		notification.setStyleName("tray failure");
		notification.setPosition(Position.BOTTOM_LEFT);
		// notification.setDelayMsec(10000);
		notification.show(Page.getCurrent());

		// e.printStackTrace();
	}

	public static void printSuccessOk(String msg) {
		Notification notification = new Notification("Ok", msg, Type.HUMANIZED_MESSAGE);
		// notification.setStyleName("humanized success");
		notification.setStyleName("tray success");
		notification.setPosition(Position.BOTTOM_LEFT);
		// notification.setDelayMsec(10000);
		notification.show(Page.getCurrent());
	}

}