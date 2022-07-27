package mdp.register.terminals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.OperationNotSupportedException;

import mdp.dtos.SearchTerminalDto;
import mdp.exceptions.TerminalNameTakenException;
import mdp.exceptions.TerminalNotFoundException;
import mdp.models.CustomsPassage;
import mdp.models.CustomsPassageStep;
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

		var entriesArr = mappedPassages.stream().filter(p -> p.isEntry()).toArray(CustomsPassage[]::new);
		var entries = mapPassagesToDto(entriesArr);
		var exitsArr = mappedPassages.stream().filter(p -> !p.isEntry()).toArray(CustomsPassage[]::new);
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
	
	// @Override
	public GetCustomsTerminalDto createTerminal(CreateTerminalDto dto) throws IOException, Exception {
		var id = Util.getIntUuid();
		int entryPassageCount = dto.getEntryPassageCount();
		var entries = new CustomsPassage[entryPassageCount];
		int exitPassageCount = dto.getExitPassageCount();
		var exits = new CustomsPassage[exitPassageCount];
		var name = dto.getTerminalName();

		var existingTerminal = terminalIdToTerminalMap.entrySet().stream()
				.filter(t -> t.getValue().getName().equals(name)).findFirst().orElse(null);
		if (existingTerminal != null)
			throw new TerminalNameTakenException();

		for (int i = 0; i < entryPassageCount; i++)
			entries[i] = new CustomsPassage(Util.getIntUuid(), true, true, CustomsPassage.customsPassageSteps());
		for (int i = 0; i < exitPassageCount; i++)
			exits[i] = new CustomsPassage(Util.getIntUuid(), true, false, CustomsPassage.customsPassageSteps());

		var passages = Stream.of(entries, Arrays.asList(exits)).toArray();
		var terminal = new CustomsTerminal(entries, id, name);

		addTerminal(terminal);

		return mapTerminalToGetDto(terminal);
	}

	// @Override
	public void deleteTerminal(BigInteger id) throws TerminalNotFoundException {
		if (id == null)
			throw new NullPointerException();
		var filePath = getFilePath(id);
		if (filePath == null)
			throw new TerminalNotFoundException();

		removeTerminal(id, filePath);
	}

// @Override
	public GetCustomsTerminalDto getTerminal(BigInteger passageId, boolean isCustomsStep, String terminalName)
			throws TerminalNotFoundException {
		var terminal = terminalIdToTerminalMap.entrySet().stream().map(pair -> pair.getValue())
				.filter(t -> t.getName().equals(terminalName) && Arrays.asList(t.getPassages()).stream()
						.filter(e -> e.getId().equals(passageId)).findFirst().orElse(null) != null)

				.findFirst().orElse(null);

		if (terminal == null)
			return null;

		return mapTerminalToGetDto(terminal);
	}

	// @Override
	public GetCustomsTerminalDto[] getTerminals() {
		return terminalIdToTerminalMap.entrySet().stream().map(kvp -> mapTerminalToGetDto(kvp.getValue()))
				.toArray(GetCustomsTerminalDto[]::new);

	}

	// @Override
	public GetCustomsTerminalDto[] getTerminalsStartingWithName(String namePrefix) {
		return terminalIdToTerminalMap.entrySet().stream().map(kvp -> mapTerminalToGetDto(kvp.getValue()))
				.filter(t -> t.getName().startsWith(namePrefix)).toArray(GetCustomsTerminalDto[]::new);

	}

// @Override
	public GetCustomsTerminalDto searchTerminal(SearchTerminalDto dto) throws TerminalNotFoundException {
		return getTerminal(dto.getPassageId(), dto.isCustomsPassage(), dto.getTerminalName());
	}

	
	
	//
////	@Override
	public GetCustomsTerminalDto updateTerminal(UpdateTerminalDto dto) throws IOException, Exception {
		var terminal = terminalIdToTerminalMap.entrySet().stream().map(kvp -> kvp.getValue())
				.filter(t -> t.getId().equals(dto.getTerminalId())).findFirst().orElse(null);
		if (terminal == null)
			throw new TerminalNotFoundException();

		synchronized (terminal) {

			boolean anyChanges = false;
			if (!terminal.getName().equals(dto.getName())) {
				anyChanges = true;
				terminal.setName(dto.getName());
			}

			var entries = Arrays.asList(terminal.getPassages()).stream().filter(p -> p.isEntry())
					.toArray(CustomsPassage[]::new);
			var entryDifference = dto.getEntryPassageCount() - entries.length;
			var entriesToSet = new ArrayList<>(Arrays.asList(entries));
			if (entryDifference != 0) {
				anyChanges = true;
				if (entryDifference > 0) {
					for (int i = 0; i < entryDifference; i++)
						entriesToSet.add(new CustomsPassage(Util.getIntUuid(), true, true,
								CustomsPassage.customsPassageSteps()));
				} else {
					for (int i = 0; i < entryDifference; i++)
						entriesToSet.remove(entriesToSet.size() - 1);
				}
			}

			var exits = Arrays.asList(terminal.getPassages()).stream().filter(p -> !p.isEntry())
					.toArray(CustomsPassage[]::new);
			var exitDifference = dto.getExitPassageCount() - exits.length;
			var exitsToSet = new ArrayList<>(Arrays.asList(exits));
			if (exitDifference != 0) {
				anyChanges = true;
				if (exitDifference > 0) {
					for (int i = 0; i < exitDifference; i++)
						exitsToSet.add(new CustomsPassage(Util.getIntUuid(), true, false,
								CustomsPassage.customsPassageSteps()));
				} else {
					for (int i = 0; i < exitDifference; i++)
						exitsToSet.remove(exitsToSet.size() - 1);
				}
			}

			var passagesToSet = Stream.concat(entriesToSet.stream(), exitsToSet.stream());
			terminal.setPassages(passagesToSet.toArray(CustomsPassage[]::new));

			deleteTerminal(terminal.getId());
			addTerminal(terminal);
		}

		return new GetCustomsTerminalDto(terminal.getId(), terminal.getName(),
				mapPassagesToDto(Arrays.asList(terminal.getPassages()).stream().filter(p -> p.isEntry())
						.toArray(CustomsPassage[]::new)),
				mapPassagesToDto(Arrays.asList(terminal.getPassages()).stream().filter(p -> !p.isEntry())
						.toArray(CustomsPassage[]::new)));
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
		var terminalFiles = Stream.of(new File(saveFolderPath).listFiles()).filter(f -> f.isFile())
				.collect(Collectors.toList());

		for (var terminalFile : terminalFiles) {
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