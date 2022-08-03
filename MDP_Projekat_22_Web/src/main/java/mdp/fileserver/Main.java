package mdp.fileserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import mdp.register.wanted.services.IPersonIdentifyingDocumentsService;
import mdp.register.wanted.services.PersonIdentifyingDocumentsService;
import mdp.util.SettingsLoader;

public class Main {

	private static FileServerSettings settings;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.setProperty("java.security.policy", "server.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		SettingsLoader.loadSettings("fileServer", props -> {
			var rmiHost = props.getProperty("rmiHost");
			var rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
			var shouldCreateRegistry = Boolean.valueOf(props.getProperty("shouldCreateRegistry"));
			var personIdentifyingDocumentsServiceBindingName = props
					.getProperty("personIdentifyingDocumentsServiceBindingName");

			settings = new FileServerSettings(rmiHost, rmiPort, shouldCreateRegistry,
					personIdentifyingDocumentsServiceBindingName);
		});

		var personIdentifyingDocumentService = new PersonIdentifyingDocumentsService();
		var personIdentifyingDocumentStub = (IPersonIdentifyingDocumentsService) UnicastRemoteObject
				.exportObject(personIdentifyingDocumentService, 0);

		if (settings.isShouldCreateRegistry())
			LocateRegistry.createRegistry(settings.getRmiPort());
		var registry = LocateRegistry.getRegistry(settings.getRmiHost(), settings.getRmiPort());

		registry.rebind(settings.getPersonIdentifyingDocumentsServiceBindingName(), personIdentifyingDocumentStub);

		System.out.println("Bound RMI services");

	}

}
