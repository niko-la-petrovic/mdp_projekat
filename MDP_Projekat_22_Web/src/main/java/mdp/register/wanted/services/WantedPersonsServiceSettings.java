package mdp.register.wanted.services;

import java.io.Serializable;

public class WantedPersonsServiceSettings implements Serializable {
	private static final long serialVersionUID = -8587316590988285372L;

	private String wantedPersonsDetectedFilePath;

	public WantedPersonsServiceSettings(String wantedPersonsDetectedFilePath) {
		super();
		this.wantedPersonsDetectedFilePath = wantedPersonsDetectedFilePath;
	}

	public String getWantedPersonsDetectedFilePath() {
		return wantedPersonsDetectedFilePath;
	}

	public void setWantedPersonsDetectedFilePath(String wantedPersonsDetectedFilePath) {
		this.wantedPersonsDetectedFilePath = wantedPersonsDetectedFilePath;
	}

}
