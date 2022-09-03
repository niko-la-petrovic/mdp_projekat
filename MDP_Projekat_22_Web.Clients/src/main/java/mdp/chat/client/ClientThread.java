package mdp.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.swing.Action;

import com.google.gson.Gson;

import mdp.chat.dtos.SocketMessage;
import mdp.chat.dtos.SocketMessageType;
import mdp.models.chat.ChatMessage;
import mdp.models.chat.ChatMessageType;

public class ClientThread extends Thread {
	private static final Gson gson = new Gson();
	private static final Logger logger = Logger.getLogger(ClientThread.class.getName());

	private ChatClientSocketSettings settings;
	private SSLSocket socket;
	private BufferedReader in;
	private PrintWriter out;
	private BigInteger terminalId;
	private BigInteger passageId;
	private boolean isCustomsPassage;
	private boolean shouldFinish;

	private Consumer<ChatMessage> messageConsumer;
	private Runnable onDisconnect;

	public void setOnDisconnect(Runnable onDisconnect) {
		this.onDisconnect = onDisconnect;
	}

	public ClientThread(String username, BigInteger terminalId, BigInteger passageId, boolean isCustomsPassage,
			ChatClientSocketSettings settings, SSLSocket socket, Consumer<ChatMessage> messageConsumer)
			throws IOException {
		super();
		this.terminalId = terminalId;
		this.passageId = passageId;
		this.isCustomsPassage = isCustomsPassage;
		this.settings = settings;
		this.socket = socket;
		this.messageConsumer = messageConsumer;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

		SocketMessage establishmentMessage = new SocketMessage(SocketMessageType.ESTABLISHMENT_MESSAGE,
				new ChatMessage(null, username, terminalId, passageId, isCustomsPassage, ChatMessageType.INFO), null, null);
		sendMessage(establishmentMessage);
	}

	public boolean isShouldFinish() {
		return shouldFinish;
	}

	@Override
	public void run() {
		try {
			Thread readThread = new Thread() {
				@Override
				public void run() {
					while (!shouldFinish) {
						try {
							String line = in.readLine();
							SocketMessage socketMessage = gson.fromJson(line, SocketMessage.class);
							messageConsumer.accept(socketMessage.getMessage());
						} catch (IOException e) {
							shouldFinish = true;
						}
					}
				}
			};
			readThread.start();
			while (!shouldFinish) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					shouldFinish = true;
					logger.log(Level.INFO, "Interrupted client thread");
				}
			}
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, String.format("Failed to release resources: %s", e.getMessage()));
			}
		}
	}

	public synchronized void sendMessage(SocketMessage establishmentMessage) {
		String json = gson.toJson(establishmentMessage);
		out.println(json);
	}

	public synchronized void sendMessage(ChatMessage message) {
		SocketMessage socketMessage = new SocketMessage(SocketMessageType.ADD_MESSAGE, message, null, null);
		String json = gson.toJson(socketMessage);
		out.println(json);
	}

	public void setShouldFinish(boolean shouldFinish) {
		this.shouldFinish = shouldFinish;
	}
}
