package mdp.register.wanted;

import java.io.Serializable;

public class NotificationSocketSettings implements Serializable {
	private static final long serialVersionUID = -1198162894843441060L;

	private int port;

	public NotificationSocketSettings(int port) {
		super();
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
