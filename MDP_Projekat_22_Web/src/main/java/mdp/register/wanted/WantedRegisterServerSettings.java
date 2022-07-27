package mdp.register.wanted;

import java.io.Serializable;

public class WantedRegisterServerSettings implements Serializable {
	private static final long serialVersionUID = -5779115822411899346L;

	private int rmiPort;
	private String policeCheckStepServiceBindingName;
	private String personIdentifyingDocumentsServiceBindingName;

	public WantedRegisterServerSettings(int rmiPort, String policeCheckStepServiceBindingName,
			String personIdentifyingDocumentsServiceBindingName) {
		super();
		this.rmiPort = rmiPort;
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public String getPersonIdentifyingDocumentsServiceBindingName() {
		return personIdentifyingDocumentsServiceBindingName;
	}

	public String getPoliceCheckStepServiceBindingName() {
		return policeCheckStepServiceBindingName;
	}

	public int getRmiPort() {
		return rmiPort;
	}

	public void setPersonIdentifyingDocumentsServiceBindingName(String personIdentifyingDocumentsServiceBindingName) {
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public void setPoliceCheckStepServiceBindingName(String policeCheckStepServiceBindingName) {
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
	}

	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}

}
