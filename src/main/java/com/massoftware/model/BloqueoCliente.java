package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;



@ClassLabelAnont(singular = "Motivo de Bloqueo a Cliente", plural = "Motivos de Bloqueos a Clientes", 
	singularPre = "el Motivo de Bloqueo a Cliente", pluralPre = "los Motivos de Bloqueos a Clientes")
public class BloqueoCliente extends EntityId {

	
	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Motivo de Bloqueo", required = true, unique = true, maxLength=2)
	private String motivoBloqueo;
	
	@FieldConfAnont(label = "Detalle", required = true, maxLength=40)
	private String detalle;
	
	@FieldConfAnont(label = "Clasificación del Cliente")
	private ClasificacionCliente clasifCliente;
	
	
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMotivoBloqueo() {
		return motivoBloqueo;
	}


	public void setMotivoBloqueo(String motivoBloqueo) {
		this.motivoBloqueo = motivoBloqueo;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}


	public ClasificacionCliente getClasifCliente() {
		return clasifCliente;
	}


	public void setClasifCliente(ClasificacionCliente clasifCliente) {
		this.clasifCliente = clasifCliente;
	}


	@Override
	public String toString() {
		return "(" + motivoBloqueo + ") " + detalle;
	}
	
	
	public List<BloqueoCliente> find(int limit, int offset, Map<String, Boolean> orderBy, BloqueosClienteFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<BloqueoCliente> listado = new ArrayList<BloqueoCliente>();

		String orderBySQL = "motivoBloqueo";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getMotivoBloqueo() != null) {
			filtros.add(filtro.getMotivoBloqueo());
			whereSQL += "motivoBloqueo" + " = ? AND ";
		}
		if (filtro.getDetalle() != null) {
			String[] palabras = filtro.getDetalle().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "detalle"
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
			listado.add((BloqueoCliente) item);
		}

		return listado;
	}

	
}
