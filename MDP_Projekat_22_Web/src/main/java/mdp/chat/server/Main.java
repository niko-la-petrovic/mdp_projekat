package mdp.chat.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.rabbitmq.client.BuiltinExchangeType;

import mdp.mq.rabbitmq.ConnectionPool;
import mdp.mq.rabbitmq.Constants;
import mdp.util.SettingsLoader;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	private static ServerSocketSettings settings;
	private static boolean shouldRun = true;
	private static SSLServerSocketFactory ssf;
	private static ServerSocket ss;

	public static void main(String[] args) throws FileNotFoundException, IOException, TimeoutException {
		logger.log(Level.INFO, "Creating server thread");
		var serverThread = startServer();
		logger.log(Level.INFO, "Starting server thread");
		serverThread.start();
	}

	@SuppressWarnings("unused")
	private static void configureMessageQueueing() throws FileNotFoundException, IOException, TimeoutException {
		if (ServerThread.connection == null) {
			ServerThread.connection = ConnectionPool.getConnection();
			ServerThread.channel = ServerThread.connection.createChannel();

			var broadcastDeclareResult = ServerThread.channel.exchangeDeclare(Constants.BROADCAST_EXCHANGE_NAME,
					BuiltinExchangeType.FANOUT, true);
			logger.log(Level.INFO,
					String.format("Binding BROADCAST EXCHANGE under name '%s'", Constants.BROADCAST_EXCHANGE_NAME));

			var topicDeclareResult = ServerThread.channel.exchangeDeclare(Constants.TOPIC_EXCHANGE_NAME,
					BuiltinExchangeType.TOPIC, true);
			logger.log(Level.INFO,
					String.format("Binding TOPIC EXCHANGE under name '%s'", Constants.TOPIC_EXCHANGE_NAME));

			var directDeclareResult = ServerThread.channel.exchangeDeclare(Constants.DIRECT_EXCHANGE_NAME,
					BuiltinExchangeType.DIRECT, true);
			logger.log(Level.INFO,
					String.format("Binding DIRECT EXCHANGE under name '%s'", Constants.DIRECT_EXCHANGE_NAME));
		}
	}

	public static Thread startServer() throws IOException, TimeoutException {
		logger.log(Level.INFO, "Configuring message queueing");
		configureMessageQueueing();
		var startServerThread = new Thread() {

			@Override
			public void run() {

				if (settings == null)
					try {
						logger.log(Level.INFO, "Loading settings");
						loadSettings();
					} catch (IOException e1) {
						logger.log(Level.SEVERE, String.format("Failed to load settings: %s", e1.getMessage()));
						return;
					}

				logger.log(Level.INFO, "Loading secure socket keystore");
				System.setProperty("javax.net.ssl.keyStore", settings.getKeyStorePath());
				System.setProperty("javax.net.ssl.keyStorePassword", settings.getKeyStorePassword());

				logger.log(Level.INFO, String.format("Creating secure socket on port", settings.getPort()));
				ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
				try {
					ss = ssf.createServerSocket(settings.getPort());
				} catch (IOException e1) {
					logger.log(Level.SEVERE, String.format("Failed to create server socket: %s", e1.getMessage()));
					return;
				}
				var serverConnectionsThread = new Thread() {
					@Override
					public void run() {
						logger.log(Level.INFO, "Started server socket handle connections loop");
						serverHandleConnectionsLoop(ss);
					}
				};
				logger.log(Level.INFO, "Starting server connections thread");
				serverConnectionsThread.start();
			}
		};
		return startServerThread;

	}

	private static void serverHandleConnectionsLoop(ServerSocket ss) {
		logger.log(Level.INFO, String.format("Started server socket"));
		while (shouldRun) {
			logger.log(Level.INFO, "Awaiting next client...");
			SSLSocket s;
			try {
				s = (SSLSocket) ss.accept();
				logger.log(Level.INFO, String.format("Accepted new client: %s", s.getInetAddress()));

				logger.log(Level.INFO, String.format("Conducting handshakre with client: %s", s.getInetAddress()));
				s.startHandshake();

				logger.log(Level.INFO, String.format("Starting server thread for client: %s", s.getInetAddress()));
				new ServerThread(s).start();
			} catch (IOException e) {
				logger.log(Level.WARNING, "Failed to connect to client");
			}
		}
		logger.log(Level.WARNING, "Stopping server socket");
	}

	private static void loadSettings() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("chatServer", props -> {
			var host = props.getProperty("host");
			var keyStorePath = props.getProperty("keyStorePath");
			var keyStorePassword = props.getProperty("keyStorePassword");
			var port = Integer.valueOf(props.getProperty("port"));
			settings = new ServerSocketSettings(host, keyStorePath, keyStorePassword, port);
		});
	}
}
