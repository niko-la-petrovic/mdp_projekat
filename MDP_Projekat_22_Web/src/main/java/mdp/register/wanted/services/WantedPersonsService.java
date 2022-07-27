package mdp.register.wanted.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import mdp.models.wanted.WantedPersonDetected;
import mdp.util.SettingsLoader;

public class WantedPersonsService implements IWantedPersonsService {
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
			initializeWantedPersonsLogFile(file,
					Arrays.asList(new WantedPersonDetected(new BigInteger("123"), LocalDateTime.now(),
							new BigInteger("165630836651969126648801254799355469939"),
							new BigInteger("165458481299780741793080579545399665715"))));

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
