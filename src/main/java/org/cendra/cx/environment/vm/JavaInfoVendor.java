package org.cendra.cx.environment.vm;

public class JavaInfoVendor {

	/** Java Runtime Environment vendor */
	@SuppressWarnings("unused")
	private String vendor = "unknown";
	/** Java vendor URL */
	@SuppressWarnings("unused")
	private String url = "unknown";

	public String getVendor() {
		return System.getProperty("java.vendor");
	}

	public void setVendor(String vendor) {
		// this.javaVendor = javaVendor;
	}

	public String getUrl() {
		return System.getProperty("java.vendor.url");
	}

	public void setUrl(String url) {
		// this.javaVendorUrl = javaVendorUrl;
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

		json += "\n" + t + "\"vendor\":" + buildValue(getVendor()) + ",";
		json += "\n" + t + "\"url\":" + buildValue(getUrl()) + "";			

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
