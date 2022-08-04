package mdp.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import io.netty.util.internal.ConcurrentSet;
import mdp.chat.dtos.SocketMessage;
import mdp.chat.dtos.SocketMessageType;
import mdp.models.chat.ChatMessage;
import mdp.mq.rabbitmq.Constants;

public class ServerThread extends Thread {
	private static final Logger logger = Logger.getLogger(ServerThread.class.getName());

	private static final Gson gson = new Gson();
	static Channel channel;
	static Connection connection;

	// TODO add final
	private static ConcurrentHashMap<Consumer, String> consumerTagMap = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, ConcurrentLinkedDeque<ChatMessage>> routingKeyChatMessageMap = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, Consumer> routingKeyConsumerMap = new ConcurrentHashMap<>();
	private static ThreadLocal<String> threadRoutingKey = new ThreadLocal<>();
	private static ConcurrentHashMap<String, ConcurrentSet<ServerThread>> routingKeySubscriberMap = new ConcurrentHashMap<>();

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private boolean shouldTerminate = false;
	private boolean awaitingEstablishment = true;
	private LinkedBlockingQueue<ChatMessage> messageQueue = new LinkedBlockingQueue<>();
	private Thread transferMessageThread;

	public ServerThread(Socket s) throws IOException {
		this.socket = s;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
	}

	public boolean isShouldTerminate() {
		return shouldTerminate;
	}

