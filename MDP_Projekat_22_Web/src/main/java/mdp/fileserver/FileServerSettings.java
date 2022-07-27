package mdp.fileserver;

import java.io.Serializable;

public class FileServerSettings implements Serializable {
	private static final long serialVersionUID = 7258106168618485788L;

	private int rmiPort;
	private boolean shouldCreateRegistry;
	private String personIdentifyingDocumentsServiceBindingName;

	public FileServerSettings(int rmiPort, boolean shouldCreateRegistry,
			String personIdentifyingDocumentsServiceBindingName) {
		super();
		this.rmiPort = rmiPort;
		this.shouldCreateRegistry = shouldCreateRegistry;
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public String getPersonIdentifyingDocumentsServiceBindingName() {
		return personIdentifyingDocumentsServiceBindingName;
	}

	public int getRmiPort() {
		return rmiPort;
	}

	public boolean isShouldCreateRegistry() {
		return shouldCreateRegistry;
	}

	public void setPersonIdentifyingDocumentsServiceBindingName(String personIdentifyingDocumentsServiceBindingName) {
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}

	public void setShouldCreateRegistry(boolean shouldCreateRegistry) {
		this.shouldCreateRegistry = shouldCreateRegistry;
	}

}
