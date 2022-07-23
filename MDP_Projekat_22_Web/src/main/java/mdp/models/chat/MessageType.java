package mdp.models.chat;

public enum MessageType {
	UNICAST("Unicast"), Multicast("Multicast"), BROADCAST("Broadcast");

	private String value;

	MessageType(String string) {
		this.value = string;
	}

	@Override
	public String toString() {
		return value;
	}
}
