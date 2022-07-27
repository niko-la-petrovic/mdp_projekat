package mdp.register.wanted.services;

import java.io.Serializable;

public class PersonIdentifyingDocumentsServiceSettings implements Serializable {
	private static final long serialVersionUID = 589848791918618454L;

	private String persistenceFolderPath;

	public PersonIdentifyingDocumentsServiceSettings(String persistenceFolderPath) {
		super();
		this.persistenceFolderPath = persistenceFolderPath;
	}

	public String getPersistenceFolderPath() {
		return persistenceFolderPath;
	}

	public void setPersistenceFolderPath(String persistenceFolderPath) {
		this.persistenceFolderPath = persistenceFolderPath;
	}

}
