package com.massoftware.windows.valores_propios;

import java.util.Date;

public class ValoresPropiosFiltro {

	private Integer numeroInterno;
	private Integer numeroChequera;
	private String nombreChequera;
	private Date emisionDesde;
	private Date emisionHasta;
	private Date pagoDesde;
	private Date pagoHasta;
	private Integer numeroCuentaFondo;
	private String nombreCuentaFondo;
	private Integer numeroBanco;
	private String nombreBanco;
	private Integer numeroEstado;
	private String nombreEstado;
	private Integer numeroProveedor;
	private String nombreProveedor;

	public Integer getNumeroInterno() {
		return numeroInterno;
	}

	public void setNumeroInterno(Integer numeroInterno) {
		this.numeroInterno = numeroInterno;
	}

	public Integer getNumeroChequera() {
		return numeroChequera;
	}

	public void setNumeroChequera(Integer numeroChequera) {
		this.numeroChequera = numeroChequera;
	}

	public String getNombreChequera() {
		return nombreChequera;
	}

	public void setNombreChequera(String nombreChequera) {
		this.nombreChequera = nombreChequera;
	}

	public Date getEmisionDesde() {
		return emisionDesde;
	}

	public void setEmisionDesde(Date emisionDesde) {
		this.emisionDesde = emisionDesde;
	}

	public Date getEmisionHasta() {
		return emisionHasta;
	}

	public void setEmisionHasta(Date emisionHasta) {
		this.emisionHasta = emisionHasta;
	}

	public Date getPagoDesde() {
		return pagoDesde;
	}

	public void setPagoDesde(Date pagoDesde) {
		this.pagoDesde = pagoDesde;
	}

	public Date getPagoHasta() {
		return pagoHasta;
	}

	public void setPagoHasta(Date pagoHasta) {
		this.pagoHasta = pagoHasta;
	}

	public Integer getNumeroCuentaFondo() {
		return numeroCuentaFondo;
	}

	public void setNumeroCuentaFondo(Integer numeroCuentaFondo) {
		this.numeroCuentaFondo = numeroCuentaFondo;
	}

	public String getNombreCuentaFondo() {
		return nombreCuentaFondo;
	}

	public void setNombreCuentaFondo(String nombreCuentaFondo) {
		this.nombreCuentaFondo = nombreCuentaFondo;
	}

	public Integer getNumeroBanco() {
		return numeroBanco;
	}

	public void setNumeroBanco(Integer numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public String getNombreBanco() {
		return nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public Integer getNumeroEstado() {
		return numeroEstado;
	}

	public void setNumeroEstado(Integer numeroEstado) {
		this.numeroEstado = numeroEstado;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public Integer getNumeroProveedor() {
		return numeroProveedor;
	}

	public void setNumeroProveedor(Integer numeroProveedor) {
		this.numeroProveedor = numeroProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	@Override
	public String toString() {
		return "ValoresPropiosFiltro [numeroInterno=" + numeroInterno
				+ ", numeroChequera=" + numeroChequera + ", nombreChequera="
				+ nombreChequera + ", emisionDesde=" + emisionDesde
				+ ", emisionHasta=" + emisionHasta + ", pagoDesde=" + pagoDesde
				+ ", pagoHasta=" + pagoHasta + ", numeroCuentaFondo="
				+ numeroCuentaFondo + ", nombreCuentaFondo="
				+ nombreCuentaFondo + ", numeroBanco=" + numeroBanco
				+ ", nombreBanco=" + nombreBanco + ", numeroEstado="
				+ numeroEstado + ", estado=" + nombreEstado + ", numeroProveedor="
				+ numeroProveedor + ", nombreProveedor=" + nombreProveedor
				+ "]";
	}

}
