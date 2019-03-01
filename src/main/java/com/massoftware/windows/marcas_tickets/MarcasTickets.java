package com.massoftware.windows.marcas_tickets;

import java.util.Date;

import com.massoftware.windows.UtilModel;

public class MarcasTickets {

	private Integer numero;
	private String nombre;
	private Date fecha;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