	@Override
	public void run() {
		try {
			while (!isShouldTerminate())
				try {
					communicate();
				}

				catch (SocketException e) {
					{
						handleTerminate();
					}
				} catch (Exception e) {
					logger.log(Level.SEVERE, String.format("Exception: %s", e.getMessage()));
				}
			handleTerminate();
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("Error while stopping client communication: %s", e.getMessage()));
		}
	}

	public void setShouldTerminate(boolean shouldTerminate) {
		this.shouldTerminate = shouldTerminate;
	}

	@SuppressWarnings("unused")
	private synchronized Consumer bindConsumer(String routingKey, BigInteger terminalId) throws IOException {
		threadRoutingKey.set(routingKey);

		synchronized (routingKeyChatMessageMap) {
			var cachedMessages = routingKeyChatMessageMap.get(routingKey);
			if (cachedMessages != null) {
				for (ChatMessage chatMessage : cachedMessages) {
					messageQueue.add(chatMessage);
				}
			}
			// TODO retrieve all
			// TODO add thread to sub map
			var subscribers = routingKeySubscriberMap.get(routingKey);
			if (subscribers == null) {
				subscribers = new ConcurrentSet<>();
				routingKeySubscriberMap.put(routingKey, subscribers);
			}
			synchronized (subscribers) {
				subscribers.add(this);
			}
		}

		var consumer = routingKeyConsumerMap.get(routingKey);
		if (consumer != null)
			return consumer;

		try {
			var declareQueueResult = channel.queueDeclare(routingKey, true, false, false, null);
			var bindBroadcastExchangeResult = channel.queueBind(routingKey, Constants.BROADCAST_EXCHANGE_NAME, "");
			var bindMulticastExchangeResult = channel.queueBind(routingKey, Constants.TOPIC_EXCHANGE_NAME,
					Constants.getTerminalBindingKey(terminalId.toString()));
			var bindDirectExchangeResult = channel.queueBind(routingKey, Constants.DIRECT_EXCHANGE_NAME, routingKey);
		} catch (AlreadyClosedException e) {
			logger.log(Level.WARNING, String.format("Channel already closed: %s", e.getMessage()));
		}

		if (!routingKeyChatMessageMap.contains(routingKey))
			routingKeyChatMessageMap.put(routingKey, new ConcurrentLinkedDeque<>());

		consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
				var message = gson.fromJson(new String(body), ChatMessage.class);
				consumeQueueMessage(routingKey, message);
			}

			private void consumeQueueMessage(String routingKey, ChatMessage message) {
				synchronized (routingKeyChatMessageMap) {
					var messageCollection = routingKeyChatMessageMap.get(routingKey);
					messageCollection.add(message);

					var subscribers = routingKeySubscriberMap.get(routingKey);
					synchronized (subscribers) {
						for (var subscriber : subscribers) {
							subscriber.messageQueue.add(message);
						}
					}
				}
				// sendMessage(new SocketMessage(SocketMessageType.TRANSFER_MESSAGE, message,
				// null, null));
			}
		};

		routingKeyConsumerMap.put(routingKey, consumer);
		var consumerTag = channel.basicConsume(routingKey, consumer);
		consumerTagMap.put(consumer, consumerTag);

		return consumer;
	}

	private void communicate() throws IOException {
		String jsonLine = null;
		try {
			jsonLine = in.readLine();
			if (jsonLine == null)
				setShouldTerminate(true);
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("Error while communicating with client: %s", e.getMessage()));
			setShouldTerminate(true);
		}

		if (isShouldTerminate()) {
			handleTerminate();
			return;
		}

		var socketMessage = gson.fromJson(jsonLine, SocketMessage.class);
		if (!socketMessage.validateMessage())
			sendMessage(SocketMessage.createErrorMessage("Invalid message content"));

		switch (socketMessage.getType()) {
		case ADD_MESSAGE:
			handleAddMessage(socketMessage);
			break;
		case ERROR:
			sendMessage(SocketMessage.createTerminateMessage());
			handleTerminate();
			break;
		case TERMINATE:
			handleTerminate();
			break;
		case TRANSFER_MESSAGE:
			sendMessage(SocketMessage.createErrorMessage("Unsupported message type from client to server"));
			handleTerminate();
			break;
		case ESTABLISHMENT_MESSAGE:
			if (awaitingEstablishment && socketMessage.getType() != SocketMessageType.ESTABLISHMENT_MESSAGE)
				sendMessage(SocketMessage.createErrorMessage("Establishment message expected"));
			else if (awaitingEstablishment)
				handleEstablishmentMessage(socketMessage);
			break;
		default:
			sendMessage(SocketMessage.createErrorMessage("Unhandled socket message type"));
		}
	}

	private String getRoutingKey(BigInteger terminalId, BigInteger passageId, boolean isCustomsStep) {
		var routingKey = Constants.getMessageRoutingKey(terminalId.toString(), passageId.toString(), isCustomsStep);
		return routingKey;
	}

	private void handleAddMessage(SocketMessage socketMessage) throws IOException {
		var message = socketMessage.getMessage();
		var messageJson = gson.toJson(message);
		var messageBytes = messageJson.getBytes();
		String routingKey = getRoutingKey(message.getTerminalId(), message.getPassageId(), message.isCustomsStep());

		switch (message.getType()) {
		case BROADCAST:
			channel.basicPublish(Constants.BROADCAST_EXCHANGE_NAME, "", null, messageBytes);
			break;
		case MULTICAST:
			channel.basicPublish(Constants.TOPIC_EXCHANGE_NAME, routingKey, null, messageBytes);
			break;
		case UNICAST:
			channel.basicPublish(Constants.DIRECT_EXCHANGE_NAME, routingKey, null, messageBytes);
			break;
		default:
			sendMessage(SocketMessage.createErrorMessage("Unsupported chat message type"));
			break;
		}
	}

	@SuppressWarnings("unused")
	private void handleEstablishmentMessage(SocketMessage socketMessage) throws IOException {
		var establishmentMessage = socketMessage.getMessage();
		var terminalId = establishmentMessage.getTerminalId();
		var passageId = establishmentMessage.getPassageId();
		var isCustomsStep = establishmentMessage.isCustomsStep();

		var routingKey = getRoutingKey(terminalId, passageId, isCustomsStep);
		var consumer = bindConsumer(routingKey, terminalId);

		transferMessageThread = new Thread() {
			@Override
			public void run() {
				while (!shouldTerminate) {
					try {
						var message = messageQueue.take();
						sendMessage(new SocketMessage(SocketMessageType.TRANSFER_MESSAGE, message, null, null));
					} catch (InterruptedException e) {
						logger.log(Level.WARNING, "ServerThread interrupted");
					}
				}
			}
		};
		transferMessageThread.setDaemon(true);
		transferMessageThread.start();
	}

	private void handleTerminate() throws IOException {
		setShouldTerminate(true);
		unbindConsumer();
		transferMessageThread.interrupt();
		logger.log(Level.INFO, String.format("Terminating client '%s'", socket.getInetAddress()));
	}

	private synchronized void sendMessage(SocketMessage message) {
		var jsonString = gson.toJson(message);
		out.println(jsonString);
	}

	private void unbindConsumer() throws IOException {
		var routingKey = threadRoutingKey.get();
		if (routingKey == null)
			return;

			// TODO remove consumer cancelling since theres one consumer per terminal - keep it running
		var consumer = routingKeyConsumerMap.get(routingKey);
		if (consumer == null)
			return;

		var consumerTag = consumerTagMap.get(consumer);
		if (consumerTag == null)
			return;

		channel.basicCancel(consumerTag);
		routingKeyConsumerMap.remove(routingKey);
		ConcurrentSet<ServerThread> subscribers = routingKeySubscriberMap.get(routingKey);
		synchronized (subscribers) {
			subscribers.remove(this);
		}
		consumerTagMap.remove(consumer);
		threadRoutingKey.remove();
	}
}
