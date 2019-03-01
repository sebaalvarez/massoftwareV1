package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class PuntosEquilibrioFiltro extends Entity {

	@FieldConfAnont(label = "Ejercicio contable")
	private EjercicioContable ejercicioContable;

	@FieldConfAnont(label = "Nº punto de equilibrio")
	private Integer numero;

	@FieldConfAnont(label = "Nombre")
	private String nombre;

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
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
		this.nombre = nombre;
	}

}
