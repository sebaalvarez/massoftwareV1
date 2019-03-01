package com.massoftware.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.cendra.cx.AbstractContext;
import org.cendra.ex.crud.UniqueException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceProperties;
import org.cendra.jdbc.DataSourceWrapper;

public class BackendContextPG extends AbstractContext {

	public final static String PG = "Postgresql";

	private DataSourceWrapper dataSourceWrapper;

	private static BackendContextPG backendContext;

	private String dbFilesPath;
	private String iconosPath;

	private BackendContextPG() {
		try {

			init();

		} catch (Exception e) {
			printFatal(e);
		}
	}

	public static synchronized BackendContextPG get() {
		if (backendContext == null) {
			backendContext = new BackendContextPG();
		}

		return backendContext;
	}

	public synchronized String getIconosPath() {
		return iconosPath;
	}

	// private Properties loadProperties() {
	//
	// Properties properties = new Properties();
	//
	// properties.put("jdbc.driverClassName", "org.postgresql.Driver");
	// properties.put("jdbc.userName", "postgres");
	// properties.put("jdbc.userPassword", "cordoba");
	// properties.put("jdbc.url",
	// "jdbc:postgresql://localhost:5432/massoftware?searchpath=massoftware");
	// properties.put("jdbc.maxActive", "10");
	// properties.put("jdbc.initialSize", "5");
	// properties.put("jdbc.maxIdle", "5");
	// properties.put("jdbc.validationQuery", "SELECT 1");
	// properties.put("jdbc.verbose", "true");
	// properties.put("file.path",
	// "D:\\dev\\source\\\\massoftware_front\\files_db");
	//
	// return properties;
	// }

	private void init() throws Exception {

		// massoftware.properties
		// Properties properties = loadProperties(
		// System.getProperty("user.dir") + File.separatorChar + "massoftware.conf");

		System.err.println("System.getProperty(\"user.dir\") " + System.getProperty("user.dir"));

		Properties properties = loadProperties("massoftware.properties");

		// -------------------------------------------------------------------

		dbFilesPath = properties.getProperty("file.path");

		iconosPath = dbFilesPath + File.separatorChar + "iconos";

		String path = "jdbc.";

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

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(dataSourceProperties.getDriverClassName());
		ds.setUrl(dataSourceProperties.getUrl());
		ds.setUsername(dataSourceProperties.getUserName());
		ds.setPassword(dataSourceProperties.getUserPassword());
		ds.setInitialSize(dataSourceProperties.getInitialSize());
		ds.setMaxActive(dataSourceProperties.getMaxActive());
		ds.setMaxIdle(dataSourceProperties.getMaxIdle());
		ds.setValidationQuery(dataSourceProperties.getValidationQuery());

		dataSourceWrapper = new DataSourceWrapper(ds, dataSourceProperties);

	}

	// ================================================================================

	public synchronized Object[][] findAll(String tableName) throws Exception {
		Object[][] table = find(tableName, "*", null, null, -1, -1, null);

		return table;

	}

	public synchronized Object[] findById(String tableName, String id) throws Exception {
		Object[][] table = find(tableName, "*", null, "id = ?", -1, -1, new Object[] { id });

		return table[0];
	}

	public synchronized Object[] findByFkId(String tableName, String attNameFK, String idFk) throws Exception {
		Object[][] table = find(tableName, "*", "id", attNameFK + " = ?", -1, -1, new Object[] { idFk });

		return table[0];
	}

	public synchronized Object[][] find(String tableName, String atts, String orderBy, String where, int limit,
			int offset, Object[] args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String sql = "SELECT " + atts + " FROM " + "massoftware." + tableName;

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

			sql += " OFFSET " + offset + " LIMIT " + limit;
		}

		// sql += ";";

		return find(sql, limit, offset, args);
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

	public synchronized boolean delete(String tableSQL, String id) throws Exception {

		tableSQL = "massoftware." + tableSQL;
		String whereSQL = "";

		ArrayList<Object> args = new ArrayList<Object>();

		args.add(id);
		whereSQL += "id = ?";

		return BackendContextPG.get().delete(tableSQL, whereSQL, args.toArray());
	}

