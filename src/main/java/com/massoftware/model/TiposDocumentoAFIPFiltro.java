package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class TiposDocumentoAFIPFiltro extends Entity {

	@FieldConfAnont(label = "Tipo")
	private Integer tipo;

	@FieldConfAnont(label = "Descripción")
	private String descripcion;

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
	
	
	
	
	
}
