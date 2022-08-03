package mdp.register.wanted.services;

import java.io.IOException;
import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

import mdp.exceptions.PassageNotFoundException;
import mdp.exceptions.TerminalNotFoundException;

public interface IPoliceCheckStepService extends Remote {
	String ping() throws RemoteException;

	void openPassages(BigInteger terminalId, BigInteger passageId) throws RemoteException, TerminalNotFoundException;

	boolean isOpenTerminalPassage(BigInteger terminalId, boolean isEntry, BigInteger passageId)
			throws RemoteException, TerminalNotFoundException, PassageNotFoundException;

	boolean isWanted(BigInteger personId, BigInteger terminalId, BigInteger passageId)
			throws RemoteException, IOException;
}
