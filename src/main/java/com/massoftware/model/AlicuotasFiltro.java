package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class AlicuotasFiltro extends Entity {

	@FieldConfAnont(label = "Código")
	private Integer codigo;

	@FieldConfAnont(label = "Descripción")
	private String descripcion;

	
	
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer numero) {
		this.codigo = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String nombre) {
		this.descripcion = nombre;
	}

	
	
	
}
