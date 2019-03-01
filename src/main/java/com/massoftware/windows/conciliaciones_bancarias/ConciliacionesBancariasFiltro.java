package com.massoftware.windows.conciliaciones_bancarias;

import java.util.Date;

public class ConciliacionesBancariasFiltro {

	private Cuentas cuenta;
	private Date desde;
	private Date hasta;

	public Cuentas getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuentas cuenta) {
		this.cuenta = cuenta;
	}

	public Date getDesde() {
		return desde;
	}

	public void setDesde(Date desde) {
		this.desde = desde;
	}

	public Date getHasta() {
		return hasta;
	}

	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}

	@Override
	public String toString() {
		return "ConciliacionesBancariasFiltro [cuenta=" + cuenta + ", desde="
				+ desde + ", hasta=" + hasta + "]";
	}

}
