package props;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static Properties prop;

    public static Properties getConfiguration() {
        return getConfiguration("config.properties");
    }

    @SneakyThrows
    public static Properties getConfiguration(String fileName) {
        try(InputStream input = Configuration.class.getClassLoader().getResourceAsStream(fileName)) {


        if (input == null) {
            throw new IllegalArgumentException("Can't find " + fileName);
        }
        prop = new Properties();
        prop.load(input);
    }

        return prop;
    }
}
