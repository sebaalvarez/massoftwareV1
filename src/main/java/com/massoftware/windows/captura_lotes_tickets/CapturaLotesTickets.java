package com.massoftware.windows.captura_lotes_tickets;

import java.math.BigDecimal;
import java.util.Date;

public class CapturaLotesTickets {

	private Integer numeroLote;
	private Date fecha;
	private Integer numeroCobranza;
	private String nombreCobranza;
	private Integer numeroVendedor;
	private String nombreVendedor;
	private Integer cuentaCliente;
	private String nombreCliente;
	private BigDecimal importeTotal;
	private String ecl;

	public Integer getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(Integer numeroLote) {
		this.numeroLote = numeroLote;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public void setNombreCobranza(String cobranza) {
		this.nombreCobranza = cobranza;
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

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getEcl() {
		return ecl;
	}

	public void setEcl(String ecl) {
		this.ecl = ecl;
	}

	@Override
	public String toString() {
		return "(" + numeroLote + ") " + numeroCobranza;
	}

}
