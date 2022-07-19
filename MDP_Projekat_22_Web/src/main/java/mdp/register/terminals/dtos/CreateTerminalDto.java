package mdp.register.terminals.dtos;

import java.io.Serializable;

public class CreateTerminalDto implements Serializable {
	private static final long serialVersionUID = -5283306602916737721L;

	private String terminalName;
	private int entryPassageCount;
	private int exitPassageCount;

	public CreateTerminalDto() {
	}

	public CreateTerminalDto(String terminalName, int entryPassageCount, int exitPassageCount) {
		super();
		this.terminalName = terminalName;
		this.entryPassageCount = entryPassageCount;
		this.exitPassageCount = exitPassageCount;
	}

	public int getEntryPassageCount() {
		return entryPassageCount;
	}

	public int getExitPassageCount() {
		return exitPassageCount;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setEntryPassageCount(int entryPassageCount) {
		this.entryPassageCount = entryPassageCount;
	}

	public void setExitPassageCount(int exitPassageCount) {
		this.exitPassageCount = exitPassageCount;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

}
