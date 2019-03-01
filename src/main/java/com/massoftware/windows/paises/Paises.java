package com.massoftware.windows.paises;

import com.massoftware.windows.UtilModel;

public class Paises {

	private Integer numero;
	private String nombre;
	private String abreviatura;

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
		this.nombre = UtilModel.format(nombre);
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = UtilModel.format(abreviatura);
	}

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
