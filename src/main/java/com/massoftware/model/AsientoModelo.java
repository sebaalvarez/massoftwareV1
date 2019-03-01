package com.massoftware.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Asiento modelo", plural = "Asientos modelo", singularPre = "el asiento modelo", pluralPre = "los asientos modelo")
public class AsientoModelo extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Ejercicio contable", required = true, readOnly = true, columns = 10)
	private EjercicioContable ejercicioContable;

	@FieldConfAnont(label = "Nº asiento modelo", required = true)
	private Integer numero;

	@FieldConfAnont(label = "Nombre", required = true)
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {

		if (ejercicioContable != null && ejercicioContable.getNumero() != null) {
			return "(" + ejercicioContable.getNumero() + "-" + numero + ") " + nombre;
		}

		return "(" + numero + ") " + nombre;

	}

	public List<AsientoModelo> find(AsientosModeloFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro);
	}

	public List<AsientoModelo> find(int limit, int offset, Map<String, Boolean> orderBy, AsientosModeloFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<AsientoModelo> listado = new ArrayList<AsientoModelo>();

		String orderBySQL = "ejercicioContable, numero";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getNumero() != null) {
			filtros.add(filtro.getNumero());
			whereSQL += "numero" + " = ? AND ";
		}
		if (filtro.getNombre() != null) {
			String[] palabras = filtro.getNombre().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "nombre"
						+ "))::VARCHAR ILIKE ('%' || TRIM(massoftware.TRANSLATE(?)) || '%')::VARCHAR AND ";
			}
		}
		if (filtro.getEjercicioContable() != null && filtro.getEjercicioContable().getId() != null) {
			filtros.add(filtro.getEjercicioContable().getId());
			whereSQL += "ejercicioContable" + " = ? AND ";
		}

		// ==================================================================

		whereSQL = whereSQL.trim();
		if (whereSQL.length() > 0) {
			whereSQL = whereSQL.substring(0, whereSQL.length() - 4);
		} else {
			whereSQL = null;
		}

		// ==================================================================

		List<EntityId> items = findUtil(orderBySQL, whereSQL, limit, offset, filtros.toArray(), 1);

		for (EntityId item : items) {
			listado.add((AsientoModelo) item);
		}

		return listado;
	}

	public void checkUniqueEjercicioContableAndNumero() throws Exception {

		String labelEjercicioContable = this.label("ejercicioContable");
		String labelNumero = this.label("numero");

		// -----------------------------------------------

		String idEjercicioContableOriginal = null;
		Integer numeroOriginal = null;
		if (this._originalDTO != null) {
			numeroOriginal = ((AsientoModelo) this._originalDTO).getNumero();
			if (((AsientoModelo) this._originalDTO).getEjercicioContable() != null) {
				idEjercicioContableOriginal = ((AsientoModelo) this._originalDTO).getEjercicioContable().getId();
			}
		}

		// -----------------------------------------------

		String idEjercicioContable = null;
		if (this.getEjercicioContable() != null) {
			idEjercicioContable = this.getEjercicioContable().getId();
		}
		Integer numero = this.getNumero();

		// -----------------------------------------------

		String[] attNames = { "ejercicioContable", "numero" };
		Object[] args = { idEjercicioContable, numero };

		// -----------------------------------------------

		if (idEjercicioContableOriginal != null && numeroOriginal != null && idEjercicioContable != null
				&& numero != null && (idEjercicioContableOriginal.equals(idEjercicioContable) == false
						|| numeroOriginal.equals(numero) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelNumero);
			}

		} else if (idEjercicioContableOriginal == null && numeroOriginal == null && idEjercicioContable != null
				&& numero != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelNumero);
			}
		}

	}

	public void checkUniqueEjercicioContableAndNombre() throws Exception {

		String labelEjercicioContable = this.label("ejercicioContable");
		String labelNombre = this.label("nombre");

		// -----------------------------------------------

		String idEjercicioContableOriginal = null;
		String nombreOriginal = null;
		if (this._originalDTO != null) {
			nombreOriginal = ((AsientoModelo) this._originalDTO).getNombre();
			if (((AsientoModelo) this._originalDTO).getEjercicioContable() != null) {
				idEjercicioContableOriginal = ((AsientoModelo) this._originalDTO).getEjercicioContable().getId();
			}
		}

		// -----------------------------------------------

		String idEjercicioContable = null;
		if (this.getEjercicioContable() != null) {
			idEjercicioContable = this.getEjercicioContable().getId();
		}
		String nombre = this.getNombre();

		// -----------------------------------------------

		String[] attNames = { "ejercicioContable", "nombre" };
		Object[] args = { idEjercicioContable, nombre };

		// -----------------------------------------------

		if (idEjercicioContableOriginal != null && nombreOriginal != null && idEjercicioContable != null
				&& nombre != null && (idEjercicioContableOriginal.equals(idEjercicioContable) == false
						|| nombreOriginal.equals(nombre) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelNombre);
			}

		} else if (idEjercicioContableOriginal == null && nombreOriginal == null && idEjercicioContable != null
				&& nombre != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelNombre);
			}
		}

	}

	public String insert(List<AsientoModeloItem> items) throws Exception {

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

		// ----------------------------------------------------------------------

		List<String> tableNamesMatrix = new ArrayList<String>();
		List<List<String>> nameAttsMatrix = new ArrayList<List<String>>();
		List<List<Object>> argsMatrix = new ArrayList<List<Object>>();

		tableNamesMatrix.add(getClass().getSimpleName());

		List<String> nameAttsList = new ArrayList<String>();

		for (String s : nameAtts) {
			nameAttsList.add(s);
		}

		nameAttsMatrix.add(nameAttsList);

		List<Object> argsList = new ArrayList<Object>();

		for (Object o : args) {
			argsList.add(o);
		}

		argsMatrix.add(argsList);

		// ----------------------------------------------------------------------

		for (AsientoModeloItem item : items) {

			item.setAsientoModelo(this);

			tableNamesMatrix.add(item.getClass().getSimpleName());

			// if (item.getId() == null || item.getId().trim().length() == 0) {
			// item.setId(UUID.randomUUID().toString());
			// }

			item.setId(UUID.randomUUID().toString());

			Field[] fields2 = item.getClass().getDeclaredFields();

			String[] nameAtts2 = new String[fields2.length];
			Object[] args2 = new Object[fields2.length];

			for (int i = 0; i < fields2.length; i++) {

				Field field2 = fields2[i];

				String nameAttDB2 = fields2[i].getName();
				nameAtts2[i] = nameAttDB2;

				Method methodGet = item.getClass().getDeclaredMethod("get" + toCamelCase(field2.getName()));

				args2[i] = methodGet.invoke(item);

				if (args2[i] == null && isScalar(fields2[i].getType()) == true) {

					args2[i] = fields2[i].getType();

				} else if (args2[i] == null && isScalar(fields2[i].getType()) == false) {

					args2[i] = String.class;

				} else if (args2[i] != null && isScalar(fields2[i].getType()) == false) {

					methodGet = fields2[i].getType().getMethod("getId");
					args2[i] = methodGet.invoke(args2[i]);
					if (args2[i] == null) {
						args2[i] = String.class;
					}
				}
			}

			nameAttsList = new ArrayList<String>();

			for (String s : nameAtts2) {
				nameAttsList.add(s);
			}

			nameAttsMatrix.add(nameAttsList);

			argsList = new ArrayList<Object>();

			for (Object o : args2) {
				argsList.add(o);
			}

			argsMatrix.add(argsList);

		}

		// ----------------------------------------------------------------------

		BackendContextPG.get().insert(tableNamesMatrix, nameAttsMatrix, argsMatrix);

		return getId();
	}

	public String update(List<AsientoModeloItem> items) throws Exception {

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

		// ----------------------------------------------------------------------

		List<String> tableNamesMatrix = new ArrayList<String>();
		List<List<String>> nameAttsMatrix = new ArrayList<List<String>>();
		List<List<Object>> argsMatrix = new ArrayList<List<Object>>();
		List<String> whereMatrix = new ArrayList<String>();
		List<Integer> operationMatrix = new ArrayList<Integer>();

		// ----------------------------------------------------------------------

		operationMatrix.add(2);

		tableNamesMatrix.add(getClass().getSimpleName());

		List<String> nameAttsList = new ArrayList<String>();

		for (String s : nameAtts) {
			nameAttsList.add(s);
		}

		nameAttsMatrix.add(nameAttsList);

		List<Object> argsList = new ArrayList<Object>();

		for (Object o : args) {
			argsList.add(o);
		}

		argsMatrix.add(argsList);

		whereMatrix.add("id = ?");

		// ----------------------------------------------------------------------

		operationMatrix.add(1);

		tableNamesMatrix.add(AsientoModeloItem.class.getSimpleName());

		nameAttsList = new ArrayList<String>();
		
		nameAttsMatrix.add(nameAttsList);

		argsList = new ArrayList<Object>();
		
		argsList.add(this.getId());
		
		argsMatrix.add(argsList);

		whereMatrix.add("asientoModelo = ?");

		// ----------------------------------------------------------------------

		for (AsientoModeloItem item : items) {
			
			operationMatrix.add(3);

			item.setAsientoModelo(this);

			tableNamesMatrix.add(item.getClass().getSimpleName());

			// if (item.getId() == null || item.getId().trim().length() == 0) {
			// item.setId(UUID.randomUUID().toString());
			// }

			item.setId(UUID.randomUUID().toString());

			Field[] fields2 = item.getClass().getDeclaredFields();

			String[] nameAtts2 = new String[fields2.length];
			Object[] args2 = new Object[fields2.length];

			for (int i = 0; i < fields2.length; i++) {

				Field field2 = fields2[i];

				String nameAttDB2 = fields2[i].getName();
				nameAtts2[i] = nameAttDB2;

				Method methodGet = item.getClass().getDeclaredMethod("get" + toCamelCase(field2.getName()));

				args2[i] = methodGet.invoke(item);

				if (args2[i] == null && isScalar(fields2[i].getType()) == true) {

					args2[i] = fields2[i].getType();

				} else if (args2[i] == null && isScalar(fields2[i].getType()) == false) {

					args2[i] = String.class;

				} else if (args2[i] != null && isScalar(fields2[i].getType()) == false) {

					methodGet = fields2[i].getType().getMethod("getId");
					args2[i] = methodGet.invoke(args2[i]);
					if (args2[i] == null) {
						args2[i] = String.class;
					}
				}
			}

			nameAttsList = new ArrayList<String>();

			for (String s : nameAtts2) {
				nameAttsList.add(s);
			}

			nameAttsMatrix.add(nameAttsList);

			argsList = new ArrayList<Object>();

			for (Object o : args2) {
				argsList.add(o);
			}

			argsMatrix.add(argsList);

		}

		// ----------------------------------------------------------------------

		BackendContextPG.get().update(tableNamesMatrix, nameAttsMatrix, argsMatrix, whereMatrix, operationMatrix);

		return this.getId();
	}

}
