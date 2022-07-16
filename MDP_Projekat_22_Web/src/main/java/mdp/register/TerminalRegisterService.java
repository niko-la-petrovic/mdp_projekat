package mdp.register;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

import mdp.exceptions.TerminalNotFoundException;
import mdp.register.dtos.CreateTerminalDto;
import mdp.register.dtos.GetTerminalDto;
import mdp.register.dtos.UpdateTerminalDto;
import mdp.util.SettingsLoader;

public class TerminalRegisterService implements ITerminalRegisterService {
	private static final String TERMINAL_REGISTER_SETTINGS_NAME = "terminalRegister";
	private TerminalRegisterSettings settings;

	public TerminalRegisterService() throws FileNotFoundException, IOException {
		loadSettings();
	}

	private void loadSettings() throws IOException, FileNotFoundException {
		SettingsLoader.loadSettings(TERMINAL_REGISTER_SETTINGS_NAME, props -> {
			var saveFolderPath = props.getProperty("saveFolderPath");
			settings = new TerminalRegisterSettings(saveFolderPath);
		});
	}

	@Override
	public GetTerminalDto[] getTerminals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetTerminalDto createTerminal(CreateTerminalDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetTerminalDto updateTerminal(UpdateTerminalDto dto) throws TerminalNotFoundException {
		// TODO Auto-generated method stub
		return new GetTerminalDto();
	}

	// TODO
	@Override
	public void deleteTerminal(BigInteger id) throws TerminalNotFoundException {
	}

	@Override
	public GetTerminalDto getTerminal(BigInteger passageId, boolean isCustomsStep, String terminalName)
			throws TerminalNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
