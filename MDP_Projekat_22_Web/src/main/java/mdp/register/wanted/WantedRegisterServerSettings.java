package mdp.register.wanted;

import java.io.Serializable;

public class WantedRegisterServerSettings implements Serializable {
	private static final long serialVersionUID = -5779115822411899346L;

	private String rmiHost;
	private int rmiPort;
	private String policeCheckStepServiceBindingName;
	private boolean shouldCreateRegistry;

	public WantedRegisterServerSettings(String rmiHost, int rmiPort, String policeCheckStepServiceBindingName,
			boolean shouldCreateRegistry) {
		super();
		this.rmiHost = rmiHost;
		this.rmiPort = rmiPort;
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
		this.shouldCreateRegistry = shouldCreateRegistry;
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

	public boolean isShouldCreateRegistry() {
		return shouldCreateRegistry;
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

	public void setShouldCreateRegistry(boolean shouldCreateRegistry) {
		this.shouldCreateRegistry = shouldCreateRegistry;
	}

}
