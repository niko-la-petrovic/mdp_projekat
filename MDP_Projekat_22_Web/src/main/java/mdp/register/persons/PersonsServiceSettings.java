package mdp.register.persons;

import java.io.Serializable;

public class PersonsServiceSettings implements Serializable {
	private static final long serialVersionUID = -1661447621448586762L;

	private String personsLogsFilePath;

	public PersonsServiceSettings(String personsLogsFilePath) {
		super();
		this.personsLogsFilePath = personsLogsFilePath;
	}

	public String getPersonsLogsFilePath() {
		return personsLogsFilePath;
	}

	public void setPersonsLogsFilePath(String personsLogsFilePath) {
		this.personsLogsFilePath = personsLogsFilePath;
	}

}
