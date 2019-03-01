package com.massoftware.windows.captura_lotes_tickets;

import java.util.Date;

public class CapturaLotesTicketsFiltro {

	private Integer numeroLote;
	private Date fechaDesde;
	private Date fechaHasta;
	private Integer numeroCobranza;
	private String nombreCobranza;
	private Integer numeroVendedor;
	private String nombreVendedor;
	private Integer cuentaCliente;
	private String nombreCliente;

	public Integer getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(Integer numeroLote) {
		this.numeroLote = numeroLote;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Integer getNumeroCobranza() {
		return numeroCobranza;
	}

	public void setNumeroCobranza(Integer numeroCobranza) {
		this.numeroCobranza = numeroCobranza;
	}

	public String getNombreCobranza() {
		return nombreCobranza;
	}

	public void setNombreCobranza(String nombreCobranza) {
		this.nombreCobranza = nombreCobranza;
	}

	public Integer getNumeroVendedor() {
		return numeroVendedor;
	}

	public void setNumeroVendedor(Integer numeroVendedor) {
		this.numeroVendedor = numeroVendedor;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public Integer getCuentaCliente() {
		return cuentaCliente;
	}

	public void setCuentaCliente(Integer cuentaCliente) {
		this.cuentaCliente = cuentaCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	@Override
	public String toString() {
		return "CapturaLotesTicketsFiltro [numeroLote=" + numeroLote
				+ ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta
				+ ", numeroCobranza=" + numeroCobranza + ", nombreCobranza="
				+ nombreCobranza + ", numeroVendedor=" + numeroVendedor
				+ ", nombreVendedor=" + nombreVendedor + ", cuentaCliente="
				+ cuentaCliente + ", nombreCliente=" + nombreCliente + "]";
	}

}
