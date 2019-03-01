package org.cendra.jdbc;

public class DataSourceProperties {

	private String driverClassName = "unknown";
	private int initialSize = -1;
	private int maxActive = -1;
	private int maxIdle = -1;
	private String validationQuery = "unknown";
	private String url = "unknown";
	private String userName = "unknown";
	private String userPassword = "unknown";
	private boolean verbose = false;

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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public String toString() {
		String json = "\n\n";

		json += "\n\tdriverClassName:" + driverClassName + "";
		json += "\n\tinitialSize:" + initialSize + "";
		json += "\n\tdmaxActive:" + maxActive + "";
		json += "\n\tmaxIdle:" + maxIdle + "";
		json += "\n\tvalidationQuery:" + validationQuery + "";
		json += "\n\turl:" + url + "";
		json += "\n\tuserName:" + userName + "";
		json += "\n\tuserPassword:" + "SECRET" + "";
		json += "\n\tverbose:" + verbose + "";

		json += "\n";

		return json;
	}
	
	

}
