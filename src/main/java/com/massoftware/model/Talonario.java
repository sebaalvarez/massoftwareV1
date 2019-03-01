package com.massoftware.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Talonario", plural = "Talonarios", singularPre = "el talonario", pluralPre = "los talonarios")
public class Talonario extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Nº talonario", required = true, unique = true)
	private Integer numero;

	@FieldConfAnont(label = "Nombre", required = true, unique = true)
	private String nombre;

	// def X
	@FieldConfAnont(label = "Letra", required = true)
	private TalonarioLetra talonarioLetra;

	// > 0
	@FieldConfAnont(label = "Punto de venta", required = true, maxValue = "9999")
	private Integer puntoVenta;

	@FieldConfAnont(label = "Autonumeración")
	private Boolean autonumeracion;

	@FieldConfAnont(label = "Numeración pre-impresa")
	private Boolean numeracionPreImpresa;

	@FieldConfAnont(label = "Asociado al RG 100/98")
	private Boolean asociadoRG10098;

	@FieldConfAnont(label = "Asociado a controlador fizcal", required = true)
	private TalonarioControladorFizcal talonarioControladorFizcal;

	@FieldConfAnont(label = "Primer nº")
	private Integer primerNumero;

	@FieldConfAnont(label = "Próximo nº")
	private Integer proximoNumero;

	@FieldConfAnont(label = "Último nº")
	private Integer ultimoNumero;

	@FieldConfAnont(label = "Cant. min. cbtes.")
	private Integer cantidadMinimaComprobantes;

	@FieldConfAnont(label = "Fecha")
	private Date fecha;

	@FieldConfAnont(label = "Nº C.A.I", maxValue = "99999999999999")
	private BigDecimal numeroCAI;

	@FieldConfAnont(label = "Vencimiento C.A.I")
	private Date vencimiento;

	@FieldConfAnont(label = "Días aviso vto.")
	private Integer diasAvisoVencimiento;

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

	public TalonarioLetra getTalonarioLetra() {
		return talonarioLetra;
	}

	public void setTalonarioLetra(TalonarioLetra talonarioLetra) {
		this.talonarioLetra = talonarioLetra;
	}

	public Integer getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(Integer puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Boolean getAutonumeracion() {
		return autonumeracion;
	}

	public void setAutonumeracion(Boolean autonumeracion) {
		this.autonumeracion = autonumeracion;
	}

	public Boolean getNumeracionPreImpresa() {
		return numeracionPreImpresa;
	}

	public void setNumeracionPreImpresa(Boolean numeracionPreImpresa) {
		this.numeracionPreImpresa = numeracionPreImpresa;
	}

	public Boolean getAsociadoRG10098() {
		return asociadoRG10098;
	}

	public void setAsociadoRG10098(Boolean asociadoRG10098) {
		this.asociadoRG10098 = asociadoRG10098;
	}

	public TalonarioControladorFizcal getTalonarioControladorFizcal() {
		return talonarioControladorFizcal;
	}

	public void setTalonarioControladorFizcal(TalonarioControladorFizcal talonarioControladorFizcal) {
		this.talonarioControladorFizcal = talonarioControladorFizcal;
	}

	public Integer getPrimerNumero() {
		return primerNumero;
	}

	public void setPrimerNumero(Integer primerNumero) {
		this.primerNumero = primerNumero;
	}

	public Integer getProximoNumero() {
		return proximoNumero;
	}

	public void setProximoNumero(Integer proximoNumero) {
		this.proximoNumero = proximoNumero;
	}

	public Integer getUltimoNumero() {
		return ultimoNumero;
	}

	public void setUltimoNumero(Integer ultimoNumero) {
		this.ultimoNumero = ultimoNumero;
	}

	public Integer getCantidadMinimaComprobantes() {
		return cantidadMinimaComprobantes;
	}

	public void setCantidadMinimaComprobantes(Integer cantidadMinimaComprobantes) {
		this.cantidadMinimaComprobantes = cantidadMinimaComprobantes;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getNumeroCAI() {
		return numeroCAI;
	}

	public void setNumeroCAI(BigDecimal numeroCAI) {
		this.numeroCAI = numeroCAI;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public Integer getDiasAvisoVencimiento() {
		return diasAvisoVencimiento;
	}

	public void setDiasAvisoVencimiento(Integer diasAvisoVencimiento) {
		this.diasAvisoVencimiento = diasAvisoVencimiento;
	}

	@Override
	public String toString() {
		if (numero != null && nombre != null) {
			return "(" + numero + ") " + nombre;
		}

		return null;
	}

	public List<Talonario> find(TalonariosFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro);
	}

	public List<Talonario> find(int limit, int offset, Map<String, Boolean> orderBy, TalonariosFiltro filtro)
			throws Exception {

		filtro.setterTrim();

		List<Talonario> listado = new ArrayList<Talonario>();

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
			listado.add((Talonario) item);
		}

		return listado;
	}

}
