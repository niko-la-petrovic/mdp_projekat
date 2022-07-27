package mdp.register.wanted.services;

import java.io.File;
import java.io.IOException;

import mdp.models.wanted.WantedPersonDetected;

public interface IWantedPersonsService {

	void addWantedPersonToLogFile(WantedPersonDetected detectedPerson) throws IOException;

	File getWantedPersonsLogsFile() throws IOException;

}
