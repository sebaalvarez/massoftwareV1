package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class FirmantesFiltro extends Entity {

	@FieldConfAnont(label = "Nº firmante")
	private Integer numero;

	@FieldConfAnont(label = "Nombre")
	private String nombre;

	@FieldConfAnont(label = "Obsoleto")
	private Integer bloqueado;

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

	public Integer getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Integer bloqueado) {
		this.bloqueado = bloqueado;
	}

}
