package mdp.adminapp;

import java.io.Serializable;

public class AdminAppSettings implements Serializable {
	private static final long serialVersionUID = 929531982282161957L;

	private String notificationSocketHost;
	private int notificationSocketPort;
	private String wantedServerHost;

	public AdminAppSettings(String notificationSocketHost, int notificationSocketPort, String wantedServerHost) {
		super();
		this.notificationSocketHost = notificationSocketHost;
		this.notificationSocketPort = notificationSocketPort;
		this.wantedServerHost = wantedServerHost;
	}

	public String getNotificationSocketHost() {
		return notificationSocketHost;
	}

	public int getNotificationSocketPort() {
		return notificationSocketPort;
	}

	public String getWantedServerHost() {
		return wantedServerHost;
	}

	public void setNotificationSocketHost(String notificationSocketHost) {
		this.notificationSocketHost = notificationSocketHost;
	}

	public void setNotificationSocketPort(int notificationSocketPort) {
		this.notificationSocketPort = notificationSocketPort;
	}

	public void setWantedServerHost(String wantedServerHost) {
		this.wantedServerHost = wantedServerHost;
	}

}
