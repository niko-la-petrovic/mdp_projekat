package mdp.fileserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
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
	public static void main(String[] args) throws FileNotFoundException, IOException {
		logger.log(Level.INFO, "Setting security settings");
		System.setProperty("java.security.policy", "server.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		logger.log(Level.INFO, "Loading settings");
		SettingsLoader.loadSettings("fileServer", props -> {
			var rmiHost = props.getProperty("rmiHost");
			var rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
			var shouldCreateRegistry = Boolean.valueOf(props.getProperty("shouldCreateRegistry"));
			var personIdentifyingDocumentsServiceBindingName = props
					.getProperty("personIdentifyingDocumentsServiceBindingName");

			settings = new FileServerSettings(rmiHost, rmiPort, shouldCreateRegistry,
					personIdentifyingDocumentsServiceBindingName);
		});
		logger.log(Level.INFO, "Loaded settings");

		logger.log(Level.INFO, "Creating IPersonIdentifyingDocumentsService");
		var personIdentifyingDocumentService = new PersonIdentifyingDocumentsService();
		var personIdentifyingDocumentStub = (IPersonIdentifyingDocumentsService) UnicastRemoteObject
				.exportObject(personIdentifyingDocumentService, 0);

		if (settings.isShouldCreateRegistry()){
			logger.log(Level.INFO, String.format("Creating registry at port '%s'", settings.getRmiPort()));
			LocateRegistry.createRegistry(settings.getRmiPort());
		}
		logger.log(Level.INFO, String.format("Obtaining RMI registry %s:%s", settings.getRmiHost(), settings.getRmiPort()));
		var registry = LocateRegistry.getRegistry(settings.getRmiHost(), settings.getRmiPort());

		logger.log(Level.INFO, String.format("Binding IPersonIdentifyingDocumentService under name '%s'", settings.getPersonIdentifyingDocumentsServiceBindingName()));
		registry.rebind(settings.getPersonIdentifyingDocumentsServiceBindingName(), personIdentifyingDocumentStub);

		logger.log(Level.INFO, "Bound all RMI services");
	}
}
