package com.massoftware.windows.asientos_modelo;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.EntityId;

@ClassLabelAnont(singular = "Asiento modelo item", plural = "Asientos modelo item", singularPre = "el asiento modelo item", pluralPre = "los asientos modelo item")
public class AsientoModeloItemGrid extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Asiento modelo", required = true, readOnly = true)
	private AsientoModelo asientoModelo;

	@FieldConfAnont(label = "Nº asiento modelo item", required = true)
	private Integer numero;

	@FieldConfAnont(label = "ID cuenta contable")
	private String idCuentaContable;

	@FieldConfAnont(label = "Ejercicio contable", required = true, readOnly = true, columns = 10)
	private EjercicioContable ejercicioContableCuentaContable;

	@FieldConfAnont(label = "Cuenta contable", required = true, columns = 11)
	private String codigoCuentaContable;

	@FieldConfAnont(label = "Nombre cuenta contable", required = true)
	private String nombreCuentaContable;

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

	public String getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(String idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

	public EjercicioContable getEjercicioContableCuentaContable() {
		return ejercicioContableCuentaContable;
	}

	public void setEjercicioContableCuentaContable(EjercicioContable ejercicioContableCuentaContable) {
		this.ejercicioContableCuentaContable = ejercicioContableCuentaContable;
	}

	public String getCodigoCuentaContable() {
		return codigoCuentaContable;
	}

	public void setCodigoCuentaContable(String codigoCuentaContable) {
		this.codigoCuentaContable = codigoCuentaContable;
	}

	public String getNombreCuentaContable() {
		return nombreCuentaContable;
	}

	public void setNombreCuentaContable(String nombreCuentaContable) {
		this.nombreCuentaContable = nombreCuentaContable;
	}

}
