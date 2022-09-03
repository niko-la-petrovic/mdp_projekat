package mdp.register.wanted.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.Remote;
import java.util.HashMap;

import mdp.exceptions.NoFilesFoundException;

public interface IPersonIdentifyingDocumentsService extends Remote {

	void addPersonDocuments(BigInteger personId, HashMap<String, byte[]> fileMap) throws IOException;

	File getArchivedPersonDocuments(BigInteger personId)
			throws NoFilesFoundException, FileNotFoundException, IOException;

}
