package com.massoftware.windows.comprobantes_emitidos;

import java.math.BigDecimal;
import java.util.Date;

import com.massoftware.windows.UtilModel;

public class ComprobantesEmitidos {

	private String cbte;
	private String comprobante;
	private Date fecha;
	private String detalle;
	private Integer numeroTipoComprobante;
	private Integer nroId;
	private Integer numeroCaja;
	private BigDecimal importe;
	private Integer numeroTalonario;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = UtilModel.format(detalle);		
	}

	public Integer getNumeroTipoComprobante() {
		return numeroTipoComprobante;
	}

	public void setNumeroTipoComprobante(Integer numeroTipoComprobante) {
		this.numeroTipoComprobante = numeroTipoComprobante;
	}

	public Integer getNroId() {
		return nroId;
	}

	public void setNroId(Integer nroId) {
		this.nroId = nroId;
	}

	public Integer getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(Integer numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Integer getNumeroTalonario() {
		return numeroTalonario;
	}

	public void setNumeroTalonario(Integer numeroTalonario) {
		this.numeroTalonario = numeroTalonario;
	}

	@Override
	public String toString() {
		return "(" + cbte + ") " + comprobante;
	}

}
