package com.massoftware.windows.monedas_cotizaciones;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.massoftware.windows.UtilModel;

public class MonedasCotizaciones {

	private Integer numeroMoneda;
	private Date fecha;
	private BigDecimal compra;
	private BigDecimal venta;
	private Timestamp ingreso;
	private String usuario;

	public Integer getNumeroMoneda() {
		return numeroMoneda;
	}

	public void setNumeroMoneda(Integer numeroMoneda) {
		this.numeroMoneda = numeroMoneda;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCompra() {
		return compra;
	}

	public void setCompra(BigDecimal compra) {
		this.compra = compra;
	}

	public BigDecimal getVenta() {
		return venta;
	}

	public void setVenta(BigDecimal venta) {
		this.venta = venta;
	}

	public Timestamp getIngreso() {
		return ingreso;
	}

	public void setIngreso(Timestamp ingreso) {
		this.ingreso = ingreso;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = UtilModel.format(usuario);
	}

	@Override
	public String toString() {
		return "MonedasCotizaciones [nroMoneda=" + numeroMoneda + ", fecha="
				+ fecha + ", compra=" + compra + ", venta=" + venta + "]";
	}

}
