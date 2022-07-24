package mdp.mq.rabbitmq;

public class Constants {

	public static final String BROADCAST_EXCHANGE_NAME = "mdp.broadcast";
	public static final String TOPIC_EXCHANGE_NAME = "mdp.topic";
	public static String getTerminalBindingKey(String terminalId) {
		return String.format("mdp.%s.#", terminalId);
	}
	public static String getTopicMessageRoutingKey(String terminalid12, String passageid12, boolean passagestep12) {
		return String.format("mdp.%s.%s.", terminalid12, passageid12);
	}

}
