package mdp.chat.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.rabbitmq.client.BuiltinExchangeType;

import mdp.mq.rabbitmq.ConnectionPool;
import mdp.mq.rabbitmq.Constants;
import mdp.util.SettingsLoader;

public class Main {
	private static ServerSocketSettings settings;
	private static boolean shouldRun = true;
	private static SSLServerSocketFactory ssf;
	private static ServerSocket ss;

	public static void main(String[] args) throws FileNotFoundException, IOException, TimeoutException {
		startServer();
	}

	private static void configureMessageQueueing() throws FileNotFoundException, IOException, TimeoutException {
		if (ServerThread.connection == null) {
			ServerThread.connection = ConnectionPool.getConnection();
			ServerThread.channel = ServerThread.connection.createChannel();

			@SuppressWarnings("unused")
			var broadcastDeclareResult = ServerThread.channel.exchangeDeclare(Constants.BROADCAST_EXCHANGE_NAME,
					BuiltinExchangeType.FANOUT, true);
			@SuppressWarnings("unused")
			var topicDeclareResult = ServerThread.channel.exchangeDeclare(Constants.TOPIC_EXCHANGE_NAME,
					BuiltinExchangeType.TOPIC, true);
		}
	}

	public static Thread startServer() throws IOException, TimeoutException {
		configureMessageQueueing();
		var startServerThread = new Thread() {

			@Override
			public void run() {

				if (settings == null)
					try {
						loadSettings();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
				System.setProperty("javax.net.ssl.keyStore", settings.getKeyStorePath());
				System.setProperty("javax.net.ssl.keyStorePassword", settings.getKeyStorePassword());

				ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
				try {
					ss = ssf.createServerSocket(settings.getPort());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}
				var serverConnectionsThread = new Thread() {
					@Override
					public void run() {
						try {
							serverHandleConnectionsLoop(ss);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				serverConnectionsThread.start();
			}
		};
		return startServerThread;
	}

	private static void serverHandleConnectionsLoop(ServerSocket ss) throws IOException {
		System.out.println("Server je pokrenut");
		while (shouldRun) {
			System.out.println("Waiting for client...");
			SSLSocket s = (SSLSocket) ss.accept();
			s.startHandshake();
			new ServerThread(s).start();
		}
		System.out.println("Stopping connection handling loop");
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
