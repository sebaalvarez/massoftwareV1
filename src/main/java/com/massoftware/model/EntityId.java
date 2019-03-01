package com.massoftware.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;
import org.cendra.ex.crud.NullFieldException;
import org.cendra.jdbc.SQLExceptionWrapper;

import com.massoftware.backend.BackendContextPG;

public class EntityId extends Entity implements Comparable<EntityId> {

	// Es la cantidad de tablas relacionadas que se van a consultar hacia la
	// izquierda
	protected int _defMaxLevel = 3;

	protected String id;

	public EntityId _originalDTO;

	public void loadById() throws Exception {
		loadById(getId(), _defMaxLevel);
	}

	public void loadById(int maxLevel) throws Exception {
		loadById(getId(), maxLevel);
	}

	public void loadById(String id) throws Exception {
		loadById(id, _defMaxLevel);
	}

	public void loadById(String id, int maxLevel) throws Exception {

		if (id != null && id.trim().length() > 0) {

			Object[] row = BackendContextPG.get().findById(this.getClass().getSimpleName(), id);

			setter(row, 0, maxLevel);
		}

	}

	public void setter(Object[] row) throws Exception {
		setter(row, 0, _defMaxLevel);
	}

	public void setter(Object[] row, int maxLevel) throws Exception {
		setter(row, 0, maxLevel);
	}

	public void setter(Object[] row, int level, int maxLevel) throws Exception {

		if (row == null) {
			this.setterNull();
			return;
		}

		Field[] fields = getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];

