package com.massoftware.windows.cobranzas;

import com.massoftware.windows.UtilModel;

public class CobranzasFiltro {

	private Integer numeroCobranza;
	private String detalleCobranza;

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

	@Override
	public String toString() {
		return "CobranzasFiltro [numeroCobranza=" + numeroCobranza
				+ ", detalleCobranza=" + detalleCobranza + "]";
	}

}
