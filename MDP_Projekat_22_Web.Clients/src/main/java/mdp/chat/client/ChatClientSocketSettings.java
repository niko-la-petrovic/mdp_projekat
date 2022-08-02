package mdp.chat.client;

import java.io.Serializable;

public class ChatClientSocketSettings implements Serializable {
	private static final long serialVersionUID = 160277069507955096L;

	private String host;
	private int port;
	private String trustStorePath;
	private String trustStorePassword;

	public ChatClientSocketSettings(String host, int port, String trustStorePath, String trustStorePassword) {
		super();
		this.host = host;
		this.port = port;
		this.trustStorePath = trustStorePath;
		this.trustStorePassword = trustStorePassword;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}

	public String getTrustStorePath() {
		return trustStorePath;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public void setTrustStorePath(String trustStorePath) {
		this.trustStorePath = trustStorePath;
	}

}
