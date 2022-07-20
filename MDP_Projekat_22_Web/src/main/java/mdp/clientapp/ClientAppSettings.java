package mdp.clientapp;

import java.io.Serializable;

public class ClientAppSettings implements Serializable {
	private static final long serialVersionUID = 8166654348790193632L;

	private String apiHost;

	public ClientAppSettings(String apiHost) {
		super();
		this.apiHost = apiHost;
	}

	public String getApiHost() {
		return apiHost;
	}

	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}

}
