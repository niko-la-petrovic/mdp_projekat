package mdp.adminapp;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.intellectualsites.http.HttpClient;

import mdp.util.Util;
import mdp.util.ui.UiUtil;

public class MediaDownloadFrame {
	static JFrame frame;

	static void setupTerminalFrame() {
		frame = new JFrame("Media Download");
		UiUtil.setHalfScreenSize(frame);

		Container contentPane = frame.getContentPane();
		frame.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JButton downloadWantedLogsButton = getDownloadWantedLogsButton();

		JButton downloadPersonDocumentsButton = new JButton("Person Documents");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Download Options"));
		buttonPanel.setMaximumSize(new Dimension(400, 400));
//		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		buttonPanel.add(downloadWantedLogsButton);
		buttonPanel.add(downloadWantedLogsButton);

		contentPane.add(buttonPanel);
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
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			connection.setRequestProperty("Content-Type", "application/octet-stream");

			connection.connect();
			int responseCode = connection.getResponseCode();
			try (InputStream inputStream = connection.getInputStream();
					FileOutputStream out = new FileOutputStream(saveFile)) {
				byte[] buf = new byte[8192];
				int length;
				while ((length = inputStream.read(buf)) != -1) {
					out.write(buf, 0, length);
				}
			}

			UiUtil.showInfoMessage(frame,
					String.format("Successfully saved %s in %s", saveFile.getName(), saveDirectoryFile),
					"Save File Success");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		connection.inpu

		// try {

//				Client httpClient = ClientBuilder.newClient();
//				WebTarget httpTarget = httpClient.target(Main.settings.getWantedServerHost()).path("api")
//						.path("wanted");
//
//				Builder invocationBuilder = httpTarget.request(MediaType.APPLICATION_OCTET_STREAM);
//				Response response = invocationBuilder.method("GET");
//
//				int responseStatus = response.getStatus();
//				if (!isSuccessStatusCode(responseStatus))
//					UiUtil.showErrorMessage(frame, "Error downloading wanted logs", "Wanted Logs Error",
//							response.getStatusInfo().toString());
//
//				try (InputStream inStream = response.readEntity(InputStream.class)) {
//					System.out.println();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
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

	private static boolean isSuccessStatusCode(int responseStatus) {
		return responseStatus >= 200 && responseStatus < 300;
	}

}
