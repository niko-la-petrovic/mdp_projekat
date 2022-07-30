package mdp.clientapp;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mdp.util.SettingsLoader;
import mdp.util.ui.UiUtil;

public class Main {
	private static ClientAppSettings settings;

	// TODO unnecessary throws declarations
	public static void main(String[] args) throws Exception {
		loadSettings();
		JFrame mainFrame = new JFrame("Client App");
		setupMainFrame(mainFrame);
	}

//	private static void searchTerminalAction(JTextField passageIdTextField, JCheckBox customsOrPoliceCheckbox,
////			JTextField terminalNameTextField) throws ServiceException, TerminalNotFoundException {
//		try {
//			var passageId = new BigInteger(passageIdTextField.getText());
//			var terminalName = terminalNameTextField.getText();
//			var isCustomsPassage = customsOrPoliceCheckbox.isSelected();
//
//			var searchDto = new SearchTerminalDto();
//			searchDto.setPassageId(passageId);
//			searchDto.setTerminalName(terminalName);
//			searchDto.setCustomsPassage(isCustomsPassage);
//
//			var terminalClient = new TerminalRegisterServiceServiceLocator().getTerminalRegisterService();
//			var searchResult = terminalClient.searchTerminal(searchDto);
//
//			var clientConfig = new ClientConfig();
//			var credentialsClient = ClientBuilder.newClient(clientConfig);
//			var credentialsTarget = credentialsClient.target(settings.getApiHost()).path("api").path("credentials")
//					.path("login");
//
//			var invocationBuilder = credentialsTarget.request(MediaType.APPLICATION_JSON);
//			var response = invocationBuilder.method("POST", Entity.json(searchDto));
//
//			// TODO check if response is 2xx
//			// var responseObject = response.readEntity();
//			// if (response == null)
//			// System.err.println();
//
//		} catch (NumberFormatException ex) {
//			// TODO ex
//			ex.printStackTrace();
//			// TODO dialog error
//		}
//	}

	private static void setupMainFrame(JFrame mainFrame) {
		UiUtil.setHalfScreenSize(mainFrame);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel terminalPanel = setupTerminalPanel();
		mainFrame.add(terminalPanel);
		mainFrame.setVisible(true);
	}

	private static JPanel setupTerminalPanel() {
		JPanel terminalPanel = new JPanel();
		terminalPanel.setBorder(BorderFactory.createTitledBorder("Terminal"));
		terminalPanel.setMaximumSize(new Dimension(400, 400));

		JPanel passageIdPanel = new JPanel();
		JLabel passageIdLabel = new JLabel("Terminal Passage Id");
		JTextField passageIdTextField = new JTextField(20);
		passageIdPanel.add(passageIdLabel);
		passageIdPanel.add(passageIdTextField);

		JPanel customsOrPolicePanel = new JPanel();
		JLabel customsOrPoliceLabel = new JLabel("Customs/Police step");
		JCheckBox customsOrPoliceCheckbox = new JCheckBox();
		customsOrPolicePanel.add(customsOrPoliceLabel);
		customsOrPolicePanel.add(customsOrPoliceCheckbox);

		JPanel terminalNamePanel = new JPanel();
		JLabel terminalNameLabel = new JLabel("Terminal Name");
		JTextField terminalNameTextField = new JTextField(20);
		terminalNamePanel.add(terminalNameLabel);
		terminalNamePanel.add(terminalNameTextField);

		JButton terminalSubmitButton = new JButton("Submit");
		terminalSubmitButton.addActionListener(e -> {
//				searchTerminalAction(passageIdTextField, customsOrPoliceCheckbox, terminalNameTextField);
		});

		terminalPanel.add(passageIdPanel);
		terminalPanel.add(customsOrPolicePanel);
		terminalPanel.add(terminalNamePanel);
		terminalPanel.add(terminalSubmitButton);
		return terminalPanel;
	}

	private static void loadSettings() throws IOException, FileNotFoundException {
		Properties props = SettingsLoader.getLoadedProperties("clientApp");
		String apiHost = props.getProperty("apiHost");
		Integer rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
		String rmiHost = props.getProperty("rmiHost");
		String policeCheckStepServiceBindingName = props.getProperty("policeCheckStepServiceBindingName");
		String personIdentifyingDocumentsServiceBindingName = props
				.getProperty("personIdentifyingDocumentsServiceBindingName");
		settings = new ClientAppSettings(apiHost, rmiPort, rmiHost, policeCheckStepServiceBindingName,
				personIdentifyingDocumentsServiceBindingName);
	}
}
