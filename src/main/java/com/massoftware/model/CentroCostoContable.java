package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Centro de costo", plural = "Centros de costo", singularPre = "el centro de costo", pluralPre = "los centros de costo")
public class CentroCostoContable extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Ejercicio contable", required = true, readOnly = true, columns = 10)
	private EjercicioContable ejercicioContable;

	@FieldConfAnont(label = "Nº centro de costo", required = true)
	private Integer numero;

	@FieldConfAnont(label = "Nombre", required = true)
	private String nombre;

	@FieldConfAnont(label = "Abreviatura", required = true)
	private String abreviatura;

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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Override
	public String toString() {

		if (ejercicioContable != null && ejercicioContable.getNumero() != null) {
			return "(" + ejercicioContable.getNumero() + "-" + numero + ") " + nombre;
		}

		return "(" + numero + ") " + nombre;

	}

	public List<CentroCostoContable> find(CentrosCostoContableFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro);
	}

	public List<CentroCostoContable> find(int limit, int offset, Map<String, Boolean> orderBy,
			CentrosCostoContableFiltro filtro) throws Exception {

		filtro.setterTrim();

		List<CentroCostoContable> listado = new ArrayList<CentroCostoContable>();

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

		List<EntityId> items = findUtil(orderBySQL, whereSQL, limit, offset, filtros.toArray(), 0);

		for (EntityId item : items) {
			listado.add((CentroCostoContable) item);
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
			numeroOriginal = ((CentroCostoContable) this._originalDTO).getNumero();
			if (((CentroCostoContable) this._originalDTO).getEjercicioContable() != null) {
				idEjercicioContableOriginal = ((CentroCostoContable) this._originalDTO).getEjercicioContable().getId();
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
			nombreOriginal = ((CentroCostoContable) this._originalDTO).getNombre();
			if (((CentroCostoContable) this._originalDTO).getEjercicioContable() != null) {
				idEjercicioContableOriginal = ((CentroCostoContable) this._originalDTO).getEjercicioContable().getId();
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
	
	public void checkUniqueEjercicioContableAndAbreviatura() throws Exception {

		String labelEjercicioContable = this.label("ejercicioContable");
		String labelAbreviatura = this.label("abreviatura");

		// -----------------------------------------------

		String idEjercicioContableOriginal = null;
		String nombreOriginal = null;
		if (this._originalDTO != null) {
			nombreOriginal = ((CentroCostoContable) this._originalDTO).getAbreviatura();
			if (((CentroCostoContable) this._originalDTO).getEjercicioContable() != null) {
				idEjercicioContableOriginal = ((CentroCostoContable) this._originalDTO).getEjercicioContable().getId();
			}
		}

		// -----------------------------------------------

		String idEjercicioContable = null;
		if (this.getEjercicioContable() != null) {
			idEjercicioContable = this.getEjercicioContable().getId();
		}
		String abreviatura = this.getAbreviatura();

		// -----------------------------------------------

		String[] attNames = { "ejercicioContable", "abreviatura" };
		Object[] args = { idEjercicioContable, abreviatura };

		// -----------------------------------------------

		if (idEjercicioContableOriginal != null && nombreOriginal != null && idEjercicioContable != null
				&& abreviatura != null && (idEjercicioContableOriginal.equals(idEjercicioContable) == false
						|| nombreOriginal.equals(abreviatura) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelAbreviatura);
			}

		} else if (idEjercicioContableOriginal == null && nombreOriginal == null && idEjercicioContable != null
				&& abreviatura != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelAbreviatura);
			}
		}

	}

}
