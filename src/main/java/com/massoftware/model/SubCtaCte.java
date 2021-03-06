package com.massoftware.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Sub Cuenta Corriente", plural = "Sub Cuentas Corriente", singularPre = "la Sub Cuenta Corriente", pluralPre = "las Sub Cuentas Corriente")
public class SubCtaCte extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Número", required = true, unique = true)
	private Integer numero;
	
	@FieldConfAnont(label = "Nombre", required = true)
	private String nombre;
	
	@FieldConfAnont(label = "Cta Cte General")
	private boolean ctaCteGral;
	
	@FieldConfAnont(label = "Cuenta Contable", required = true)
	private String ctaCtbleVta;
	
	@FieldConfAnont(label = "Cuenta fondo", required = true)
	private String ctaFondo;
	
	@FieldConfAnont(label = "")
	private Integer apropContable;
	
	@FieldConfAnont(label = "Plazo Máximo Bloqueo")
	private Integer plazoMaximoBloqueo;
	
	@FieldConfAnont(label = "Monto a Bloquear (mayor a)")
	private BigDecimal montoABloquear;
	
	@FieldConfAnont(label = "Puerta de Seguridad")
	private Integer door;
	
	@FieldConfAnont(label = "")
	private Integer ejercicio;
	
	@FieldConfAnont(label = "Moneda")
	private Integer moneda;

	
	
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


	public boolean isCtaCteGral() {
		return ctaCteGral;
	}


	public void setCtaCteGral(boolean ctaCteGral) {
		this.ctaCteGral = ctaCteGral;
	}


	public String getCtaCtbleVta() {
		return ctaCtbleVta;
	}


	public void setCtaCtbleVta(String ctaCtbleVta) {
		this.ctaCtbleVta = ctaCtbleVta;
	}


	public String getCtaFondo() {
		return ctaFondo;
	}


	public void setCtaFondo(String ctaFondo) {
		this.ctaFondo = ctaFondo;
	}


	public Integer getApropContable() {
		return apropContable;
	}


	public void setApropContable(Integer apropContable) {
		this.apropContable = apropContable;
	}


	public Integer getPlazoMaximoBloqueo() {
		return plazoMaximoBloqueo;
	}


	public void setPlazoMaximoBloqueo(Integer plazoMaximoBloqueo) {
		this.plazoMaximoBloqueo = plazoMaximoBloqueo;
	}


	public BigDecimal getMontoABloquear() {
		return montoABloquear;
	}


	public void setMontoABloquear(BigDecimal montoABloquear) {
		this.montoABloquear = montoABloquear;
	}


	public Integer getDoor() {
		return door;
	}


	public void setDoor(Integer door) {
		this.door = door;
	}


	public Integer getEjercicio() {
		return ejercicio;
	}


	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}


	public Integer getMoneda() {
		return moneda;
	}


	public void setMoneda(Integer moneda) {
		this.moneda = moneda;
	}


	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}
	
	
	public List<SubCtaCte> find(int limit, int offset, Map<String, Boolean> orderBy, SubCtasCteFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<SubCtaCte> listado = new ArrayList<SubCtaCte>();

		String orderBySQL = "SubCtaCte";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getNumero() != null) {
			filtros.add(filtro.getNumero());
			whereSQL += "SubCtaCte" + " = ? AND ";
		}
		if (filtro.getNombre() != null) {
			String[] palabras = filtro.getNombre().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "nombre"
						+ "))::VARCHAR ILIKE ('%' || TRIM(massoftware.TRANSLATE(?)) || '%')::VARCHAR AND ";
			}
		}

		// ==================================================================

		whereSQL = whereSQL.trim();
		if (whereSQL.length() > 0) {
			whereSQL = whereSQL.substring(0, whereSQL.length() - 4);
		} else {
			whereSQL = null;
		}

		// ==================================================================

		List<EntityId> items = findUtil(orderBySQL, whereSQL, limit, offset, filtros.toArray(), 1);

		for (EntityId item : items) {
			listado.add((SubCtaCte) item);
		}

		return listado;
	}

	
}
