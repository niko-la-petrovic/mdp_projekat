package mdp.models.chat;

import java.io.Serializable;
import java.math.BigInteger;

public class ChatMessage implements Serializable {
	private static final long serialVersionUID = 2212169997841420794L;

	// TODO add terminal name
	private String text;
	private String username;
	private BigInteger terminalId;
	private BigInteger passageId;
	private boolean isCustomsStep;
	private ChatMessageType type;

	public ChatMessage() {
	}

	public ChatMessage(String text, String username, BigInteger terminalId, BigInteger passageId, boolean isCustomsStep,
			ChatMessageType type) {
		super();
		this.text = text;
		this.username = username;
		this.terminalId = terminalId;
		this.passageId = passageId;
		this.isCustomsStep = isCustomsStep;
		this.type = type;
	}

	public BigInteger getPassageId() {
		return passageId;
	}

	public BigInteger getTerminalId() {
		return terminalId;
	}

	public String getText() {
		return text;
	}

	public ChatMessageType getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}

	public boolean isCustomsStep() {
		return isCustomsStep;
	}

	public void setCustomsStep(boolean isCustomsStep) {
		this.isCustomsStep = isCustomsStep;
	}

	public void setPassageId(BigInteger passageId) {
		this.passageId = passageId;
	}

	public void setTerminalId(BigInteger terminalId) {
		this.terminalId = terminalId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setType(ChatMessageType type) {
		this.type = type;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
