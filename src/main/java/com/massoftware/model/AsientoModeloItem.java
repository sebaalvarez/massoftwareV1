package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Asiento modelo item", plural = "Asientos modelo item", singularPre = "el asiento modelo item", pluralPre = "los asientos modelo item")
public class AsientoModeloItem extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Asiento modelo", required = true, readOnly = true)
	private AsientoModelo asientoModelo;

	@FieldConfAnont(label = "Nº asiento modelo item", required = true)
	private Integer numero;

	@FieldConfAnont(label = "Cuenta contable", required = true)
	private CuentaContable cuentaContable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AsientoModelo getAsientoModelo() {
		return asientoModelo;
	}

	public void setAsientoModelo(AsientoModelo asientoModelo) {
		this.asientoModelo = asientoModelo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	@Override
	public String toString() {

		return cuentaContable + "";

	}	

	public List<AsientoModeloItem> find(AsientoModeloItemFiltro filtro) throws Exception {

		filtro.setterTrim();

		List<AsientoModeloItem> listado = new ArrayList<AsientoModeloItem>();

		String orderBySQL = "asientoModelo, numero";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getAsientoModelo() != null && filtro.getAsientoModelo().getId() != null) {
			filtros.add(filtro.getAsientoModelo().getId());
			whereSQL += "asientoModelo" + " = ? AND ";
		}

		// ==================================================================

		whereSQL = whereSQL.trim();
		if (whereSQL.length() > 0) {
			whereSQL = whereSQL.substring(0, whereSQL.length() - 4);
		} else {
			whereSQL = null;
		}

		// ==================================================================

		List<EntityId> items = findUtil(orderBySQL, whereSQL, -1, -1, filtros.toArray(), 2);

		for (EntityId item : items) {
			listado.add((AsientoModeloItem) item);
		}

		return listado;
	}

	public void checkUniqueAsientoModeloAndNumero() throws Exception {

		String labelAsientoModelo = this.label("asientoModelo");
		String labelNumero = this.label("numero");

		// -----------------------------------------------

		String idAsientoModeloOriginal = null;
		Integer numeroOriginal = null;
		if (this._originalDTO != null) {
			numeroOriginal = ((AsientoModeloItem) this._originalDTO).getNumero();
			if (((AsientoModeloItem) this._originalDTO).getAsientoModelo() != null) {
				idAsientoModeloOriginal = ((AsientoModeloItem) this._originalDTO).getAsientoModelo().getId();
			}
		}

		// -----------------------------------------------

		String idAsientoModelo = null;
		if (this.getAsientoModelo() != null) {
			idAsientoModelo = this.getAsientoModelo().getId();
		}
		Integer numero = this.getNumero();

		// -----------------------------------------------

		String[] attNames = { "asientoModelo", "numero" };
		Object[] args = { idAsientoModelo, numero };

		// -----------------------------------------------

		if (idAsientoModeloOriginal != null && numeroOriginal != null && idAsientoModelo != null && numero != null
				&& (idAsientoModeloOriginal.equals(idAsientoModelo) == false
						|| numeroOriginal.equals(numero) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelAsientoModelo, labelNumero);
			}

		} else if (idAsientoModeloOriginal == null && numeroOriginal == null && idAsientoModelo != null
				&& numero != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelAsientoModelo, labelNumero);
			}
		}

	}

	public void checkUniqueAsientoModeloAndCuentaContable() throws Exception {

		String labelAsientoModelo = this.label("asientoModelo");
		String labelCuentaContable = this.label("cuentaContable");

		// -----------------------------------------------

		String idAsientoModeloOriginal = null;
		String cuentaContableOriginal = null;
		
		if (this._originalDTO != null) {
			
			if (((AsientoModeloItem) this._originalDTO).getCuentaContable() != null) {
				cuentaContableOriginal = ((AsientoModeloItem) this._originalDTO).getCuentaContable().getId();	
			}						
			
			if (((AsientoModeloItem) this._originalDTO).getAsientoModelo() != null) {
				idAsientoModeloOriginal = ((AsientoModeloItem) this._originalDTO).getAsientoModelo().getId();
			}
		}

		// -----------------------------------------------

		String idAsientoModelo = null;
				
		if (this.getAsientoModelo() != null) {
			idAsientoModelo = this.getAsientoModelo().getId();
		}
		
		String cuentaContable = this.getCuentaContable().getId();
		
		if (this.getCuentaContable() != null) {
			cuentaContable = this.getCuentaContable().getId();
		}
		
		

		// -----------------------------------------------

		String[] attNames = { "asientoModelo", "cuentaContable" };
		Object[] args = { idAsientoModelo, cuentaContable };

		// -----------------------------------------------

		if (idAsientoModeloOriginal != null && cuentaContableOriginal != null && idAsientoModelo != null && cuentaContable != null
				&& (idAsientoModeloOriginal.equals(idAsientoModelo) == false
						|| cuentaContableOriginal.equals(cuentaContable) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelAsientoModelo, labelCuentaContable);
			}

		} else if (idAsientoModeloOriginal == null && cuentaContableOriginal == null && idAsientoModelo != null
				&& cuentaContable != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelAsientoModelo, labelCuentaContable);
			}
		}

	}

}