	public synchronized boolean delete(String nameTableDB, String where, Object... args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String sql = "DELETE FROM " + nameTableDB;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		// sql += ";";

		ConnectionWrapper connectionWrapper = dataSourceWrapper.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = -1;

			rows = connectionWrapper.delete(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto " + rows + " registros.");
			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public synchronized Integer maxValueInteger(String attName, String tableName) throws Exception {

		String sql = "SELECT COALESCE(MAX(" + attName + "), 0) + 1 FROM massoftware." + tableName;

		ConnectionWrapper connectionWrapper = dataSourceWrapper.getConnectionWrapper();

		try {

			Object[][] table = connectionWrapper.findToTable(sql);

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public synchronized Integer maxValueInteger(String tableName, String[] attNames, String attNameCount, Object[] args)
			throws Exception {

		String tableSQL = tableName;

		String attsSQL = "COALESCE(MAX(" + attNameCount + "), 0) + 1";
		String orderBySQL = null;
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		for (int i = 0; i < attNames.length; i++) {
			Object arg = args[i];
			String attName = attNames[i];

			filtros.add(arg);
			whereSQL += attName + " = ? AND ";

		}

		// ==================================================================

		whereSQL = whereSQL.trim();
		if (whereSQL.length() > 0) {
			whereSQL = whereSQL.substring(0, whereSQL.length() - 4);
		} else {
			whereSQL = null;
		}

		// ==================================================================

		Object[][] table = BackendContextPG.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1,
				filtros.toArray());

		return (Integer) table[0][0];

	}

	public synchronized boolean update(String nameTableDB, String[] nameAtts, Object[] args, String where)
			throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String sql = buildUpdate(nameTableDB, nameAtts, where);

		ConnectionWrapper connectionWrapper = dataSourceWrapper.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = connectionWrapper.update(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un registro, la sentencia afecto " + rows + " registros.");
			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	public synchronized boolean update(List<String> tableNames, List<List<String>> nameAtts, List<List<Object>> args,
			List<String> where, List<Integer> operation) throws Exception {

		if (tableNames == null) {
			tableNames = new ArrayList<String>();
		}

		if (nameAtts == null) {
			nameAtts = new ArrayList<List<String>>();
		}

		if (args == null) {
			args = new ArrayList<List<Object>>();
		}

		if (where == null) {
			where = new ArrayList<String>();
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			for (int i = 0; i < tableNames.size(); i++) {

				String[] nameAttsArray = new String[nameAtts.get(i).size()];
				nameAttsArray = nameAtts.get(i).toArray(nameAttsArray);

				String sql = null;

				if (operation.get(i) == 1) {

					sql = "DELETE FROM massoftware." + tableNames.get(i);

					if (where.get(i) != null && where.get(i).trim().length() > 0) {
						sql += " WHERE " + where.get(i);
					}

					connectionWrapper.update(sql, args.get(i).toArray());

				} else if (operation.get(i) == 2) {
					sql = buildUpdate(tableNames.get(i), nameAttsArray, where.get(i));

					int rows = connectionWrapper.update(sql, args.get(i).toArray());

					if (rows != 1) {
						throw new Exception("La sentencia debería afectar un registro, la sentencia afecto " + rows
								+ " registros.");
					}

				} else if (operation.get(i) == 3) {
					sql = buildInsert(tableNames.get(i), nameAttsArray);

					int rows = connectionWrapper.update(sql, args.get(i).toArray());

					if (rows != 1) {
						throw new Exception("La sentencia debería afectar un registro, la sentencia afecto " + rows
								+ " registros.");
					}

				}

			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	private synchronized String buildUpdate(String tableName, String[] nameAtts, String where) throws Exception {

		String sql = "UPDATE " + "massoftware." + tableName + " SET ";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += nameAtts[i] + " = ?";
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += " WHERE " + where + ";";

		return sql;
	}

	private synchronized String buildInsert(String tableName, String[] nameAtts) throws Exception {

		String sql = "INSERT INTO " + "massoftware." + tableName + " (";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += nameAtts[i];
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += ") VALUES (";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += "?";
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += ")";

		return sql;
	}

	public synchronized boolean insert(String tableName, String[] nameAtts, Object[] args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String sql = "INSERT INTO " + "massoftware." + tableName + " (";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += nameAtts[i];
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += ") VALUES (";

		for (int i = 0; i < args.length; i++) {
			sql += "?";
			if (i < args.length - 1) {
				sql += ", ";
			}
		}

		sql += ")";

		ConnectionWrapper connectionWrapper = dataSourceWrapper.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = connectionWrapper.insert(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un registro, la sentencia afecto " + rows + " registros.");
			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	public synchronized boolean insert(List<String> tableNames, List<List<String>> nameAtts, List<List<Object>> args)
			throws Exception {

		if (tableNames == null) {
			tableNames = new ArrayList<String>();
		}

		if (nameAtts == null) {
			nameAtts = new ArrayList<List<String>>();
		}

		if (args == null) {
			args = new ArrayList<List<Object>>();
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			for (int i = 0; i < tableNames.size(); i++) {

				String sql = "INSERT INTO " + "massoftware." + tableNames.get(i) + " (";

				for (int j = 0; j < nameAtts.get(i).size(); j++) {

					sql += nameAtts.get(i).get(j);
					if (j < nameAtts.get(i).size() - 1) {
						sql += ", ";
					}

				}

				sql += ") VALUES (";

				for (int j = 0; j < args.get(i).size(); j++) {
					sql += "?";
					if (j < args.get(i).size() - 1) {
						sql += ", ";
					}
				}

				sql += ")";

				int rows = connectionWrapper.insert(sql, args.get(i).toArray());

				if (rows != 1) {
					throw new Exception(
							"La sentencia debería afectar un registro, la sentencia afecto " + rows + " registros.");
				}

			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	public synchronized void checkUnique(String tableName, String attName, String label, Object originalValue,
			Object newValue) throws Exception {

		if (originalValue != null && originalValue.equals(newValue) == false) {

			if (ifExists(tableName, attName, newValue)) {
				throw new UniqueException(label);
			}

		} else if (originalValue == null) {

			if (ifExists(tableName, attName, newValue)) {
				throw new UniqueException(label);
			}
		}

	}

	private synchronized boolean ifExists(String tableName, String attName, Object arg) throws Exception {

		String tableSQL = tableName;

		String attsSQL = "COUNT(" + "*" + ") ";
		String orderBySQL = null;
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		filtros.add(arg);
		if (arg instanceof String) {
			whereSQL += "LOWER(TRIM(massoftware.TRANSLATE(" + attName
					+ ")))::VARCHAR = LOWER(TRIM(massoftware.TRANSLATE(?)))::VARCHAR";
		} else {
			whereSQL += attName + " = ?";
		}

		// ==================================================================

		Object[][] table = BackendContextPG.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1,
				filtros.toArray());

		return (Long) table[0][0] > 0;

	}

	public synchronized boolean ifExists(String tableSQL, String[] attNames, Object[] args) throws Exception {

		String attsSQL = "COUNT(" + "*" + ") ";
		String orderBySQL = null;
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		for (int i = 0; i < attNames.length; i++) {
			Object arg = args[i];
			String attName = attNames[i];

			filtros.add(arg);
			if (arg instanceof String) {
				whereSQL += "LOWER(TRIM(massoftware.TRANSLATE(" + attName
						+ ")))::VARCHAR = LOWER(TRIM(massoftware.TRANSLATE(?)))::VARCHAR AND ";
			} else {
				whereSQL += attName + " = ? AND ";
			}

		}

		// ==================================================================

		whereSQL = whereSQL.trim();
		if (whereSQL.length() > 0) {
			whereSQL = whereSQL.substring(0, whereSQL.length() - 4);
		} else {
			whereSQL = null;
		}

		// ==================================================================

		Object[][] table = BackendContextPG.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1,
				filtros.toArray());

		return (Long) table[0][0] > 0;

	}

	public synchronized Object[] ifExistsByFkId(String tableSQLFK, String attNameFK, String idFK) throws Exception {

		String attsSQL = " * ";
		String orderBySQL = null;
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		filtros.add(idFK);
		whereSQL += attNameFK + " = ?";

		// ==================================================================

		Object[][] table = BackendContextPG.get().find(tableSQLFK, attsSQL, orderBySQL, whereSQL, -1, -1,
				filtros.toArray());

		return table[0];

	}

	// ================================================================================

}
