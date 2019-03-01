package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Controlador fizcal", plural = "Controladores fizcales", singularPre = "el controlador fizcal", pluralPre = "los controladores fizcales")
public class TalonarioControladorFizcal extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Código", required = true, unique = true)
	private String codigo;

	@FieldConfAnont(label = "Nombre", required = true, unique = true)
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public List<TalonarioControladorFizcal> find() throws Exception {

		List<TalonarioControladorFizcal> listado = new ArrayList<TalonarioControladorFizcal>();

		List<EntityId> items = findUtil("codigo", null, -1, -1, null, 1);

		for (EntityId item : items) {
			listado.add((TalonarioControladorFizcal) item);
		}

		return listado;
	}

}
