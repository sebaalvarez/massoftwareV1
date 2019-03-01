package org.cendra.cx.environment.vm;

public class InfoJavaVm {

//	/** Java installation directory */
//	@SuppressWarnings("unused")
//	private String javaHome = "unknown";
	private JavaVmSpecification specification = new JavaVmSpecification();
//	/** Java Virtual Machine implementation version */
//	@SuppressWarnings("unused")
//	private String javaVmVersion = "unknown";
//	/** Java Virtual Machine implementation vendor */
//	@SuppressWarnings("unused")
//	private String javaVmVendor = "unknown";
//	/** Java Virtual Machine implementation name */
//	@SuppressWarnings("unused")
//	private String version = "unknown";
//	/** Name of JIT compiler to use */
//	@SuppressWarnings("unused")
//	private String javaCompiler = "unknown";

	public String getVersion() {
		return System.getProperty("java.vm.version");
	}

	public void setVersion(String version) {
		// this.javaVmVersion = javaVmVersion;
	}

	public String getVendor() {
		return System.getProperty("java.vm.vendor");
	}

	public void setVendor(String vendor) {
		// this.javaVmVendor = javaVmVendor;
	}

	public String getName() {
		return System.getProperty("java.vm.name");
	}

	public void setName(String name) {
		// this.javaVmName = javaVmName;
	}

	public JavaVmSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(JavaVmSpecification specification) {
		this.specification = specification;
	}

	public String getJavaHome() {
		return System.getProperty("java.home");
	}

	public void setJavaHome(String javaHome) {
		// this.javaHome = javaHome;
	}

	public String getJavaCompiler() {
		return System.getProperty("java.compiler");
	}

	public void setJavaCompiler(String javaCompiler) {
		// this.javaCompiler = javaCompiler;
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

		json += "\n" + t + "\"name\":" + buildValue(getName()) + ",";
		json += "\n" + t + "\"version\":" + buildValue(getVersion()) + ",";
		json += "\n" + t + "\"javaCompiler\":" + buildValue(getJavaCompiler())
				+ ",";
		json += "\n" + t + "\"javaVmVendor\":" + buildValue(getVendor()) + ",";
		json += "\n" + t + "\"javaHome\":" + buildValue(getJavaHome()) + ",";

		json += "\n" + getSpecification().toJson("specification", cantT + 1)
				+ "";

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

		return "\"" + value + "\"";

	}

}
