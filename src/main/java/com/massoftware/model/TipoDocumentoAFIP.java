package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Tipo Documento AFIP", plural = "Tipos Documento AFIP", singularPre = "el tipo de documento AFIP", pluralPre = "los tipos de documento AFIP")
public class TipoDocumentoAFIP extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;
	
	@FieldConfAnont(label = "Tipo", required = true, unique = true, columns = 3)
	private Integer tipo;
	
	@FieldConfAnont(label = "Descripción", required = true, columns = 30, maxLength = 40)
	private String descripcion;
	
	
	
	
	
	public TipoDocumentoAFIP() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}





	public Integer getTipo() {
		return tipo;
	}





	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}





	public String getDescripcion() {
		return descripcion;
	}





	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}





	@Override
	public String toString() {
		if(tipo != null && descripcion != null) {
			return "(" + tipo + ") " + descripcion;	
		}
		
		return null;
	}

	public List<TipoDocumentoAFIP> find(TiposDocumentoAFIPFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro);
	}

	public List<TipoDocumentoAFIP> find(int limit, int offset, Map<String, Boolean> orderBy, TiposDocumentoAFIPFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<TipoDocumentoAFIP> listado = new ArrayList<TipoDocumentoAFIP>();

		String orderBySQL = "tipo";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getTipo() != null) {
			filtros.add(filtro.getTipo());
			whereSQL += "tipo" + " = ? AND ";
		}
		if (filtro.getDescripcion() != null) {
			String[] palabras = filtro.getDescripcion().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "descripcion"
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

		List<EntityId> items = findUtil(orderBySQL, whereSQL, limit, offset, filtros.toArray(), 0);

		for (EntityId item : items) {
			listado.add((TipoDocumentoAFIP) item);
		}

		return listado;
	}



}
