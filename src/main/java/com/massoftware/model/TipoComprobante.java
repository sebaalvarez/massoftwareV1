package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Tipo de comprobante", plural = "Tipos de comprobantes", singularPre = "el tipo de comprobante", pluralPre = "los tipos de comprobantes")
public class TipoComprobante extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;
	
	@FieldConfAnont(label = "Nº tipo comprobante", required = true, unique = true)
	private Integer numero;
	
	@FieldConfAnont(label = "Nombre", required = true, unique = true)
	private String nombre;
	
	@FieldConfAnont(label = "Abreviatura", required = true, unique = true)
	private String abreviatura;
	
	
	
	
	
	@FieldConfAnont(label = "CUIT", required = true, unique = true,  columns = 11, maxLength = 11, mask = "99-99999999-9" )
	private Long cuit;
	
	@FieldConfAnont(label = "Obsoleto")
	private Boolean bloqueado;
	
	@FieldConfAnont(label = "Hoja")
	private Integer hoja;
	
	@FieldConfAnont(label = "Primera fila")
	private Integer primeraFila;
	
	@FieldConfAnont(label = "Última fila")
	private Integer ultimaFila;
	
	@FieldConfAnont(label = "Fecha", columns = 6, maxLength = 3)
	private String fecha;
	
	@FieldConfAnont(label = "Descripción", columns = 6, maxLength = 3)
	private String descripcion;
	
	@FieldConfAnont(label = "Referencia 1", columns = 6, maxLength = 3)
	private String referencia1;
	
	@FieldConfAnont(label = "Importe", columns = 6, maxLength = 3)
	private String importe;
	
	@FieldConfAnont(label = "Referencia 2", columns = 6, maxLength = 3)
	private String referencia2;
	
	@FieldConfAnont(label = "Saldo", columns = 6, maxLength = 3)
	private String saldo;

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

	public Long getCuit() {
		return cuit;
	}

	public void setCuit(Long cuit) {
		this.cuit = cuit;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Integer getHoja() {
		return hoja;
	}

	public void setHoja(Integer hoja) {
		this.hoja = hoja;
	}

	public Integer getPrimeraFila() {
		return primeraFila;
	}

	public void setPrimeraFila(Integer primeraFila) {
		this.primeraFila = primeraFila;
	}

	public Integer getUltimaFila() {
		return ultimaFila;
	}

	public void setUltimaFila(Integer ultimaFila) {
		this.ultimaFila = ultimaFila;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getReferencia1() {
		return referencia1;
	}

	public void setReferencia1(String referencia1) {
		this.referencia1 = referencia1;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getReferencia2() {
		return referencia2;
	}

	public void setReferencia2(String referencia2) {
		this.referencia2 = referencia2;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		if(numero != null && nombre != null) {
			return "(" + numero + ") " + nombre;	
		}
		
		return null;
	}

	public List<TipoComprobante> find(BancosFiltro bancosFiltro) throws Exception {
		return find(-1, -1, null, bancosFiltro);
	}

	public List<TipoComprobante> find(int limit, int offset, Map<String, Boolean> orderBy, BancosFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<TipoComprobante> listado = new ArrayList<TipoComprobante>();

		String orderBySQL = "numero";
		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getNumero() != null) {
			filtros.add(filtro.getNumero());
			whereSQL += "numero" + " = ? AND ";
		}
		if (filtro.getNombre() != null) {
			String[] palabras = filtro.getNombre().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "nombre"
						+ "))::VARCHAR ILIKE ('%' || TRIM(massoftware.TRANSLATE(?)) || '%')::VARCHAR AND ";
			}
		}
		if (filtro.getBloqueado() != null && filtro.getBloqueado() == 1) {
			filtros.add(true);
			whereSQL += "bloqueado" + " = ? AND ";
		} else if (filtro.getBloqueado() != null && filtro.getBloqueado() == 2) {
			filtros.add(false);
			whereSQL += "bloqueado" + " = ? AND ";
		}

		// ==================================================================

		whereSQL = whereSQL.trim();
		if (whereSQL.length() > 0) {
			whereSQL = whereSQL.substring(0, whereSQL.length() - 4);
		} else {
			whereSQL = null;
		}

		// ==================================================================

		List<EntityId> items = findUtil(orderBySQL, whereSQL, limit, offset, filtros.toArray(), 0);

		for (EntityId item : items) {
			listado.add((TipoComprobante) item);
		}

		return listado;
	}



}
