package com.massoftware.windows.monedas;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.massoftware.windows.UtilModel;

public class Monedas {

	private Integer numero;
	private String nombre;
	private String abreviatura;
	private BigDecimal cotizacion;
	private Date fecha;

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

	public BigDecimal getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(BigDecimal cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {

		String c = "";
		String f = "";

		if (cotizacion != null) {
			BigDecimal bd = cotizacion.setScale(4, BigDecimal.ROUND_DOWN);

			NumberFormat nf = DecimalFormat.getNumberInstance(Locale.US);
			DecimalFormat df = (DecimalFormat) nf;
			// df.applyPattern("#,###,###");

			df.setMaximumFractionDigits(5);

			df.setMinimumFractionDigits(0);

			df.setGroupingUsed(false);

			c = df.format(bd);
		}

		if (fecha != null) {
			f = new SimpleDateFormat("dd/MM/YYYY").format(fecha);
		}

		return "(" + numero + ") " + nombre + " [ " + c + "  //  " + f + " ]";
	}

}
