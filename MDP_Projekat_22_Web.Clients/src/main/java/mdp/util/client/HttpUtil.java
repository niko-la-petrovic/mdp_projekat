package mdp.util.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.swing.JFrame;

import com.google.gson.Gson;

import mdp.util.ui.UiUtil;

public class HttpUtil {
	private static Gson gson = new Gson();

	public static boolean isSuccessStatusCode(int responseStatus) {
		return responseStatus >= 200 && responseStatus < 300;
	}

	public static void sendJson(HttpURLConnection connection, String json) throws IOException {
		try (OutputStream out = connection.getOutputStream();) {
			byte[] jsonBytes = json.getBytes();
			out.write(jsonBytes, 0, jsonBytes.length);
		}
	}

	public static String getJson(HttpURLConnection connection) throws IOException, UnsupportedEncodingException {
		StringBuilder response = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
			String responseLine;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
		}
		return response.toString();
	}

	public static void downloadToFileFromUrl(JFrame frame, File saveDirectoryFile, File saveFile, URL url)
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

	public static void copyInStreamToOut(InputStream inputStream, FileOutputStream out) throws IOException {
		byte[] buf = new byte[8192];
		int length;
		while ((length = inputStream.read(buf)) != -1) {
			out.write(buf, 0, length);
		}
	}
}