			if (field.getName().startsWith("_") == false) {

				@SuppressWarnings("rawtypes")
				Class[] argTypes = new Class[] { field.getType() };

				Method methodSet = this.getClass().getDeclaredMethod("set" + toCamelCase(field.getName()), argTypes);

				methodSet.invoke(this, new Object[] { null }); // para limpiar el objeto

				if (isScalar(field.getType())) {
//					System.out.println(field + " __________ " + row[i]);
					methodSet.invoke(this, row[i]);

				} else if (row[i] != null) {

					EntityId other = (EntityId) field.getType().newInstance();

					if (level < maxLevel) {
						Object[] rowOther = BackendContextPG.get().findById(field.getType().getSimpleName(),
								row[i].toString());
						other.setter(rowOther, level + 1, maxLevel);
					} else {
						other.setId(row[i].toString());
					}

					methodSet.invoke(this, other);
				}

			}

		}

		_originalDTO = (EntityId) this.clone();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		EntityId other = (EntityId) obj;

		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;

		return true;
	}

	@Override
	public int compareTo(EntityId entityId) {

		return this.getId().compareTo(entityId.getId());
	}

	@Override
	public String toString() {

		if (this.getId() != null) {
			return this.getId();
		}

		return "";
	}

	public String insert() throws Exception {

		if (this.getId() == null || this.getId().trim().length() == 0) {
			this.setId(UUID.randomUUID().toString());
		}

		Field[] fields = getClass().getDeclaredFields();

		String[] nameAtts = new String[fields.length];
		Object[] args = new Object[fields.length];

		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];

			String nameAttDB = fields[i].getName();
			nameAtts[i] = nameAttDB;

			Method methodGet = getClass().getDeclaredMethod("get" + toCamelCase(field.getName()));

			args[i] = methodGet.invoke(this);

			if (args[i] == null && isScalar(fields[i].getType()) == true) {

				args[i] = fields[i].getType();

			} else if (args[i] == null && isScalar(fields[i].getType()) == false) {

				args[i] = String.class;

			} else if (args[i] != null && isScalar(fields[i].getType()) == false) {

				methodGet = fields[i].getType().getMethod("getId");
				args[i] = methodGet.invoke(args[i]);
				if (args[i] == null) {
					args[i] = String.class;
				}
			}
		}

		BackendContextPG.get().insert(getClass().getSimpleName(), nameAtts, args);

		return getId();
	}

	public String update() throws Exception {

		Field[] fields = getClass().getDeclaredFields();

		String[] nameAtts = new String[fields.length];
		Object[] args = new Object[fields.length + 1];

		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];

			String nameAttDB = field.getName();
			nameAtts[i] = nameAttDB;

			Method methodGet = getClass().getDeclaredMethod("get" + toCamelCase(field.getName()));

			args[i] = methodGet.invoke(this);

			if (args[i] == null && isScalar(fields[i].getType()) == true) {

				args[i] = fields[i].getType();

			} else if (args[i] == null && isScalar(fields[i].getType()) == false) {

				args[i] = String.class;

			} else if (args[i] != null && isScalar(fields[i].getType()) == false) {

				methodGet = field.getType().getMethod("getId");
				args[i] = methodGet.invoke(args[i]);
				if (args[i] == null) {
					args[i] = String.class;
				}
			}
		}

		args[fields.length] = this._originalDTO.getId();

		BackendContextPG.get().update(getClass().getSimpleName(), nameAtts, args, "id = ?");

		return this.getId();
	}

	public void checkUnique(String attName, String label, boolean ignoreOriginal) throws Exception {
		checkUnique(null, attName, label, ignoreOriginal);
	}

	public void checkUnique(Object value, String attName, String label, boolean ignoreOriginal) throws Exception {

		Object valueOriginal = null;

		if (_originalDTO != null && ignoreOriginal == false) {

			Field fieldOriginal = _originalDTO.getClass().getDeclaredField(attName);

			Method methodGetOriginal = this.getClass().getDeclaredMethod("get" + toCamelCase(fieldOriginal.getName()));

			valueOriginal = methodGetOriginal.invoke(this);

		}

		if (value == null) {

			Field field = this.getClass().getDeclaredField(attName);

			Method methodGet = this.getClass().getDeclaredMethod("get" + toCamelCase(field.getName()));

			value = methodGet.invoke(this);

		}

		if (value != null) {
			BackendContextPG.get().checkUnique(this.getClass().getSimpleName(), attName, label, valueOriginal, value);
		}

	}

	public void checkNull(String attName) throws Exception {

		Field field = this.getClass().getDeclaredField(attName);

		Method methodGet = this.getClass().getDeclaredMethod("get" + toCamelCase(field.getName()));

		Object value = methodGet.invoke(this);

		if (value == null) {
			throw new NullFieldException(attName);
		}
	}

	public Integer maxValueInteger(String attName) throws Exception {

		return BackendContextPG.get().maxValueInteger(attName, this.getClass().getSimpleName());

	}

	public Object maxValue(String[] attNames, String attNameCount) throws Exception {

		List<Object> filtros = new ArrayList<Object>();

		for (String attName : attNames) {

			Field field = getClass().getDeclaredField(attName);

			Method methodGet = this.getClass().getDeclaredMethod("get" + toCamelCase(field.getName()));

			Object value = null;

			if (isScalar(field.getType())) {

				value = methodGet.invoke(this);

			} else {

				EntityId other = (EntityId) methodGet.invoke(this);

				if (other != null) {
					value = other.getId();
				}

			}

			filtros.add(value);

		}

		return BackendContextPG.get().maxValueInteger(this.getClass().getSimpleName(), attNames, attNameCount,
				filtros.toArray());
	}

	protected List<EntityId> findUtil(String orderBy, String where, int limit, int offset, Object[] args, int maxLevel)
			throws Exception {

		Object[][] table = BackendContextPG.get().find(this.getClass().getSimpleName(), "*", orderBy, where, limit,
				offset, args);

		List<EntityId> items = new ArrayList<EntityId>();

		for (int i = 0; i < table.length; i++) {
			EntityId item = this.getClass().newInstance();
			item.setter(table[i], maxLevel);

			items.add(item);
		}

		return items;
	}

	public void delete() throws Exception {

		checkFKForDelete();

		// ==================================================================

		try {
			BackendContextPG.get().delete(this.getClass().getSimpleName(), getId());
		} catch (SQLExceptionWrapper e) {

			if ("23503".equals(e.getSQLState())) {

				throw new DeleteForeingObjectConflictException(this);
			}

			throw e;

		}

	}

	@SuppressWarnings({ "rawtypes" })
	private void checkFKForDelete() throws Exception {

		String packageModel = "com.massoftware.model";

		String[] classNames = { "Banco", "Caja", "ComprobanteDeFondo", "CuentaFondo", "CuentaFondoGrupo",
				"CuentaFondoRubro", "CuentaFondoTipo", "Firmante", "SeguridadModulo", "SeguridadPuerta", "Sucursal",
				"SucursalTipo", "Talonario", "TalonarioControladorFizcal", "TalonarioLetra", "TipoComprobante",
				"Zona" };

		for (String nameJavaClass : classNames) {

			Class objClass = Class.forName(packageModel + "." + nameJavaClass);

			Field[] fields = objClass.getDeclaredFields();

			for (Field field : fields) {

				if (this.getClass().equals(field.getType())) {

					Object[] row = BackendContextPG.get().findByFkId(objClass.getSimpleName(), field.getName(),
							this.getId());

					EntityId entityId = (EntityId) objClass.newInstance();

					entityId.setter(row, 0, _defMaxLevel);

					if (entityId.getId() != null) {

						throw new DeleteForeingObjectConflictException(this, entityId.labelSingularPre(), entityId);
					}

					break;
				}
			}

		}

	}

	// private boolean check(Banco itemOriginal, Banco item) throws Exception {
	//
	// // ==================================================================
	// // CHECKs NULLs
	//
	//
	// if (item.getNumero() == null) {
	// throw new NullFieldException("Numero");
	// }
	// if (item.getNombre() == null) {
	// throw new NullFieldException("Nombre");
	// }
	// if (item.getCuit() == null) {
	// throw new NullFieldException("CUIT");
	// }
	//
	// // ==================================================================
	// // CHECKs UNIQUE
	//
	//// if (itemOriginal != null) {
	//// checkUniqueNumero("Numero", itemOriginal.getNumero(), item.getNumero());
	//// checkUniqueNombre("Nombre", itemOriginal.getNombre(), item.getNombre());
	//// checkUniqueNombreOficial("Nombre oficial", itemOriginal.getNombreOficial(),
	// item.getNombreOficial());
	//// checkUniqueCuit("CUIT", itemOriginal.getCuit(), item.getCuit());
	//// } else {
	//// checkUniqueNumero("Numero", null, item.getNumero());
	//// checkUniqueNombre("Nombre", null, item.getNombre());
	//// checkUniqueNombreOficial("Nombre oficial", null, item.getNombreOficial());
	//// checkUniqueCuit("CUIT", null, item.getCuit());
	//// }
	//
	// // ==================================================================
	//
	// return true;
	// }

}
