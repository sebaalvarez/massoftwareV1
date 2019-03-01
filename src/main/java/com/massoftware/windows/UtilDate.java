package com.massoftware.windows;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.data.util.converter.Converter;

public class UtilDate {
	
	

	public static Date parseDate(String dateString, String prefixYear1,
			String prefixYear2, String prefixYear3) {
		return parseDate(dateString, null, "2", "20", "200");
	}

	public static Date parseDate(String dateString, Integer yearDefault) {
		return parseDate(dateString, yearDefault, "2", "20", "200");
	}

	public static Date parseDate(String dateString) {
		return parseDate(dateString, null, "2", "20", "200");
	}

	public static Date parseDate(String dateString, Integer yearDefault,
			String prefixYear1, String prefixYear2, String prefixYear3) {

		try {

			dateString = dateString.trim();

			LocalDateTime now = LocalDateTime.now();

			int day = 0;
			int month = 0;
			int year = 0;

			if (dateString.contains("/")) {

				String fields[] = dateString.split("/");

				for (int i = 0; i < fields.length; i++) {
					fields[i] = fields[i].trim();
				}

				if (fields.length == 0) {
					day = now.getDayOfMonth();
					month = now.getMonthValue() - 1;
					year = now.getYear();
				} else if (fields.length == 1) {
					if (fields[0].isEmpty()) {
						day = now.getDayOfMonth();
					} else {
						day = Integer.parseInt(fields[0]);
					}
					month = now.getMonthValue() - 1;
					year = now.getYear();
				} else if (fields.length == 2) {
					if (fields[0].isEmpty()) {
						day = now.getDayOfMonth();
					} else {
						day = Integer.parseInt(fields[0]);
					}
					if (fields[1].isEmpty()) {
						month = now.getMonthValue() - 1;
					} else {
						month = Integer.parseInt(fields[1]) - 1;
					}
					year = now.getYear();
				} else if (fields.length >= 3) {
					if (fields[0].isEmpty()) {
						day = now.getDayOfMonth();
					} else {
						day = Integer.parseInt(fields[0]);
					}
					if (fields[1].isEmpty()) {
						month = now.getMonthValue() - 1;
					} else {
						month = Integer.parseInt(fields[1]) - 1;
					}
					if (fields[2].isEmpty()) {
						year = now.getYear();
					} else {

						year = Integer.parseInt(fields[2]);

						if (year < 0) {
							throw new Exception("year < 0");
						} else if (year < 10) {
							if (prefixYear3 != null
									&& prefixYear3.trim().isEmpty() == false) {
								year = Integer.parseInt(prefixYear3.trim()
										+ year);
							}
						} else if (year < 100) {
							if (prefixYear2 != null
									&& prefixYear2.trim().isEmpty() == false) {
								year = Integer.parseInt(prefixYear2.trim()
										+ year);
							}
						} else if (year < 1000) {
							if (prefixYear1 != null
									&& prefixYear1.trim().isEmpty() == false) {
								year = Integer.parseInt(prefixYear1.trim()
										+ year);
							}
						}

					}
				}

			} else {

				if (dateString.length() == 1) {

					day = Integer.parseInt(dateString);
					month = now.getMonthValue() - 1;
					year = now.getYear();

				} else if (dateString.length() == 2) {

					day = Integer.parseInt(dateString);
					month = now.getMonthValue() - 1;
					year = now.getYear();

				} else if (dateString.length() == 3) {

					String dayString = dateString.substring(0, 2);
					String monthString = dateString.substring(2, 3);

					day = Integer.parseInt(dayString);
					month = Integer.parseInt(monthString) - 1;
					year = now.getYear();

				} else if (dateString.length() == 4) {

					String dayString = dateString.substring(0, 2);
					String monthString = dateString.substring(2, 4);

					day = Integer.parseInt(dayString);
					month = Integer.parseInt(monthString) - 1;
					year = now.getYear();

				} else if (dateString.length() == 5) {

					String dayString = dateString.substring(0, 2);
					String monthString = dateString.substring(2, 4);
					String yearString = dateString.substring(4, 5);

					day = Integer.parseInt(dayString);
					month = Integer.parseInt(monthString) - 1;
					if (prefixYear3 != null
							&& prefixYear3.trim().isEmpty() == false) {
						year = Integer
								.parseInt(prefixYear3.trim() + yearString);
					} else {
						year = Integer.parseInt(yearString);
					}
				} else if (dateString.length() == 6) {

					String dayString = dateString.substring(0, 2);
					String monthString = dateString.substring(2, 4);
					String yearString = dateString.substring(4, 6);

					day = Integer.parseInt(dayString);
					month = Integer.parseInt(monthString) - 1;
					if (prefixYear2 != null
							&& prefixYear2.trim().isEmpty() == false) {
						year = Integer
								.parseInt(prefixYear2.trim() + yearString);
					} else {
						year = Integer.parseInt(yearString);
					}
				} else if (dateString.length() == 7) {

					String dayString = dateString.substring(0, 2);
					String monthString = dateString.substring(2, 4);
					String yearString = dateString.substring(4, 7);

					day = Integer.parseInt(dayString);
					month = Integer.parseInt(monthString) - 1;
					if (prefixYear1 != null
							&& prefixYear1.trim().isEmpty() == false) {
						year = Integer
								.parseInt(prefixYear1.trim() + yearString);
					} else {
						year = Integer.parseInt(yearString);
					}
				} else if (dateString.length() == 8) {

					String dayString = dateString.substring(0, 2);
					String monthString = dateString.substring(2, 4);
					String yearString = dateString.substring(4, 8);

					day = Integer.parseInt(dayString);
					month = Integer.parseInt(monthString) - 1;
					year = Integer.parseInt(yearString);

				} else {

					String dayString = dateString.substring(0, 2);
					String monthString = dateString.substring(2, 4);
					String yearString = dateString.substring(4, 8);

					day = Integer.parseInt(dayString);
					month = Integer.parseInt(monthString) - 1;
					year = Integer.parseInt(yearString);

				}

			}

			if (yearDefault != null && yearDefault > 0) {
				String yearDefaultString = yearDefault.toString();
				if (yearDefaultString.length() == 1) {
					if (prefixYear3 != null
							&& prefixYear3.trim().isEmpty() == false) {
						year = Integer.parseInt(prefixYear3.trim()
								+ yearDefaultString);
					} else {
						year = Integer.parseInt(yearDefaultString);
					}
				} else if (yearDefaultString.length() == 2) {
					if (prefixYear2 != null
							&& prefixYear2.trim().isEmpty() == false) {
						year = Integer.parseInt(prefixYear2.trim()
								+ yearDefaultString);
					} else {
						year = Integer.parseInt(yearDefaultString);
					}
				} else if (yearDefaultString.length() == 3) {
					if (prefixYear1 != null
							&& prefixYear1.trim().isEmpty() == false) {
						year = Integer.parseInt(prefixYear1.trim()
								+ yearDefaultString);
					} else {
						year = Integer.parseInt(yearDefaultString);
					}
				} else if (yearDefaultString.length() >= 4) {
					year = Integer.parseInt(yearDefaultString);
				}
			}

			GregorianCalendar c = new GregorianCalendar(year, month, day);
			return c.getTime();

		} catch (Exception e) {
//			e.printStackTrace();
			throw new Converter.ConversionException(
					"La fecha ingresada es inválida. Usted ingresó el valor \""
							+ dateString + "\".");
		}

	}

}
