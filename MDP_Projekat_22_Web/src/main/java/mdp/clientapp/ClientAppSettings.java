package mdp.clientapp;

import java.io.Serializable;

public class ClientAppSettings implements Serializable {
	private static final long serialVersionUID = 8166654348790193632L;

	// TODO add separate host for rmi file server
	private String apiHost;
	private int rmiPort;
	private String rmiHost;
	private String policeCheckStepServiceBindingName;
	private String personIdentifyingDocumentsServiceBindingName;

	public ClientAppSettings(String apiHost, int rmiPort, String rmiHost, String policeCheckStepServiceBindingName,
			String personIdentifyingDocumentsServiceBindingName) {
		super();
		this.apiHost = apiHost;
		this.rmiPort = rmiPort;
		this.rmiHost = rmiHost;
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public String getApiHost() {
		return apiHost;
	}

	public String getPersonIdentifyingDocumentsServiceBindingName() {
		return personIdentifyingDocumentsServiceBindingName;
	}

	public String getPoliceCheckStepServiceBindingName() {
		return policeCheckStepServiceBindingName;
	}

	public String getRmiHost() {
		return rmiHost;
	}

	public int getRmiPort() {
		return rmiPort;
	}

	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}

	public void setPersonIdentifyingDocumentsServiceBindingName(String personIdentifyingDocumentsServiceBindingName) {
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public void setPoliceCheckStepServiceBindingName(String policeCheckStepServiceBindingName) {
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
	}

	public void setRmiHost(String rmiHost) {
		this.rmiHost = rmiHost;
	}

	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}
}
