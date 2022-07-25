package mdp.mq.rabbitmq;

public class Constants {

	public static final String BROADCAST_EXCHANGE_NAME = "mdp.broadcast";
	public static final String TOPIC_EXCHANGE_NAME = "mdp.topic";

	public static final String DIRECT_EXCHANGE_NAME = "mdp.direct";

	public static String getTerminalBindingKey(String terminalId) {
		return String.format("mdp.%s.#", terminalId);
	}

	public static String getMessageRoutingKey(String terminalId, String passageId, boolean isCustomsStep) {
		return String.format("mdp.%s.%s.%s", terminalId, passageId, isCustomsStep ? "customs" : "police");
	}

}
