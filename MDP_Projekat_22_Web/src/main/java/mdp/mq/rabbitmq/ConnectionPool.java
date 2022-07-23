package mdp.mq.rabbitmq;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import mdp.util.SettingsLoader;

public class ConnectionPool {
	private static final String RABBITMQ_CONNECTION_SETTINGS_NAME = "rabbitmqConnection";

	private static ConnectionFactory factory;
	private static ConnectionPoolSettings settings;

	public static Connection getConnection() throws FileNotFoundException, IOException, TimeoutException {
		if (factory == null) {
			factory = new ConnectionFactory();

			SettingsLoader.loadSettings(RABBITMQ_CONNECTION_SETTINGS_NAME, props -> {
				var host = props.getProperty("host");
				var port = Integer.valueOf(props.getProperty("port"));
				var username = props.getProperty("username");
				var password = props.getProperty("password");

				settings = new ConnectionPoolSettings(host, port, username, password);
			});

			factory.setUsername(settings.getUsername());
			factory.setPassword(settings.getPassword());
			factory.setHost(settings.getHost());
			factory.setPort(settings.getPort());
		}

		var connection = factory.newConnection();
		return connection;
	}
}
