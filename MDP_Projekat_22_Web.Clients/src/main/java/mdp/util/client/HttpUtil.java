package mdp.util.client;

public class HttpUtil {

	public static boolean isSuccessStatusCode(int responseStatus) {
		return responseStatus >= 200 && responseStatus < 300;
	}

}