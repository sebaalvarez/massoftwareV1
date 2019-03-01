package com.massoftware.windows.monedas_cotizaciones;

import com.massoftware.windows.monedas.Monedas;

public class MonedasCotizacionesFiltro {

	private Monedas moneda;

	public Monedas getMoneda() {
		return moneda;
	}

	public void setMoneda(Monedas moneda) {
		this.moneda = moneda;
	}

	@Override
	public String toString() {
		return "MonedasCotizacionesFiltro [moneda=" + moneda + "]";
	}

}
