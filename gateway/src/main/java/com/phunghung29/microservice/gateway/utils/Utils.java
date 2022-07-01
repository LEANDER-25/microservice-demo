package com.phunghung29.microservice.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
public class Utils {

    private Utils() {
    }

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

    /**
     * Convert Json string to an object
     * Get value of each field in json and map it to given class
     *
     * @param json  String
     * @param clazz Class
     * @param <T>   Generic Type
     * @return new object of given class
     * @author hung.phung
     */
    public static <T> T convertJsonToObject(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * Use Reflect to map value between two classes which have common fields
     *
     * @param originClass        Class ex: User. class
     * @param origin             F
     * @param destinyClass       CLass ex: UserDto. class
     * @param destinyNewInstance Supplier, new instance of destiny ex User::new
     * @param <F>                GenericType
     * @param <T>                GenericType
     * @return a new object contains information of old object
     * @author hung.phung
     */
    public static <F, T> T mapTo(Class<F> originClass, F origin, Class<T> destinyClass, Supplier<T> destinyNewInstance) {
        try {
            T destiny = destinyNewInstance.get();
            Field[] fieldsOfOrigin = originClass.getDeclaredFields();
            Field[] fieldsOfDestiny = destinyClass.getDeclaredFields();
            for (Field fieldOrigin : fieldsOfOrigin) {
                fieldOrigin.setAccessible(true);
                for (Field fieldDestiny : fieldsOfDestiny) {
                    fieldDestiny.setAccessible(true);
                    if (fieldOrigin.getName().equals(fieldDestiny.getName())) {
                        fieldDestiny.set(destiny, fieldOrigin.get(origin));
                    }
                }
            }
            return destiny;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Use Reflect to map value between two classes which have common fields, ignore field has null <br/>
     * - Next gen of mapTo()
     *
     * @param originClass  Class ex: User. class
     * @param origin       F
     * @param destinyClass CLass ex: UserDto. class
     * @param destiny      A target object
     * @param <F>          GenericType
     * @param <T>          GenericType
     * @return a new object contains information of old object
     * @author hung.phung
     */
    public static <F, T> T mapToExisted(Class<F> originClass, F origin, Class<T> destinyClass, T destiny) {
        try {
            Field[] fieldsOfOrigin = originClass.getDeclaredFields();
            Field[] fieldsOfDestiny = destinyClass.getDeclaredFields();
            for (Field fieldOrigin : fieldsOfOrigin) {
                fieldOrigin.setAccessible(true);
                for (Field fieldDestiny : fieldsOfDestiny) {
                    fieldDestiny.setAccessible(true);
                    if (!fieldOrigin.getName().equals(fieldDestiny.getName())) {
                        continue;
                    }
                    if (fieldOrigin.get(origin) == null) {
                        continue;
                    }
                    fieldDestiny.set(destiny, fieldOrigin.get(origin));
                }
            }
            return destiny;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Use Reflect to map value between two classes which have common fields, ignore field has null <br/>
     * - Next gen of mapTo()
     *
     * @param originClass  Class ex: User. class
     * @param origin       F
     * @param destinyClass CLass ex: UserDto. class
     * @param destiny      A target object
     * @param ignoreFields Array or origin fields will be ignored when map happen
     * @param <F>          GenericType
     * @param <T>          GenericType
     * @return a new object contains information of old object
     * @author hung.phung
     */
    public static <F, T> T mapToExisted(Class<F> originClass, F origin, Class<T> destinyClass, T destiny, String[] ignoreFields) {
        try {
            Field[] fieldsOfOrigin = originClass.getDeclaredFields();
            Field[] fieldsOfDestiny = destinyClass.getDeclaredFields();
            for (Field fieldOrigin : fieldsOfOrigin) {
                fieldOrigin.setAccessible(true);
                for (Field fieldDestiny : fieldsOfDestiny) {
                    fieldDestiny.setAccessible(true);
                    if (Arrays.stream(ignoreFields).anyMatch(
                            item -> item.equalsIgnoreCase(fieldOrigin.getName()))) {
                        continue;
                    }
                    if (!fieldOrigin.getName().equals(fieldDestiny.getName())) {
                        continue;
                    }
                    if (fieldOrigin.get(origin) == null) {
                        continue;
                    }
                    fieldDestiny.set(destiny, fieldOrigin.get(origin));
                }
            }
            return destiny;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Convert String (Map format) to Map
     * - String must start with { and end with }
     * - String must have = between key and value
     *
     * @param input Map Format String
     * @return Map with key: String and value: Object
     */
    public static Map<String, Object> convertMapStringToMap(String input) {
        log.info("Input string: {}", input);
        if (!input.startsWith("{") || !input.endsWith("}")) {
            throw new IllegalArgumentException("The input string does not start with { or end with }");
        }
        if (!input.contains("=")) {
            throw new IllegalArgumentException("The input string does not contain =");
        }
        input = input.replace("{", "");
        input = input.replace("}", "");
        String[] splits = input.split(",");
        Map<String, Object> map = new HashMap<>();
        for (String item : splits) {
            item = item.trim();
            String[] pair = item.split("=");
            map.put(pair[0], pair[1]);
        }
        return map;
    }

    /**
     * Convert String to Map then convert Map to Destiny Object
     *
     * @param input        Map Format String: {key=value, key2=value2}
     * @param destinyClass Result class
     * @param <T>          T Any Class
     * @return T
     */
    public static <T> T convertMapStringToObject(String input, Class<T> destinyClass) {
        Map<String, Object> objectMap = Utils.convertMapStringToMap(input);
        JSONObject jsonObject = new JSONObject(objectMap);
        return Utils.convertJsonToObject(jsonObject.toString(), destinyClass);
    }

    public static String convertObjectToJson(Object source) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        return gson.toJson(source);
    }

    public static Map<String, Object> convertJsonToMap(String json) {
        try {
            return new ObjectMapper().readValue(json, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> convertObjectToMap(Object source) {
        String json = Utils.convertObjectToJson(source);
        return Utils.convertJsonToMap(json);
    }
}
