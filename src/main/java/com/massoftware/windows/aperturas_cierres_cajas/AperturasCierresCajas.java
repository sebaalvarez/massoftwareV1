package com.massoftware.windows.aperturas_cierres_cajas;

import java.sql.Timestamp;

import com.massoftware.windows.UtilModel;

public class AperturasCierresCajas {

	private Integer numero;
	private Integer numeroCaja;
	private String nombreCaja;
	private Integer numeroUsuario;
	private String nombreUsuario;
	private Timestamp apertura;
	private Timestamp cierre;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

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

	public Integer getNumeroUsuario() {
		return numeroUsuario;
	}

	public void setNumeroUsuario(Integer numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = UtilModel.format(nombreUsuario);
	}

	public Timestamp getApertura() {
		return apertura;
	}

	public void setApertura(Timestamp apertura) {
		this.apertura = apertura;
	}

	public Timestamp getCierre() {
		return cierre;
	}

	public void setCierre(Timestamp cierre) {
		this.cierre = cierre;
	}

	@Override
	public String toString() {
		return "(" + numeroCaja + "-" + numero + ") ";
	}

}
