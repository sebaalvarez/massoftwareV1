package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class CuentasContableFiltro extends Entity {

	@FieldConfAnont(label = "Ejercicio contable")
	private EjercicioContable ejercicioContable;

	@FieldConfAnont(label = "Integra")
	private String integra;

	@FieldConfAnont(label = "Cuenta de jerarquia")
	private String cuentaJerarquia;

	@FieldConfAnont(label = "Cuenta contable")
	private String codigo;

	@FieldConfAnont(label = "Nombre")
	private String nombre;

	@FieldConfAnont(label = "Centro de costo")
	private CentroCostoContable centroCostoContable;

	@FieldConfAnont(label = "Cuenta agrupadora")
	private String cuentaAgrupadora;

	@FieldConfAnont(label = "Punto de equilibrio")
	private PuntoEquilibrio puntoEquilibrio;

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	public String getIntegra() {
		return integra;
	}

	public void setIntegra(String integra) {
		this.integra = integra;
	}

	public String getCuentaJerarquia() {
		return cuentaJerarquia;
	}

	public void setCuentaJerarquia(String cuentaJerarquia) {
		this.cuentaJerarquia = cuentaJerarquia;
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

	public CentroCostoContable getCentroCostoContable() {
		return centroCostoContable;
	}

	public void setCentroCostoContable(CentroCostoContable centroCostoContable) {
		this.centroCostoContable = centroCostoContable;
	}

	public String getCuentaAgrupadora() {
		return cuentaAgrupadora;
	}

	public void setCuentaAgrupadora(String cuentaAgrupadora) {
		this.cuentaAgrupadora = cuentaAgrupadora;
	}

	public PuntoEquilibrio getPuntoEquilibrio() {
		return puntoEquilibrio;
	}

	public void setPuntoEquilibrio(PuntoEquilibrio puntoEquilibrio) {
		this.puntoEquilibrio = puntoEquilibrio;
	}

}
