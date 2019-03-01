package org.cendra.jdbc;

public class DataSourceMetaData {

	public String url = "unknown";
	public String userName = "unknown";
	public String databaseProductName = "unknown";
	public String databaseProductVersion = "unknown";
	public String driverName = "unknown";
	public String driverVersion = "unknown";
	public int jDBCMajorVersion = -1;
	public int jDBCMinorVersion = -1;

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

	// public String toString() {
	// return getDatabaseProductName() + " " + getDatabaseProductVersion()
	// + " " + getDriverName() + " " + getDriverVersion();
	// }

	public String toString() {
		String json = "\n\n";

		json += "\n\turl:" + url + "";
		json += "\n\tuserName:" + userName + "";
		json += "\n\tdatabaseProductName:" + databaseProductName + "";
		json += "\n\tdatabaseProductVersion:" + databaseProductVersion + "";
		json += "\n\tdriverName:" + driverName + "";
		json += "\n\tdriverVersion:" + driverVersion + "";
		json += "\n\tjDBCMajorVersion:" + jDBCMajorVersion + "";
		json += "\n\tjDBCMinorVersion:" + jDBCMinorVersion + "";

		json += "\n";

		return json;
	}
	
	public boolean isDatabasePostgreSql() {
		return getDatabaseProductName().equals("PostgreSQL");
	}

}
