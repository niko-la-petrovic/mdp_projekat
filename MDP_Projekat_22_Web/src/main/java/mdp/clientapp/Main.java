package mdp.clientapp;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import mdp.register.terminals.client.SearchTerminalDto;
import mdp.register.terminals.client.TerminalNotFoundException_Exception;
import mdp.register.terminals.client.TerminalRegisterServiceService;
import mdp.util.SettingsLoader;
import mdp.util.ui.UiUtil;

public class Main {
	private static ClientAppSettings settings;

	// TODO unnecessary throws declarations
	public static void main(String[] args) throws Exception {
		loadSettings();
		var mainFrame = new JFrame("Client App");
		setupMainFrame(mainFrame);
	}

	private static void searchTerminalAction(JTextField passageIdTextField, JCheckBox customsOrPoliceCheckbox,
			JTextField terminalNameTextField) {
		try {
			var passageId = new BigInteger(passageIdTextField.getText());
			var terminalName = terminalNameTextField.getText();
			var isCustomsPassage = customsOrPoliceCheckbox.isSelected();

			var searchDto = new SearchTerminalDto();
			searchDto.setPassageId(passageId);
			searchDto.setTerminalName(terminalName);
			searchDto.setCustomsPassage(isCustomsPassage);

			var terminalClient = new TerminalRegisterServiceService().getTerminalRegisterService();
			var searchResult = terminalClient.searchTerminal(searchDto);

			var clientConfig = new ClientConfig();
			var credentialsClient = ClientBuilder.newClient(clientConfig);
			var credentialsTarget = credentialsClient.target(settings.getApiHost()).path("api").path("credentials")
					.path("login");

			var invocationBuilder = credentialsTarget.request(MediaType.APPLICATION_JSON);
			var response = invocationBuilder.method("POST", Entity.json(searchDto));

			// TODO check if response is 2xx
			// var responseObject = response.readEntity();
			// if (response == null)
			// System.err.println();

		} catch (NumberFormatException ex) {
			// TODO ex
			ex.printStackTrace();
			// TODO dialog error
		} catch (TerminalNotFoundException_Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void setupMainFrame(JFrame mainFrame) {
		UiUtil.setHalfScreenSize(mainFrame);

		var terminalPanel = setupTerminalPanel();
		mainFrame.add(terminalPanel);
		mainFrame.setVisible(true);
	}

	private static JPanel setupTerminalPanel() {
		var terminalPanel = new JPanel();
		terminalPanel.setBorder(BorderFactory.createTitledBorder("Terminal"));
		terminalPanel.setMaximumSize(new Dimension(400, 400));

		var passageIdPanel = new JPanel();
		var passageIdLabel = new JLabel("Terminal Passage Id");
		var passageIdTextField = new JTextField(20);
		passageIdPanel.add(passageIdLabel);
		passageIdPanel.add(passageIdTextField);

		var customsOrPolicePanel = new JPanel();
		var customsOrPoliceLabel = new JLabel("Customs/Police step");
		var customsOrPoliceCheckbox = new JCheckBox();
		customsOrPolicePanel.add(customsOrPoliceLabel);
		customsOrPolicePanel.add(customsOrPoliceCheckbox);

		var terminalNamePanel = new JPanel();
		var terminalNameLabel = new JLabel("Terminal Name");
		var terminalNameTextField = new JTextField(20);
		terminalNamePanel.add(terminalNameLabel);
		terminalNamePanel.add(terminalNameTextField);

		var terminalSubmitButton = new JButton("Submit");
		terminalSubmitButton.addActionListener(
				e -> searchTerminalAction(passageIdTextField, customsOrPoliceCheckbox, terminalNameTextField));

		terminalPanel.add(passageIdPanel);
		terminalPanel.add(customsOrPolicePanel);
		terminalPanel.add(terminalNamePanel);
		terminalPanel.add(terminalSubmitButton);
		return terminalPanel;
	}

	private static void loadSettings() throws IOException, FileNotFoundException {
		var props = SettingsLoader.getLoadedProperties("clientApp");
		var apiHost = props.getProperty("apiHost");
		var rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
		var rmiHost = props.getProperty("rmiHost");
		var policeCheckStepServiceBindingName = props.getProperty("policeCheckStepServiceBindingName");
		var personIdentifyingDocumentsServiceBindingName = props
				.getProperty("personIdentifyingDocumentsServiceBindingName");
		settings = new ClientAppSettings(apiHost, rmiPort, rmiHost, policeCheckStepServiceBindingName,
				personIdentifyingDocumentsServiceBindingName);
	}
}
