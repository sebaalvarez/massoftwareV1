package org.cendra.jdbc;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SQLExceptionWrapper extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5504598004204150746L;

	private SQLException sqlException;

	private ZonedDateTime time;
	private String title = "unknown";
	private String subject = "unknown";

	private String operationType = "unknown";

	private String driverClassName = "unknown";
	private int initialSize = -1;
	private int maxActive = -1;
	private int maxIdle = -1;
	private String validationQuery = "unknown";

	private String url = "unknown";
	private String userName = "unknown";
	private String databaseProductName = "unknown";
	private String databaseProductVersion = "unknown";
	private String driverName = "unknown";
	private String driverVersion = "unknown";
	private int jDBCMajorVersion = -1;
	private int jDBCMinorVersion = -1;
	private List<String> sqlStatements = new ArrayList<String>();

	public SQLExceptionWrapper(SQLException sqlException) {
		super();
		time = ZonedDateTime.now();
		this.sqlException = sqlException;
	}

	public SQLException getSQLException() {
		return sqlException;
	}

	public Throwable getCause() {
		return sqlException.getCause();
	}

	public String getMessage() {
		return sqlException.getMessage();
	}

	public StackTraceElement[] getStackTrace() {
		return sqlException.getStackTrace();
	}

	public int getErrorCode() {
		return sqlException.getErrorCode();
	}

	public String getSQLState() {
		return sqlException.getSQLState();
	}

	public String getOperationType() {
		return operationType;
	}

	public ZonedDateTime getTime() {
		return time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDatabaseProductName() {
		return databaseProductName;
	}

	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}

	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverVersion() {
		return driverVersion;
	}

	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

	public int getjDBCMajorVersion() {
		return jDBCMajorVersion;
	}

	public void setjDBCMajorVersion(int jDBCMajorVersion) {
		this.jDBCMajorVersion = jDBCMajorVersion;
	}

	public int getjDBCMinorVersion() {
		return jDBCMinorVersion;
	}

	public void setjDBCMinorVersion(int jDBCMinorVersion) {
		this.jDBCMinorVersion = jDBCMinorVersion;
	}

	public List<String> getSqlStatements() {
		return sqlStatements;
	}

	public void setSqlStatements(List<String> sqlStatements) {
		this.sqlStatements = sqlStatements;
	}

	@Override
	public String toString() {
		return sqlException.toString();
	}

}
