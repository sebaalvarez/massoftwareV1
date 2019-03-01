package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Estado", plural = "Estados", singularPre = "el estado", pluralPre = "los estados")
public class CuentaContableEstado extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;
	
	@FieldConfAnont(label = "Nº tipo", required = true, unique = true)
	private Integer numero;
	
	@FieldConfAnont(label = "Nombre", required = true, unique = true)
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}
	
	public List<CuentaContableEstado> find() throws Exception {

		List<CuentaContableEstado> listado = new ArrayList<CuentaContableEstado>();

		List<EntityId> items = findUtil("numero", null, -1, -1, null, 1);

		for (EntityId item : items) {
			listado.add((CuentaContableEstado) item);
		}

		return listado;
	}

}
