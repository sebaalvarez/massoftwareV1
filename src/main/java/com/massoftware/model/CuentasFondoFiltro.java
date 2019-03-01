package com.massoftware.model;

import com.massoftware.backend.annotation.FieldConfAnont;

public class CuentasFondoFiltro extends Entity {

	@FieldConfAnont(label = "Rubro")
	private CuentaFondoRubro cuentaFondoRubro = new CuentaFondoRubro();

	@FieldConfAnont(label = "Grupo")
	private CuentaFondoGrupo cuentaFondoGrupo = new CuentaFondoGrupo();

	@FieldConfAnont(label = "Banco")
	private Banco banco = new Banco();

	@FieldConfAnont(label = "Nº cuenta")
	private Integer numero;

	@FieldConfAnont(label = "Nombre")
	private String nombre;

	@FieldConfAnont(label = "Obsoleta")
	private Integer bloqueado;

	public CuentaFondoRubro getCuentaFondoRubro() {
		return cuentaFondoRubro;
	}

	public void setCuentaFondoRubro(CuentaFondoRubro cuentaFondoRubro) {
		this.cuentaFondoRubro = cuentaFondoRubro;
	}

	public CuentaFondoGrupo getCuentaFondoGrupo() {
		return cuentaFondoGrupo;
	}

	public void setCuentaFondoGrupo(CuentaFondoGrupo cuentaFondoGrupo) {
		this.cuentaFondoGrupo = cuentaFondoGrupo;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
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

	public Integer getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Integer bloqueado) {
		this.bloqueado = bloqueado;
	}
}
