package mdp.dtos;

import java.io.Serializable;
import java.math.BigInteger;

public class SearchTerminalDto implements Serializable {
	private static final long serialVersionUID = -3328741309625306535L;

	private BigInteger passageId;
	private String terminalName;
	private boolean isCustomsPassage;

	public SearchTerminalDto() {
	}

	public SearchTerminalDto(BigInteger passageId, String terminalName, boolean isCustomsPassage) {
		super();
		this.passageId = passageId;
		this.terminalName = terminalName;
		this.isCustomsPassage = isCustomsPassage;
	}

	public BigInteger getPassageId() {
		return passageId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public boolean isCustomsPassage() {
		return isCustomsPassage;
	}

	public void setCustomsPassage(boolean isCustomsPassage) {
		this.isCustomsPassage = isCustomsPassage;
	}

	public void setPassageId(BigInteger passageId) {
		this.passageId = passageId;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
}
