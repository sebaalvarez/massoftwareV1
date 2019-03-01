package com.massoftware.windows.aperturas_cierres_cajas;

import com.massoftware.windows.UtilModel;

public class AperturasCierresCajasFiltro {

	private Integer numeroCaja;
	private String nombreCaja;
	private Usuarios usuario;

	public Integer getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(Integer numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	public String getNombreCaja() {
		return nombreCaja;
	}

	public void setNombreCaja(String nombreCaja) {
		this.nombreCaja = UtilModel.format(nombreCaja);
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "AperturasCierresCajasFiltro [numeroCaja=" + numeroCaja
				+ ", nombreCaja=" + nombreCaja + ", usuario=" + usuario + "]";
	}
	
	

}
