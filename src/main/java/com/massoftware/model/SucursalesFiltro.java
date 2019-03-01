package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class SucursalesFiltro extends Entity {

	@FieldConfAnont(label = "Nº sucursal")
	private Integer numero;

	@FieldConfAnont(label = "Nombre")
	private String nombre;

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

}
