package com.massoftware.windows.comprobantes_emitidos;

import java.util.Date;

import com.massoftware.windows.UtilModel;

public class ComprobantesEmitidosFiltro {

	private Integer nroId;
	private String cbte;
	private String comprobante;
	private Date fechaDesde;
	private Date fechaHasta;
	private String detalle;
	private Integer numeroTalonario;
	private String nombreTalonario;
	private Integer numeroTipoComprobante;
	private String nombreTipoComprobante;
	private Integer numeroAperturaCierreCaja;
	private Integer numeroCaja;
	private String nombreCaja;

	public Integer getNroId() {
		return nroId;
	}

	public void setNroId(Integer nroId) {
		this.nroId = nroId;
	}

	public String getCbte() {
		return cbte;
	}

	public void setCbte(String cbte) {
		this.cbte = UtilModel.format(cbte);
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = UtilModel.format(comprobante);
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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = UtilModel.format(detalle);
	}

	public Integer getNumeroTalonario() {
		return numeroTalonario;
	}

	public void setNumeroTalonario(Integer numeroTalonario) {
		this.numeroTalonario = numeroTalonario;
	}

	public String getNombreTalonario() {
		return nombreTalonario;
	}

	public void setNombreTalonario(String nombreTalonario) {
		this.nombreTalonario = UtilModel.format(nombreTalonario);
	}

	public Integer getNumeroTipoComprobante() {
		return numeroTipoComprobante;
	}

	public void setNumeroTipoComprobante(Integer numeroTipoComprobante) {
		this.numeroTipoComprobante = numeroTipoComprobante;
	}

	public String getNombreTipoComprobante() {
		return nombreTipoComprobante;
	}

	public void setNombreTipoComprobante(String nombreTipoComprobante) {
		this.nombreTipoComprobante = UtilModel.format(nombreTipoComprobante);
	}

	public Integer getNumeroAperturaCierreCaja() {
		return numeroAperturaCierreCaja;
	}

	public void setNumeroAperturaCierreCaja(Integer numeroAperturaCierreCaja) {
		this.numeroAperturaCierreCaja = numeroAperturaCierreCaja;
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

	@Override
	public String toString() {
		return "ComprobantesEmitidosFiltro [nroId=" + nroId + ", cbte=" + cbte
				+ ", comprobante=" + comprobante + ", fechaDesde=" + fechaDesde
				+ ", fechaHasta=" + fechaHasta + ", detalle=" + detalle
				+ ", numeroTalonario=" + numeroTalonario + ", nombreTalonario="
				+ nombreTalonario + ", numeroTipoComprobante="
				+ numeroTipoComprobante + ", nombreTipoComprobante="
				+ nombreTipoComprobante + ", numeroAperturaCierreCaja="
				+ numeroAperturaCierreCaja + ", numeroCaja=" + numeroCaja
				+ ", nombreCaja=" + nombreCaja + "]";
	}

}
