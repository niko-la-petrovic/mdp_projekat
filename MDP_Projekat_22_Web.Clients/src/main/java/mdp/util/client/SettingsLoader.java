package mdp.util.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;

import mdp.settings.client.Constants;

public class SettingsLoader {
	public static Properties getLoadedProperties(String propertiesFileName) throws FileNotFoundException, IOException {
		Properties props = new Properties();

		String loadPath = Constants.getpropertiesLocation(propertiesFileName);
		try (FileInputStream in = new FileInputStream(loadPath)) {
			props.load(in);
		}

		return props;
	}

	public static void loadSettings(String propertiesFileName, Consumer<Properties> useProperties)
			throws FileNotFoundException, IOException {
		Properties props = getLoadedProperties(propertiesFileName);

		useProperties.accept(props);
	}

	public static void test() {

	}
}
