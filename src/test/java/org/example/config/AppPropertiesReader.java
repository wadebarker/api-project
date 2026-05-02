package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Читает {@code application.properties} из classpath (корректно при {@code mvn test} из любой директории).
 */
public final class AppPropertiesReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = AppPropertiesReader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new IllegalStateException("Classpath resource application.properties not found");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load application.properties", e);
        }
    }

    private AppPropertiesReader() {
    }

    public static String get(String key) {
        String v = PROPS.getProperty(key);
        if (v == null) {
            throw new IllegalStateException("Missing property: " + key);
        }
        return v;
    }
}
