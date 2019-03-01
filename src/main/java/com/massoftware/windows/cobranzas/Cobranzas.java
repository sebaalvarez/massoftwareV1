package com.massoftware.windows.cobranzas;

import java.sql.Timestamp;

import com.massoftware.windows.UtilModel;

public class Cobranzas {

	private Integer numeroCobranza;
	private String detalleCobranza;
	private Integer numeroVendedor;
	private String nombreVendedor;
	private Integer numeroZona;
	private String nombreZona;
	private Timestamp recepcion;
	private Timestamp ticketInicio;
	private String estado;

	public Integer getNumeroCobranza() {
		return numeroCobranza;
	}

	public void setNumeroCobranza(Integer numeroCobranza) {
		this.numeroCobranza = numeroCobranza;
	}

	public String getDetalleCobranza() {
		return detalleCobranza;
	}

	public void setDetalleCobranza(String detalleCobranza) {		
		this.detalleCobranza = UtilModel.format(detalleCobranza);
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
		this.nombreVendedor = UtilModel.format(nombreVendedor);
	}

	public Integer getNumeroZona() {
		return numeroZona;
	}

	public void setNumeroZona(Integer numeroZona) {
		this.numeroZona = numeroZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = UtilModel.format(nombreZona);	
	}

	public Timestamp getRecepcion() {
		return recepcion;
	}

	public void setRecepcion(Timestamp recepcion) {
		this.recepcion = recepcion;
	}

	public Timestamp getTicketInicio() {
		return ticketInicio;
	}

	public void setTicketInicio(Timestamp ticketInicio) {
		this.ticketInicio = ticketInicio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = UtilModel.format(estado);		
	}

	@Override
	public String toString() {
		return "(" + numeroCobranza + ") " + detalleCobranza;
	}

}
