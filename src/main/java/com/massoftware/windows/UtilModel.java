package com.massoftware.windows;

import java.util.Calendar;
import java.util.Date;

public class UtilModel {

	public static String format(String value) {
		if (value != null) {
			value = value.trim();
			if (value.isEmpty()) {
				value = null;
			}
		}
		return value;
	}

	public static Boolean format(Boolean value) {
		if (value == null) {
			value = false;
		}
		return value;
	}
	
	public static Date sumarDiasAFecha(Date fecha, int dias) {

		if (dias == 0)
			return fecha;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);

		return calendar.getTime();
	}

}
