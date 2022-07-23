package mdp.chatserver.server;

import java.io.Serializable;

public class ServerSocketSettings implements Serializable {
	private static final long serialVersionUID = -746125061760300832L;

	private String host;
	private int port;

	public ServerSocketSettings(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
