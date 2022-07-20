package mdp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;

import mdp.settings.Constants;

public class SettingsLoader {
	public static Properties getLoadedProperties(String propertiesFileName) throws FileNotFoundException, IOException {
		var props = new Properties();

		var loadPath = Constants.getpropertiesLocation(propertiesFileName);
		try (var in = new FileInputStream(loadPath)) {
			props.load(in);
		}

		return props;
	}

	public static void loadSettings(String propertiesFileName, Consumer<Properties> useProperties)
			throws FileNotFoundException, IOException {
		var props = getLoadedProperties(propertiesFileName);

		useProperties.accept(props);
	}

	public static void test() {

	}
}
