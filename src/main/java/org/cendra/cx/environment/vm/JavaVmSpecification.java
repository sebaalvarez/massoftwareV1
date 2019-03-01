package org.cendra.cx.environment.vm;

public class JavaVmSpecification {

	// /** Java Virtual Machine specification version */
	// @SuppressWarnings("unused")
	// private String version = "unknown";
	// /** Java Virtual Machine specification vendor */
	// @SuppressWarnings("unused")
	// private String vendor = "unknown";
	// /** Java Virtual Machine specification name */
	// @SuppressWarnings("unused")
	// private String name = "unknown";

	public String getVersion() {
		return System.getProperty("java.vm.specification.version");
	}

	public void setVersion(String javaVmSpecificationVersion) {
		// this.javaVmSpecificationVersion = javaVmSpecificationVersion;
	}

	public String getVendor() {
		return System.getProperty("java.vm.specification.vendor");
	}

	public void setVendor(String javaVmSpecificationVendor) {
		// this.javaVmSpecificationVendor = javaVmSpecificationVendor;
	}

	public String getName() {
		return System.getProperty("java.vm.specification.name");
	}

	public void setName(String javaVmSpecificationName) {
		// this.javaVmSpecificationName = javaVmSpecificationName;
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
		json += "\n" + t + "\"vendor\":" + buildValue(getVendor()) + ",";

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
