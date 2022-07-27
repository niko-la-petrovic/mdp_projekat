package mdp.register.wanted;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.OperationNotSupportedException;

import mdp.register.wanted.services.IPersonIdentifyingDocumentsService;
import mdp.register.wanted.services.IPoliceCheckStepService;
import mdp.register.wanted.services.PersonIdentifyingDocumentsService;
import mdp.register.wanted.services.PoliceCheckStepService;
import mdp.util.SettingsLoader;

public class Main {
	private static WantedRegisterServerSettings settings;
	private static boolean shouldStop;
	private static NotificationSocketSettings notificationSettings;

	@SuppressWarnings("unused")
	public static void main(String[] args)
			throws AlreadyBoundException, FileNotFoundException, IOException, OperationNotSupportedException {
		System.setProperty("java.security.policy", "server.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		SettingsLoader.loadSettings("wantedRegisterServer", props -> {
			var rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
			var policeCheckStepServiceBindingName = props.getProperty("policeCheckStepServiceBindingName");
			var personIdentifyingDocumentsServiceBindingName = props
					.getProperty("personIdentifyingDocumentsServiceBindingName");
			settings = new WantedRegisterServerSettings(rmiPort, policeCheckStepServiceBindingName,
					personIdentifyingDocumentsServiceBindingName);
		});

		var policeCheckStepService = new PoliceCheckStepService();
		var policeServiceStub = (IPoliceCheckStepService) UnicastRemoteObject.exportObject(policeCheckStepService, 0);

		var personIdentifyingDocumentService = new PersonIdentifyingDocumentsService();
		var personIdentifyingDocumentStub = (IPersonIdentifyingDocumentsService) UnicastRemoteObject
				.exportObject(personIdentifyingDocumentService, 0);

		LocateRegistry.createRegistry(settings.getRmiPort());
		var registry = LocateRegistry.getRegistry();

		registry.rebind(settings.getPoliceCheckStepServiceBindingName(), policeServiceStub);
		registry.rebind(settings.getPersonIdentifyingDocumentsServiceBindingName(), personIdentifyingDocumentStub);
		System.out.println("Bound RMI services");

		setupNotificationSocket(policeCheckStepService);
	}

	private static void setupNotificationSocket(PoliceCheckStepService policeCheckStepService) throws IOException {
		SettingsLoader.loadSettings("notificationSocket", props -> {
			var port = Integer.valueOf(props.getProperty("port"));
			notificationSettings = new NotificationSocketSettings(port);
		});

		var serverSocket = new ServerSocket(notificationSettings.getPort());
		while (!shouldStop) {
			var remoteSocket = serverSocket.accept();
			new NotificationThread(remoteSocket, policeCheckStepService).start();
		}
	}
}
