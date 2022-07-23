package mdp.mq.rabbitmq;

import java.io.Serializable;

public class ConnectionPoolSettings implements Serializable {
	private static final long serialVersionUID = 2971934180548335140L;

	private String host;
	private Integer port;
	private String username;
	private String password;

	public ConnectionPoolSettings(String host, Integer port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public String getPassword() {
		return password;
	}

	public Integer getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
