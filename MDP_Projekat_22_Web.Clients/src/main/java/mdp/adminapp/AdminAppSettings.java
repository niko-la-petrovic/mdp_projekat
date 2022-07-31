package mdp.adminapp;

import java.io.Serializable;

public class AdminAppSettings implements Serializable {
	private static final long serialVersionUID = 929531982282161957L;

	private String notificationSocketHost;
	private int notificationSocketPort;

	public AdminAppSettings(String notificationSocketHost, int notificationSocketPort) {
		super();
		this.notificationSocketHost = notificationSocketHost;
		this.notificationSocketPort = notificationSocketPort;
	}

	public String getNotificationSocketHost() {
		return notificationSocketHost;
	}

	public int getNotificationSocketPort() {
		return notificationSocketPort;
	}

	public void setNotificationSocketHost(String notificationSocketHost) {
		this.notificationSocketHost = notificationSocketHost;
	}

	public void setNotificationSocketPort(int notificationSocketPort) {
		this.notificationSocketPort = notificationSocketPort;
	}

}
