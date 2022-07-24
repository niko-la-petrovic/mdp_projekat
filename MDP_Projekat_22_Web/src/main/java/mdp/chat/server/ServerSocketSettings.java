package mdp.chat.server;

import java.io.Serializable;

public class ServerSocketSettings implements Serializable {
	private static final long serialVersionUID = -746125061760300832L;

	private String host;
	private String keyStorePath;
	private String keyStorePassword;
	private int port;

	public ServerSocketSettings(String host, String keyStorePath, String keyStorePassword, int port) {
		super();
		this.host = host;
		this.keyStorePath = keyStorePath;
		this.keyStorePassword = keyStorePassword;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
