package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppPropertiesReader {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load properties file", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}

