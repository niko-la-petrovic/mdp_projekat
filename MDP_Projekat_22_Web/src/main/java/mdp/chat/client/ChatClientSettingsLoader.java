package mdp.chat.client;

import java.io.FileNotFoundException;
import java.io.IOException;

import mdp.util.SettingsLoader;

public class ChatClientSettingsLoader {
	private static ChatClientSocketSettings settings;

	public static ChatClientSocketSettings getSettings() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("chatClient", props -> {
			var host = props.getProperty("host");
			var port = Integer.valueOf(props.getProperty("port"));
			var trustStorePath = props.getProperty("trustStorePath");
			var trustStorePassword = props.getProperty("trustStorePassword");

			settings = new ChatClientSocketSettings(host, port, trustStorePath, trustStorePassword);
		});
		return settings;
	}
}
