package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Grupo", plural = "Grupos", singularPre = "el grupo", pluralPre = "los grupos")
public class CuentaFondoGrupo extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Rubro", required = true)
	private CuentaFondoRubro cuentaFondoRubro;

	@FieldConfAnont(label = "Nº grupo", required = true)
	private Integer numero;

	@FieldConfAnont(label = "Nombre", required = true)
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CuentaFondoRubro getCuentaFondoRubro() {
		return cuentaFondoRubro;
	}

	public void setCuentaFondoRubro(CuentaFondoRubro cuentaFondoRubro) {
		this.cuentaFondoRubro = cuentaFondoRubro;
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

	public List<CuentaFondoGrupo> findByRubro(CuentaFondoRubro rubro) throws Exception {

		List<CuentaFondoGrupo> listado = new ArrayList<CuentaFondoGrupo>();

		List<EntityId> items = findUtil("numero", CuentaFondoRubro.class.getSimpleName() + " = ?", -1, -1,
				new Object[] { rubro.getId() }, 2);

		for (EntityId item : items) {
			listado.add((CuentaFondoGrupo) item);
		}

		return listado;
	}

	public void checkUniqueRubroAndNumero() throws Exception {

		String labelRubro = this.label("cuentaFondoRubro");
		String labelNumero = this.label("numero");

		// -----------------------------------------------

		String idRubroOriginal = null;
		Integer numeroOriginal = null;
		if (this._originalDTO != null) {
			numeroOriginal = ((CuentaFondoGrupo) this._originalDTO).getNumero();
			if (((CuentaFondoGrupo) this._originalDTO).getCuentaFondoRubro() != null) {
				idRubroOriginal = ((CuentaFondoGrupo) this._originalDTO).getCuentaFondoRubro().getId();
			}
		}

		// -----------------------------------------------

		String idRubro = null;
		if (this.getCuentaFondoRubro() != null) {
			idRubro = this.getCuentaFondoRubro().getId();
		}
		Integer numero = this.getNumero();

		// -----------------------------------------------

		String[] attNames = { "cuentaFondoRubro", "numero" };
		Object[] args = { idRubro, numero };

		// -----------------------------------------------

		if (idRubroOriginal != null && numeroOriginal != null && idRubro != null && numero != null
				&& (idRubroOriginal.equals(idRubro) == false || numeroOriginal.equals(numero) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelRubro, labelNumero);
			}

		} else if (idRubroOriginal == null && numeroOriginal == null && idRubro != null && numero != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelRubro, labelNumero);
			}
		}

	}

	public void checkUniqueRubroAndNombre() throws Exception {

		String labelRubro = this.label("cuentaFondoRubro");
		String labelNombre = this.label("nombre");

		// -----------------------------------------------

		String idRubroOriginal = null;
		String nombreOriginal = null;
		if (this._originalDTO != null) {
			nombreOriginal = ((CuentaFondoGrupo) this._originalDTO).getNombre();
			if (((CuentaFondoGrupo) this._originalDTO).getCuentaFondoRubro() != null) {
				idRubroOriginal = ((CuentaFondoGrupo) this._originalDTO).getCuentaFondoRubro().getId();
			}
		}

		// -----------------------------------------------

		String idRubro = null;
		if (this.getCuentaFondoRubro() != null) {
			idRubro = this.getCuentaFondoRubro().getId();
		}
		String nombre = this.getNombre();

		// -----------------------------------------------

		String[] attNames = { "cuentaFondoRubro", "nombre" };
		Object[] args = { idRubro, nombre };

		// -----------------------------------------------

		if (idRubroOriginal != null && nombreOriginal != null && idRubro != null && nombre != null
				&& (idRubroOriginal.equals(idRubro) == false || nombreOriginal.equals(nombre) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelRubro, labelNombre);
			}

		} else if (idRubroOriginal == null && nombreOriginal == null && idRubro != null && nombre != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelRubro, labelNombre);
			}
		}

	}

	@Override
	public String toString() {

		if (cuentaFondoRubro != null && cuentaFondoRubro.getNumero() != null) {
			return "(" + cuentaFondoRubro.getNumero() + "-" + numero + ") " + nombre;
		}

		return "(" + numero + ") " + nombre;

	}

}
