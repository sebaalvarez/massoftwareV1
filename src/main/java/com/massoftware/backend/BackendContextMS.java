package com.massoftware.backend;

import java.util.Properties;

import org.cendra.cx.AbstractContext;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceProperties;
import org.cendra.jdbc.DataSourceWrapper;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class BackendContextMS extends AbstractContext {

	public final static String MS = "sqlserver";

	private DataSourceWrapper dataSourceWrapper;

	private static BackendContextMS backendContext;

	private BackendContextMS() {

		Properties propertiesMS = new Properties();

		propertiesMS.put("jdbc.driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		propertiesMS.put("jdbc.userName", "sa");
		propertiesMS.put("jdbc.userPassword", "cordoba");
		// properties.put("jdbc.url", ds.getURL());
		propertiesMS.put("jdbc.maxActive", "10");
		propertiesMS.put("jdbc.initialSize", "5");
		propertiesMS.put("jdbc.maxIdle", "5");
		propertiesMS.put("jdbc.validationQuery", "SELECT 1");
		propertiesMS.put("jdbc.verbose", "true");
		propertiesMS.put("jdbc.serverName", "localhost");
		propertiesMS.put("jdbc.databaseName", "VetaroRep");
		propertiesMS.put("jdbc.portNumber", "1433");

		start(propertiesMS);

	}

	public static synchronized BackendContextMS get() {
		if (backendContext == null) {
			backendContext = new BackendContextMS();
		}

		return backendContext;
	}

	private void start(Properties properties) {

		try {

			init(properties);

		} catch (Exception e) {
			printFatal(e);
		}

	}

	private void init(Properties properties) throws Exception {

		initContextDbMsSqlServer(properties);
	}

	private void initContextDbMsSqlServer(Properties properties) throws Exception {

		String path = "jdbc.";

		SQLServerDataSource ds = new SQLServerDataSource();
		// ds.setIntegratedSecurity(true);
		ds.setServerName(properties.getProperty(path + "serverName"));
		ds.setPortNumber(new Integer(properties.getProperty(path + "portNumber")));
		ds.setDatabaseName(properties.getProperty(path + "databaseName"));
		ds.setUser(properties.getProperty(path + "userName"));
		ds.setPassword(properties.getProperty(path + "userPassword"));

		properties.put("jdbc.url", ds.getURL());

		DataSourceProperties dataSourceProperties = new DataSourceProperties();

		dataSourceProperties.setDriverClassName(properties.getProperty(path + "driverClassName"));
		dataSourceProperties.setUrl(properties.getProperty(path + "url"));
		dataSourceProperties.setUserName(properties.getProperty(path + "userName"));
		dataSourceProperties.setUserPassword(properties.getProperty(path + "userPassword"));
		dataSourceProperties.setInitialSize(new Integer(properties.getProperty(path + "initialSize")));
		dataSourceProperties.setMaxActive(new Integer(properties.getProperty(path + "maxActive")));
		dataSourceProperties.setMaxIdle((new Integer(properties.getProperty(path + "maxIdle"))));
		dataSourceProperties.setValidationQuery(properties.getProperty(path + "validationQuery"));
		dataSourceProperties.setVerbose((new Boolean(properties.getProperty(path + "verbose"))));

		// dataSource = ds;

		dataSourceWrapper = new DataSourceWrapper(ds, dataSourceProperties);

	}

	// ================================================================================

	public synchronized Object[][] find(String tableName, String atts, String orderBy, String where, int limit,
			int offset, Object[] args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String sql = "SELECT " + atts + " FROM " + tableName;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		// if (orderBy == null || orderBy.trim().length() == 0) {
		// throw new Exception("orderBy not found.");
		// }

		if (orderBy != null && orderBy.trim().length() > 0) {
			sql += " ORDER BY " + orderBy;
		} else {
			sql += " ORDER BY " + 1;
		}

		if (offset > -1 && limit > -1) {

			sql += " OFFSET " + offset + " ROWS FETCH NEXT " + limit + " ROWS ONLY ";
		}

		// sql += ";";

		return find(sql, limit, offset, args);
	}

	public synchronized Object[][] find(String sql) throws Exception {
		return find(sql, -1, -1);
	}
	
	public synchronized Object[][] find(String sql, int limit, int offset) throws Exception {
		return find(sql, limit, offset, null);
	}
	
	public synchronized Object[][] find(String sql, int limit, int offset, Object[] args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper.getConnectionWrapper();

		try {

			Object[][] table = null;

			if (args.length == 0) {
				table = connectionWrapper.findToTable(sql);
			} else {
				table = connectionWrapper.findToTable(sql, args);
			}

			// for (Object item : list) {
			// if (item instanceof Valuable) {
			// ((Valuable) item).validate();
			// }
			// }

			return table;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	// ================================================================================

}
