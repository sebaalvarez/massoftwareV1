package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Tipo", plural = "Tipos", singularPre = "el tipo", pluralPre = "los tipos")
public class PuntoEquilibrioTipo extends EntityId {

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
	
	public List<PuntoEquilibrioTipo> find() throws Exception {

		List<PuntoEquilibrioTipo> listado = new ArrayList<PuntoEquilibrioTipo>();

		List<EntityId> items = findUtil("numero", null, -1, -1, null, 1);

		for (EntityId item : items) {
			listado.add((PuntoEquilibrioTipo) item);
		}

		return listado;
	}

}
