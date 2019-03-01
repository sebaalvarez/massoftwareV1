package com.massoftware.windows.provincias;

import com.massoftware.windows.UtilModel;

public class Provincias {

	private Integer numeroPais;
	private Integer numero;
	private String nombre;
	private String abreviatura;

	public Integer getNumeroPais() {
		return numeroPais;
	}

	public void setNumeroPais(Integer numeroPais) {
		this.numeroPais = numeroPais;
	}

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
		return "(" + numeroPais + "-" + numero + ") " + nombre;
	}

}
