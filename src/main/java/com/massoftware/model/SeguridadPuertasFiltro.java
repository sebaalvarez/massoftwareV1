package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class SeguridadPuertasFiltro extends Entity {

	@FieldConfAnont(label = "Módulo")
	private String idModulo;		

	public String getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}

}
