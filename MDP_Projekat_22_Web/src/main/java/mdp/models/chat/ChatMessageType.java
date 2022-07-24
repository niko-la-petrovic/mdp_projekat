package mdp.models.chat;

public enum ChatMessageType {
	INFO("Info"), UNICAST("Unicast"), Multicast("Multicast"), BROADCAST("Broadcast");

	private String value;

	ChatMessageType(String string) {
		this.value = string;
	}

	@Override
	public String toString() {
		return value;
	}
}
