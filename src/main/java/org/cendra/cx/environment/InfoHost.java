package org.cendra.cx.environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;

import org.cendra.cx.environment.vm.InfoJava;

public class InfoHost {

	
	private String hostAddress = "unknown";
	private String hostName = "unknown";	
	private String timeInfo = "unknown";
	private InfoOs os = new InfoOs();
	private InfoJava infoJava = new InfoJava();

	public InfoHost() {
		super();
		timeInfo = ZonedDateTime.now().toString();
		InetAddress localHost;
		try {
			localHost = InetAddress.getLocalHost();
			hostName = localHost.getHostName();
			hostAddress = localHost.getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public String getTimeInfo() {
		return timeInfo;
	}

	public void setTimeInfo(String timeInfo) {
		this.timeInfo = timeInfo;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public InfoOs getOs() {
		return os;
	}

	public void setOs(InfoOs os) {
		this.os = os;
	}

	public InfoJava getInfoJava() {
		return infoJava;
	}

	public void setInfoJava(InfoJava infoJava) {
		this.infoJava = infoJava;
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

		json += "\n" + t + "\"timeInfo\":" + buildValue(getTimeInfo()) + ",";
		json += "\n" + t + "\"hostName\":" + buildValue(getHostName()) + ",";
		json += "\n" + t + "\"hostAddress\":" + buildValue(getHostAddress()) + ",";
		json += "\n" + getOs().toJson("os", cantT + 1) + ",";
		json += "\n" + getInfoJava().toJson("infoJava", cantT + 1) + "";
		

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
