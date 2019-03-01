package com.massoftware.windows.valores_terceros;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ValoresTerceros {

	private Integer numeroInterno;
	private Integer numeroCheque;
	// private Integer numeroChequera;
	// private String nombreChequera;
	private Timestamp emision;
	private Timestamp cobro;
	private Integer numeroCuentaFondo;
	private String nombreCuentaFondo;
	private Integer numeroBanco;
	private String nombreBanco;
	private Integer numeroEstado;
	private String nombreEstado;
	private BigDecimal importe;
	private Integer numeroProveedor;
	private String nombreProveedor;
	private Integer numeroCliente;
	private String nombreCliente;

	public Integer getNumeroInterno() {
		return numeroInterno;
	}

	public void setNumeroInterno(Integer numeroInterno) {
		this.numeroInterno = numeroInterno;
	}

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public Timestamp getEmision() {
		return emision;
	}

	public void setEmision(Timestamp emision) {
		this.emision = emision;
	}

	public Timestamp getCobro() {
		return cobro;
	}

	public void setCobro(Timestamp cobro) {
		this.cobro = cobro;
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

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
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
		return "(" + numeroInterno + ") " + numeroCheque;
	}

}
