package com.massoftware.model;

import java.security.Timestamp;
import java.util.Date;

import com.massoftware.windows.comprobante_de_fondo.Comprobantes;

public class ComprobanteDeFondo {

	// ALTER TABLE Fondos ADD CONSTRAINT fk_ TablaDeComprobantes FOREIGN KEY
	// (TipoID) REFERENCES TablaDeComprobantes(Tipo)
	// ALTER TABLE Fondos ADD CONSTRAINT fk_ TablaDeMultiproposito FOREIGN KEY
	// (Multiproposito) REFERENCES TablaDeMultiproposito(Multiproposito)
	// ALTER TABLE Fondos ADD CONSTRAINT fk_ CajasApertura FOREIGN KEY (Caja,
	// NroCaja) REFERENCES CajasApertura(Caja, NroCaja)
	// ALTER TABLE Fondos ADD CONSTRAINT fk_ Usuarios FOREIGN KEY (Usuario)
	// REFERENCES SSECUR_User(No)

	private Caja caja;
	private Timestamp apertura;
	private Comprobantes comprobante;
	private Integer codigo;
	private Date fecha;
	private String detalle;
	private Boolean conciliacionAutomatica;

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Timestamp getApertura() {
		return apertura;
	}

	public void setApertura(Timestamp apertura) {
		this.apertura = apertura;
	}

	public Comprobantes getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobantes comprobante) {
		this.comprobante = comprobante;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
		this.detalle = detalle;
	}

	public Boolean getConciliacionAutomatica() {
		return conciliacionAutomatica;
	}

	public void setConciliacionAutomatica(Boolean conciliacionAutomatica) {
		this.conciliacionAutomatica = conciliacionAutomatica;
	}

}
