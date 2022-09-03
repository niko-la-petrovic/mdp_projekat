package mdp.chat.client;

import java.io.FileNotFoundException;
import java.io.IOException;

import mdp.util.client.SettingsLoader;

public class ChatClientSettingsLoader {
	private static ChatClientSocketSettings settings;

	public static ChatClientSocketSettings getSettings() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("chatClient", props -> {
			String host = props.getProperty("host");
			int port = Integer.valueOf(props.getProperty("port"));
			String trustStorePath = props.getProperty("trustStorePath");
			String trustStorePassword = props.getProperty("trustStorePassword");

			settings = new ChatClientSocketSettings(host, port, trustStorePath, trustStorePassword);
		});
		return settings;
	}
}
