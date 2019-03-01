package com.massoftware.windows.valores_terceros;

import java.util.Date;

public class ValoresTercerosFiltro {

	private Integer numeroInterno;
	private Integer numeroCheque;
	// private Integer numeroChequera;
	// private String nombreChequera;
	private Date emisionDesde;
	private Date emisionHasta;
	private Date cobroDesde;
	private Date cobroHasta;
	private Integer numeroCuentaFondo;
	private String nombreCuentaFondo;
	private Integer numeroBanco;
	private String nombreBanco;
	private Integer numeroEstado;
	private String nombreEstado;
	private Integer numeroProveedor;
	private String nombreProveedor;
	private Integer numeroCliente;
	private String nombreCliente;

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public Integer getNumeroInterno() {
		return numeroInterno;
	}

	public void setNumeroInterno(Integer numeroInterno) {
		this.numeroInterno = numeroInterno;
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

	public Date getCobroDesde() {
		return cobroDesde;
	}

	public void setCobroDesde(Date cobroDesde) {
		this.cobroDesde = cobroDesde;
	}

	public Date getCobroHasta() {
		return cobroHasta;
	}

	public void setCobroHasta(Date cobroHasta) {
		this.cobroHasta = cobroHasta;
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

	public Integer getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(Integer numeroCliente) {
		this.numeroCliente = numeroCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	@Override
	public String toString() {
		return "ValoresTercerosFiltro [numeroInterno=" + numeroInterno
				+ ", numeroCheque=" + numeroCheque + ", emisionDesde="
				+ emisionDesde + ", emisionHasta=" + emisionHasta
				+ ", cobroDesde=" + cobroDesde + ", cobroHasta=" + cobroHasta
				+ ", numeroCuentaFondo=" + numeroCuentaFondo
				+ ", nombreCuentaFondo=" + nombreCuentaFondo + ", numeroBanco="
				+ numeroBanco + ", nombreBanco=" + nombreBanco
				+ ", numeroEstado=" + numeroEstado + ", nombreEstado="
				+ nombreEstado + ", numeroProveedor=" + numeroProveedor
				+ ", nombreProveedor=" + nombreProveedor + ", numeroCliente="
				+ numeroCliente + ", nombreCliente=" + nombreCliente + "]";
	}

}
