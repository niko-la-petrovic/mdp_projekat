package mdp.simulation;

import java.io.Serializable;

public class SimulationAppSettings implements Serializable {
	private static final long serialVersionUID = -5729982044868584187L;

	private String apiHost;
	private int wantedRmiPort;
	private String wantedRmiHost;
	private int fileServerRmiPort;
	private String fileServerRmiHost;
	private String policeCheckStepServiceBindingName;
	private String personIdentifyingDocumentsServiceBindingName;

	public SimulationAppSettings(String apiHost, int wantedRmiPort, String wantedRmiHost, int fileServerRmiPort,
			String fileServerRmiHost, String policeCheckStepServiceBindingName,
			String personIdentifyingDocumentsServiceBindingName) {
		super();
		this.apiHost = apiHost;
		this.wantedRmiPort = wantedRmiPort;
		this.wantedRmiHost = wantedRmiHost;
		this.fileServerRmiPort = fileServerRmiPort;
		this.fileServerRmiHost = fileServerRmiHost;
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public String getApiHost() {
		return apiHost;
	}

	public String getFileServerRmiHost() {
		return fileServerRmiHost;
	}

	public int getFileServerRmiPort() {
		return fileServerRmiPort;
	}

	public String getPersonIdentifyingDocumentsServiceBindingName() {
		return personIdentifyingDocumentsServiceBindingName;
	}

	public String getPoliceCheckStepServiceBindingName() {
		return policeCheckStepServiceBindingName;
	}

	public String getWantedRmiHost() {
		return wantedRmiHost;
	}

	public int getWantedRmiPort() {
		return wantedRmiPort;
	}

	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}

	public void setFileServerRmiHost(String fileServerRmiHost) {
		this.fileServerRmiHost = fileServerRmiHost;
	}

	public void setFileServerRmiPort(int fileServerRmiPort) {
		this.fileServerRmiPort = fileServerRmiPort;
	}

	public void setPersonIdentifyingDocumentsServiceBindingName(String personIdentifyingDocumentsServiceBindingName) {
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public void setPoliceCheckStepServiceBindingName(String policeCheckStepServiceBindingName) {
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
	}

	public void setWantedRmiHost(String wantedRmiHost) {
		this.wantedRmiHost = wantedRmiHost;
	}

	public void setWantedRmiPort(int wantedRmiPort) {
		this.wantedRmiPort = wantedRmiPort;
	}
}
