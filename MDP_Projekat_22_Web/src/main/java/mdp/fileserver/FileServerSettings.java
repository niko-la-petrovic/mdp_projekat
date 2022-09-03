package mdp.fileserver;

import java.io.Serializable;

public class FileServerSettings implements Serializable {
	private static final long serialVersionUID = 7258106168618485788L;

	private String rmiHost;
	private int rmiPort;
	private boolean shouldCreateRegistry;
	private String personIdentifyingDocumentsServiceBindingName;

	public FileServerSettings(String rmiHost, int rmiPort, boolean shouldCreateRegistry,
			String personIdentifyingDocumentsServiceBindingName) {
		super();
		this.rmiHost = rmiHost;
		this.rmiPort = rmiPort;
		this.shouldCreateRegistry = shouldCreateRegistry;
		this.personIdentifyingDocumentsServiceBindingName = personIdentifyingDocumentsServiceBindingName;
	}

	public String getPersonIdentifyingDocumentsServiceBindingName() {
		return personIdentifyingDocumentsServiceBindingName;
	}

	public String getRmiHost() {
		return rmiHost;
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

	public void setRmiHost(String rmiHost) {
		this.rmiHost = rmiHost;
	}

	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}

	public void setShouldCreateRegistry(boolean shouldCreateRegistry) {
		this.shouldCreateRegistry = shouldCreateRegistry;
	}
}
