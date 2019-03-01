package com.massoftware.windows.firmantes;

import com.massoftware.windows.UtilModel;

public class FirmantesFiltroOld {

	private Integer numero;
	private String nombre;
	private String cargo;
	private Integer activo;

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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = UtilModel.format(cargo);
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "FirmantesFiltro [numero=" + numero + ", nombre=" + nombre
				+ ", cargo=" + cargo + ", activo=" + activo + "]";
	}

}
