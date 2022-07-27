package mdp.models.wanted;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class WantedPersonDetected implements Serializable {
	private static final long serialVersionUID = -3375197902821200259L;

	private BigInteger personId;
	private LocalDateTime detectedAt;
	private BigInteger terminalId;
	private BigInteger passageId;

	public WantedPersonDetected(BigInteger personId, LocalDateTime detectedAt, BigInteger terminalId,
			BigInteger passageId) {
		super();
		this.personId = personId;
		this.detectedAt = detectedAt;
		this.terminalId = terminalId;
		this.passageId = passageId;
	}

	public LocalDateTime getDetectedAt() {
		return detectedAt;
	}

	public BigInteger getPassageId() {
		return passageId;
	}

	public BigInteger getPersonId() {
		return personId;
	}

	public BigInteger getTerminalId() {
		return terminalId;
	}

	public void setDetectedAt(LocalDateTime detectedAt) {
		this.detectedAt = detectedAt;
	}

	public void setPassageId(BigInteger passageId) {
		this.passageId = passageId;
	}

	public void setPersonId(BigInteger personId) {
		this.personId = personId;
	}

	public void setTerminalId(BigInteger terminalId) {
		this.terminalId = terminalId;
	}

}
