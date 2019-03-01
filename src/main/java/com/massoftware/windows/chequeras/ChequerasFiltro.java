package com.massoftware.windows.chequeras;

public class ChequerasFiltro {

	private Integer numeroCuentaFondo;
	private Integer bloqueado;

	public Integer getNumeroCuentaFondo() {
		return numeroCuentaFondo;
	}

	public void setNumeroCuentaFondo(Integer numeroCuentaFondo) {
		this.numeroCuentaFondo = numeroCuentaFondo;
	}

	public Integer getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Integer bloqueado) {
		this.bloqueado = bloqueado;
	}

	@Override
	public String toString() {
		return "ChequerasFiltro [numeroCuentaFondo=" + numeroCuentaFondo
				+ ", bloqueado=" + bloqueado + "]";
	}

}
