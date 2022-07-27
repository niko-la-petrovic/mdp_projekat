package mdp.register.wanted;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;

import mdp.exceptions.TerminalNotFoundException;
import mdp.register.wanted.services.PoliceCheckStepService;

public class NotificationThread extends Thread {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void communicate() throws ClassNotFoundException, IOException {
		BigInteger terminalId = (BigInteger) in.readObject();
		BigInteger passageId = (BigInteger) in.readObject();
		try {
			policeCheckStepService.openPassages(terminalId, passageId);
			out.writeObject("Success");
			out.flush();
		} catch (TerminalNotFoundException e) {
			out.writeObject("Terminal not found");
			out.flush();
		}
	}
}
