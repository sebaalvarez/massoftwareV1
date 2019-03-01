package org.cendra.cx.environment;

import java.util.Locale;
import java.util.TimeZone;

public class InfoOs {

	/** Operating system name */
	@SuppressWarnings("unused")
	private String osNname = "unknown";
	/** Operating system version */
	@SuppressWarnings("unused")
	private String osVersion = "unknown";		
	/** Operating system architecture */
	@SuppressWarnings("unused")
	private String osArchitecture = "unknown";
	@SuppressWarnings("unused")
	private String locale = "unknown";
	// private String encode = "unknown";
	@SuppressWarnings("unused")
	private String timeZone = "unknown";	

	/** File separator ("/" on UNIX) */
	@SuppressWarnings("unused")
	private String fileSeparator = "unknown";
	/** Path separator (":" on UNIX) */
	@SuppressWarnings("unused")
	private String pathSeparator = "unknown";
	/** Line separator ("\n" on UNIX) */
	@SuppressWarnings("unused")
	private String lineSeparator = "unknown";

	
	private InfoUser user = new InfoUser();

	public String getOsNname() {
		return System.getProperty("os.name");
	}

	public void setOsNname(String osNname) {
		// this.osNname = osNname;
	}

	public String getOsArchitecture() {
		return System.getProperty("os.arch");
	}

	public void setOsArchitecture(String osArch) {
		// this.osArch = osArch;
	}

	public String getOsVersion() {
		return System.getProperty("os.version");
	}

	public void setOsVersion(String osVersion) {
		// this.osVersion = osVersion;
	}

	public String getFileSeparator() {
		return System.getProperty("file.separator");
	}

	public void setFileSeparator(String fileSeparator) {
		// this.fileSeparator = fileSeparator;
	}

	public String getPathSeparator() {									
		return System.getProperty("path.separator");
	}

	public void setPathSeparator(String pathSeparator) {
		// this.pathSeparator = pathSeparator;
	}

	public String getLineSeparator() {	
		return System.getProperty("line.separator");
	}

	public void setLineSeparator(String lineSeparator) {
		// this.lineSeparator = lineSeparator;
	}

	public InfoUser getUser() {
		return user;
	}

	public void setUser(InfoUser user) {
		this.user = user;
	}

	public String getLocale() {
		return Locale.getDefault().toString();
	}

	public void setLocale(String locale) {
		// this.locale = locale;
	}

	public String getTimeZone() {
		return TimeZone.getDefault().getID();
	}

	public void setTimeZone(String timeZone) {
		// this.timeZone = timeZone;
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

		json += "\n" + t + "\"osNname\":" + buildValue(getOsNname()) + ",";
		json += "\n" + t + "\"osArchitecture\":" + buildValue(getOsArchitecture()) + ",";
		json += "\n" + t + "\"osVersion\":" + buildValue(getOsVersion()) + ",";
		json += "\n" + t + "\"fileSeparator\":" + buildValue(getFileSeparator()) + ",";
		json += "\n" + t + "\"pathSeparator\":" + buildValue(getPathSeparator()) + ",";
		json += "\n" + t + "\"lineSeparator\":" + buildValue(getLineSeparator()) + ",";		
		json += "\n" + t + "\"locale\":" + buildValue(getLocale()) + ",";
		json += "\n" + t + "\"timeZone\":" + buildValue(getTimeZone()) + ",";		
		json += "\n" + getUser().toJson("user", cantT + 1) + "";

		json += "\n" + t0 + "}";

		return json;
	}
	
	private String buildValue(Object value) {

		if (value == null) {
			return null;
		}
		
		if(value instanceof Number){
			return value.toString();
		}
		
		if(value instanceof Boolean){
			return value.toString();
		}

		return "\"" + value + "\"";

	}

}
