package com.massoftware.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;



@ClassLabelAnont(singular = "Alicuota", plural = "Alicuotas", singularPre = "la alicuota", pluralPre = "las alicuotas")
public class Alicuota extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Código", required = true, unique = true)
	private Integer codigo;
	
	@FieldConfAnont(label = "Nombre", required = true, unique = true, maxLength = 20 )
	private String descripcion;
	
	@FieldConfAnont(label = "Porcentaje")
	private BigDecimal porcentaje;
	
	@FieldConfAnont(label = "Alternativos")
	private BigDecimal porcentaje2;
	
	@FieldConfAnont(label = "")
	private BigDecimal porcentaje3;
	
	@FieldConfAnont(label = "")
	private BigDecimal porcentaje4;
	
	@FieldConfAnont(label = "Importe")
	private BigDecimal importeFijo;
	
	@FieldConfAnont(label = "Anticipo IVA")
	private boolean anticipoIva;
	
	@FieldConfAnont(label = "Cta. Contable Venta", required = true, maxLength = 11)
	private String cuentaCtbleVta;
	
	@FieldConfAnont(label = "Cta. Contable Compra", required = true, maxLength = 11)
	private String cuentaCtbleCompra;
	
	@FieldConfAnont(label = "")
	private Integer totalizaVarFact;
	
	@FieldConfAnont(label = "Tipo Percepción")
	private Integer tipoDePercepcion;
	
	@FieldConfAnont(label = "Convenio Multilateral")
	private boolean convenioMultiLateral;
	
	@FieldConfAnont(label = "")
	private Integer ejercicio;
	
	@FieldConfAnont(label = "Tipo Alicuota")
	private Integer tipoDeAlicuota;
	
	@FieldConfAnont(label = "Base Porcentaje")
	private BigDecimal basePorcentaje;
	
	@FieldConfAnont(label = "Código IIBB/AFIP",  maxLength = 4)
	private String codigoIIBBAfip;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Integer getCodigo() {
		return codigo;
	}



	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public BigDecimal getPorcentaje() {
		return porcentaje;
	}



	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}



	public BigDecimal getPorcentaje2() {
		return porcentaje2;
	}



	public void setPorcentaje2(BigDecimal porcentaje2) {
		this.porcentaje2 = porcentaje2;
	}



	public BigDecimal getPorcentaje3() {
		return porcentaje3;
	}



	public void setPorcentaje3(BigDecimal porcentaje3) {
		this.porcentaje3 = porcentaje3;
	}



	public BigDecimal getPorcentaje4() {
		return porcentaje4;
	}



	public void setPorcentaje4(BigDecimal porcentaje4) {
		this.porcentaje4 = porcentaje4;
	}



	public BigDecimal getImporteFijo() {
		return importeFijo;
	}



	public void setImporteFijo(BigDecimal importeFijo) {
		this.importeFijo = importeFijo;
	}



	public boolean isAnticipoIva() {
		return anticipoIva;
	}



	public void setAnticipoIva(boolean anticipoIva) {
		this.anticipoIva = anticipoIva;
	}



	public String getCuentaCtbleVta() {
		return cuentaCtbleVta;
	}



	public void setCuentaCtbleVta(String cuentaCtbleVta) {
		this.cuentaCtbleVta = cuentaCtbleVta;
	}



	public String getCuentaCtbleCompra() {
		return cuentaCtbleCompra;
	}



	public void setCuentaCtbleCompra(String cuentaCtbleCompra) {
		this.cuentaCtbleCompra = cuentaCtbleCompra;
	}



	public Integer getTotalizaVarFact() {
		return totalizaVarFact;
	}



	public void setTotalizaVarFact(Integer totalizaVarFact) {
		this.totalizaVarFact = totalizaVarFact;
	}



	public Integer getTipoDePercepcion() {
		return tipoDePercepcion;
	}



	public void setTipoDePercepcion(Integer tipoDePercepcion) {
		this.tipoDePercepcion = tipoDePercepcion;
	}



	public boolean isConvenioMultiLateral() {
		return convenioMultiLateral;
	}



	public void setConvenioMultiLateral(boolean convenioMultiLateral) {
		this.convenioMultiLateral = convenioMultiLateral;
	}



	public Integer getEjercicio() {
		return ejercicio;
	}



	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}



	public Integer getTipoDeAlicuota() {
		return tipoDeAlicuota;
	}



	public void setTipoDeAlicuota(Integer tipoDeAlicuota) {
		this.tipoDeAlicuota = tipoDeAlicuota;
	}



	public BigDecimal getBasePorcentaje() {
		return basePorcentaje;
	}



	public void setBasePorcentaje(BigDecimal basePorcentaje) {
		this.basePorcentaje = basePorcentaje;
	}



	public String getCodigoIIBBAfip() {
		return codigoIIBBAfip;
	}



	public void setCodigoIIBBAfip(String codigoIIBBAfip) {
		this.codigoIIBBAfip = codigoIIBBAfip;
	}


	@Override
	public String toString() {
		return "(" + codigo + ") " + descripcion;
	}
	
	
	public List<Alicuota> find(int limit, int offset, Map<String, Boolean> orderBy, AlicuotasFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<Alicuota> listado = new ArrayList<Alicuota>();

		String orderBySQL = "codigo";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getCodigo() != null) {
			filtros.add(filtro.getCodigo());
			whereSQL += "codigo" + " = ? AND ";
		}
		if (filtro.getDescripcion() != null) {
			String[] palabras = filtro.getDescripcion().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "descripcion"
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
			listado.add((Alicuota) item);
		}

		return listado;
	}

	
}
