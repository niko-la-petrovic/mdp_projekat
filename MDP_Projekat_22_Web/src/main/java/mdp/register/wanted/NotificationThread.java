package mdp.register.wanted;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import mdp.exceptions.TerminalNotFoundException;
import mdp.register.wanted.services.PoliceCheckStepService;

public class NotificationThread extends Thread {
private static final Logger logger = Logger.getLogger(NotificationThread.class.getName());

	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	private PoliceCheckStepService policeCheckStepService;

	public NotificationThread(Socket remoteSocket, PoliceCheckStepService policeCheckStepService) throws IOException {
		this.socket = remoteSocket;
		this.policeCheckStepService = policeCheckStepService;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run() {
		try {
			communicate();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, String.format("Class not found exception with client %s: %s", socket.getInetAddress(), e.getMessage()));
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("IO Exception with client %s: %s", socket.getInetAddress(), e.getMessage()));
		}
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("Failed to close socket with client %s: %s", socket.getInetAddress(), e.getMessage()));
		}
	}

	private void communicate() throws ClassNotFoundException, IOException {
		BigInteger terminalId = (BigInteger) in.readObject();
		BigInteger passageId = (BigInteger) in.readObject();
		try {
			logger.log(Level.INFO, String.format("Opening customs passage of terminal '%s' with passage '%s'", terminalId, passageId));

			policeCheckStepService.openPassages(terminalId, passageId);
			out.writeObject("Success");
			out.flush();

			logger.log(Level.INFO, String.format("Opened customs passage of terminal '%s' with passage '%s'", terminalId, passageId));
		} catch (TerminalNotFoundException e) {
			out.writeObject("Terminal not found");
			out.flush();
			logger.log(Level.INFO, String.format("Terminal '%s' not found", terminalId));
		}
	}
}
