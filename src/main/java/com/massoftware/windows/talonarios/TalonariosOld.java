package com.massoftware.windows.talonarios;

public class TalonariosOld {

	private Integer numero;
	private String nombre;
	private String letra;
	private Integer sucursal;
	private Integer proximoNumero;

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
		this.nombre = nombre;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Integer getSucursal() {
		return sucursal;
	}

	public void setSucursal(Integer sucursal) {
		this.sucursal = sucursal;
	}

	public Integer getProximoNumero() {
		return proximoNumero;
	}

	public void setProximoNumero(Integer proximoNumero) {
		this.proximoNumero = proximoNumero;
	}

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
