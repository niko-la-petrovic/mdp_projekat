package mdp.adminapp;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mdp.util.Util;
import mdp.util.client.HttpUtil;
import mdp.util.ui.UiUtil;

public class MediaDownloadFrame {
private static final Logger logger = Logger.getLogger(MediaDownloadFrame.class.getName());

	static JFrame frame;

	static void setupFrame() {
		frame = new JFrame("Media Download");
		UiUtil.setHalfScreenSize(frame);

		Container contentPane = frame.getContentPane();
		frame.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.mainFrame.setVisible(true);
			}
		});

		JButton downloadWantedLogsButton = getDownloadWantedLogsButton();

		JButton downloadPersonDocumentsButton = getDownloadPersonDocumentsButton();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Download Options"));
		buttonPanel.setMaximumSize(new Dimension(400, 400));

		buttonPanel.add(downloadWantedLogsButton);
		buttonPanel.add(downloadPersonDocumentsButton);

		contentPane.add(buttonPanel);
	}

	private static JButton getDownloadPersonDocumentsButton() {
		JButton button = new JButton("Person Documents");

		button.addActionListener(e -> {
			String personId = JOptionPane.showInputDialog("Insert Person ID");
			if (personId == null || personId.equals(""))
				return;

			File saveDirectoryFile = getSaveDirectory();
			if (saveDirectoryFile == null)
				return;

			downloadAndSavePersonDocumentsToFile(saveDirectoryFile, personId);
		});

		return button;
	}

	private static void downloadAndSavePersonDocumentsToFile(File saveDirectoryFile, String personId) {
		Path saveDirectoryPath = saveDirectoryFile.toPath();
		Path saveFilePath = Paths.get(saveDirectoryPath.toString(),
				String.format("%s_%s_person.zip", Util.getStringUuid(), personId));
		File saveFile = saveFilePath.toFile();

		URL url;
		try {
			url = new URL(
					String.format("http://%s/api/documents/person/%s", Main.settings.getWantedServerHost(), personId));
			HttpUtil.downloadToFileFromUrl(frame, saveDirectoryFile, saveFile, url);
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, String.format("Invalid URL format: %s", e.getMessage()));
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("IO Exception: %s", e.getMessage()));
		}
	}

	private static JButton getDownloadWantedLogsButton() {
		JButton button = new JButton("Wanted Logs");

		button.addActionListener(evt -> {
			File saveDirectoryFile = getSaveDirectory();
			if (saveDirectoryFile == null)
				return;

			downloadAndSaveWantedLogsToFile(saveDirectoryFile);
		});

		return button;
	}

	private static void downloadAndSaveWantedLogsToFile(File saveDirectoryFile) {
		Path saveDirectoryPath = saveDirectoryFile.toPath();
		Path saveFilePath = Paths.get(saveDirectoryPath.toString(), String.format("%s_logs.xml", Util.getStringUuid()));
		File saveFile = saveFilePath.toFile();

		URL url;
		try {
			url = new URL(String.format("http://%s/api/wanted", Main.settings.getWantedServerHost()));
			HttpUtil.downloadToFileFromUrl(frame, saveDirectoryFile, saveFile, url);
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, String.format("Invalid URL format: %s", e.getMessage()));
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("IO Exception: %s", e.getMessage()));
		}
	}

	private static File getSaveDirectory() {
		return UiUtil.getSaveDirectory(frame);
	}

}
