package org.cendra.cx.environment.vm;

import java.util.ArrayList;
import java.util.List;

public class BinaryPath {

	/** User's current working directory */
	@SuppressWarnings("unused")
	private String userCurrentWorkingDirectory = "unknown";

	// /** Java class path */
	// @SuppressWarnings("unused")
	// private String javaClassPath = "unknown";
	/** List of paths to search when loading libraries */
	@SuppressWarnings("unused")
	private List<String> libraryPath;
	/** Path of extension directory or directories */
	@SuppressWarnings("unused")
	private String extDirs = "unknown";

	/** Java class path */
	@SuppressWarnings("unused")
	private List<String> classPath;

	// public String getJavaClassPath() {
	// return System.getProperty("java.class.path");
	// }
	//
	// public void setJavaClassPath(String javaClassPath) {
	// // this.javaClassPath = javaClassPath;
	// }

	public List<String> getLibraryPath() {
		List<String> r = new ArrayList<String>();

		String s = System.getProperty("java.library.path");
		
		if (s.contains(";")) {
			String[] array = s.split(";");

			for (String item : array) {
				if (item != null && item.isEmpty() == false) {
					r.add(item);
				}
			}
		} else {
			r.add(s);
		}

		return r;
	}

	public void setLibraryPath(String javaLibraryPath) {
		// this.javaLibraryPath = javaLibraryPath;
	}

	public List<String> getExtDirs() {
		List<String> l = new ArrayList<String>();
		String s = System.getProperty("java.ext.dirs");
		if (s != null) {
			String[] ss = s.split(";");

			for (String e : ss) {
				l.add(e);
			}

		}

		return l;

		// return System.getProperty("java.ext.dirs");
	}

	public void setExtDirs(String javaExtDirs) {
		// this.javaExtDirs = javaExtDirs;
	}

	public List<String> getClassPath() {
		List<String> l = new ArrayList<String>();
		String s = System.getProperty("java.class.path");		
		if (s != null) {
			String[] ss = s.split(";");

			for (String e : ss) {
				l.add(e);
			}

		}

		return l;

	}

	public void setClassPath(List<String> binaryPathList) {
		// this.binaryPathList = binaryPathList;
	}

	public String getUserCurrentWorkingDirectory() {
		return System.getProperty("user.dir");
	}

	public void setUserCurrentWorkingDirectory(String dir) {
		// this.dir = dir;
	}

	public String toJson() {
		return toJson(this.getClass().getSimpleName(), 1);
	}

	public String toJson(String name, int cantT) {

		String t = "";
		String t0 = "";

		for (int i = 0; i < cantT; i++) {
			t += "\t";
			if (i < cantT - 1) {
				t0 += "\t";
			}
		}

		String json = t0 + "\"" + name + "\": {\n";

		json += "\n" + t + "\"userCurrentWorkingDirectory\":"
				+ buildValue(getUserCurrentWorkingDirectory()) + ",";
		json += "\n" + t + "\"extDirs\":" + buildValueArray(getExtDirs(), cantT + 1) + ",";
		json += "\n" + t + "\"classPath\":" + buildValueArray(getClassPath(), cantT + 1) + ",";
		json += "\n" + t + "\"libraryPath\":" + buildValueArray(getLibraryPath(), cantT + 1) + "";

		json += "\n" + t0 + "}";

		return json;
	}

	private String buildValue(Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof Number) {
			return value.toString();
		}

		if (value instanceof Boolean) {
			return value.toString();
		}

		if (value instanceof List) {

		}

		return "\"" + value + "\"";

	}

	private String buildValueArray(List<String> list, int cantT) {
		
		String t = "";
		String t0 = "";

		for (int i = 0; i < cantT; i++) {
			t += "\t";
			if (i < cantT - 1) {
				t0 += "\t";
			}
		}

		String newValue = "";

		for (String item : list) {
			newValue +=  "\n" + t + "\"" + item + "\", ";
		}
		newValue = newValue.substring(0, newValue.length() - 2);

		return " [\n" + newValue + "\n "+ t0 + "]";

	}

}
