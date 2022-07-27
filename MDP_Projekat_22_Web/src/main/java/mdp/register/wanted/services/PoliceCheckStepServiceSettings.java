package mdp.register.wanted.services;

import java.io.Serializable;

public class PoliceCheckStepServiceSettings implements Serializable {
	private static final long serialVersionUID = -5228537679886654147L;

	private String wantedPersonsFilePath;

	public PoliceCheckStepServiceSettings(String wantedPersonsFilePath) {
		super();
		this.wantedPersonsFilePath = wantedPersonsFilePath;
	}

	public String getWantedPersonsFilePath() {
		return wantedPersonsFilePath;
	}

	public void setWantedPersonsFilePath(String wantedPersonsFilePath) {
		this.wantedPersonsFilePath = wantedPersonsFilePath;
	}
}
