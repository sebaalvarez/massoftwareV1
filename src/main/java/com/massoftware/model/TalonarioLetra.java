package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Letra", plural = "Letras", singularPre = "la letra", pluralPre = "las letras")
public class TalonarioLetra extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;	
	
	@FieldConfAnont(label = "Nombre", required = true, unique = true)
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public List<TalonarioLetra> find() throws Exception {

		List<TalonarioLetra> listado = new ArrayList<TalonarioLetra>();

		List<EntityId> items = findUtil("nombre", null, -1, -1, null, 1);

		for (EntityId item : items) {
			listado.add((TalonarioLetra) item);
		}

		return listado;
	}

}
