package com.massoftware.windows.modelos_tickets;

import com.massoftware.windows.UtilModel;
import com.massoftware.windows.marcas_tickets.MarcasTickets;

public class ModelosTicketsFiltro {

	private MarcasTickets marcaTicket;
	private Integer numero;
	private String nombre;

	public MarcasTickets getMarcaTicket() {
		return marcaTicket;
	}

	public void setMarcaTicket(MarcasTickets marcaTicket) {
		this.marcaTicket = marcaTicket;
	}

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

	@Override
	public String toString() {
		return "ModelosTicketsFiltro [marcasTickets=" + marcaTicket
				+ ", numero=" + numero + ", nombre=" + nombre + "]";
	}

}
