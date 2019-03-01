package org.cendra.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConnectionWrapper {

	private final String OPERATION_TYPE_BEGIN_TRANSACTION = "BEGIN_TRANSACTION";
	private final String OPERATION_TYPE_COMMIT_TRANSACTION = "COMMIT_TRANSACTION";
	private final String OPERATION_TYPE_ROLLBACK_TRANSACTION = "ROLLBACK_TRANSACTION";
	private final String OPERATION_TYPE_CLOSE_CONNECTION = "CLOSE_CONNECTION";
	private final String OPERATION_TYPE_SELECT = "SELECT";
	private final String OPERATION_TYPE_INSERT = "INSERT";
	private final String OPERATION_TYPE_UPDATE = "UPDATE";
	private final String OPERATION_TYPE_DELETE = "DELETE";
	private final String OPERATION_TYPE_EXECUTE = "EXECUTE";

	private final String TITLE_BEGIN_TRANSACTION = "Comienzo de Transacción";
	private final String TITLE_COMMIT_TRANSACTION = "Fin de Transacción";
	private final String TITLE_ROLLBACK_TRANSACTION = "Deshacer de Cambios de una Transacción";
	private final String TITLE_CLOSE_CONNECTION = "Cierre de Conexión";
	private final String TITLE_SELECT = "Consultando Listado de Registros";
	private final String TITLE_INSERT = "Insertando un Registro";
	private final String TITLE_UPDATE = "Actualizando un Registro";
	private final String TITLE_DELETE = "Borrando un Registro";
	private final String TITLE_EXECUTE = "Modificando la base de datos";

	private final String SUBJECT_BEGIN_TRANSACTION = "Error al intentar iniciar una transacción.";
	private final String SUBJECT_COMMIT_TRANSACTION = "Fin de Transacción";
	private final String SUBJECT_ROLLBACK_TRANSACTION = "Error al intentar deshacer los cambios de una transacción.";
	private final String SUBJECT_CLOSE_CONNECTION = "Error al intentar cerrar una conexión.";
	private final String SUBJECT_SELECT = "Error al intentar consultar un listado del total de registros.";
	private final String SUBJECT_INSERT = "Error al intentar insertar un registro.";
	private final String SUBJECT_UPDATE = "Error al intentar actualizar un registro.";
	private final String SUBJECT_DELETE = "Error al intentar borrar un registro.";
	private final String SUBJECT_EXECUTE = "Error al intentar modificar la base de datos.";

	private final String MSG_1 = "Se pretende agregar un parámetro a una sentencia sql que posee un tipo de dato desconocido. Se recibió [${value}] de tipo ${class}, y se espera String | Boolean | Short | Integer | Long | Float | Double | BigDecimal | Date | Timestamp | Time";

	private Connection connection;
	private DataSourceMetaData dataSourceMetaData;
	private DataSourceProperties dataSourceProperties;
	private List<String> sqlStatements = new ArrayList<String>();

	private boolean verbose = false;

	// ========================================================================================

	public ConnectionWrapper(Connection connection, DataSourceMetaData dataSourceMetaData,
			DataSourceProperties dataSourceProperties/* , LogPrinter logPrinter */) {

		this.connection = connection;
		this.dataSourceMetaData = dataSourceMetaData;
		this.dataSourceProperties = dataSourceProperties;
		this.verbose = this.dataSourceProperties.isVerbose();
	}

	// ========================================================================================

	public boolean isVerbose() {
		return verbose;
	}

	public void addSqlStatement(String sql) {
		sqlStatements.add(sql);
	}

	public List<String> getSqlStatements() {
		return sqlStatements;
	}

	public Connection getConnection() throws SQLException {
		return connection;
	}

	public DataSourceMetaData getDataSourceMetaData() {
		return dataSourceMetaData;
	}

	public String getUrl() {
		return dataSourceMetaData.url;
	}

	public String getUserName() {
		return dataSourceMetaData.userName;
	}

	public String getDatabaseProductName() {
		return dataSourceMetaData.databaseProductName;
	}

	public String getDatabaseProductVersion() {
		return dataSourceMetaData.databaseProductVersion;
	}

	public String getDriverName() {
		return dataSourceMetaData.driverName;
	}

	public String getDriverVersion() {
		return dataSourceMetaData.driverVersion;
	}

	public int getjDBCMajorVersion() {
		return dataSourceMetaData.jDBCMajorVersion;
	}

	public int getjDBCMinorVersion() {
		return dataSourceMetaData.jDBCMinorVersion;
	}

	// ========================================================================================

	public void begin() throws SQLException, Exception {
		begin(this);
	}

	public void begin(ConnectionWrapper connectionWrapper) throws SQLException, Exception {
		try {
			connectionWrapper.getConnection().setAutoCommit(false);
			connectionWrapper.addSqlStatement("begin();");
		} catch (SQLException e) {
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_BEGIN_TRANSACTION, TITLE_BEGIN_TRANSACTION,
					SUBJECT_BEGIN_TRANSACTION);
		}
	}

	public void commit() throws SQLException, Exception {
		commit(this);
	}

	public void commit(ConnectionWrapper connectionWrapper) throws SQLException, Exception {
		try {
			connectionWrapper.getConnection().commit();
			connectionWrapper.addSqlStatement("commit();");
			close(connectionWrapper);
		} catch (SQLException e) {
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_COMMIT_TRANSACTION, TITLE_COMMIT_TRANSACTION,
					SUBJECT_COMMIT_TRANSACTION);
		}
	}

	public void rollBack() throws SQLException, Exception {
		rollBack(this);
	}

	public void rollBack(ConnectionWrapper connectionWrapper) throws SQLException, Exception {
		try {
			connectionWrapper.getConnection().rollback();
			connectionWrapper.addSqlStatement("rollback();");
			close(connectionWrapper);
		} catch (SQLException e) {
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_ROLLBACK_TRANSACTION, TITLE_ROLLBACK_TRANSACTION,
					SUBJECT_ROLLBACK_TRANSACTION);
		}
	}

	public void close() throws SQLException, Exception {
		close(this);
	}

	public void close(ConnectionWrapper connectionWrapper) throws SQLException, Exception {
		try {
			if (connectionWrapper != null && connectionWrapper.getConnection() != null
					& connectionWrapper.getConnection().isClosed() == false) {
				connectionWrapper.getConnection().close();
				connectionWrapper.addSqlStatement("close();");
			}
		} catch (SQLException e) {
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_CLOSE_CONNECTION, TITLE_CLOSE_CONNECTION,
					SUBJECT_CLOSE_CONNECTION);
		}
	}

	// ===================================================================================================
	// ===================================================================================================

	@SuppressWarnings("rawtypes")
	public List findToListByCendraConvention(String sql) throws SQLExceptionWrapper, Exception {
		return findToListByCendraConvention(sql, new Object[0]);
	}

	@SuppressWarnings("rawtypes")
	public List findToListByCendraConvention(String sql, Object... args) throws SQLExceptionWrapper {

		try {

			PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

			printSQLWarning(preparedStatement.getWarnings());

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					set(preparedStatement, args[i], (i + 1));
				}
			}

			sql = formatSQL(preparedStatement, args, sql);

			addSqlStatement(sql);

			return executeQueryToListByCendraConvention(preparedStatement, sql);

		} catch (SQLException e) {
			printSQLEnd(buildPrintSQLStart(formatSQL(args, sql)), null, false);
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_SELECT, TITLE_SELECT, SUBJECT_SELECT);
		}

	}

	@SuppressWarnings("rawtypes")
	private List executeQueryToListByCendraConvention(PreparedStatement preparedStatement, String sql)
			throws SQLException {

		String msg = buildPrintSQLStart(sql);

		// System.out.println("SQL 1 " + sql);
		ZonedDateTime startTime = ZonedDateTime.now();
		ResultSet resultSet = preparedStatement.executeQuery();
		ZonedDateTime endTime = ZonedDateTime.now();
		// System.out.println("SQL 2 " + sql);
		printSQLWarning(resultSet.getWarnings());

		printSQLEnd(msg, Duration.between(startTime, endTime), true);

		MapperCendraConvention mapper = new MapperCendraConvention();
		List list = new ArrayList();
		try {
			list = mapper.listMapper(resultSet);
		} catch (IllegalAccessException e) {
			throw new SQLExceptionByCendraConvention(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace(); // 666
			throw new SQLExceptionByCendraConvention(e);
		} catch (InvocationTargetException e) {
			throw new SQLExceptionByCendraConvention(e);
		} catch (InstantiationException e) {
			throw new SQLExceptionByCendraConvention(e);
		} catch (ClassNotFoundException e) {
			throw new SQLExceptionByCendraConvention(e);
		}

		// if (resultSet != null && resultSet.isClosed() == false) {
		// resultSet.close();
		// }

		// if (preparedStatement != null && preparedStatement.isClosed() ==
		// false) {
		// preparedStatement.close();
		// }

		return list;
	}

	public Object[][] findToTable(String sql) throws SQLExceptionWrapper, Exception {
		return findToTable(sql, new Object[0]);
	}

	public Object[][] findToTable(String sql, Object... args) throws SQLExceptionWrapper {

		try {

			PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

			printSQLWarning(preparedStatement.getWarnings());

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					set(preparedStatement, args[i], (i + 1));
				}
			}

			sql = formatSQL(preparedStatement, args, sql);

			addSqlStatement(sql);

			return executeQueryToTable(preparedStatement, sql);

		} catch (SQLException e) {
			printSQLEnd(buildPrintSQLStart(formatSQL(args, sql)), null, false);
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_SELECT, TITLE_SELECT, SUBJECT_SELECT);
		}

	}

	private Object[][] executeQueryToTable(PreparedStatement preparedStatement, String sql) throws SQLException {

		List<Object[]> listT = new ArrayList<Object[]>();

		String msg = buildPrintSQLStart(sql);

		ZonedDateTime startTime = ZonedDateTime.now();

		ResultSet resultSet = preparedStatement.executeQuery();

		ZonedDateTime endTime = ZonedDateTime.now();

		printSQLWarning(resultSet.getWarnings());

		printSQLEnd(msg, Duration.between(startTime, endTime), true);

		int c = resultSet.getMetaData().getColumnCount();

		while (resultSet.next()) {
			Object[] row = new Object[c];

			for (int j = 0; j < c; j++) {
				row[j] = resultSet.getObject((j + 1));
			}

			listT.add(row);
		}

		// if (resultSet != null && resultSet.isClosed() == false) {
		// resultSet.close();
		// }

		// if (preparedStatement != null && preparedStatement.isClosed() ==
		// false) {
		// preparedStatement.close();
		// }

		Object[][] table = new Object[listT.size()][c];

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < c; j++) {
				table[i][j] = listT.get(i)[j];
			}

		}

		return table;
	}

	public ResultSet findToResultSet(String sql, Object... args) throws SQLExceptionWrapper, SQLException {

		try {

			PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

			printSQLWarning(preparedStatement.getWarnings());

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					set(preparedStatement, args[i], (i + 1));
				}
			}

			sql = formatSQL(preparedStatement, args, sql);

			addSqlStatement(sql);

			return executeQueryToResultSet(preparedStatement, sql);

		} catch (SQLException e) {
			printSQLEnd(buildPrintSQLStart(formatSQL(args, sql)), null, false);
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_SELECT, TITLE_SELECT, SUBJECT_SELECT);
		}

	}

	private ResultSet executeQueryToResultSet(PreparedStatement preparedStatement, String sql) throws SQLException {

		String msg = buildPrintSQLStart(sql);

		ZonedDateTime startTime = ZonedDateTime.now();

		ResultSet resultSet = preparedStatement.executeQuery();

		ZonedDateTime endTime = ZonedDateTime.now();

		printSQLWarning(resultSet.getWarnings());

		printSQLEnd(msg, Duration.between(startTime, endTime), true);

		// if (resultSet != null && resultSet.isClosed() == false) {
		// resultSet.close();
		// }

		// if (preparedStatement != null && preparedStatement.isClosed() ==
		// false) {
		// preparedStatement.close();
		// }

		return resultSet;
	}

	public int genericExecute(String sql) throws SQLExceptionWrapper, SQLException {
		return genericExecute(sql, new Object[0]);
	}

	public int genericExecute(String sql, Object... args) throws SQLExceptionWrapper, SQLException {

		try {

			// PreparedStatement preparedStatement = this.getConnection()
			// .prepareStatement(sql);
			// printSQLWarning(preparedStatement.getWarnings());
			//
			// if (args != null) {
			// for (int i = 0; i < args.length; i++) {
			// set(preparedStatement, args[i], (i + 1));
			// }
			// }
			//
			// sql = this.formatSQL(preparedStatement, args, sql);
			//
			// this.addSqlStatement(sql);
			//
			// return executeUpdateByExample(preparedStatement, sql);

			return execute(sql, args);

		} catch (SQLException e) {
			printSQLEnd(buildPrintSQLStart(formatSQL(args, sql)), null, false);
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_EXECUTE, TITLE_EXECUTE, SUBJECT_EXECUTE);
		}
	}

	public int insert(String sql) throws SQLExceptionWrapper, SQLException {
		return insert(sql, new Object[0]);
	}

	public int insert(String sql, Object... args) throws SQLExceptionWrapper, SQLException {

		try {

			// PreparedStatement preparedStatement = this.getConnection()
			// .prepareStatement(sql);
			// printSQLWarning(preparedStatement.getWarnings());
			//
			// if (args != null) {
			// for (int i = 0; i < args.length; i++) {
			// set(preparedStatement, args[i], (i + 1));
			// }
			// }
			//
			// sql = this.formatSQL(preparedStatement, args, sql);
			//
			// this.addSqlStatement(sql);
			//
			// return executeUpdateByExample(preparedStatement, sql);

			return execute(sql, args);

		} catch (SQLException e) {
			printSQLEnd(buildPrintSQLStart(formatSQL(args, sql)), null, false);
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_INSERT, TITLE_INSERT, SUBJECT_INSERT);
		}
	}

	public int update(String sql) throws SQLExceptionWrapper, SQLException {
		return update(sql, new Object[0]);
	}

	public int update(String sql, Object... args) throws SQLExceptionWrapper, SQLException {

		try {

			// PreparedStatement preparedStatement = this.getConnection()
			// .prepareStatement(sql);
			// printSQLWarning(preparedStatement.getWarnings());
			//
			// if (args != null) {
			// for (int i = 0; i < args.length; i++) {
			// set(preparedStatement, args[i], (i + 1));
			// }
			// }
			//
			// sql = this.formatSQL(preparedStatement, args, sql);
			//
			// this.addSqlStatement(sql);
			//
			// return executeUpdateByExample(preparedStatement, sql);

			return execute(sql, args);

		} catch (SQLException e) {
			printSQLEnd(buildPrintSQLStart(formatSQL(args, sql)), null, false);
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_UPDATE, TITLE_UPDATE, SUBJECT_UPDATE);
		}
	}

	public int dalete(String sql) throws SQLExceptionWrapper, SQLException {
		return delete(sql, new Object[0]);
	}

	public int delete(String sql, Object... args) throws SQLExceptionWrapper, SQLException {

		try {

			return execute(sql, args);

		} catch (SQLException e) {
			printSQLEnd(buildPrintSQLStart(formatSQL(args, sql)), null, false);
			throw this.buildSQLExceptionWrapper(e, OPERATION_TYPE_DELETE, TITLE_DELETE, SUBJECT_DELETE);
		}
	}

	private int execute(String sql, Object... args) throws SQLException {

		PreparedStatement preparedStatement = this.getConnection().prepareStatement(sql);

		printSQLWarning(preparedStatement.getWarnings());

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				set(preparedStatement, args[i], (i + 1));
			}
		}

		if (this.dataSourceMetaData.isDatabasePostgreSql()) {
			sql = preparedStatement.toString();
		} else {
			sql = this.formatSQL(preparedStatement, args, sql);
		}

		this.addSqlStatement(sql);

		return executeUpdateByExample(preparedStatement, sql);

	}

	private int executeUpdateByExample(PreparedStatement preparedStatement, String sql) throws SQLException {

		String msg = buildPrintSQLStart(sql);

		int r = 0;

		// System.out.println(preparedStatement);

		ZonedDateTime startTime = ZonedDateTime.now();

		r = preparedStatement.executeUpdate();

		ZonedDateTime endTime = ZonedDateTime.now();

		// System.out.println(preparedStatement);

		printSQLEnd(msg, Duration.between(startTime, endTime), true);

		// if (preparedStatement.isClosed() == false) {
		// preparedStatement.close();
		// }

		return r;
	}

	// ===================================================================================================

	private String buildPrintSQLStart(String sql) {

		String sep = "\n\n================================= SQL START =======================================\n\n";

		if (isVerbose()) {

			String msgSql = sep + "\n\n[..] Ejecutando SQL " + ZonedDateTime.now() + "\n[" + dataSourceMetaData + "]\n["
					+ dataSourceProperties.getUrl() + "]\n\n" + sql;

			System.out.println(msgSql);

			return msgSql;
		}

		return null;
	}

	private String printSQLEnd(String msgSql, Duration duration, boolean ok) {

		String sep = "\n\n================================= SQL END =========================================\n\n";

		if (isVerbose()) {

			String s = "";
			if (ok) {
				s = "[OK]";
			} else {
				s = "[ERROR]";
			}

			if (duration != null) {
				msgSql = "\n\n" + s + " SQL ejecutando (Total query runtime: " + duration.toMillis() + " Millis ) "
						+ ZonedDateTime.now() + "\n\n" + sep;
			} else {
				msgSql = "\n\n" + s + " SQL ejecutando " + ZonedDateTime.now() + "\n\n" + sep;
			}

			if (ok) {
				System.out.println(msgSql);
			} else {
				System.err.println(msgSql);
			}

			return msgSql;
		}

		return null;
	}

	private void printSQLWarning(SQLWarning sqlWarning) {

		String msg = "\n\nSQL WARNING " + ZonedDateTime.now() + "\n\n";

		String msg2 = "";

		while (sqlWarning != null) {

			msg2 += "Warning : " + sqlWarning.getErrorCode() + " Message : " + sqlWarning.getMessage() + " SQL state "
					+ sqlWarning.getSQLState() + "\n";

			sqlWarning = sqlWarning.getNextWarning();
		}

		if (msg2 != null && msg2.isEmpty() == false) {
			msg += msg2;
		}

		msg += "\n\nEND SQL WARNING " + ZonedDateTime.now() + "\n\n";

		if (isVerbose() && msg2 != null && msg2.isEmpty() == false) {
			System.out.println(msg);
		}
	}

	@SuppressWarnings("rawtypes")
	private void set(PreparedStatement preparedStatement, Object value, Integer i) throws SQLException {

		if (value != null) {

			if (value.getClass() == String.class) {

				preparedStatement.setString(i, (String) value);

			} else if (value.getClass() == Boolean.class) {

				preparedStatement.setBoolean(i, (Boolean) value);

			} else if (value.getClass() == Short.class) {

				preparedStatement.setShort(i, (Short) value);

			} else if (value.getClass() == Integer.class) {

				preparedStatement.setInt(i, (Integer) value);

			} else if (value.getClass() == Long.class) {

				preparedStatement.setLong(i, (Long) value);

			} else if (value.getClass() == Float.class) {

				preparedStatement.setFloat(i, (Float) value);

			} else if (value.getClass() == Double.class) {

				preparedStatement.setDouble(i, (Double) value);

			} else if (value.getClass() == BigDecimal.class) {

				preparedStatement.setBigDecimal(i, (BigDecimal) value);

			} else if (value.getClass() == Date.class) {

				preparedStatement.setDate(i, (Date) value);

			} else if (value.getClass() == java.util.Date.class) {

				Date sqlDate = new Date(((java.util.Date) value).getTime());

				preparedStatement.setDate(i, sqlDate);

			} else if (value.getClass() == Timestamp.class) {

				preparedStatement.setTimestamp(i, (Timestamp) value);

			} else if (value.getClass() == Time.class) {

				preparedStatement.setTime(i, (Time) value);

			} else if (value instanceof Class) {

				Class c = (Class) value;

				if (c.equals(String.class)) {
					preparedStatement.setNull(i, Types.VARCHAR);
				} else if (c.equals(Boolean.class)) {
					preparedStatement.setNull(i, Types.BOOLEAN);
				} else if (c.equals(Short.class)) {
					preparedStatement.setNull(i, Types.SMALLINT);
				} else if (c.equals(Integer.class)) {
					preparedStatement.setNull(i, Types.INTEGER);
				} else if (c.equals(Long.class)) {
					preparedStatement.setNull(i, Types.BIGINT);
				} else if (c.equals(Float.class)) {
					preparedStatement.setNull(i, Types.REAL);
				} else if (c.equals(Double.class)) {
					preparedStatement.setNull(i, Types.FLOAT);
				} else if (c.equals(BigDecimal.class)) {
					preparedStatement.setNull(i, Types.NUMERIC);
				} else if (c.equals(Date.class)) {
					preparedStatement.setNull(i, Types.DATE);
				} else if (c.equals(java.util.Date.class)) {
					preparedStatement.setNull(i, Types.DATE);
				} else if (c.equals(Timestamp.class)) {
					preparedStatement.setNull(i, Types.TIMESTAMP);
				} else if (c.equals(Time.class)) {
					preparedStatement.setNull(i, Types.TIME);
				} else {
					throw new IllegalArgumentException(MSG_1.replace("${value}", value.toString()).replace("${class}",
							value.getClass().getSimpleName()));
				}

			} else {
				throw new IllegalArgumentException(MSG_1.replace("${value}", value.toString()).replace("${class}",
						value.getClass().getCanonicalName()));
			}
		}
	}

	private SQLExceptionWrapper buildSQLExceptionWrapper(SQLException sQLException, String operationType, String title,
			String subject) {

		sQLException.printStackTrace(); // 666

		SQLExceptionWrapper sqlExceptionWrapper = new SQLExceptionWrapper(sQLException);

		sqlExceptionWrapper.setTitle(title);
		sqlExceptionWrapper.setSubject(subject);

		sqlExceptionWrapper.setOperationType(operationType);

		if (this.dataSourceProperties != null) {
			sqlExceptionWrapper.setDriverClassName(dataSourceProperties.getDriverClassName());
			sqlExceptionWrapper.setInitialSize(dataSourceProperties.getInitialSize());
			sqlExceptionWrapper.setMaxActive(dataSourceProperties.getMaxActive());
			sqlExceptionWrapper.setMaxIdle(dataSourceProperties.getMaxIdle());
			sqlExceptionWrapper.setValidationQuery(dataSourceProperties.getValidationQuery());
		}

		if (dataSourceMetaData != null) {
			sqlExceptionWrapper.setDatabaseProductName(dataSourceMetaData.getDatabaseProductName());
			sqlExceptionWrapper.setDatabaseProductVersion(dataSourceMetaData.getDatabaseProductVersion());
			sqlExceptionWrapper.setDriverName(dataSourceMetaData.getDriverName());
			sqlExceptionWrapper.setDriverVersion(dataSourceMetaData.getDriverVersion());
			sqlExceptionWrapper.setjDBCMajorVersion(dataSourceMetaData.getjDBCMajorVersion());
			sqlExceptionWrapper.setjDBCMinorVersion(dataSourceMetaData.getjDBCMinorVersion());
			sqlExceptionWrapper.setUrl(dataSourceMetaData.getUrl());
			sqlExceptionWrapper.setUserName(dataSourceMetaData.getUserName());

		} else if (this.dataSourceProperties != null) {
			sqlExceptionWrapper.setUrl(dataSourceProperties.getUrl());
			sqlExceptionWrapper.setUserName(dataSourceProperties.getUserName());
		}

		sqlExceptionWrapper.setSqlStatements(sqlStatements);

		return sqlExceptionWrapper;

	}

	private String formatSQL(PreparedStatement preparedStatement, Object[] args, String sql) throws SQLException {

		if (preparedStatement.getConnection().getMetaData().getDatabaseProductName().equalsIgnoreCase("PostgreSQL")) {

			sql = preparedStatement.toString();
			if (sql.trim().endsWith(";") == false) {
				sql += ";";
			}

			String argsString = "";
			for (int i = 0; i < args.length; i++) {
				argsString += "[" + args[i] + "] ";
			}
			argsString = argsString.trim();
			if (args.length > 0) {
				sql += "\nargs = " + argsString;
			}

		} else {

			if (sql.trim().endsWith(";") == false) {
				sql += ";";
			}

			String argsString = "";
			for (int i = 0; i < args.length; i++) {
				argsString += "[" + args[i] + "] ";
			}
			argsString = argsString.trim();
			if (args.length > 0) {
				sql += "\nargs = " + argsString;
			}
		}

		return sql.trim();
	}

	private String formatSQL(Object[] args, String sql) {

		if (sql.trim().endsWith(";") == false) {
			sql += ";";
		}

		String argsString = "";
		for (int i = 0; i < args.length; i++) {
			argsString += "[" + args[i] + "] ";
		}
		argsString = argsString.trim();
		if (args.length > 0) {
			sql += "\nargs = " + argsString;
		}

		return sql.trim();
	}

}
