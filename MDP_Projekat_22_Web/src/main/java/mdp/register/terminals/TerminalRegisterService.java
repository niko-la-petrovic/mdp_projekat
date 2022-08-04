package mdp.register.terminals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;

import mdp.dtos.SearchTerminalDto;
import mdp.exceptions.TerminalNameTakenException;
import mdp.exceptions.TerminalNotFoundException;
import mdp.models.CustomsPassage;
import mdp.models.CustomsTerminal;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.register.terminals.dtos.GetCustomsPassageDto;
import mdp.register.terminals.dtos.GetCustomsPassageStepDto;
import mdp.register.terminals.dtos.GetCustomsTerminalDto;
import mdp.register.terminals.dtos.UpdateTerminalDto;
import mdp.util.Serialization;
import mdp.util.SerializationType;
import mdp.util.SettingsLoader;
import mdp.util.Util;

public class TerminalRegisterService
//implements ITerminalRegisterService 
{
	private static final Logger logger = Logger.getLogger(TerminalRegisterService.class.getName());

	private static final String TERMINAL_REGISTER_SETTINGS_NAME = "terminalRegister";
	private static int serializedCount = 0;
	private static final SerializationType[] serializationTypes;
	private static final Object serializationLock = new Object();
	private static final ConcurrentMap<BigInteger, String> terminalIdToFileMap = new ConcurrentHashMap<BigInteger, String>();
	private static final ConcurrentMap<BigInteger, CustomsTerminal> terminalIdToTerminalMap = new ConcurrentHashMap<BigInteger, CustomsTerminal>();

	static {
		serializationTypes = SerializationType.values();
	}

	private static GetCustomsPassageDto[] mapPassagesToDto(CustomsPassage[] entriesToMap) {
		var entries = new GetCustomsPassageDto[entriesToMap.length];
		for (int i = 0; i < entriesToMap.length; i++) {
			var entryToMap = entriesToMap[i];

			var passageStepsToMap = entryToMap.getPassageSteps();
			var passageSteps = new GetCustomsPassageStepDto[passageStepsToMap.length];
			for (int j = 0; j < passageSteps.length; j++)
				passageSteps[j] = new GetCustomsPassageStepDto(passageStepsToMap[j].isCustomsCheck());

			entries[i] = new GetCustomsPassageDto(entryToMap.getId(), entryToMap.isOpen(), entryToMap.isEntry(),
					passageSteps);
		}

		return entries;
	}

	private static GetCustomsTerminalDto mapTerminalToGetDto(CustomsTerminal terminal) {
		var passagesToMap = terminal.getPassages();
		var mappedPassages = Arrays.asList(passagesToMap);

		var entriesArrList = new ArrayList<CustomsPassage>();
		var exitsArrList = new ArrayList<CustomsPassage>();

		for (CustomsPassage customsPassage : mappedPassages) {
			if (customsPassage.isEntry())
				entriesArrList.add(customsPassage);
			else
				exitsArrList.add(customsPassage);
		}
		var entriesArr = entriesArrList.toArray(new CustomsPassage[entriesArrList.size()]);
		var exitsArr = exitsArrList.toArray(new CustomsPassage[exitsArrList.size()]);

		var entries = mapPassagesToDto(entriesArr);
		var exits = mapPassagesToDto(exitsArr);

		var dto = new GetCustomsTerminalDto(terminal.getId(), terminal.getName(), entries, exits);

		return dto;
	}

	protected static int getSerializedCount() {
		return serializedCount;
	}

	protected static void setSerializedCount(int serializedCount) {
		TerminalRegisterService.serializedCount = serializedCount;
	}

	private TerminalRegisterSettings settings;

	public TerminalRegisterService() throws FileNotFoundException, IOException, OperationNotSupportedException {
		loadSettings();
		loadTerminals();
	}

	// TODO add method to update terminal passage

//	 @Override
	public GetCustomsTerminalDto createTerminal(CreateTerminalDto dto) throws IOException, Exception {
		var id = Util.getIntUuid();
		int entryPassageCount = dto.getEntryPassageCount();
		var entries = new CustomsPassage[entryPassageCount];
		int exitPassageCount = dto.getExitPassageCount();
		var exits = new CustomsPassage[exitPassageCount];
		var name = dto.getTerminalName();

		logger.log(Level.INFO, String.format("Attempting to create terminal with name '%s'", name));

		var existingTerminal = terminalIdToTerminalMap.values();
		boolean found = false;
		for (CustomsTerminal customsTerminal : existingTerminal)
			if (customsTerminal.getName().equals(dto.getTerminalName())) {
				logger.log(Level.WARNING, String.format("Terminal with name '%s' already exists", name));
				throw new TerminalNameTakenException();
			}

		for (int i = 0; i < entryPassageCount; i++)
			entries[i] = new CustomsPassage(Util.getIntUuid(), true, true, CustomsPassage.customsPassageSteps());
		for (int i = 0; i < exitPassageCount; i++)
			exits[i] = new CustomsPassage(Util.getIntUuid(), true, false, CustomsPassage.customsPassageSteps());

		var passagesList = new ArrayList<CustomsPassage>();
		for (CustomsPassage customsPassage : entries) {
			passagesList.add(customsPassage);
		}
		for (CustomsPassage customsPassage : exits) {
			passagesList.add(customsPassage);
		}
		var passages = passagesList.toArray(new CustomsPassage[passagesList.size()]);

		var terminal = new CustomsTerminal(passages, id, name);

		addTerminal(terminal);

		return mapTerminalToGetDto(terminal);
	}

	// @Override
	public void deleteTerminal(BigInteger id) throws TerminalNotFoundException {
		if (id == null) {
			logger.log(Level.WARNING, "Terminal id is null");
			throw new NullPointerException();
		}
		var filePath = getFilePath(id);
		if (filePath == null) {
			logger.log(Level.WARNING, String.format("File for terminal with id '%s' does not exist", id));
			throw new TerminalNotFoundException();
		}

		logger.log(Level.INFO, String.format("Removing terminal with id '%s'", id));
		removeTerminal(id, filePath);
	}

// @Override
	public GetCustomsTerminalDto getTerminal(BigInteger passageId, boolean isCustomsStep, String terminalName)
			throws TerminalNotFoundException {
		logger.log(Level.INFO, String.format("Getting terminalName:passageId:customs/policeStep '%s:%s:%s'",
				terminalName, passageId, isCustomsStep));
		var terminals = terminalIdToTerminalMap.values();

		CustomsTerminal terminal = null;
		for (CustomsTerminal customsTerminal : terminals) {
			var terminalPassages = Arrays.asList(customsTerminal.getPassages());
			boolean foundTerminal = customsTerminal.getName().equals(terminalName);
			boolean foundPassage = false;
			for (CustomsPassage passage : terminalPassages) {
				if (passage.getId().equals(passageId)) {
					foundPassage = true;
					break;
				}
			}

			if (foundTerminal && foundPassage)
				terminal = customsTerminal;
		}

		if (terminal == null) {
			logger.log(Level.INFO,
					String.format("Terminal terminalName:passageId:customs/policeStep '%s:%s:%s' not found",
							terminalName, passageId, isCustomsStep));
			return null;
		}

		return mapTerminalToGetDto(terminal);
	}

//
//	 @Override
	public GetCustomsTerminalDto[] getTerminals() {
		logger.log(Level.INFO, "Getting terminals");

		var terminals = terminalIdToTerminalMap.values();
		var mappedTerminals = new ArrayList<GetCustomsTerminalDto>();
		for (CustomsTerminal terminal : terminals) {
			var mappedTerminal = mapTerminalToGetDto(terminal);
			mappedTerminals.add(mappedTerminal);
		}

		return mappedTerminals.toArray(new GetCustomsTerminalDto[mappedTerminals.size()]);
	}

	// @Override
	public GetCustomsTerminalDto[] getTerminalsStartingWithName(String namePrefix) {
		logger.log(Level.INFO, String.format("Getting terminals with name starting with '%s'", namePrefix));

		var terminals = terminalIdToTerminalMap.values();
		var mappedTerminals = new ArrayList<GetCustomsTerminalDto>();
		for (CustomsTerminal terminal : terminals)
			if (terminal.getName().startsWith(namePrefix))
				mappedTerminals.add(mapTerminalToGetDto(terminal));

		return mappedTerminals.toArray(new GetCustomsTerminalDto[mappedTerminals.size()]);
	}

	// @Override
	public GetCustomsTerminalDto searchTerminal(SearchTerminalDto dto) throws TerminalNotFoundException {
		logger.log(Level.INFO, String.format(
				"Searching for terminal with parameters: passageId: '%s' isCustomsPassage: '%s' terminalName: '%s'",
				dto.getPassageId(), dto.isCustomsPassage(), dto.getTerminalName()));

		return getTerminal(dto.getPassageId(), dto.isCustomsPassage(), dto.getTerminalName());
	}

	public GetCustomsTerminalDto searchTerminalSimulation(String terminalName, BigInteger passageId, boolean isEntry) {
		logger.log(Level.INFO,
				String.format(
						"Searching for terminal with parameters: terminalName: '%s' passageId: '%s' isEntry: '%s' ",
						terminalName, passageId, isEntry));

		return getTerminalSimulation(terminalName, passageId, isEntry);
	}

	private GetCustomsTerminalDto getTerminalSimulation(String terminalName, BigInteger passageId, boolean isEntry) {
		var terminals = terminalIdToTerminalMap.values();

		CustomsTerminal terminal = null;
		for (CustomsTerminal customsTerminal : terminals) {
			var terminalPassages = Arrays.asList(customsTerminal.getPassages());
			boolean foundTerminal = customsTerminal.getName().equals(terminalName);
			boolean foundPassage = false;
			for (CustomsPassage passage : terminalPassages) {
				if (passage.getId().equals(passageId) && passage.isEntry() == isEntry) {
					foundPassage = true;
					break;
				}
			}

			if (foundTerminal && foundPassage)
				terminal = customsTerminal;
		}

		if (terminal == null)
			return null;

		return mapTerminalToGetDto(terminal);
	}

	//
////	@Override
	public GetCustomsTerminalDto updateTerminal(UpdateTerminalDto dto) throws IOException, Exception {
		logger.log(Level.INFO, String.format("Updating terminal with id '%s'", dto.getTerminalId()));

		var terminals = terminalIdToTerminalMap.values();
		CustomsTerminal terminal = null;
		for (CustomsTerminal customsTerminal : terminals) {
			if (customsTerminal.getId().equals(dto.getTerminalId())) {
				terminal = customsTerminal;
				break;
			}
		}
		if (terminal == null) {
			logger.log(Level.WARNING, String.format("Terminal with id '%s' not found", dto.getTerminalId()));
			throw new TerminalNotFoundException();
		}

		synchronized (terminal) {

			boolean anyChanges = false;
			if (!terminal.getName().equals(dto.getName())) {
				anyChanges = true;
				terminal.setName(dto.getName());
			}

			var entriesList = new ArrayList<CustomsPassage>();
			var exitsList = new ArrayList<CustomsPassage>();
			for (CustomsPassage customsPassage : terminal.getPassages()) {
				if (customsPassage.isEntry())
					entriesList.add(customsPassage);
				else
					exitsList.add(customsPassage);
			}
			var entries = entriesList.toArray(new CustomsPassage[entriesList.size()]);
			var exits = exitsList.toArray(new CustomsPassage[exitsList.size()]);

			var entryDifference = dto.getEntryPassageCount() - entries.length;
			var entriesToSet = new ArrayList<>(Arrays.asList(entries));
			if (entryDifference != 0) {
				anyChanges = true;
				if (entryDifference > 0) {
					for (int i = 0; i < entryDifference; i++)
						entriesToSet.add(new CustomsPassage(Util.getIntUuid(), true, true,
								CustomsPassage.customsPassageSteps()));
				} else {
					for (int i = 0; i < -entryDifference; i++)
						entriesToSet.remove(entriesToSet.size() - 1);
				}
			}

			var exitDifference = dto.getExitPassageCount() - exits.length;
			var exitsToSet = new ArrayList<>(Arrays.asList(exits));
			if (exitDifference != 0) {
				anyChanges = true;
				if (exitDifference > 0) {
					for (int i = 0; i < exitDifference; i++)
						exitsToSet.add(new CustomsPassage(Util.getIntUuid(), true, false,
								CustomsPassage.customsPassageSteps()));
				} else {
					for (int i = 0; i < -exitDifference; i++)
						exitsToSet.remove(exitsToSet.size() - 1);
				}
			}

			var passagesToSetList = new ArrayList<CustomsPassage>();
			for (CustomsPassage passage : entriesToSet) {
				passagesToSetList.add(passage);
			}
			for (CustomsPassage passage : exitsToSet) {
				passagesToSetList.add(passage);
			}
			var passagesToSet = passagesToSetList.toArray(new CustomsPassage[passagesToSetList.size()]);
			terminal.setPassages(passagesToSet);

			deleteTerminal(terminal.getId());
			addTerminal(terminal);
		}

		var entryList = new ArrayList<CustomsPassage>();
		var exitsList = new ArrayList<CustomsPassage>();
		for (CustomsPassage passage : terminal.getPassages()) {
			if (passage.isEntry())
				entryList.add(passage);
			else
				exitsList.add(passage);
		}

		var entries = entryList.toArray(new CustomsPassage[entryList.size()]);
		var exits = exitsList.toArray(new CustomsPassage[exitsList.size()]);

		return new GetCustomsTerminalDto(terminal.getId(), terminal.getName(), mapPassagesToDto(entries),
				mapPassagesToDto(exits));
	}

	private void addTerminal(CustomsTerminal terminal) throws IOException, Exception {
		var terminalFile = serializeTerminal(terminal).toFile();
		putTerminalInMaps(terminalFile, terminal);
	}

	private String getFilePath(BigInteger id) {
		var filePath = terminalIdToFileMap.getOrDefault(id, null);
		return filePath;
	}

	private void loadSettings() throws IOException, FileNotFoundException {
		var props = SettingsLoader.getLoadedProperties(TERMINAL_REGISTER_SETTINGS_NAME);
		var saveFolderPath = props.getProperty("saveFolderPath");
		settings = new TerminalRegisterSettings(saveFolderPath);
	}

	private void loadTerminals() throws OperationNotSupportedException, IOException {
		var saveFolderPath = settings.getSaveFolderPath();
		var terminalFileArray = new File(saveFolderPath).listFiles();
		var terminalFiles = new File(saveFolderPath).listFiles();

		var files = new ArrayList<File>();
		for (File f : terminalFiles) {
			if (f.isFile())
				files.add(f);
		}

		for (var terminalFile : files) {
			var terminal = Serialization.deserializeFromFile(terminalFile.getAbsolutePath(), CustomsTerminal.class);
			putTerminalInMaps(terminalFile, terminal);
		}
	}

	private void putTerminalInMaps(File terminalFile, CustomsTerminal terminal) {
		terminalIdToFileMap.put(terminal.getId(), terminalFile.getAbsolutePath());
		terminalIdToTerminalMap.put(terminal.getId(), terminal);
	}

	private void removeTerminal(BigInteger id, String filePath) {
		var toDelete = new File(filePath);
		toDelete.delete();
		removeTerminalFromMaps(id);
	}

	private void removeTerminalFromMaps(BigInteger id) {
		terminalIdToFileMap.remove(id);
		terminalIdToTerminalMap.remove(id);
	}

	private Path serializeTerminal(CustomsTerminal terminal) throws IOException, Exception {
		SerializationType serializationType;
		synchronized (serializationLock) {
			int currentSerializedCount = getSerializedCount();
			serializationType = serializationTypes[currentSerializedCount % serializationTypes.length];
			setSerializedCount(currentSerializedCount + 1);
		}

		return Serialization.serializeToFile(terminal, serializationType, settings.getSaveFolderPath(), "terminal",
				null);
	}
}