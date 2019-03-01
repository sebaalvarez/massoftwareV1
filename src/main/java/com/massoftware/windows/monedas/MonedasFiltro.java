package com.massoftware.windows.monedas;

import com.massoftware.windows.UtilModel;

public class MonedasFiltro {

	private Integer numero;
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
		this.nombre = UtilModel.format(nombre);
	}

	@Override
	public String toString() {
		return "PaisesFiltro [numero=" + numero + ", nombre=" + nombre + "]";
	}

}
