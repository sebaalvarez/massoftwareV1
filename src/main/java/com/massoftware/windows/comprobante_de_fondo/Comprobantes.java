package com.massoftware.windows.comprobante_de_fondo;

import com.massoftware.windows.UtilModel;

public class Comprobantes {

	private Integer numero;
	private String nombre;
	private String codigo;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = UtilModel.format(codigo);
	}

}
