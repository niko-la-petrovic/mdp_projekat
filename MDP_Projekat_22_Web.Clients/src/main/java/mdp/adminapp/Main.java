package mdp.adminapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.rpc.ServiceException;

import mdp.util.client.SettingsLoader;
import mdp.util.ui.UiUtil;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	static JFrame mainFrame;
	public static Toolkit toolkit;
	public static Dimension screenSize;
	public static AdminAppSettings settings;

	public static void main(String[] args) {
		try {
			SettingsLoader.loadSettings("adminApp", props -> {
				String notificationSocketHost = props.getProperty("notificationSocketHost");
				int notificationSocketPort = Integer.valueOf(props.getProperty("notificationSocketPort"));
				String wantedServerHost = props.getProperty("wantedServerHost");
				String credentialsServerHost = props.getProperty("credentialsServerHost");

				settings = new AdminAppSettings(notificationSocketHost, notificationSocketPort, wantedServerHost,
						credentialsServerHost);
			});
		} catch (IOException e) {
			UiUtil.showErrorMessage(mainFrame, String.format("Failed to load settings: %s", e.getMessage()));
			logger.log(Level.SEVERE, String.format("Failed to load settings: %s", e.getMessage()));
			return;
		}

		mainFrame = new JFrame("Admin App");
		setupMainFrame(mainFrame);
		mainFrame.setVisible(true);
	}

	private static JButton getTerminalAdministrationButton() {
		JButton terminalAdministrationButtton = new JButton("Terminals");

		terminalAdministrationButtton.addActionListener(e -> {
			if (TerminalFrame.terminalFrame == null) {
				try {
					TerminalFrame.setupTerminalFrame();
				} catch (OperationNotSupportedException | IOException | ServiceException e1) {
					UiUtil.showErrorMessage(mainFrame, "Initialize terminal frame", "Error Initializing Terminal Frame",
							e1.getMessage());
					logger.log(Level.SEVERE, String.format("Error Initializing Terminal Frame: %s", e1.getMessage()));
				}
			}

			TerminalFrame.terminalFrame.setVisible(true);
			mainFrame.setVisible(false);
		});

		return terminalAdministrationButtton;
	}

	private static void setupMainFrame(JFrame mainFrame) {
		UiUtil.setHalfScreenSize(mainFrame);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		buttonPanel.setMaximumSize(new Dimension(400, 400));

		JButton terminalAdministrationButtton = getTerminalAdministrationButton();
		JButton mediaDownloadButton = getMediaDownloadButton();
		JButton credentialsAdminButton = getCredentialsAdminButton();

		buttonPanel.add(terminalAdministrationButtton);
		buttonPanel.add(mediaDownloadButton);
		buttonPanel.add(credentialsAdminButton);

		mainFrame.getContentPane().add(buttonPanel);
	}

	private static JButton getCredentialsAdminButton() {
		JButton button = new JButton("Credentials");

		button.addActionListener(e -> handleCredentialsAdminButtonPress());

		return button;
	}

	private static void handleCredentialsAdminButtonPress() {
		if (CredentialsFrame.frame == null) {
			CredentialsFrame.setupCredentialsFrame();
		}

		CredentialsFrame.frame.setVisible(true);
		mainFrame.setVisible(false);
	}

	private static JButton getMediaDownloadButton() {
		JButton button = new JButton("Media Download");

		button.addActionListener(e -> handleMediaDownloadButtonPress());

		return button;
	}

	private static void handleMediaDownloadButtonPress() {
		if (MediaDownloadFrame.frame == null) {
			MediaDownloadFrame.setupFrame();
		}

		MediaDownloadFrame.frame.setVisible(true);
		mainFrame.setVisible(false);
	}
}
