package com.massoftware.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.massoftware.backend.BackendContextPG;
import com.massoftware.backend.annotation.ClassLabelAnont;
import com.massoftware.backend.annotation.FieldConfAnont;

@ClassLabelAnont(singular = "Ejercicio contable", plural = "Ejercicios contables", singularPre = "el ejercicio contable", pluralPre = "los ejercicios contables")
public class EjercicioContable extends EntityId {

	@FieldConfAnont(label = "ID")
	private String id;

	@FieldConfAnont(label = "Nº ejercicio", required = true, unique = true)
	private Integer numero;

	@FieldConfAnont(label = "Apertura", required = true)
	private Date apertura;

	@FieldConfAnont(label = "Cierre", required = true)
	private Date cierre;

	@FieldConfAnont(label = "Cerrado")
	private Boolean cerrado;

	@FieldConfAnont(label = "Cerrado módulos")
	private Boolean cerradoModulos;

	@FieldConfAnont(label = "Comentario")
	private String comentario;

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

	public Date getApertura() {
		return apertura;
	}

	public void setApertura(Date apertura) {
		this.apertura = apertura;
	}

	public Date getCierre() {
		return cierre;
	}

	public void setCierre(Date cierre) {
		this.cierre = cierre;
	}

	public Boolean getCerrado() {
		return cerrado;
	}

	public void setCerrado(Boolean cerrado) {
		this.cerrado = cerrado;
	}

	public Boolean getCerradoModulos() {
		return cerradoModulos;
	}

	public void setCerradoModulos(Boolean cerradoModulos) {
		this.cerradoModulos = cerradoModulos;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {

		return numero + "";
	}

	public List<EjercicioContable> find() throws Exception {
		return find(-1, -1, null, new EjerciciosContablesFiltro());
	}
	
	public List<EjercicioContable> find(EjerciciosContablesFiltro filtro) throws Exception {
		return find(-1, -1, null, filtro);
	}

	public List<EjercicioContable> find(int limit, int offset, Map<String, Boolean> orderBy,
			EjerciciosContablesFiltro filtro) throws Exception {

		filtro.setterTrim();

		List<EjercicioContable> listado = new ArrayList<EjercicioContable>();

		String orderBySQL = "numero DESC";
		String whereSQL = null;

		ArrayList<Object> filtros = new ArrayList<Object>();

		// ==================================================================

		List<EntityId> items = findUtil(orderBySQL, whereSQL, limit, offset, filtros.toArray(), 0);

		for (EntityId item : items) {
			listado.add((EjercicioContable) item);
		}

		return listado;
	}

	public void loadByMaxEjercicioContable() throws Exception {
		loadByMaxEjercicioContable(_defMaxLevel);
	}

	public void loadByMaxEjercicioContable(int maxLevel) throws Exception {

		Integer maxNumero = maxValueInteger("numero");

		String atts = "*";
//		String atts = "id,numero, apertura + '1 years'::interval, cierre + '1 years'::interval, cerrado, cerradoModulos, comentario";
		String orderBy = null;
		String where = " numero = " + (maxNumero - 1);

		Object[][] table = BackendContextPG.get().find(this.getClass().getSimpleName(), atts, orderBy, where, -1, -1,
				new Object[0]);

		if (table.length > 0) {
			setter(table[0], maxLevel);
		}

		this.setId(null);
		this.setNumero(maxNumero);

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(this.getApertura());
		calendar.add(Calendar.YEAR, 1);
		this.setApertura(calendar.getTime());
		
		calendar.setTime(this.getCierre());
		calendar.add(Calendar.YEAR, 1);
		this.setCierre(calendar.getTime());

	}

}
