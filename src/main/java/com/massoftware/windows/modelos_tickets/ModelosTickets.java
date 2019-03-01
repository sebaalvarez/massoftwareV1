package com.massoftware.windows.modelos_tickets;

import com.massoftware.windows.UtilModel;

public class ModelosTickets {

	private Integer numeroMarcaTicket;
	private String nombreMarcaTicket;
	private Integer numero;
	private String nombre;

	public Integer getNumeroMarcaTicket() {
		return numeroMarcaTicket;
	}

	public void setNumeroMarcaTicket(Integer numeroMarcaTicket) {
		this.numeroMarcaTicket = numeroMarcaTicket;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombreMarcaTicket() {
		return nombreMarcaTicket;
	}

	public void setNombreMarcaTicket(String nombreMarcaTicket) {
		this.nombreMarcaTicket = UtilModel.format(nombreMarcaTicket);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = UtilModel.format(nombre);
	}

	@Override
	public String toString() {
		return "(" + numeroMarcaTicket + "-" + numero + ") " + nombre;
	}

}
