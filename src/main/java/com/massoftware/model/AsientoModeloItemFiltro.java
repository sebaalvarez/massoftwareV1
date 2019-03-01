package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class AsientoModeloItemFiltro extends Entity {

	@FieldConfAnont(label = "Asiento modelo")
	private AsientoModelo asientoModelo;

	public AsientoModelo getAsientoModelo() {
		return asientoModelo;
	}

	public void setAsientoModelo(AsientoModelo asientoModelo) {
		this.asientoModelo = asientoModelo;
	}

}
