package mdp.register.terminals.dtos;

import java.io.Serializable;
import java.math.BigInteger;

public class UpdateTerminalDto implements Serializable {
	private static final long serialVersionUID = 466082971461878475L;

	private BigInteger terminalId;
	private String name;
	private int entryPassageCount;
	private int exitPassageCount;

	public UpdateTerminalDto() {
	}

	public UpdateTerminalDto(BigInteger terminalId, String name, int entryPassageCount, int exitPassageCount) {
		super();
		this.terminalId = terminalId;
		this.name = name;
		this.entryPassageCount = entryPassageCount;
		this.exitPassageCount = exitPassageCount;
	}

	public int getEntryPassageCount() {
		return entryPassageCount;
	}

	public int getExitPassageCount() {
		return exitPassageCount;
	}

	public String getName() {
		return name;
	}

	public BigInteger getTerminalId() {
		return terminalId;
	}

	public void setEntryPassageCount(int entryPassageCount) {
		this.entryPassageCount = entryPassageCount;
	}

	public void setExitPassageCount(int exitPassageCount) {
		this.exitPassageCount = exitPassageCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTerminalId(BigInteger terminalId) {
		this.terminalId = terminalId;
	}

}
