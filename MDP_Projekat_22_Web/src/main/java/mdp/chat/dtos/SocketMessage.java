package mdp.chat.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import mdp.models.chat.ChatMessage;
import mdp.models.chat.ChatMessageType;

public class SocketMessage implements Serializable {
	private static final long serialVersionUID = 8123352800517061756L;

	public static SocketMessage createAddMessageMessage(ChatMessage message) {
		return new SocketMessage(SocketMessageType.ADD_MESSAGE, message, null, null);
	}

	public static SocketMessage createErrorMessage(String error) {
		return new SocketMessage(SocketMessageType.ERROR, null, null, error);
	}

	public static SocketMessage createRetrieveAllMessage(ChatMessage[] messages) {
		return new SocketMessage(SocketMessageType.RETRIEVE_ALL, null, messages, null);
	}

	// TODO add username, passage id, ... to termination message
	public static SocketMessage createTerminateMessage() {
		return new SocketMessage(SocketMessageType.TERMINATE, null, null, null);
	}

	public static SocketMessage createTransferMessage(ChatMessage message) {
		return new SocketMessage(SocketMessageType.TRANSFER_MESSAGE, message, null, null);
	}

	// TODO add username to establishment message
	public static SocketMessage craeteEstablishmentMessage(BigInteger terminalId, BigInteger passageId,
			boolean isCustomsStep) {
		return new SocketMessage(SocketMessageType.ESTABLISHMENT_MESSAGE,
				new ChatMessage(null, null, terminalId, passageId, isCustomsStep, ChatMessageType.INFO), null, null);
	}

	private SocketMessageType type;

	private ChatMessage message;

	private ChatMessage messages[];

	private String error;

	public SocketMessage() {
	}

	public SocketMessage(SocketMessageType type, ChatMessage message, ChatMessage[] messages, String error) {
		super();
		this.type = type;
		this.message = message;
		this.messages = messages;
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public ChatMessage getMessage() {
		return message;
	}

	public ChatMessage[] getMessages() {
		return messages;
	}

	public SocketMessageType getType() {
		return type;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setMessage(ChatMessage message) {
		this.message = message;
	}

	public void setMessages(ChatMessage[] messages) {
		this.messages = messages;
	}

	public void setType(SocketMessageType type) {
		this.type = type;
	}

	public boolean validateMessage() {
		switch (type) {
		case TERMINATE:
			return message == null && messages == null;
		case ADD_MESSAGE:
			return message != null && messages == null;
		case RETRIEVE_ALL:
			return message == null && messages != null;
		case TRANSFER_MESSAGE:
			return message != null && messages == null;
		case ERROR:
			return error != null && message == null && messages == null;
		case ESTABLISHMENT_MESSAGE:
			return error != null && message != null && message.getTerminalId() != null && message.getPassageId() != null
					&& messages == null;
		default:
			return false;
		}
	}
}
