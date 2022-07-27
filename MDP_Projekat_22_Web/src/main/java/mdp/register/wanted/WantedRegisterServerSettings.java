package mdp.register.wanted;

import java.io.Serializable;

public class WantedRegisterServerSettings implements Serializable {
	private static final long serialVersionUID = -5779115822411899346L;

	private int rmiPort;
	private String policeCheckStepServiceBindingName;
	private boolean shouldCreateRegistry;

	public WantedRegisterServerSettings(int rmiPort, String policeCheckStepServiceBindingName,
			boolean shouldCreateRegistry) {
		super();
		this.rmiPort = rmiPort;
		this.policeCheckStepServiceBindingName = policeCheckStepServiceBindingName;
		this.shouldCreateRegistry = shouldCreateRegistry;
	}

	public String getPoliceCheckStepServiceBindingName() {
		return policeCheckStepServiceBindingName;
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

	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}

	public void setShouldCreateRegistry(boolean shouldCreateRegistry) {
		this.shouldCreateRegistry = shouldCreateRegistry;
	}

}
