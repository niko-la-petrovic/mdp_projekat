package mdp.fileserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import mdp.register.wanted.services.IPersonIdentifyingDocumentsService;
import mdp.register.wanted.services.PersonIdentifyingDocumentsService;
import mdp.util.SettingsLoader;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	private static FileServerSettings settings;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		logger.log(Level.INFO, "Setting security settings");
		System.setProperty("java.security.policy", "server.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		logger.log(Level.INFO, "Loading settings");
		try {
			SettingsLoader.loadSettings("fileServer", props -> {
				var rmiHost = props.getProperty("rmiHost");
				var rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
				var shouldCreateRegistry = Boolean.valueOf(props.getProperty("shouldCreateRegistry"));
				var personIdentifyingDocumentsServiceBindingName = props
						.getProperty("personIdentifyingDocumentsServiceBindingName");

				settings = new FileServerSettings(rmiHost, rmiPort, shouldCreateRegistry,
						personIdentifyingDocumentsServiceBindingName);
			});
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to parse settings", e);
			return;
		}
		logger.log(Level.INFO, "Loaded settings");

		logger.log(Level.INFO, "Creating IPersonIdentifyingDocumentsService");
		PersonIdentifyingDocumentsService personIdentifyingDocumentService = null;
		try {
			personIdentifyingDocumentService = new PersonIdentifyingDocumentsService();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to initialize service", e);
			return;
		}
		IPersonIdentifyingDocumentsService personIdentifyingDocumentStub;
		try {
			personIdentifyingDocumentStub = (IPersonIdentifyingDocumentsService) UnicastRemoteObject
					.exportObject(personIdentifyingDocumentService, 0);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Failed export service", e);
			return;
		}

		if (settings.isShouldCreateRegistry()) {
			logger.log(Level.INFO, String.format("Creating registry at port '%s'", settings.getRmiPort()));
			try {
				LocateRegistry.createRegistry(settings.getRmiPort());
			} catch (RemoteException e) {
				logger.log(Level.SEVERE, "Failed to create registry", e);
				return;
			}
		}
		logger.log(Level.INFO,
				String.format("Obtaining RMI registry %s:%s", settings.getRmiHost(), settings.getRmiPort()));
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(settings.getRmiHost(), settings.getRmiPort());
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Failed to get registry", e);
			return;
		}

		logger.log(Level.INFO, String.format("Binding IPersonIdentifyingDocumentService under name '%s'",
				settings.getPersonIdentifyingDocumentsServiceBindingName()));
		try {
			registry.rebind(settings.getPersonIdentifyingDocumentsServiceBindingName(), personIdentifyingDocumentStub);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Failed to rebind service", e);
			return;
		}

		logger.log(Level.INFO, "Bound all RMI services");
	}
}
