package mdp.register.terminals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.OperationNotSupportedException;

import mdp.dtos.SearchTerminalDto;
import mdp.exceptions.TerminalNotFoundException;
import mdp.models.CustomsEntry;
import mdp.models.CustomsExit;
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
	private static final String TERMINAL_REGISTER_SETTINGS_NAME = "terminalRegister";
	private static int serializedCount = 0;
	private static final SerializationType[] serializationTypes;
	private static final Object serializationLock = new Object();
	private static final ConcurrentMap<BigInteger, String> terminalIdToFileMap = new ConcurrentHashMap<BigInteger, String>();
	private static final ConcurrentMap<BigInteger, CustomsTerminal> terminalIdToTerminalMap = new ConcurrentHashMap<BigInteger, CustomsTerminal>();

	static {
		serializationTypes = SerializationType.values();
	}

	protected static int getSerializedCount() {
		return serializedCount;
	}

	protected static void setSerializedCount(int serializedCount) {
		TerminalRegisterService.serializedCount = serializedCount;
	}

	private TerminalRegisterSettings settings;

	public TerminalRegisterService() throws FileNotFoundException, IOException {
		loadSettings();
		loadTerminals();
	}

	// @Override
	public GetCustomsTerminalDto createTerminal(CreateTerminalDto dto) {
		// TODO check if terminal name taken
		
		
		var id = Util.getIntUuid();
		int entryPassageCount = dto.getEntryPassageCount();
		var entries = new CustomsEntry[entryPassageCount];
		int exitPassageCount = dto.getExitPassageCount();
		var exits = new CustomsExit[exitPassageCount];
		var name = dto.getTerminalName();

		for (int i = 0; i < entryPassageCount; i++)
			entries[i] = new CustomsEntry(Util.getIntUuid(), true);
		for (int i = 0; i < exitPassageCount; i++)
			exits[i] = new CustomsExit(Util.getIntUuid(), true);

		var terminal = new CustomsTerminal(entries, exits, id, name);

		SerializationType serializationType;
		serializeTerminal(terminal);

		return mapTerminalToGetDto(terminal);
	}

//	@Override
	public void deleteTerminal(BigInteger id) throws TerminalNotFoundException {
		var filePath = getFilePath(id);
		if (filePath == null)
			throw new TerminalNotFoundException();

		var toDelete = new File(filePath);
		toDelete.delete();
		terminalIdToFileMap.remove(id);
	}

	// @Override
	public GetCustomsTerminalDto getTerminal(BigInteger passageId, boolean isCustomsStep, String terminalName)
			throws TerminalNotFoundException {
		var terminal = terminalIdToTerminalMap.entrySet().stream().map(pair -> pair.getValue())
				.filter(t -> t.getName().equals(terminalName) && t.getPassages().stream()
						.filter(e -> e.getId().equals(passageId)).findFirst().orElse(null) != null)

				.findFirst().orElse(null);

		if (terminal == null)
			return null;

		return mapTerminalToGetDto(terminal);
	}

//	@Override
	public GetCustomsTerminalDto[] getTerminals() {
		return (GetCustomsTerminalDto[]) terminalIdToTerminalMap.entrySet().stream()
				.map(kvp -> mapTerminalToGetDto(kvp.getValue())).toArray();

	}

	// @Override
	public GetCustomsTerminalDto searchTerminal(SearchTerminalDto dto) throws TerminalNotFoundException {
		return getTerminal(dto.getPassageId(), dto.isCustomsPassage(), dto.getTerminalName());
	}

	//
////	@Override
	public GetCustomsTerminalDto updateTerminal(UpdateTerminalDto dto) throws TerminalNotFoundException {
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

			var entryDifference = dto.getEntryPassageCount() - terminal.getEntries().length;
			if (entryDifference != 0) {
				anyChanges = true;
				var entriesToAdd = Arrays.asList(terminal.getEntries());
				if (entryDifference > 0) {
					for (int i = 0; i < entryDifference; i++)
						entriesToAdd.add(new CustomsEntry(Util.getIntUuid(), true));
					terminal.setEntries((CustomsEntry[]) entriesToAdd.toArray());
				} else {
					for (int i = 0; i < entryDifference; i++)
						entriesToAdd.remove(entriesToAdd.size() - 1);
					terminal.setEntries((CustomsEntry[]) entriesToAdd.toArray());
				}
			}

			var exitDifference = dto.getExitPassageCount() - terminal.getExits().length;
			if (exitDifference != 0) {
				anyChanges = true;
				var exitsToAdd = Arrays.asList(terminal.getExits());
				if (exitDifference > 0) {
					for (int i = 0; i < exitDifference; i++)
						exitsToAdd.add(new CustomsExit(Util.getIntUuid(), true));
					terminal.setExits(((CustomsExit[]) exitsToAdd.toArray()));
				} else {
					for (int i = 0; i < exitDifference; i++)
						exitsToAdd.remove(exitsToAdd.size() - 1);
					terminal.setExits((CustomsExit[]) exitsToAdd.toArray());
				}
			}

			deleteTerminal(terminal.getId());
			serializeTerminal(terminal);
		}

		return new GetCustomsTerminalDto();
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

	private void loadTerminals() {
		var saveFolderPath = settings.getSaveFolderPath();
		var terminalFileArray = new File(saveFolderPath).listFiles();
		var terminalFiles = Stream.of(new File(saveFolderPath).listFiles()).filter(f -> f.isFile())
				.collect(Collectors.toList());

		for (var terminalFile : terminalFiles) {
			try {
				var terminal = Serialization.deserializeFromFile(terminalFile.getAbsolutePath(), CustomsTerminal.class);
				terminalIdToFileMap.put(terminal.getId(), terminalFile.getAbsolutePath());
				terminalIdToTerminalMap.put(terminal.getId(), terminal);
			} catch (OperationNotSupportedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private GetCustomsPassageDto[] mapPassagesToDto(CustomsPassage[] entriesToMap) {
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

	private GetCustomsTerminalDto mapTerminalToGetDto(CustomsTerminal terminal) {
		var entriesToMap = terminal.getEntries();
		var mappedEntries = mapPassagesToDto(entriesToMap);

		var exitsToMap = terminal.getExits();
		var mappedExits = mapPassagesToDto(exitsToMap);

		return new GetCustomsTerminalDto(terminal.getId(), terminal.getName(), mappedEntries, mappedExits);
	}

	private void serializeTerminal(CustomsTerminal terminal) {
		SerializationType serializationType;
		synchronized (serializationLock) {
			int currentSerializedCount = getSerializedCount();
			serializationType = serializationTypes[currentSerializedCount % serializationTypes.length];
			setSerializedCount(currentSerializedCount + 1);
		}

		try {
			Serialization.serializeToFile(terminal, serializationType, settings.getSaveFolderPath(), "terminal", null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}