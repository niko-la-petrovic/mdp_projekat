package mdp.register;

public class TerminalRegisterSettings {
	private String saveFolderPath;

	public TerminalRegisterSettings(String saveFolderPath) {
		super();
		this.saveFolderPath = saveFolderPath;
	}

	public String getSaveFolderPath() {
		return saveFolderPath;
	}

	public void setSaveFolderPath(String saveFolderPath) {
		this.saveFolderPath = saveFolderPath;
	}

	@Override
	public String toString() {
		return "TerminalRegisterSettings [saveFolderPath=" + saveFolderPath + "]";
	}
}
