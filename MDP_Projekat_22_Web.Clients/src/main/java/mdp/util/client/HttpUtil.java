package mdp.util.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import com.google.gson.Gson;

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
}
