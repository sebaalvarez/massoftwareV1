package com.massoftware.windows.comprobantes_ventas_compras_pendientes_fondos;

public class ComprobantesVentasComprasPendientesFondosFiltro {

	private Integer numeroDias = 30;

	public Integer getNumeroDias() {
		return numeroDias;
	}

	public void setNumeroDias(Integer numeroDias) {
		this.numeroDias = numeroDias;
	}

	@Override
	public String toString() {
		return "ComprobantesVentasComprasPendientesFondosFiltro [numeroDias="
				+ numeroDias + "]";
	}

}
