package mdp.simulation;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.rpc.ServiceException;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.google.gson.Gson;

import mdp.dtos.PostPersonDto;
import mdp.exceptions.PassageNotFoundException;
import mdp.exceptions.TerminalNotFoundException;
import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.TerminalRegisterServiceServiceLocator;
import mdp.register.terminals.dtos.GetCustomsTerminalDto;
import mdp.register.wanted.services.IPersonIdentifyingDocumentsService;
import mdp.register.wanted.services.IPoliceCheckStepService;
import mdp.util.Util;
import mdp.util.client.HttpUtil;
import mdp.util.client.SettingsLoader;
import mdp.util.ui.UiUtil;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	private static final Gson gson = new Gson();

	static SimulationAppSettings settings;

	private static JFrame frame;
	private static TerminalRegisterService terminalService;

	private static GetCustomsTerminalDto terminal;
	private static BigInteger passageId;
	private static boolean isEntry;

	private static JTextField passageIdTextField;

	private static JCheckBox customsOrPoliceCheckbox;

	private static PostPersonDto dto;
	private static boolean isWanted;

	private static IPoliceCheckStepService policeCheckStepService;

	private static boolean isOpen;

	private static IPersonIdentifyingDocumentsService documentsService;

	public static void main(String[] args) {
		loadSettings();

		try {
			loadWantedService();
		} catch (RemoteException | NotBoundException e2) {
			UiUtil.showErrorMessage(frame, String.format("Failed to load wanted service: %s", e2.getMessage()));
			logger.log(Level.SEVERE, String.format("Failed to load wanted service: %s", e2.getMessage()));
			return;
		}

		try {
			loadFileServerService();
		} catch (RemoteException | NotBoundException e1) {
			UiUtil.showErrorMessage(frame, String.format("Failed to load documents service: %s", e1.getMessage()));
			logger.log(Level.SEVERE, String.format("Failed to load documents service: %s", e1.getMessage()));
			return;
		}

		try {
			terminalService = new TerminalRegisterServiceServiceLocator().getTerminalRegisterService();
		} catch (ServiceException e) {
			UiUtil.showErrorMessage(frame, "Failed to communicate with remote terminal registry service");
			logger.log(Level.SEVERE,
					String.format("Failed to communicate with remote terminal registry service: %s", e.getMessage()));
			return;
		}
		setupMainFrame();
	}

	private static void loadFileServerService() throws RemoteException, NotBoundException {
		Registry registry = loadFileServerRegistry();
		documentsService = (IPersonIdentifyingDocumentsService) registry
				.lookup(settings.getPersonIdentifyingDocumentsServiceBindingName());
	}

	private static Registry loadFileServerRegistry() throws RemoteException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Registry registry = LocateRegistry.getRegistry(settings.getFileServerRmiHost(),
				settings.getFileServerRmiPort());
		return registry;
	}

	private static void loadWantedService() throws RemoteException, NotBoundException {
		Registry registry = loadWantedRmiRegistry();
		policeCheckStepService = (IPoliceCheckStepService) registry
				.lookup(settings.getPoliceCheckStepServiceBindingName());
	}

	private static Registry loadWantedRmiRegistry() throws RemoteException {
		System.setProperty("java.security.policy", "client.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Registry registry = LocateRegistry.getRegistry(settings.getWantedRmiHost(), settings.getWantedRmiPort());
		return registry;
	}

	private static void loadSettings() {
		Properties props;
		try {
			props = SettingsLoader.getLoadedProperties("simulationApp");
			String apiHost = props.getProperty("apiHost");
			int wantedRmiPort = Integer.valueOf(props.getProperty("wantedRmiPort"));
			String wantedRmiHost = props.getProperty("wantedRmiHost");
			int fileServerRmiPort = Integer.valueOf(props.getProperty("fileServerRmiPort"));
			String fileServerRmiHost = props.getProperty("fileServerRmiHost");
			String policeCheckStepServiceBindingName = props.getProperty("policeCheckStepServiceBindingName");
			String personIdentifyingDocumentsServiceBindingName = props
					.getProperty("personIdentifyingDocumentsServiceBindingName");

			settings = new SimulationAppSettings(apiHost, wantedRmiPort, wantedRmiHost, fileServerRmiPort,
					fileServerRmiHost, policeCheckStepServiceBindingName, personIdentifyingDocumentsServiceBindingName);

		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("IO Exception: %s", e.getMessage()));
			System.exit(1);
		}
	}

	private static void setupMainFrame() {
		frame = new JFrame("Client App");
		UiUtil.setHalfScreenSize(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		JPanel terminalPanel = setupTerminalPanel();

		frame.add(terminalPanel);
		frame.setVisible(true);
	}

	private static JPanel setupTerminalPanel() {
		JPanel terminalPanel = new JPanel();
		terminalPanel.setBorder(BorderFactory.createTitledBorder("Terminal"));

		JPanel passageIdPanel = new JPanel();
		JLabel passageIdLabel = new JLabel("Terminal Passage Id");
		passageIdTextField = new JTextField(20);
		passageIdPanel.add(passageIdLabel);
		passageIdPanel.add(passageIdTextField);

		JPanel customsOrPolicePanel = new JPanel();
		JLabel customsOrPoliceLabel = new JLabel("Entry/Exit");
		customsOrPoliceCheckbox = new JCheckBox();
		customsOrPolicePanel.add(customsOrPoliceLabel);
		customsOrPolicePanel.add(customsOrPoliceCheckbox);

		JPanel terminalNamePanel = new JPanel();
		JLabel terminalNameLabel = new JLabel("Terminal Name");
		JTextField terminalNameTextField = new JTextField(20);
		terminalNamePanel.add(terminalNameLabel);
		terminalNamePanel.add(terminalNameTextField);

		JButton terminalSubmitButton = new JButton("Submit");
		terminalSubmitButton.addActionListener(e -> {
			if (!isWanted) {
				boolean foundTerminal = searchTerminalAction(passageIdTextField, customsOrPoliceCheckbox,
						terminalNameTextField);

				if (!foundTerminal)
					return;

				dto = getPersonInfo(frame);
				if (dto == null)
					return;

				if (!logPerson(dto))
					return;

				try {
					boolean isWanted = policeCheckStepService.isWanted(dto.getPersonId(), terminal.getId(), passageId);
					Main.isWanted = isWanted;
				} catch (IOException e1) {
					UiUtil.showErrorMessage(frame,
							String.format("Failed to check if person is wanted: %s", e1.getMessage()));
					logger.log(Level.SEVERE,
							String.format("Failed to check if person is wanted - IO Exception: %s", e1.getMessage()));
					return;
				}

				if (isWanted) {
					Main.isWanted = true;
					UiUtil.showInfoMessage(frame, "You were detected as a wanted person.", "Wanted Person Detected");
				}
			}

			try {
				boolean isOpen = policeCheckStepService.isOpenTerminalPassage(terminal.getId(), isEntry, passageId);
				Main.isOpen = isOpen;
			} catch (TerminalNotFoundException e1) {
				UiUtil.showErrorMessage(frame, "Specified terminal was not found");
				logger.log(Level.SEVERE, "Specified terminal was not found");
				return;
			} catch (RemoteException e1) {
				UiUtil.showErrorMessage(frame, String.format(
						"Error occurred while communicating with police check step service: %s", e1.getMessage()));
				logger.log(Level.SEVERE, String.format("RemoteException: %s", e1.getMessage()));
				return;
			} catch (PassageNotFoundException e1) {
				UiUtil.showErrorMessage(frame, "Specified passage was not found");
				logger.log(Level.SEVERE, "The specified passage was not found");
				return;
			}

			if (!isOpen) {
				UiUtil.showInfoMessage(frame, "The terminal will be closed until you are fully processed",
						"Wanted Person Detected");
				logger.log(Level.SEVERE, "The temrinal will be closed until you are processed");
				return;
			}

			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(true);
			chooser.showOpenDialog(frame);
			File[] files = chooser.getSelectedFiles();

			HashMap<String, byte[]> fileMap = new HashMap<>();
			for (File file : files) {
				try {
					byte[] deflatedBytes = Util.getDeflatedBytes(file);
					fileMap.put(file.getName(), deflatedBytes);
				} catch (IOException e1) {
					UiUtil.showErrorMessage(frame,
							String.format("Failed to read file '%s': %s", file.toPath(), e1.getMessage()));
					logger.log(Level.SEVERE, String.format("IO Exception: %s", e1.getMessage()));
				}
			}
			try {
				documentsService.addPersonDocuments(dto.getPersonId(), fileMap);
				UiUtil.showInfoMessage(frame, "You have passed the border", "Success");
			} catch (IOException e1) {
				UiUtil.showErrorMessage(frame, String.format("Failed to upload files: %s", e1.getMessage()));
				logger.log(Level.SEVERE, String.format("IO Exception: %s", e1.getMessage()));
			}
		});

		terminalPanel.add(passageIdPanel);
		terminalPanel.add(customsOrPolicePanel);
		terminalPanel.add(terminalNamePanel);
		terminalPanel.add(terminalSubmitButton);

		return terminalPanel;

	}

	private static boolean logPerson(PostPersonDto dto2) {
		URL url;
		try {
			url = new URL(String.format("http://%s/api/persons", settings.getApiHost()));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();

			String json = gson.toJson(dto);
			HttpUtil.sendJson(connection, json);

			int responseCode = connection.getResponseCode();
			if (!HttpUtil.isSuccessStatusCode(responseCode)) {
				UiUtil.showErrorMessage(frame, String.format("Failed to log person: %s", responseCode));
				return false;
			}

			logger.log(Level.INFO, "Logged passage of person");
			return true;
		} catch (IOException e1) {
			UiUtil.showErrorMessage(frame, String.format("Failed to log person: %s", e1.getMessage()));
			logger.log(Level.SEVERE, String.format("IO Exception: %s", e1.getMessage()));
			return false;
		}
	}

	private static PostPersonDto getPersonInfo(JFrame frame) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel personIdPanel = new JPanel();
		JLabel personIdLabel = new JLabel("Person Id");
		JTextField personIdTextField = new JTextField(40);
		personIdPanel.add(personIdLabel);
		personIdPanel.add(personIdTextField);

		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("Name");
		JTextField nameTextField = new JTextField(20);
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);

		JPanel lastNamePanel = new JPanel();
		JLabel lastNameLabel = new JLabel("Last Name");
		JTextField lastNameTextField = new JTextField(20);
		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameTextField);

		JPanel ssnPanel = new JPanel();
		JLabel ssnLabel = new JLabel("SSN");
		JTextField ssnTextField = new JTextField(20);
		ssnPanel.add(ssnLabel);
		ssnPanel.add(ssnTextField);

		JPanel birthDatePanel = new JPanel();
		JLabel birthDateLabel = new JLabel("Birth Date");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
		JDatePickerImpl birthDatePicker = new JDatePickerImpl(datePanel, new AbstractFormatter() {
			private String datePattern = "yyyy-MM-dd";
			private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			@Override
			public Object stringToValue(String text) throws ParseException {
				return dateFormatter.parseObject(text);
			}

			@Override
			public String valueToString(Object value) throws ParseException {
				if (value != null) {
					Calendar cal = (Calendar) value;
					return dateFormatter.format(cal.getTime());
				}

				return "";
			}
		});
		birthDatePanel.add(birthDateLabel);
		birthDatePanel.add(birthDatePicker);

		JPanel isMalePanel = new JPanel();
		JLabel isMaleLabel = new JLabel("Male/Female");
		JCheckBox isMaleCheckbox = new JCheckBox();
		isMalePanel.add(isMaleLabel);
		isMalePanel.add(isMaleCheckbox);

		panel.add(personIdPanel);
		panel.add(namePanel);
		panel.add(lastNamePanel);
		panel.add(ssnPanel);
		panel.add(birthDatePanel);
		panel.add(isMalePanel);

		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(frame, panel, "Person Info", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (option != 0)
			return null;

		BigInteger personId;
		String name;
		String lastName;
		String ssn;
		String birthDate;
		boolean isMale;
		try {
			personId = new BigInteger(personIdTextField.getText());
			name = nameTextField.getText();
			lastName = lastNameTextField.getText();
			ssn = ssnTextField.getText();
			birthDate = LocalDate.of(model.getYear(), model.getMonth(), model.getDay()).toString();
			isMale = isMaleCheckbox.isSelected();
			PostPersonDto dto = new PostPersonDto(personId, name, lastName, ssn, birthDate, isMale);
			return dto;
		} catch (Exception e) {
			UiUtil.showErrorMessage(frame, String.format("Invalid format: %s", e.getMessage()));
			logger.log(Level.SEVERE, String.format("Exception: %s", e.getMessage()));
			return null;
		}
	}

	private static boolean searchTerminalAction(JTextField passageIdTextField, JCheckBox isEntryCheckBox,
			JTextField terminalNameTextField) {
		try {
			BigInteger passageId = new BigInteger(passageIdTextField.getText());
			String terminalName = terminalNameTextField.getText();
			boolean isEntry = isEntryCheckBox.isSelected();

			Main.passageId = passageId;
			Main.isEntry = isEntry;

			terminal = terminalService.searchTerminalSimulation(terminalName, passageId, isEntry);
			if (terminal == null) {
				UiUtil.showErrorMessage(frame, "Specified passage and customs/police step not found");
				return false;
			}

			UiUtil.showInfoMessage(frame, String.format("Terminal '%s' Found", terminalName), "Terminal found");
			return true;
		} catch (NumberFormatException ex) {
			UiUtil.showErrorMessage(frame, "Parsing terminal parameters", "Error In Terminal Parameters",
					"Invalid number format");
			logger.log(Level.SEVERE, String.format("NumberFormatException: %s", ex.getMessage()));
		} catch (TerminalNotFoundException e) {
			UiUtil.showErrorMessage(frame, "Couldn't find terminal with specified parameters");
			logger.log(Level.SEVERE, "Couldn't find terminal with specified parameters");
		} catch (RemoteException e) {
			UiUtil.showErrorMessage(frame, "Error occurred during remote communication");
			logger.log(Level.SEVERE, String.format("Remote exception: %s", e.getMessage()));
		}
		return false;
	}
}
