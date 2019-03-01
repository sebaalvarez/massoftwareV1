package com.massoftware.windows.tipos_comprobantes;

import com.massoftware.windows.UtilModel;

public class TiposComprobantes {

	private Integer numeroSucursal;
	private Integer numero;
	private String nombre;
	private String abreviatura;
	private String clase;
	private String concepto;
	private Boolean stk;
	private Boolean iva;
	private Boolean comision;
	private Boolean comLey;
	private Boolean esta;

	public Integer getNumeroSucursal() {
		return numeroSucursal;
	}

	public void setNumeroSucursal(Integer numeroSucursal) {
		this.numeroSucursal = numeroSucursal;
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
		this.nombre = UtilModel.format(nombre);
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = UtilModel.format(abreviatura);
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Boolean getStk() {
		return stk;
	}

	public void setStk(Boolean stk) {
		this.stk = stk;
	}

	public Boolean getIva() {
		return iva;
	}

	public void setIva(Boolean iva) {
		this.iva = iva;
	}

	public Boolean getComision() {
		return comision;
	}

	public void setComision(Boolean comision) {
		this.comision = comision;
	}

	public Boolean getComLey() {
		return comLey;
	}

	public void setComLey(Boolean comLey) {
		this.comLey = comLey;
	}

	public Boolean getEsta() {
		return esta;
	}

	public void setEsta(Boolean esta) {
		this.esta = esta;
	}

	@Override
	public String toString() {
		return "(" + numeroSucursal + "-" + numero + ") " + nombre;
	}

}
