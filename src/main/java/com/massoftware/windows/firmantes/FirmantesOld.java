package com.massoftware.windows.firmantes;

import com.massoftware.windows.UtilModel;

public class FirmantesOld {

	private Integer numero;
	private String nombre;
	private String cargo;
	private Boolean activo;

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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = UtilModel.format(cargo);
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = UtilModel.format(activo);
	}

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
