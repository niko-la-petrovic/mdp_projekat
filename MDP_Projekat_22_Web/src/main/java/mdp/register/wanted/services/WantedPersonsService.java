package mdp.register.wanted.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import mdp.models.wanted.WantedPersonDetected;
import mdp.util.SettingsLoader;

public class WantedPersonsService implements IWantedPersonsService {
	private static final XmlMapper xmlMapper = new XmlMapper();

	private WantedPersonsServiceSettings settings;

	public WantedPersonsService() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("wantedPersonsService", props -> {
			var wantedPersonsDetectedFilePath = props.getProperty("wantedPersonsDetectedFilePath");

			settings = new WantedPersonsServiceSettings(wantedPersonsDetectedFilePath);
		});
	}

	@Override
	public synchronized void addWantedPersonToLogFile(WantedPersonDetected detectedPerson) throws IOException {
		var file = getLogsFile();
		ArrayList<WantedPersonDetected> logs = xmlMapper.readValue(file, ArrayList.class);

		logs.add(detectedPerson);

		saveLogs(file, logs);
	}

	@Override
	public synchronized File getWantedPersonsLogsFile() throws IOException {
		return getLogsFile();
	}

	private File getLogsFile() throws StreamWriteException, DatabindException, IOException {
		var filePath = settings.getWantedPersonsDetectedFilePath();
		var file = new File(filePath);
		if (!file.exists())
			initializeWantedPersonsLogFile(file, new ArrayList<>());

		return file;
	}

	private void initializeWantedPersonsLogFile(File file, ArrayList<WantedPersonDetected> logs)
			throws StreamWriteException, DatabindException, IOException {
		saveLogs(file, logs);
	}

	private void saveLogs(File file, ArrayList<WantedPersonDetected> logs)
			throws IOException, StreamWriteException, DatabindException {
		xmlMapper.writeValue(file, logs);
	}
}
