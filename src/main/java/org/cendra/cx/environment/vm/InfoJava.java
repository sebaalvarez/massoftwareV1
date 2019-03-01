package org.cendra.cx.environment.vm;

public class InfoJava {

	/** Java Runtime Environment version */
	@SuppressWarnings("unused")
	private String javaVersion = "unknown";
	/** Java class format version number */
	@SuppressWarnings("unused")
	private String javaClassVersion = "unknown";
	private JavaInfoVendor vendor = new JavaInfoVendor();
	private JavaSpecification specification = new JavaSpecification();
	private InfoJavaVm vm = new InfoJavaVm();
	private BinaryPath binaryPath = new BinaryPath();

	public String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public void setJavaVersion(String javaVersion) {
		// this.javaVersion = javaVersion;
	}

	public InfoJavaVm getVm() {
		return vm;
	}

	public void setVm(InfoJavaVm vm) {
		this.vm = vm;
	}

	public String getJavaClassVersion() {
		return System.getProperty("java.class.version");
	}

	public void setJavaClassVersion(String javaClassVersion) {
		// this.javaClassVersion = javaClassVersion;
	}

	public JavaInfoVendor getVendor() {
		return vendor;
	}

	public void setVendor(JavaInfoVendor vendor) {
		this.vendor = vendor;
	}

	public JavaSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(JavaSpecification specification) {
		this.specification = specification;
	}

	public BinaryPath getBinaryPath() {
		return binaryPath;
	}

	public void setBinaryPath(BinaryPath binaryPath) {
		this.binaryPath = binaryPath;
	}
	
	public String toJson() {
		return toJson(this.getClass().getSimpleName(), 1);
	}

	public String toJson(String name, int cantT) {
		
		String t = "";
		String t0 = "";

		for (int i = 0; i < cantT; i++) {			
			t += "\t";
			if(i < cantT -1){
				t0 += "\t";
			}
		}

		String json = t0 +  "\"" + name + "\": {\n";

		json += "\n" + t + "\"javaVersion\":" + buildValue(getJavaVersion()) + ",";
		json += "\n" + t + "\"javaClassVersion\":" + buildValue(getJavaClassVersion()) + ",";		
		json += "\n" + getVendor().toJson("vendor", cantT + 1) + ",";				
		json += "\n" + getSpecification().toJson("specification", cantT + 1) + ",";
		json += "\n" + getVm().toJson("vm", cantT + 1) + ",";
		json += "\n" + getBinaryPath().toJson("binaryPath", cantT + 1) + "";

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
