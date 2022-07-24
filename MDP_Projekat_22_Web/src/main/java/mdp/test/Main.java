package mdp.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP.BasicProperties;

import mdp.chat.client.ChatClientSettingsLoader;
import mdp.chat.dtos.SocketMessage;
import mdp.chat.dtos.SocketMessageType;
import mdp.db.redis.JedisConnectionPool;
import mdp.dtos.SearchTerminalDto;
import mdp.exceptions.TerminalNameTakenException;
import mdp.models.CustomsPassage;
import mdp.models.CustomsTerminal;
import mdp.models.Passenger;
import mdp.models.chat.ChatMessage;
import mdp.models.chat.ChatMessageType;
import mdp.mq.rabbitmq.ConnectionPool;
import mdp.mq.rabbitmq.Constants;
import mdp.register.terminals.TerminalRegisterService;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.register.terminals.dtos.UpdateTerminalDto;
import mdp.test.client.TestSoapServiceService;
import mdp.util.PasswordUtil;
import mdp.util.Serialization;
import mdp.util.SerializationType;
import mdp.util.Util;

public class Main {

	private static final boolean passageStep1 = true;
	private static final String QNAME3 = "qname3";
	private static final String QNAME2 = "qname2";
	private static final String QNAME1 = "qname1";
	private static final String terminalId1 = "123";
	private static final String terminalId2 = "1234";
	private static final String terminalId3 = "1235";
	private static final String passageId1 = "456";
	private static final String passageId2 = "567";
	private static final String passageId3 = "5678";

	private static final String serializationTestDir = "I:\\downloads\\mdp_data\\test";

	public static void main(String[] args) throws Exception {
		// testUuid();

//		testSerialization();

//		testRedis();
//		testPassword();
//		testSoap();
//		testTerminalRegisterService();
//		testRabbitMessageQueue();
		testChatServer();
	}

	private static void testChatServer() throws FileNotFoundException, IOException, TimeoutException {

		var startServerThread = mdp.chat.server.Main.startServer();
		startServerThread.start();

		try {
			startServerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		var gson = new Gson();

		var clientSettings = ChatClientSettingsLoader.getSettings();
		System.setProperty("javax.net.ssl.trustStore", clientSettings.getTrustStorePath());
		System.setProperty("javax.net.ssl.trustStorePassword", clientSettings.getTrustStorePassword());

		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket s = (SSLSocket) sf.createSocket(clientSettings.getHost(), clientSettings.getPort());

//		s.setSoTimeout(10000);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

		var establishmentMessage = new SocketMessage(SocketMessageType.ESTABLISHMENT_MESSAGE, new ChatMessage(null,
				"user1", new BigInteger("123"), new BigInteger("456"), true, ChatMessageType.INFO), null, null);
		var establishmentMessageJson = gson.toJson(establishmentMessage);
		System.out.println("Sending: " + establishmentMessageJson);
		out.println(establishmentMessageJson);

		System.out.println();
		boolean shouldFinish = false;
		while (!shouldFinish) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Client finished");
	}

	private static void testPassword() {
		var pwd = "testpwd";
		var hash = PasswordUtil.hashPassword(pwd);
		System.out.println(hash);
		if (!PasswordUtil.checkPassword(pwd, hash))
			System.err.println("Failed password check");
		System.out.println("Password matches");
	}

	private static void testRabbitMessageQueue()
			throws FileNotFoundException, IOException, TimeoutException, InterruptedException {
		try (var connection = ConnectionPool.getConnection()) {
			var channel = connection.createChannel();
			System.out.println();

			var broadcastDeclareResult = channel.exchangeDeclare(Constants.BROADCAST_EXCHANGE_NAME,
					BuiltinExchangeType.FANOUT, true);
			var topicDeclareResult = channel.exchangeDeclare(Constants.TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC,
					true);

			var q1DeclareResult = channel.queueDeclare(QNAME1, true, false, false, null);
			var q2DeclareResult = channel.queueDeclare(QNAME2, true, false, false, null);
			var q3DeclareResult = channel.queueDeclare(QNAME3, true, false, false, null);

			var bk1 = Constants.getTerminalBindingKey(terminalId1);
			var bk2 = Constants.getTerminalBindingKey(terminalId2);
			var bk3 = Constants.getTerminalBindingKey(terminalId3);

			var qbb1 = channel.queueBind(QNAME1, Constants.BROADCAST_EXCHANGE_NAME, "");
			var qbb2 = channel.queueBind(QNAME2, Constants.BROADCAST_EXCHANGE_NAME, "");
			var qbb3 = channel.queueBind(QNAME3, Constants.BROADCAST_EXCHANGE_NAME, "");

			var qbt1 = channel.queueBind(QNAME1, Constants.TOPIC_EXCHANGE_NAME, bk1);
			var qbt2 = channel.queueBind(QNAME2, Constants.TOPIC_EXCHANGE_NAME, bk2);
			var qbt3 = channel.queueBind(QNAME3, Constants.TOPIC_EXCHANGE_NAME, bk3);

			channel.queuePurge(QNAME1);
			channel.queuePurge(QNAME2);
			channel.queuePurge(QNAME3);

			var message1 = "Message 1 text";
			channel.basicPublish(Constants.BROADCAST_EXCHANGE_NAME, "", null, message1.getBytes());
			var topicMessageRoutingKey = Constants.getTopicMessageRoutingKey(terminalId1, passageId1, passageStep1);
			channel.basicPublish(Constants.TOPIC_EXCHANGE_NAME, topicMessageRoutingKey, null, message1.getBytes());

			setConsumer(channel, QNAME1);
			setConsumer(channel, QNAME2);
			setConsumer(channel, QNAME3);
			Thread.currentThread().join();
			System.out.println("Finished thread join");
		}
	}

	private static void setConsumer(Channel channel, String queueName) throws IOException {
		var consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				var content = new String(body);
				System.out.println(String.format("[%s](%s) %s", envelope.getExchange(), queueName, content));
			}
		};
		channel.basicConsume(queueName, consumer);
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

	private static void testSoap() {
		var testService = new TestSoapServiceService().getTestSoapService();
		var result = testService.result();
		System.out.println(result);
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

	private static void testUuid() {
		System.out.println(mdp.util.Util.getStringUuid());
		String strUuid = "f2c53aa4-772e-49e9-a067-3c14dd2333dd";
		UUID uuid = UUID.fromString(strUuid);
		System.out.println(uuid);
		System.out.println(Util.convertUuidToInt(uuid));
	}
}
