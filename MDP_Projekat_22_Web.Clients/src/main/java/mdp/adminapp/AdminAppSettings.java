package mdp.adminapp;

import java.io.Serializable;

public class AdminAppSettings implements Serializable {
	private static final long serialVersionUID = 929531982282161957L;

	private String notificationSocketHost;
	private int notificationSocketPort;
	private String wantedServerHost;
	private String credentialsServerHost;

	public AdminAppSettings(String notificationSocketHost, int notificationSocketPort, String wantedServerHost,
			String credentialsServerHost) {
		super();
		this.notificationSocketHost = notificationSocketHost;
		this.notificationSocketPort = notificationSocketPort;
		this.wantedServerHost = wantedServerHost;
		this.credentialsServerHost = credentialsServerHost;
	}

	public String getCredentialsServerHost() {
		return credentialsServerHost;
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

	public void setCredentialsServerHost(String credentialsServerHost) {
		this.credentialsServerHost = credentialsServerHost;
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
