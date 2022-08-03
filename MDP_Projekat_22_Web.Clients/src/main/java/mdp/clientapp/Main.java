package mdp.clientapp;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.xml.rpc.ServiceException;

import com.google.gson.Gson;

import mdp.chat.client.ChatClientSettingsLoader;
import mdp.chat.client.ChatClientSocketSettings;
import mdp.chat.client.ClientThread;
import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.dtos.SearchTerminalDto;
import mdp.exceptions.TerminalNotFoundException;
import mdp.exceptions.UsernameNotFoundException;
import mdp.models.chat.ChatMessage;
import mdp.models.chat.ChatMessageType;
import mdp.register.credentials.CredentialsService;
import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.TerminalRegisterServiceServiceLocator;
import mdp.register.terminals.dtos.GetCustomsTerminalDto;
import mdp.util.client.HttpUtil;
import mdp.util.client.SettingsLoader;
import mdp.util.ui.UiUtil;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	// TODO terminal register service with map of terminal id -> name
	static ClientAppSettings settings;
	static ChatClientSocketSettings chatSettings;
	private static JFrame frame;
	private static final Gson gson = new Gson();

	private static String username;
	private static ChatMessageType messageType;
	private static GetCustomsTerminalDto terminal;
	private static BigInteger passageId;
	private static boolean isCustomsStep;

	private static TerminalRegisterService terminalService;
	private static CredentialsService credentialsService;
	private static JTextField messageTextField;
	private static ClientThread thread;
	private static SSLSocketFactory sf;

	private static JTextPane textPane;

	private static JTextField passageIdTextField;

	private static JCheckBox customsOrPoliceCheckbox;

	private static JComboBox<ChatMessageType> messageTypeComboBox;

	public static void main(String[] args) {
		loadSettings();
		initializeSecurity();
		try {
			terminalService = new TerminalRegisterServiceServiceLocator().getTerminalRegisterService();
		} catch (ServiceException e) {
			UiUtil.showErrorMessage(frame, "Failed to communicate with remote terminal registry service");
			return;
		}

		try {
			credentialsService = new CredentialsService();
		} catch (Exception e1) {
			UiUtil.showErrorMessage(frame, "Failed to initialize credentials service");
			return;
		}
		setupMainFrame();
	}

	private static void initializeSecurity() {
		System.setProperty("javax.net.ssl.trustStore", chatSettings.getTrustStorePath());
		System.setProperty("javax.net.ssl.trustStorePassword", chatSettings.getTrustStorePassword());
	}

	private static boolean searchTerminalAction(JTextField passageIdTextField, JCheckBox customsOrPoliceCheckbox,
			JTextField terminalNameTextField) {
		try {
			BigInteger passageId = new BigInteger(passageIdTextField.getText());
			String terminalName = terminalNameTextField.getText();
			boolean isCustomsPassage = customsOrPoliceCheckbox.isSelected();

			SearchTerminalDto searchDto = new SearchTerminalDto(isCustomsPassage, passageId, terminalName);

			terminal = terminalService.searchTerminal(searchDto);
			if (terminal == null) {
				UiUtil.showErrorMessage(frame, "Specified passage and customs/police step not found");
				return false;
			}

			UiUtil.showInfoMessage(frame, String.format("Terminal '%s' Found", terminalName), "Terminal found");
			return true;
		} catch (NumberFormatException ex) {
			UiUtil.showErrorMessage(frame, "Parsing terminal parameters", "Error In Terminal Parameters",
					"Invalid number format");
		} catch (TerminalNotFoundException e) {
			UiUtil.showErrorMessage(frame, "Couldn't find terminal with specified parameters");
		} catch (RemoteException e) {
			UiUtil.showErrorMessage(frame, "Error occurred during remote communication");
		}
		return false;
	}

	private static void setupMainFrame() {
		frame = new JFrame("Client App");
		UiUtil.setHalfScreenSize(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		JPanel terminalPanel = setupTerminalPanel();
		JScrollPane messageLogsPanel = setupMessageLogsPanel();
		JPanel messageInputPanel = setupMessageInputPanel();

		JMenuBar menuBar = setupMenuBar();
		frame.setJMenuBar(menuBar);
		frame.add(terminalPanel);
		frame.add(messageLogsPanel);
		frame.add(messageInputPanel);
		frame.setVisible(true);
	}

	private static JMenuBar setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu exit = new JMenu("Exit");
		exit.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				handleExitPressed();
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});

		JMenu changePassword = new JMenu("Change Password");
		changePassword.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				handleChangePassword();
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});

		JMenu showPassageLogs = new JMenu("Passage Logs");
		showPassageLogs.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				handleShowPassageLogs();
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});

		menuBar.add(exit);
		menuBar.add(changePassword);
		menuBar.add(showPassageLogs);

		return menuBar;
	}

	private static void handleShowPassageLogs() {
		File saveDirectory = UiUtil.getSaveDirectory(frame);
		if (saveDirectory == null)
			return;

		String fileName = "personLogs.txt";
		Path filePath = Paths.get(saveDirectory.toString(), fileName);
		File file = filePath.toFile();

		URL url;
		try {
			url = new URL(String.format("http://%s/api/persons", Main.settings.getApiHost()));

			HttpUtil.downloadToFileFromUrl(frame, saveDirectory, file, url);
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			UiUtil.showErrorMessage(frame, String.format("Failed to download persons logs: %s", e.getMessage()));
		}
	}

	private static void handleChangePassword() {
		if (username == null || username.equals("")) {
			UiUtil.showErrorMessage(frame, "You must be logged in first");
			return;
		}

		String password = UiUtil.getPassword(frame);
		if (password == null || password.equals("")) {
			UiUtil.showErrorMessage(frame, "Invalid password provided");
			return;
		}

		PutCredentialsDto dto = new PutCredentialsDto(username, password);
		try {
			credentialsService.updateCredentials(dto);
			UiUtil.showInfoMessage(frame, "Successfully changed password", "Password Change");
		} catch (UsernameNotFoundException e) {

			UiUtil.showErrorMessage(frame, String.format("Username '%s' not found", username));
		}
	}

	private static void handleExitPressed() {
		int result = JOptionPane.showConfirmDialog(frame, "Do you wish to close the app?");
		if (result == 0)
			System.exit(0);
	}

	private static JPanel setupMessageInputPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Message Input"));

		JLabel messageLabel = new JLabel("Message");
		messageTextField = new JTextField(40);
		JButton submitMessageButton = new JButton("Send");
		submitMessageButton.addActionListener(e -> handleSendMessage());

		List<ChatMessageType> messageTypesList = new ArrayList<ChatMessageType>(Arrays.asList(ChatMessageType.values()))
				.stream().filter(t -> !t.equals(ChatMessageType.INFO)).collect(Collectors.toList());
		ChatMessageType[] messageTypes = messageTypesList.toArray(new ChatMessageType[messageTypesList.size()]);
		messageTypeComboBox = new JComboBox<>(messageTypes);
		messageTypeComboBox
				.addActionListener(e -> messageType = (ChatMessageType) messageTypeComboBox.getSelectedItem());

		panel.add(messageLabel);
		panel.add(messageTextField);
		panel.add(messageTypeComboBox);
		panel.add(submitMessageButton);

		return panel;
	}

	private static void handleSendMessage() {
		String messageText = messageTextField.getText();
		if (messageText == null || messageText.equals(""))
			return;

		passageId = new BigInteger(passageIdTextField.getText());
		isCustomsStep = customsOrPoliceCheckbox.isSelected();
		messageType = (ChatMessageType) messageTypeComboBox.getSelectedItem();

		ChatMessage chatMessage = new ChatMessage(messageText, username, terminal.getId(), passageId, isCustomsStep,
				messageType);
		thread.sendMessage(chatMessage);
		messageTextField.setText("");
	}

	private synchronized static void addChatMessageToLogs(ChatMessage message) {
		appendToPane(textPane, String.format("[%s] ", message.getTerminalId()), Color.RED);
		appendToPane(textPane, String.format("[%s] ", message.getPassageId()), Color.GREEN);
		appendToPane(textPane, String.format("(%s) ", message.getType()), Color.BLUE);
		appendToPane(textPane, String.format("%s\n", message.getText()), Color.BLACK);
	}

	private static void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		StyledDocument doc = textPane.getStyledDocument();
		try {
			doc.insertString(doc.getEndPosition().getOffset(), msg, aset);
		} catch (BadLocationException e) {
			UiUtil.showErrorMessage(frame, String.format("Failed to show message:", e.getMessage()));
		}
	}

	private static JScrollPane setupMessageLogsPanel() {

		textPane = new JTextPane();
		textPane.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Message Logs"));

		return scrollPane;
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
		JLabel customsOrPoliceLabel = new JLabel("Customs/Police step");
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
			boolean foundTerminal = searchTerminalAction(passageIdTextField, customsOrPoliceCheckbox,
					terminalNameTextField);

			if (!foundTerminal) {
				return;
			}

			if (!attemptLoginAction())
				return;

			try {
				sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
				SSLSocket sockets = (SSLSocket) sf.createSocket(chatSettings.getHost(), chatSettings.getPort());
				thread = new ClientThread(terminal.getId(), new BigInteger(passageIdTextField.getText()),
						customsOrPoliceCheckbox.isSelected(), chatSettings, sockets, m -> addChatMessageToLogs(m));
				thread.setOnDisconnect(() -> {
					UiUtil.showInfoMessage(frame, "Chat Server disconnected", "Chat Server Disconnected");
				});
				thread.start();
				logger.log(Level.INFO, "Initialized ClientThread");
				UiUtil.showInfoMessage(frame, "Connected to chat server", "Chat Server Connection");
			} catch (IOException e1) {
				UiUtil.showErrorMessage(frame, String.format("Failed to initialize ClientThread: %s", e1.getMessage()));
			}
		});

		terminalPanel.add(passageIdPanel);
		terminalPanel.add(customsOrPolicePanel);
		terminalPanel.add(terminalNamePanel);
		terminalPanel.add(terminalSubmitButton);
		return terminalPanel;
	}

	private static boolean attemptLoginAction() {
		String username = UiUtil.getUsername(frame);
		if (username == null || username.length() == 0)
			return false;

		String password = UiUtil.getPassword(frame);
		if (password == null)
			return false;
		PostCredentialsDto dto = new PostCredentialsDto(username, password);

		URL url;
		try {
			url = new URL(String.format("http://%s/api/credentials/login", settings.getApiHost()));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			connection.connect();

			String json = gson.toJson(dto);
			HttpUtil.sendJson(connection, json);

			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				UiUtil.showInfoMessage(frame, "Login successful", "Success");
				Main.username = dto.getUsername();
				return true;
			} else if (responseCode == 401) {
				UiUtil.showErrorMessage(frame, "Invalid credentials");
				return false;
			}
		} catch (MalformedURLException e) {
			UiUtil.showErrorMessage(frame, "Invalid URL");
		} catch (IOException e) {
			UiUtil.showErrorMessage(frame, String.format("Error: %s", e.getMessage()));
		}

		return false;
	}

	private static void loadSettings() {
		Properties props;
		try {
			props = SettingsLoader.getLoadedProperties("clientApp");
			String apiHost = props.getProperty("apiHost");
			Integer rmiPort = Integer.valueOf(props.getProperty("rmiPort"));
			String rmiHost = props.getProperty("rmiHost");
			String policeCheckStepServiceBindingName = props.getProperty("policeCheckStepServiceBindingName");
			String personIdentifyingDocumentsServiceBindingName = props
					.getProperty("personIdentifyingDocumentsServiceBindingName");

			settings = new ClientAppSettings(apiHost, rmiPort, rmiHost, policeCheckStepServiceBindingName,
					personIdentifyingDocumentsServiceBindingName);

			chatSettings = ChatClientSettingsLoader.getSettings();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
