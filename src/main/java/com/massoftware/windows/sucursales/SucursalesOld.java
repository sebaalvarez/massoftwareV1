package com.massoftware.windows.sucursales;

import com.massoftware.windows.UtilModel;

public class SucursalesOld {

	private Integer numero;
	private String nombre;
	private String tipoSucursal;

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

	public String getTipoSucursal() {
		return tipoSucursal;
	}

	public void setTipoSucursal(String tipoSucursal) {
		this.tipoSucursal = tipoSucursal;
	}

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
