package com.massoftware.windows.chequeras;

import com.massoftware.windows.UtilModel;

public class Chequeras {

	private Integer numeroCuentaFondo;
	private Integer numero;
	private Integer primerNumero;
	private Integer ultimoNumero;
	private Integer proximoNumero;
	private Boolean bloqueado;

	public Integer getNumeroCuentaFondo() {
		return numeroCuentaFondo;
	}

	public void setNumeroCuentaFondo(Integer numeroCuentaFondo) {
		this.numeroCuentaFondo = numeroCuentaFondo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getPrimerNumero() {
		return primerNumero;
	}

	public void setPrimerNumero(Integer primerNumero) {
		this.primerNumero = primerNumero;
	}

	public Integer getUltimoNumero() {
		return ultimoNumero;
	}

	public void setUltimoNumero(Integer ultimoNumero) {
		this.ultimoNumero = ultimoNumero;
	}

	public Integer getProximoNumero() {
		return proximoNumero;
	}

	public void setProximoNumero(Integer proximoNumero) {
		this.proximoNumero = proximoNumero;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = UtilModel.format(bloqueado);
	}

	@Override
	public String toString() {
		return "(" + numeroCuentaFondo + ") " + numero;
	}

}
