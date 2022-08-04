package mdp.register.wanted.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.naming.OperationNotSupportedException;

import com.google.gson.Gson;

import mdp.exceptions.PassageNotFoundException;
import mdp.exceptions.TerminalNotFoundException;
import mdp.models.wanted.WantedPersonDetected;
import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.dtos.GetCustomsPassageDto;
import mdp.register.terminals.dtos.GetCustomsTerminalDto;
import mdp.util.SettingsLoader;

// TODO add persistence of open and closed states
// TODO add in-memory map of personid -> terminalid and passage id of wanted person - accept the open only if called with those arguments

public class PoliceCheckStepService implements IPoliceCheckStepService {
	private static final Logger logger = Logger.getLogger(PoliceCheckStepService.class.getName());

	private static Gson gson = new Gson();

	private PoliceCheckStepServiceSettings settings;
	private HashSet<BigInteger> wantedIds = new HashSet<>();
	private ConcurrentHashMap<BigInteger, GetCustomsTerminalDto> terminalsMap = new ConcurrentHashMap<>();
	private WantedPersonsService wantedPersonsService;

	public PoliceCheckStepService() throws FileNotFoundException, IOException, OperationNotSupportedException {
		loadSettings();
		loadWantedIds();
		loadTerminalRegisterService();
		loadWantedPersonsService();
	}

	// TODO exception if terminal is null

	public synchronized boolean isOpenTerminalPassage(BigInteger terminalId, boolean isEntry, BigInteger passageId)
			throws TerminalNotFoundException, PassageNotFoundException {
		logger.log(Level.INFO,
				String.format("Checking if terminalId:passageId '%s:%s' is open", terminalId, passageId));

		var terminal = getTerminal(terminalId);

		GetCustomsPassageDto passage;
		var passages = isEntry ? terminal.getEntries() : terminal.getExits();
		passage = Arrays.asList(passages).stream().filter(p -> p.getId().equals(passageId)).findFirst().orElse(null);

		if (passage == null)
			throw new PassageNotFoundException();

		return passage.isOpen();
	}

	@Override
	public synchronized boolean isWanted(BigInteger personId, BigInteger terminalId, BigInteger passageId)
			throws IOException {
		logger.log(Level.INFO, String.format("Checking if person with id '%s' is wanted", passageId));

		var terminal = terminalsMap.get(terminalId);

		if (!wantedIds.contains(personId))
			return false;

		logger.log(Level.INFO, String.format("Person with id '%s' is wanted. Closing terminalId:passageId '%s:%s'",
				personId, terminalId, passageId));
		var passages = Stream.concat(Arrays.asList(terminal.getEntries()).stream(),
				Arrays.asList(terminal.getExits()).stream());

		passages.forEach(p -> {
			p.setOpen(false);
		});

		logger.log(Level.INFO,
				String.format("Adding to detected persons logs person with id '%s' at terminalId:passageId '%s:%s'",
						personId, terminalId, passageId));
		wantedPersonsService.addWantedPersonToLogFile(
				new WantedPersonDetected(personId, LocalDateTime.now(), terminalId, passageId));

		return true;
	}

	@Override
	public synchronized void openPassages(BigInteger terminalId, BigInteger passageId)
			throws TerminalNotFoundException {
		logger.log(Level.INFO, String.format("Opening passages at terminal '%s'", terminalId));
		var terminal = getTerminal(terminalId);

		Stream.concat(Arrays.asList(terminal.getEntries()).stream(), Arrays.asList(terminal.getExits()).stream())
				.forEach(p -> p.setOpen(true));
	}

	@Override
	public String ping() throws RemoteException {
		return "pong";
	}

	private GetCustomsTerminalDto getTerminal(BigInteger terminalId) throws TerminalNotFoundException {
		var terminal = terminalsMap.get(terminalId);
		if (terminal == null)
			throw new TerminalNotFoundException();
		return terminal;
	}

	private void loadSettings() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("policeCheckStep", props -> {
			var wantedPersonsFilePath = props.getProperty("wantedPersonsFilePath");

			settings = new PoliceCheckStepServiceSettings(wantedPersonsFilePath);
		});
	}

	private void loadTerminalRegisterService()
			throws FileNotFoundException, OperationNotSupportedException, IOException {
		var terminalService = new TerminalRegisterService();
		var terminalsArray = terminalService.getTerminals();

		Arrays.asList(terminalsArray).stream().forEach(t -> {
			terminalsMap.put(t.getId(), t);
		});
	}

	private void loadWantedIds() throws IOException {
		var filePath = Paths.get(settings.getWantedPersonsFilePath());
		var file = filePath.toFile();
		if (!file.exists()) {
			wantedIds = new HashSet<>();
			var jsonStr = gson.toJson(wantedIds);
			Files.writeString(filePath, jsonStr);
			return;
		}

		var jsonStr = Files.readString(filePath);
		var wantedIdsArr = gson.fromJson(jsonStr, BigInteger[].class);
		wantedIds = new HashSet<>(Arrays.asList(wantedIdsArr));
	}

	private void loadWantedPersonsService() throws FileNotFoundException, IOException {
		wantedPersonsService = new WantedPersonsService();
	}
}
