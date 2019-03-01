package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Sucursal", plural = "Sucursales", singularPre = "la sucursal", pluralPre = "las sucursales")
public class Sucursal extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Tipo", required = true)
	private SucursalTipo sucursalTipo;

	@FieldConfAnont(label = "Nº sucursal", required = true, unique = true)
	private Integer numero;

	@FieldConfAnont(label = "Nombre", required = true, unique = true)
	private String nombre;

	@FieldConfAnont(label = "Abreviatura", required = true, unique = true, columns = 5, maxLength = 4 )
	private String abreviatura;

	// ----------------------------------------------------------

	@FieldConfAnont(labelError = "Cuenta cliente desde", columns = 10)	
	private String cuentaClienteDesde;

	@FieldConfAnont(labelError = "Cuenta cliente desde", columns = 10)	
	private String cuentaClienteHasa;

	@FieldConfAnont(labelError = "Cantidad de caracteres ciente", required = true, columns = 6)	
	private Integer cantidadCaracteresCliente;

	@FieldConfAnont(labelError = "Identificación numérica ciente")	
	private Boolean identificacionNumericaCliente;

	@FieldConfAnont(labelError = "Permite cambiar ciente")	
	private Boolean permiteCambiarCliente;

	// ----------------------------------------------------------

	@FieldConfAnont(labelError = "Clientes ocacionales desde")	
	private Integer clientesOcacionalesDesde;

	@FieldConfAnont(labelError = "Clientes ocacionales hasta")	
	private Integer clientesOcacionalesHasa;

	// ----------------------------------------------------------

	@FieldConfAnont(labelError = "Nº cobranza desde")	
	private Integer nroCobranzaDesde;

	@FieldConfAnont(labelError = "Nº cobranza hasta")	
	private Integer nroCobranzaHasa;

	// ----------------------------------------------------------

	@FieldConfAnont(labelError = "Proveedores desde", columns = 10)
	private String proveedoresDesde;

	@FieldConfAnont(labelError = "Proveedores hasta", columns = 10)
	private String proveedoresHasa;

	@FieldConfAnont(labelError = "Cantidad de caracteres proovedor", required = true, columns = 6)	
	private Integer cantidadCaracteresProveedor;

	@FieldConfAnont(labelError = "Identificación numérica proovedor")	
	private Boolean identificacionNumericaProveedor;

	@FieldConfAnont(labelError = "Permite cambiar proovedor")		
	private Boolean permiteCambiarProveedor;

	// ----------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SucursalTipo getSucursalTipo() {
		return sucursalTipo;
	}

	public void setSucursalTipo(SucursalTipo sucursalTipo) {
		this.sucursalTipo = sucursalTipo;
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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getCuentaClienteDesde() {
		return cuentaClienteDesde;
	}

	public void setCuentaClienteDesde(String cuentaClienteDesde) {
		this.cuentaClienteDesde = cuentaClienteDesde;
	}

	public String getCuentaClienteHasa() {
		return cuentaClienteHasa;
	}

	public void setCuentaClienteHasa(String cuentaClienteHasa) {
		this.cuentaClienteHasa = cuentaClienteHasa;
	}

	public Integer getCantidadCaracteresCliente() {
		return cantidadCaracteresCliente;
	}

	public void setCantidadCaracteresCliente(Integer cantidadCaracteresCliente) {
		this.cantidadCaracteresCliente = cantidadCaracteresCliente;
	}

	public Boolean getIdentificacionNumericaCliente() {
		return identificacionNumericaCliente;
	}

	public void setIdentificacionNumericaCliente(Boolean identificacionNumericaCliente) {
		this.identificacionNumericaCliente = identificacionNumericaCliente;
	}

	public Boolean getPermiteCambiarCliente() {
		return permiteCambiarCliente;
	}

	public void setPermiteCambiarCliente(Boolean permiteCambiarCliente) {
		this.permiteCambiarCliente = permiteCambiarCliente;
	}

	public Integer getClientesOcacionalesDesde() {
		return clientesOcacionalesDesde;
	}

	public void setClientesOcacionalesDesde(Integer clientesOcacionalesDesde) {
		this.clientesOcacionalesDesde = clientesOcacionalesDesde;
	}

	public Integer getClientesOcacionalesHasa() {
		return clientesOcacionalesHasa;
	}

	public void setClientesOcacionalesHasa(Integer clientesOcacionalesHasa) {
		this.clientesOcacionalesHasa = clientesOcacionalesHasa;
	}

	public Integer getNroCobranzaDesde() {
		return nroCobranzaDesde;
	}

	public void setNroCobranzaDesde(Integer nroCobranzaDesde) {
		this.nroCobranzaDesde = nroCobranzaDesde;
	}

	public Integer getNroCobranzaHasa() {
		return nroCobranzaHasa;
	}

	public void setNroCobranzaHasa(Integer nroCobranzaHasa) {
		this.nroCobranzaHasa = nroCobranzaHasa;
	}

	public String getProveedoresDesde() {
		return proveedoresDesde;
	}

	public void setProveedoresDesde(String proveedoresDesde) {
		this.proveedoresDesde = proveedoresDesde;
	}

	public String getProveedoresHasa() {
		return proveedoresHasa;
	}

	public void setProveedoresHasa(String proveedoresHasa) {
		this.proveedoresHasa = proveedoresHasa;
	}

	public Integer getCantidadCaracteresProveedor() {
		return cantidadCaracteresProveedor;
	}

	public void setCantidadCaracteresProveedor(Integer cantidadCaracteresProveedor) {
		this.cantidadCaracteresProveedor = cantidadCaracteresProveedor;
	}

	public Boolean getIdentificacionNumericaProveedor() {
		return identificacionNumericaProveedor;
	}

	public void setIdentificacionNumericaProveedor(Boolean identificacionNumericaProveedor) {
		this.identificacionNumericaProveedor = identificacionNumericaProveedor;
	}

	public Boolean getPermiteCambiarProveedor() {
		return permiteCambiarProveedor;
	}

	public void setPermiteCambiarProveedor(Boolean permiteCambiarProveedor) {
		this.permiteCambiarProveedor = permiteCambiarProveedor;
	}

	@Override
	public String toString() {

		if (sucursalTipo != null && sucursalTipo.getNumero() != null) {
			return "(" + sucursalTipo.getNumero() + "-" + numero + ") " + abreviatura + " " + nombre;
		}

		return "(" + numero + ") " + nombre;

	}

	public List<Sucursal> find(int limit, int offset, Map<String, Boolean> orderBy, SucursalesFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<Sucursal> listado = new ArrayList<Sucursal>();

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
			listado.add((Sucursal) item);
		}

		return listado;
	}

}
