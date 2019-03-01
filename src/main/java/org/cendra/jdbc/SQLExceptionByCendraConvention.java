package org.cendra.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class SQLExceptionByCendraConvention extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 382793820907556823L;
	
	private Exception e;

	public SQLExceptionByCendraConvention(IllegalAccessException e) {
		this.e = e;
	}

	public SQLExceptionByCendraConvention(IllegalArgumentException e) {
		this.e = e;
	}

	public SQLExceptionByCendraConvention(InvocationTargetException e) {
		this.e = e;
	}

	public SQLExceptionByCendraConvention(InstantiationException e) {
		this.e = e;
	}

	public SQLExceptionByCendraConvention(ClassNotFoundException e) {
		this.e = e;
	}

	public Exception getSQLException() {
		return e;
	}

	public Throwable getCause() {
		return e.getCause();
	}

	public String getMessage() {
		return "The query SQL does not respect the Cendra convention:\n" + e.getMessage();
	}

	public StackTraceElement[] getStackTrace() {
		return e.getStackTrace();
	}

	public int getErrorCode() {
		return -1;
	}

	public String getSQLState() {
		return e.getClass().getCanonicalName();
	}

	@Override
	public String toString() {
		return "The query SQL does not respect the Cendra convention:\n" + e.toString();
	}

}
