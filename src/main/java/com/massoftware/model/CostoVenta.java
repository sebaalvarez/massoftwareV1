package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Costo de venta", plural = "Costos de venta", singularPre = "el costo de venta", pluralPre = "los costos de venta")
public class CostoVenta extends EntityId {

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
	
	public List<CostoVenta> find() throws Exception {

		List<CostoVenta> listado = new ArrayList<CostoVenta>();

		List<EntityId> items = findUtil("numero", null, -1, -1, null, 1);

		for (EntityId item : items) {
			listado.add((CostoVenta) item);
		}

		return listado;
	}

}
