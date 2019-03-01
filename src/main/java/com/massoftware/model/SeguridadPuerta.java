package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Puerta", plural = "Puertas", singularPre = "la puerta", pluralPre = "las puertas")
public class SeguridadPuerta extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Módulo", required = true)
	private SeguridadModulo seguridadModulo;

	@FieldConfAnont(label = "Nº puerta", required = true)
	private Integer numero;

	@FieldConfAnont(label = "Nombre", required = true)
	private String nombre;

	@FieldConfAnont(label = "I.D", required = true)
	private String equate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SeguridadModulo getSeguridadModulo() {
		return seguridadModulo;
	}

	public void setSeguridadModulo(SeguridadModulo seguridadModulo) {
		this.seguridadModulo = seguridadModulo;
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

	public String getEquate() {
		return equate;
	}

	public void setEquate(String equate) {
		this.equate = equate;
	}

	public List<SeguridadPuerta> find(SeguridadPuertasFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro);
	}

	public List<SeguridadPuerta> find(int limit, int offset, Map<String, Boolean> orderBy,
			SeguridadPuertasFiltro filtro) throws Exception {

		filtro.setterTrim();

		List<SeguridadPuerta> listado = new ArrayList<SeguridadPuerta>();

		String orderBySQL = "numero";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getIdModulo() != null) {
			filtros.add(filtro.getIdModulo());
			whereSQL += "seguridadModulo" + " = ? AND ";
		}

		// ==================================================================

		whereSQL = whereSQL.trim();
		if (whereSQL.length() > 0) {
			whereSQL = whereSQL.substring(0, whereSQL.length() - 4);
		} else {
			whereSQL = null;
		}

		// ==================================================================

		String sql = "SELECT " + this.getClass().getSimpleName() + ".* FROM massoftware."
				+ this.getClass().getSimpleName();

		if (whereSQL != null && whereSQL.trim().length() > 0) {
			sql += " WHERE " + whereSQL;
		}

		if (orderBySQL != null && orderBySQL.trim().length() > 0) {
			sql += " ORDER BY " + orderBySQL;
		} else {
			sql += " ORDER BY " + 1;
		}

		if (offset > -1 && limit > -1) {

			sql += " OFFSET " + offset + " LIMIT " + limit;
		}

		Object[][] table = BackendContextPG.get().find(sql, limit, offset, filtros.toArray());

		for (int i = 0; i < table.length; i++) {
			SeguridadPuerta item = new SeguridadPuerta();
			item.setter(table[i], 3);

			listado.add(item);
		}

		return listado;
	}

	public void checkUniqueModuloAndNumero() throws Exception {

		String labelModulo = this.label("seguridadModulo");
		String labelNumero = this.label("numero");

		// -----------------------------------------------

		String idModuloOriginal = null;
		Integer numeroOriginal = null;
		if (this._originalDTO != null) {
			numeroOriginal = ((SeguridadPuerta) this._originalDTO).getNumero();
			if (((SeguridadPuerta) this._originalDTO).getSeguridadModulo() != null) {
				idModuloOriginal = ((SeguridadPuerta) this._originalDTO).getSeguridadModulo().getId();
			}
		}

		// -----------------------------------------------

		String idModulo = null;
		if (this.getSeguridadModulo() != null) {
			idModulo = this.getSeguridadModulo().getId();
		}
		Integer numero = this.getNumero();

		// -----------------------------------------------

		String[] attNames = { "seguridadModulo", "numero" };
		Object[] args = { idModulo, numero };

		// -----------------------------------------------

		if (idModuloOriginal != null && numeroOriginal != null && idModulo != null && numero != null
				&& (idModuloOriginal.equals(idModulo) == false || numeroOriginal.equals(numero) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelModulo, labelNumero);
			}

		} else if (idModuloOriginal == null && numeroOriginal == null && idModulo != null && numero != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelModulo, labelNumero);
			}
		}

	}

	public void checkUniqueRubroAndNombre() throws Exception {

		String labelModulo = this.label("seguridadModulo");
		String labelNombre = this.label("nombre");

		// -----------------------------------------------

		String idModuloOriginal = null;
		String nombreOriginal = null;
		if (this._originalDTO != null) {
			nombreOriginal = ((SeguridadPuerta) this._originalDTO).getNombre();
			if (((SeguridadPuerta) this._originalDTO).getSeguridadModulo() != null) {
				idModuloOriginal = ((SeguridadPuerta) this._originalDTO).getSeguridadModulo().getId();
			}
		}

		// -----------------------------------------------

		String idModulo = null;
		if (this.getSeguridadModulo() != null) {
			idModulo = this.getSeguridadModulo().getId();
		}
		String nombre = this.getNombre();

		// -----------------------------------------------

		String[] attNames = { "seguridadModulo", "nombre" };
		Object[] args = { idModulo, nombre };

		// -----------------------------------------------

		if (idModuloOriginal != null && nombreOriginal != null && idModulo != null && nombre != null
				&& (idModuloOriginal.equals(idModulo) == false || nombreOriginal.equals(nombre) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelModulo, labelNombre);
			}

		} else if (idModuloOriginal == null && nombreOriginal == null && idModulo != null && nombre != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelModulo, labelNombre);
			}
		}

	}

	@Override
	public String toString() {

		if (seguridadModulo != null && seguridadModulo.getNumero() != null) {
			return "(" + seguridadModulo.getNumero() + "-" + numero + ") " + nombre;
		}
		
		if(numero != null && nombre != null) {
			return "(" + numero + ") " + nombre;	
		}

		return null;

	}

}
