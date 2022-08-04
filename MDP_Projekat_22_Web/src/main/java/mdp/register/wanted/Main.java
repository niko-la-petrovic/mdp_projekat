package mdp.register.wanted;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;

import mdp.register.wanted.services.IPoliceCheckStepService;
import mdp.register.wanted.services.PoliceCheckStepService;
import mdp.util.SettingsLoader;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	private static WantedRegisterServerSettings settings;
	private static boolean shouldStop;
	private static NotificationSocketSettings notificationSettings;

	// TODO remove thrown exceptions in this and other classes
	@SuppressWarnings("unused")
	public static void main(String[] args)
			throws AlreadyBoundException, FileNotFoundException, IOException, OperationNotSupportedException {
		logger.log(Level.INFO, "Setting security");
		System.setProperty("java.security.policy", "server.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		logger.log(Level.INFO, "Loading settings");
		SettingsLoader.loadSettings("wantedRegisterServer", props -> {
			var rmiHost = props.getProperty("rmiHost");
			var rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
			var policeCheckStepServiceBindingName = props.getProperty("policeCheckStepServiceBindingName");
			var shouldCreateRegistry = Boolean.valueOf(props.getProperty("shouldCreateRegistry"));
			settings = new WantedRegisterServerSettings(rmiHost, rmiPort, policeCheckStepServiceBindingName,
					shouldCreateRegistry);
		});

		logger.log(Level.INFO, "Creating PoliceCheckStepService");
		var policeCheckStepService = new PoliceCheckStepService();
		var policeServiceStub = (IPoliceCheckStepService) UnicastRemoteObject.exportObject(policeCheckStepService, 0);

		try {
			if (settings.isShouldCreateRegistry()) {
				logger.log(Level.INFO, String.format("Creating RMI registry on port %s", settings.getRmiPort()));
				LocateRegistry.createRegistry(settings.getRmiPort());
			}
			logger.log(Level.INFO,
					String.format("Obtaining RMI registry %s:%s", settings.getRmiHost(), settings.getRmiPort()));
			var registry = LocateRegistry.getRegistry(settings.getRmiHost(), settings.getRmiPort());

			logger.log(Level.INFO, String.format("Binding IPoliceCheckStepService under name '%s'",
					settings.getPoliceCheckStepServiceBindingName()));
			registry.rebind(settings.getPoliceCheckStepServiceBindingName(), policeServiceStub);
		} catch (AccessException e) {
			logger.log(Level.SEVERE, String.format("Access exception: %s", e.getMessage()));
			System.exit(1);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, String.format("Remote exception: %s", e.getMessage()));
			System.exit(1);
		}

		logger.log(Level.INFO, "Bound all RMI services");

		logger.log(Level.INFO, "Setting up notification socket");
		setupNotificationSocket(policeCheckStepService);
	}

	private static void setupNotificationSocket(PoliceCheckStepService policeCheckStepService) throws IOException {
		logger.log(Level.INFO, "Loading notification socket settings");
		SettingsLoader.loadSettings("notificationSocket", props -> {
			var port = Integer.valueOf(props.getProperty("port"));
			notificationSettings = new NotificationSocketSettings(port);
		});

		logger.log(Level.INFO,
				String.format("Starting notifications socket on port %s", notificationSettings.getPort()));
		var serverSocket = new ServerSocket(notificationSettings.getPort());
		while (!shouldStop) {
			var remoteSocket = serverSocket.accept();
			logger.log(Level.INFO, String.format("Accepted client %s", remoteSocket.getInetAddress()));
			new NotificationThread(remoteSocket, policeCheckStepService).start();
		}
	}
}
