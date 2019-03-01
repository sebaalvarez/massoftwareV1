package org.cendra.cx.environment;

public class InfoUser {

	/** User's account name */
	@SuppressWarnings("unused")
	private String name = "unknown";
	/** User's home directory */
	@SuppressWarnings("unused")
	private String home = "unknown";

	public String getName() {
		return System.getProperty("user.name");
	}

	public void setName(String name) {
		// this.name = name;
	}

	public String getHome() {
		return System.getProperty("user.home");
	}

	public void setHome(String home) {
		// this.home = home;
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

		json += "\n" + t + "\"name\":" + buildValue(getName()) + ",";
		json += "\n" + t + "\"home\":" + buildValue(getHome()) + "";
		
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
