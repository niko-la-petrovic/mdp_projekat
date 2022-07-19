package mdp.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

import mdp.db.redis.JedisConnectionPool;
import mdp.models.Passenger;
import mdp.register.credentials.CredentialsController;
import mdp.register.credentials.CredentialsService;
import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.test.client.TestSoapServiceService;
import mdp.util.PasswordUtil;
import mdp.util.Serialization;
import mdp.util.Util;

public class Main {

	private static final String serializationTestDir = "I:\\downloads\\mdp_data\\test";

	public static void main(String[] args) throws Exception {
		// testUuid();

		// testSerialization();

//		testRedis();
//		testPassword();
//		testSoap();
		testTerminalRegisterService();
	}

	private static void testTerminalRegisterService() throws Exception {
		var service = new TerminalRegisterService();
		service.createTerminal(new CreateTerminalDto("test", 2, 3));
	}

	private static void testSoap() {
		var testService = new TestSoapServiceService().getTestSoapService();
		var result = testService.result();
		System.out.println(result);
	}

	private static void testPassword() {
		var pwd = "testpwd";
		var hash = PasswordUtil.hashPassword(pwd);
		System.out.println(hash);
		if (!PasswordUtil.checkPassword(pwd, hash))
			System.err.println("Failed password check");
		System.out.println("Password matches");
	}

	private static void testRedis() throws Exception {
		var jedis = JedisConnectionPool.getConnection();
		jedis.set("key", "value");

		var value = jedis.get("key");
		System.out.println(value);
	}

	private static void testSerialization() throws Exception {
		System.out.println(Serialization.getRandomSerializationType());
		Serialization.randomSerializeToFile(new Passenger(BigInteger.valueOf(123)), serializationTestDir, "passenger",
				null);

		var gsonPassenger = Serialization.randomDeserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\GSON_89020598-b20e-4ac8-a4b9-f4930bb7f2c0_passenger.json",
				Passenger.class);

		var kryoPassenger = Serialization.randomDeserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\KRYO_884e3c74-ab78-41cd-afdf-5a5722365a58_passenger - Copy.bin",
				Passenger.class);

		var xmlPassenger = Serialization.randomDeserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\XML_ffaab0e1-c71a-420a-8be5-4cb5b65cd789_passenger.xml",
				Passenger.class);

		var yamlPassenger = Serialization.randomDeserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\YAML_36dd8465-bf17-41c5-9eee-5e69a0143382_passenger.yaml",
				Passenger.class);
	}

	private static void testUuid() {
		System.out.println(mdp.util.Util.getStringUuid());
		String strUuid = "f2c53aa4-772e-49e9-a067-3c14dd2333dd";
		UUID uuid = UUID.fromString(strUuid);
		System.out.println(uuid);
		System.out.println(Util.convertUuidToInt(uuid));
	}
}
