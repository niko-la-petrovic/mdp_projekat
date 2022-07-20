package mdp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
//import java.util.Random;
import java.util.Random;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.io.FilenameUtils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.Gson;

public class Serialization {
	private static Random random = new Random();
	private static final Gson gson = new Gson();
	private static final YAMLMapper yamlMapper = new YAMLMapper();
	private static final Kryo kryo = new Kryo();
	private static final XmlMapper xmlMapper = new XmlMapper();

	public static SerializationType getRandomSerializationType() {
		var values = SerializationType.values();
		return values[random.nextInt(values.length)];
	}

	public static void randomSerializeToFile(Serializable obj, String folderPath, String fileNamePartial,
			String extension) throws IOException, Exception {
		var serializationType = getRandomSerializationType();
		serializeToFile(obj, serializationType, folderPath, fileNamePartial, extension);
	}

	public static void serializeToFile(Serializable obj, SerializationType serializationType, String folderPath,
			String fileNamePartial, String extension) throws IOException, Exception {

		var isSuppliedExtension = extension != null;
		var id = Util.getStringUuid();

		var fileName = serializationType.toString() + "_" + id + "_" + fileNamePartial;

		if (!isSuppliedExtension)
			switch (serializationType) {
			case GSON:
				extension = ".json";
				break;
			case KRYO:
				extension = ".bin";
				break;
			case XML:
				extension = ".xml";
				break;
			case YAML:
				extension = ".yaml";
				break;
			}
		else
			extension = ".bin";

		var filePath = Paths.get(folderPath, fileName + extension);
		var file = filePath.toFile();

		switch (serializationType) {
		case GSON:
			var json = gson.toJson(obj);
			Files.write(filePath, json.getBytes());
			break;
		case YAML:
			yamlMapper.writeValue(file, obj);
			break;
		case KRYO:
			try (var output = new Output(new FileOutputStream(file))) {
				kryo.setRegistrationRequired(false);
				kryo.writeClassAndObject(output, obj);
				output.flush();
				output.close();
			}
			break;
		case XML:
			xmlMapper.writeValue(file, obj);
			break;
		default:
			throw new OperationNotSupportedException();
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> T deserializeFromFile(String filePath, Class<T> type)
			throws IOException, OperationNotSupportedException {

		File file = new File(filePath);
		var fileName = FilenameUtils.removeExtension(file.toPath().getFileName().toString());

		var serializationTypeStr = fileName.split("_")[0];
		SerializationType serializationType = Arrays.asList(SerializationType.values()).stream()
				.filter(t -> t.toString().equals(serializationTypeStr)).findFirst().orElse(null);

		switch (serializationType) {
		case GSON:
			var gson = new Gson();

			var jsonStr = Files.readString(file.toPath());
			var jsonObject = gson.fromJson(jsonStr, type);
			return jsonObject;
		case YAML:
			var yamlMapper = new YAMLMapper();
			var yamlObject = yamlMapper.readValue(file, type);
			return yamlObject;
		case KRYO:
			var kryo = new Kryo();
			kryo.setRegistrationRequired(false);

			try (var input = new Input(new FileInputStream(filePath))) {

				var obj = kryo.readClassAndObject(input);
				return (T) obj;
			}
		case XML:
			var xmlMapper = new XmlMapper();
			var xmlObject = xmlMapper.readValue(Files.readAllBytes(file.toPath()), type);
			return xmlObject;
		default:
			throw new OperationNotSupportedException();
		}
	}
}
