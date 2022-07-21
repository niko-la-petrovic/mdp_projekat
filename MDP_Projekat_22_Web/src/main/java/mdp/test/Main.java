package mdp.test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mdp.db.redis.JedisConnectionPool;
import mdp.dtos.SearchTerminalDto;
import mdp.exceptions.TerminalNameTakenException;
import mdp.models.CustomsPassage;
import mdp.models.CustomsTerminal;
import mdp.models.Passenger;
import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.register.terminals.dtos.UpdateTerminalDto;
import mdp.test.client.TestSoapServiceService;
import mdp.util.PasswordUtil;
import mdp.util.Serialization;
import mdp.util.SerializationType;
import mdp.util.Util;

public class Main {

	private static final String serializationTestDir = "I:\\downloads\\mdp_data\\test";

	public static void main(String[] args) throws Exception {
		// testUuid();

//		testSerialization();

//		testRedis();
//		testPassword();
//		testSoap();
		testTerminalRegisterService();
	}

	private static void testTerminalRegisterService() throws Exception {
		var service = new TerminalRegisterService();
		BigInteger terminalIdToDelete = null;
		for (int i = 0; i < 6; i++) {
			String terminalName = "test" + i;
			try {
				var getTerminalDto = service.createTerminal(new CreateTerminalDto(terminalName, 2, 3));
				if (i == 2)
					terminalIdToDelete = getTerminalDto.getId();
			} catch (TerminalNameTakenException e) {
				System.err.println("Terminal name taken:" + terminalName);
			}
		}

		var terminals = service.getTerminals();
//		if (terminalIdToDelete != null)
//			service.deleteTerminal(terminalIdToDelete);

		var foundTerminal = service.searchTerminal(
				new SearchTerminalDto(new BigInteger("22999505657271045070099198464351983149"), "test0", true));

		var foundTerminals = service.getTerminalsStartingWithName("test");

		try {
			for (int i = 0; i < 3; i++) {
				service.updateTerminal(new UpdateTerminalDto(new BigInteger("250284490837732570918661700119854885447"),
						"test4", 4, 0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
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

		var gsonPassenger = Serialization.deserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\GSON_89020598-b20e-4ac8-a4b9-f4930bb7f2c0_passenger.json",
				Passenger.class);

		var kryoPassenger = Serialization.deserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\KRYO_884e3c74-ab78-41cd-afdf-5a5722365a58_passenger - Copy.bin",
				Passenger.class);

		var xmlPassenger = Serialization.deserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\XML_ffaab0e1-c71a-420a-8be5-4cb5b65cd789_passenger.xml",
				Passenger.class);

		var yamlPassenger = Serialization.deserializeFromFile(
				"I:\\downloads\\mdp_data\\test\\YAML_36dd8465-bf17-41c5-9eee-5e69a0143382_passenger.yaml",
				Passenger.class);

		var entries = new CustomsPassage[] {
				new CustomsPassage(Util.getIntUuid(), true, true, CustomsPassage.customsPassageSteps()) };
		var exits = new CustomsPassage[] {
				new CustomsPassage(Util.getIntUuid(), true, false, CustomsPassage.customsPassageSteps()) };

		var passages = Stream.concat(Arrays.asList(entries).stream(), Arrays.asList(exits).stream())
				.toArray(CustomsPassage[]::new);
		var customsTerminal = new CustomsTerminal(passages, Util.getIntUuid(), "name");

		Serialization.serializeToFile(customsTerminal, SerializationType.GSON, serializationTestDir, "terminal", null);
		System.out.println("Terminal GSON");
		Serialization.serializeToFile(customsTerminal, SerializationType.KRYO, serializationTestDir, "terminal", null);
		System.out.println("Terminal KRYO");
		Serialization.serializeToFile(customsTerminal, SerializationType.YAML, serializationTestDir, "terminal", null);
		System.out.println("Terminal YAML");
		Serialization.serializeToFile(customsTerminal, SerializationType.XML, serializationTestDir, "terminal", null);
		System.out.println("Terminal XML");
		System.out.println();

	}

	private static void testUuid() {
		System.out.println(mdp.util.Util.getStringUuid());
		String strUuid = "f2c53aa4-772e-49e9-a067-3c14dd2333dd";
		UUID uuid = UUID.fromString(strUuid);
		System.out.println(uuid);
		System.out.println(Util.convertUuidToInt(uuid));
	}
}
