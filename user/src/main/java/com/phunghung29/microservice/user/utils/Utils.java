package com.phunghung29.microservice.user.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    private Utils() {}

    public static Properties loadProperties(String fileName) {
        try (InputStream input = Utils.class.getClassLoader().getResourceAsStream(fileName)) {

            Properties prop = new Properties();

            if (input == null) {
                throw new IOException();
            }
            prop.load(input);
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
            return new Properties();
        }
    }
}
