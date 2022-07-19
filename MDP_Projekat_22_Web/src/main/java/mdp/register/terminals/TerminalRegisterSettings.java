package mdp.register.terminals;

import java.io.Serializable;

public class TerminalRegisterSettings implements Serializable {
	private static final long serialVersionUID = -2465892459936299840L;

	private String saveFolderPath;

	public TerminalRegisterSettings() {
	}

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
