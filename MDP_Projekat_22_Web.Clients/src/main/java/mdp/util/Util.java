package mdp.util;

import java.math.BigInteger;
import java.util.UUID;

public class Util {
	public static String getStringUuid() {
		return UUID.randomUUID().toString();
	}

	public static BigInteger getIntUuid() {
		return convertUuidToInt(UUID.randomUUID());
	}

	public static BigInteger convertUuidToInt(UUID uuid) {
		return new BigInteger(uuid.toString().replace("-", ""), 16);
	}
	
	
}
