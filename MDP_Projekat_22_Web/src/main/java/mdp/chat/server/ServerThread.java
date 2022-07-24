package mdp.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import mdp.chat.dtos.SocketMessage;
import mdp.chat.dtos.SocketMessageType;

public class ServerThread extends Thread {

	private static final Gson gson = new Gson();
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private boolean shouldTerminate = false;
	private boolean awaitingEstablishment = true;
	static Channel channel;
	static Connection connection;

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
		System.out.println("Client connected");
		try {
			while (!isShouldTerminate())
				communicate();
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setShouldTerminate(boolean shouldTerminate) {
		this.shouldTerminate = shouldTerminate;
	}

	private void communicate() throws IOException {
		String jsonLine = null;
		System.out.println("Reading next line..");
		try {
			jsonLine = in.readLine();
			if (jsonLine == null)
				handleTerminate();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (isShouldTerminate())
			return;

		var socketMessage = gson.fromJson(jsonLine, SocketMessage.class);
		if (!socketMessage.validateMessage())
			sendMessage(SocketMessage.createErrorMessage("Invalid message content"));

		switch (socketMessage.getType()) {
		case ADD_MESSAGE:
			// TODO rabbitmq - add to queue method - switch on message type
			handleAddMessage(socketMessage);
			break;
		case ERROR:
			sendMessage(SocketMessage.createTerminateMessage());
			handleTerminate();
			break;
		case RETRIEVE_ALL:
			// TODO read through cached messages
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
		default:
			sendMessage(SocketMessage.createErrorMessage("Unhandled socket message type"));
		}
	}

	private void handleTerminate() {
		setShouldTerminate(true);
	}

	private void handleEstablishmentMessage(SocketMessage socketMessage) {
		// TODO Auto-generated method stub
		// TODO subscribe this socket as a consumer of the queue
		var establishmentMessage = socketMessage.getMessage();
		var terminalId = establishmentMessage.getTerminalId();
		var passageId = establishmentMessage.getPassageId();
		var isCustomsStep = establishmentMessage.isCustomsStep();

	}

	private void handleAddMessage(SocketMessage socketMessage) {
		// TODO Auto-generated method stub
		switch (socketMessage.getMessage().getType()) {
		case BROADCAST:
			// TODO rmq - send to broadcast exchange
			break;
		case Multicast:
			// TODO rmq - send to topic exchange
			break;
		case UNICAST:
			// TODO rmq - send to specific queue
			break;
		default:
			sendMessage(SocketMessage.createErrorMessage("Unsupported chat message type"));
			break;

		}
	}

	private synchronized void sendMessage(SocketMessage createErrorMessage) {
		var jsonString = gson.toJson(createErrorMessage);
		out.println(jsonString);
		out.flush();
	}
}
