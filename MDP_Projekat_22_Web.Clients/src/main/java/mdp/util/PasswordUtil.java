package mdp.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
	private static int logRounds = 15;

	public static String hashPassword(String password) {
		String salt = BCrypt.gensalt(logRounds);
		String hashed_password = BCrypt.hashpw(password, salt);

		return hashed_password;
	}

	public static boolean checkPassword(String password, String hash) {
		boolean hashMatches = false;

		if (hash == null || !hash.startsWith("$2a$"))
			throw new UnsupportedOperationException();

		hashMatches = BCrypt.checkpw(password, hash);

		return hashMatches;
	}
}