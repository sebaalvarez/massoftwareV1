package com.massoftware.windows.jurisdicciones_convenio_multilateral;

import com.massoftware.windows.UtilModel;

public class JurisdiccionesConvenioMultilateral {

	private Integer numero;
	private String nombre;
	private String cuentaFondo;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = UtilModel.format(nombre);
	}

	public String getCuentaFondo() {
		return cuentaFondo;
	}

	public void setCuentaFondo(String cuentaFondo) {
		this.cuentaFondo = cuentaFondo;
	}

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
