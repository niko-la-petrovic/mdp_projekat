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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mdp.util.Util;
import mdp.util.ui.UiUtil;

public class MediaDownloadFrame {
	static JFrame frame;

	static void setupTerminalFrame() {
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
			downloadToFileFromUrl(saveDirectoryFile, saveFile, url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			downloadToFileFromUrl(saveDirectoryFile, saveFile, url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void downloadToFileFromUrl(File saveDirectoryFile, File saveFile, URL url)
			throws IOException, ProtocolException, FileNotFoundException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		connection.setRequestProperty("Content-Type", "application/octet-stream");

		connection.connect();
		int responseCode = connection.getResponseCode();
		if (!mdp.util.client.HttpUtil.isSuccessStatusCode(responseCode)) {
			String errorMessage = connection.getResponseMessage();
			if (errorMessage == null || errorMessage.equals("null"))
				if (responseCode == 404)
					errorMessage = "Files not found";
				else
					errorMessage = "Generic error";

			UiUtil.showErrorMessage(frame, "Download failed", "Download failed", errorMessage);
			return;
		}

		try (InputStream inputStream = connection.getInputStream();
				FileOutputStream out = new FileOutputStream(saveFile)) {
			copyInStreamToOut(inputStream, out);
		} catch (FileNotFoundException ex) {

		}

		UiUtil.showInfoMessage(frame,
				String.format("Successfully saved %s in %s", saveFile.getName(), saveDirectoryFile),
				"Save File Success");
	}

	private static void copyInStreamToOut(InputStream inputStream, FileOutputStream out) throws IOException {
		byte[] buf = new byte[8192];
		int length;
		while ((length = inputStream.read(buf)) != -1) {
			out.write(buf, 0, length);
		}
	}

	private static File getSaveDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Select save directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
			return fileChooser.getSelectedFile();
		else
			return null;
	}

}
