package com.massoftware.windows.comprobantes_ventas_compras_pendientes_fondos;

import java.math.BigDecimal;
import java.util.Date;

public class ComprobantesVentasComprasPendientesFondos {

	private String modulo;
	private String comprobante;
	private Date fecha;
	private String clienteProveedor;
	private BigDecimal totalCbte;

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getClienteProveedor() {
		return clienteProveedor;
	}

	public void setClienteProveedor(String clienteProveedor) {
		this.clienteProveedor = clienteProveedor;
	}

	public BigDecimal getTotalCbte() {
		return totalCbte;
	}

	public void setTotalCbte(BigDecimal totalCbte) {
		this.totalCbte = totalCbte;
	}

	@Override
	public String toString() {
		return "(" + modulo + ") " + comprobante;
	}

}
