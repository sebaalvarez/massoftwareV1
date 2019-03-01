package com.massoftware.windows.provincias;

import com.massoftware.windows.UtilModel;
import com.massoftware.windows.paises.Paises;

public class ProvinciasFiltro {

	private Paises pais;
	private Integer numero;
	private String nombre;

	public Paises getPais() {
		return pais;
	}

	public void setPais(Paises pais) {
		this.pais = pais;
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

	@Override
	public String toString() {
		return "ProvinciasFiltro [pais=" + pais + ", numero=" + numero
				+ ", nombre=" + nombre + "]";
	}

}
