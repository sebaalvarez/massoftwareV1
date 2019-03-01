package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class BloqueosClienteFiltro extends Entity {

	@FieldConfAnont(label = "Motivo de Bloqueo")
	private String motivoBloqueo;

	@FieldConfAnont(label = "Detalle")
	private String detalle;

	@FieldConfAnont(label = "Clasificación del Cliente")
	private ClasificacionCliente clasifCliente;
	
	
	
	public String getMotivoBloqueo() {
		return motivoBloqueo;
	}

	public void setMotivoBloqueo(String numero) {
		this.motivoBloqueo = numero;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String nombre) {
		this.detalle = nombre;
	}

	public ClasificacionCliente getClasifCliente() {
		return clasifCliente;
	}

	public void setClasifCliente(ClasificacionCliente clasifCliente) {
		this.clasifCliente = clasifCliente;
	}

	
	
	
}
