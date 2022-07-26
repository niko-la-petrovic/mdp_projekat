package mdp.register.wanted.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import mdp.models.wanted.WantedPersonDetected;
import mdp.util.SettingsLoader;

public class WantedPersonsService implements IWantedPersonsService {
	private static final Logger logger = Logger.getLogger(WantedPersonsService.class.getName());
	private static final XmlMapper xmlMapper = (XmlMapper) new XmlMapper().findAndRegisterModules();

	private WantedPersonsServiceSettings settings;

	public WantedPersonsService() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("wantedPersonsService", props -> {
			var wantedPersonsDetectedFilePath = props.getProperty("wantedPersonsDetectedFilePath");

			settings = new WantedPersonsServiceSettings(wantedPersonsDetectedFilePath);
		});
	}

	@Override
	public synchronized void addWantedPersonToLogFile(WantedPersonDetected detectedPerson) throws IOException {
		logger.log(Level.INFO,
				String.format("Adding detected wanted person with id '%s' to logs", detectedPerson.getPersonId()));

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
			initializeWantedPersonsLogFile(file, Arrays.asList());

		return file;
	}

	private void initializeWantedPersonsLogFile(File file, List<WantedPersonDetected> logs)
			throws StreamWriteException, DatabindException, IOException {
		saveLogs(file, logs);
	}

	private void saveLogs(File file, List<WantedPersonDetected> logs)
			throws IOException, StreamWriteException, DatabindException {
		xmlMapper.writeValue(file, logs);
	}
}
