package pl.kk.quizmon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public final class ConfigLoader {
    private static ConfigLoader instance;
    private final Properties properties;

    private ConfigLoader() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getResourceAsStream("/pl/kk/quizmon/config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                Logger.getGlobal().severe("Config file not found!");
            }
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

    public String getProperty(String key) {
        if (!properties.containsKey(key)) {
            Logger.getGlobal().severe("Property \"" + key + "\" not found!");
            return "0";
        }
        return properties.getProperty(key);
    }

    public static ConfigLoader getInstance() {
        if (instance == null)
            instance = new ConfigLoader();
        return instance;
    }
}
