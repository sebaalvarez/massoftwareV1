package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Cuenta contable", plural = "Cuentas contables", singularPre = "la cuenta contable", pluralPre = "las cuentas contables")
public class CuentaContable extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Ejercicio contable", required = true, readOnly = true, columns = 10)
	private EjercicioContable ejercicioContable;

	@FieldConfAnont(label = "Integra", required = true, readOnly = true, columns = 16, maxLength = 16, mask = "9.99.99.99.99.99") // STARTS_WITCH
	private String integra;

	@FieldConfAnont(label = "Cuenta de jerarquia", required = true, columns = 16, maxLength = 16, mask = "9.99.99.99.99.99") // ENDS_WITCH
	private String cuentaJerarquia;

	@FieldConfAnont(label = "Cuenta contable", required = true, columns = 11)
	private String codigo;

	@FieldConfAnont(label = "Nombre", required = true)
	private String nombre;

	// --------------------------------------------------------------------------

	@FieldConfAnont(label = "Imputable")
	private Boolean imputable;

	@FieldConfAnont(label = "Ajusta por inflación")
	private Boolean ajustaPorInflacion;

	@FieldConfAnont(label = "Estado", required = true)
	private CuentaContableEstado cuentaContableEstado;

	@FieldConfAnont(label = "Cuenta con apropiación")
	private Boolean cuentaConApropiacion;

	// --------------------------------------------------------------------------

	@FieldConfAnont(label = "Centro de costo")
	private CentroCostoContable centroCostoContable;

	@FieldConfAnont(label = "Cuenta agrupadora")
	private String cuentaAgrupadora; // aca va cualquier texto, texto libre

	@FieldConfAnont(label = "Porcentaje", columns = 6, minValue = "0", maxValue = "999.99")
	private Double porcentaje;

	@FieldConfAnont(label = "Punto de equilibrio")
	private PuntoEquilibrio puntoEquilibrio;

	@FieldConfAnont(label = "Costo de venta")
	private CostoVenta costoVenta;

	@FieldConfAnont(label = "Puerta")
	private SeguridadPuerta seguridadPuerta;

	// --------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Boolean getImputable() {
		return imputable;
	}

	public void setImputable(Boolean imputable) {
		this.imputable = imputable;
	}

	public Boolean getAjustaPorInflacion() {
		return ajustaPorInflacion;
	}

	public void setAjustaPorInflacion(Boolean ajustaPorInflacion) {
		this.ajustaPorInflacion = ajustaPorInflacion;
	}

	public CuentaContableEstado getCuentaContableEstado() {
		return cuentaContableEstado;
	}

	public void setCuentaContableEstado(CuentaContableEstado cuentaContableEstado) {
		this.cuentaContableEstado = cuentaContableEstado;
	}

	public Boolean getCuentaConApropiacion() {
		return cuentaConApropiacion;
	}

	public void setCuentaConApropiacion(Boolean cuentaConApropiacion) {
		this.cuentaConApropiacion = cuentaConApropiacion;
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

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public PuntoEquilibrio getPuntoEquilibrio() {
		return puntoEquilibrio;
	}

	public void setPuntoEquilibrio(PuntoEquilibrio puntoEquilibrio) {
		this.puntoEquilibrio = puntoEquilibrio;
	}

	public CostoVenta getCostoVenta() {
		return costoVenta;
	}

	public void setCostoVenta(CostoVenta costoVenta) {
		this.costoVenta = costoVenta;
	}

	public SeguridadPuerta getSeguridadPuerta() {
		return seguridadPuerta;
	}

	public void setSeguridadPuerta(SeguridadPuerta seguridadPuerta) {
		this.seguridadPuerta = seguridadPuerta;
	}

	@Override
	public String toString() {

		if (ejercicioContable != null && ejercicioContable.getNumero() != null) {
			return "(" + ejercicioContable.getNumero() + "-" + codigo + ") " + nombre;
		}
		
		if(codigo == null && nombre == null ) {
			return null;
		}

		return "(" + codigo + ") " + nombre;

	}

	public List<CuentaContable> find(CuentasContableFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro, 1);
	}

	public List<CuentaContable> find(CuentasContableFiltro filtro, Map<String, Boolean> orderBy, int maxLevel) throws Exception {
		return find(-1, -1, orderBy, filtro, maxLevel);
	}

	public List<CuentaContable> find(int limit, int offset, Map<String, Boolean> orderBy, CuentasContableFiltro filtro, int maxLevel)
			throws Exception {

		filtro.setterTrim();

		List<CuentaContable> listado = new ArrayList<CuentaContable>();

		String orderBySQL = "ejercicioContable, codigo";

		if (orderBy != null && orderBy.size() > 0) {

			orderBySQL = "";

			for (Map.Entry<String, Boolean> entry : orderBy.entrySet()) {
				String order = entry.getValue() == true ? "ASC" : "DESC";
				orderBySQL += entry.getKey() + " " + order + ", ";
			}

			orderBySQL = orderBySQL.trim();
			orderBySQL = orderBySQL.substring(0, orderBySQL.length() - 1);

		}

		String whereSQL = "";

		ArrayList<Object> filtros = new ArrayList<Object>();

		if (filtro.getIntegra() != null) {
			filtros.add(filtro.getIntegra());
			whereSQL += "integra" + " = ? AND ";
		}
		if (filtro.getCuentaJerarquia() != null) {
			filtros.add(filtro.getCuentaJerarquia());
			whereSQL += "cuentaJerarquia" + " = ? AND ";
		}
		if (filtro.getCodigo() != null) {
			filtros.add(filtro.getCodigo());
			whereSQL += "codigo" + " = ? AND ";
		}
		if (filtro.getNombre() != null) {
			String[] palabras = filtro.getNombre().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "nombre"
						+ "))::VARCHAR ILIKE ('%' || TRIM(massoftware.TRANSLATE(?)) || '%')::VARCHAR AND ";
			}
		}
		if (filtro.getCuentaAgrupadora() != null) {
			String[] palabras = filtro.getCuentaAgrupadora().split(" ");
			for (String palabra : palabras) {
				filtros.add(palabra.trim());
				whereSQL += "TRIM(massoftware.TRANSLATE(" + "cuentaAgrupadora"
						+ "))::VARCHAR ILIKE ('%' || TRIM(massoftware.TRANSLATE(?)) || '%')::VARCHAR AND ";
			}
		}
		if (filtro.getCentroCostoContable() != null && filtro.getCentroCostoContable().getId() != null) {
			filtros.add(filtro.getCentroCostoContable().getId());
			whereSQL += "centroCostoContable" + " = ? AND ";
		}
		if (filtro.getPuntoEquilibrio() != null && filtro.getPuntoEquilibrio().getId() != null) {
			filtros.add(filtro.getPuntoEquilibrio().getId());
			whereSQL += "puntoEquilibrio" + " = ? AND ";
		}
		if (filtro.getEjercicioContable() != null && filtro.getEjercicioContable().getId() != null) {
			filtros.add(filtro.getEjercicioContable().getId());
			whereSQL += "ejercicioContable" + " = ? AND ";
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
			listado.add((CuentaContable) item);
		}

		return listado;
	}
	
	
	public void checkUniqueEjercicioContableAndCuentaJerarquia() throws Exception {

		String labelEjercicioContable = this.label("ejercicioContable");
		String labelCuentaJerarquia = this.label("cuentaJerarquia");

		// -----------------------------------------------

		String idEjercicioContableOriginal = null;
		String cuentaJerarquiaOriginal = null;
		if (this._originalDTO != null) {
			cuentaJerarquiaOriginal = ((CuentaContable) this._originalDTO).getCuentaJerarquia();
			if (((CuentaContable) this._originalDTO).getEjercicioContable() != null) {
				idEjercicioContableOriginal = ((CuentaContable) this._originalDTO).getEjercicioContable().getId();
			}
		}

		// -----------------------------------------------

		String idEjercicioContable = null;
		if (this.getEjercicioContable() != null) {
			idEjercicioContable = this.getEjercicioContable().getId();
		}
		String cuentaJerarquia = this.getCuentaJerarquia();

		// -----------------------------------------------

		String[] attNames = { "ejercicioContable", "cuentaJerarquia" };
		Object[] args = { idEjercicioContable, cuentaJerarquia };

		// -----------------------------------------------

		if (idEjercicioContableOriginal != null && cuentaJerarquiaOriginal != null && idEjercicioContable != null && cuentaJerarquia != null
				&& (idEjercicioContableOriginal.equals(idEjercicioContable) == false || cuentaJerarquiaOriginal.equals(cuentaJerarquia) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelCuentaJerarquia);
			}

		} else if (idEjercicioContableOriginal == null && cuentaJerarquiaOriginal == null && idEjercicioContable != null && cuentaJerarquia != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelCuentaJerarquia);
			}
		}

	}
	
	public void checkUniqueEjercicioContableAndCodigo() throws Exception {

		String labelEjercicioContable = this.label("ejercicioContable");
		String labelCodigo = this.label("codigo");

		// -----------------------------------------------

		String idEjercicioContableOriginal = null;
		String codigoOriginal = null;
		if (this._originalDTO != null) {
			codigoOriginal = ((CuentaContable) this._originalDTO).getCodigo();
			if (((CuentaContable) this._originalDTO).getEjercicioContable() != null) {
				idEjercicioContableOriginal = ((CuentaContable) this._originalDTO).getEjercicioContable().getId();
			}
		}

		// -----------------------------------------------

		String idEjercicioContable = null;
		if (this.getEjercicioContable() != null) {
			idEjercicioContable = this.getEjercicioContable().getId();
		}
		String codigo = this.getCodigo();

		// -----------------------------------------------

		String[] attNames = { "ejercicioContable", "codigo" };
		Object[] args = { idEjercicioContable, codigo };

		// -----------------------------------------------

		if (idEjercicioContableOriginal != null && codigoOriginal != null && idEjercicioContable != null && codigo != null
				&& (idEjercicioContableOriginal.equals(idEjercicioContable) == false || codigoOriginal.equals(codigo) == false)) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelCodigo);
			}

		} else if (idEjercicioContableOriginal == null && codigoOriginal == null && idEjercicioContable != null && codigo != null) {

			if (BackendContextPG.get().ifExists(this.getClass().getSimpleName(), attNames, args)) {
				throw new UniqueException(labelEjercicioContable, labelCodigo);
			}
		}

	}

}
