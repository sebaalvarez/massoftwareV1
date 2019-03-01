package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;



@ClassLabelAnont(singular = "Clasificación de Cliente (Cta Cte)", plural = "Clasificaciones de Clientes (Cta Cte)", 
	singularPre = "la Clasificación de Cliente (Cta Cte)", pluralPre = "las Clasificaciones de Clientes (Cta Cte)")
public class ClasificacionCliente extends EntityId {

	
	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Número", required = true, unique = true)
	private Integer numero;
	
	@FieldConfAnont(label = "Nombre", required = true, maxLength=20)
	private String nombre;
	
	@FieldConfAnont(label = "Color")
	private String color;
	
	
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}
	
	


	public List<ClasificacionCliente> find(ClasificacionesClientesFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro);
	}
	
	
	public List<ClasificacionCliente> find(int limit, int offset, Map<String, Boolean> orderBy, ClasificacionesClientesFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<ClasificacionCliente> listado = new ArrayList<ClasificacionCliente>();

		String orderBySQL = "clasifcliente";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getNumero() != null) {
			filtros.add(filtro.getNumero());
			whereSQL += "clasifcliente" + " = ? AND ";
		}
		if (filtro.getNombre() != null) {
			String[] palabras = filtro.getNombre().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "nombre"
						+ "))::VARCHAR ILIKE ('%' || TRIM(massoftware.TRANSLATE(?)) || '%')::VARCHAR AND ";
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

		List<EntityId> items = findUtil(orderBySQL, whereSQL, limit, offset, filtros.toArray(), 1);

		for (EntityId item : items) {
			listado.add((ClasificacionCliente) item);
		}

		return listado;
	}

	
}
