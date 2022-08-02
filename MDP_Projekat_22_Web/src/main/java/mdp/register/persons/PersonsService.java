package mdp.register.persons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import mdp.dtos.PostPersonDto;
import mdp.util.SettingsLoader;

public class PersonsService implements IPersonsService {
	private final static Logger logger = Logger.getLogger(PersonsService.class.getName());

	private PersonsServiceSettings settings;
	private File logsFile;

	public PersonsService() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("personsService", props -> {
			String personsLogsFilePath = props.getProperty("personsLogsFilePath");

			settings = new PersonsServiceSettings(personsLogsFilePath);
		});

		logsFile = new File(settings.getPersonsLogsFilePath());
		if (!logsFile.exists()) {
			Files.write(logsFile.toPath(),
					"[DATETIME] [ID] [NAME] [LASTNAME] [SSN] [BIRTH_DATA] [MALE/FEMALE]".getBytes(),
					StandardOpenOption.CREATE_NEW);
		}
	}

	@Override
	public synchronized void addPersonToLogs(PostPersonDto dto) throws IOException {
		String line = String.format("[%s] %s %s %s %s %s %s", LocalDateTime.now(), dto.getPersonId(), dto.getName(),
				dto.getLastName(), dto.getSsn(), dto.getBirthDate(), dto.isMale());
		try {
			Files.write(logsFile.toPath(), line.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("Failed to add logg: %s", e.getMessage()));
			throw e;
		}
	}

	@Override
	public File getPersonsLogsFile() {
		return logsFile;
	}
}
