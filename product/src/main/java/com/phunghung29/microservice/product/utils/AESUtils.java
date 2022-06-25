package com.phunghung29.microservice.product.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESUtils {

    private AESUtils(){}

    public static final String BASE_FILE_NAME = "AES.properties";
    public static final String BASE_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String BASE_SALT = "10";
    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }
    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static void createEnvironment(){
        //code here
    }

    public static String decrypt(String cipherText, String rawKey, IvParameterSpec ivParameterSpec) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        SecretKey key = getKeyFromPassword(rawKey, BASE_SALT);
        return decrypt(BASE_ALGORITHM, cipherText, key, ivParameterSpec);
    }

    public static String decrypt(String cipherText, String rawKey) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        SecretKey key = getKeyFromPassword(rawKey, BASE_SALT);
        return decrypt(BASE_ALGORITHM, cipherText, key, new IvParameterSpec(new byte[16]));
    }

    public static boolean match(SecretKey key, String cipherText, String plainText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return match(key, BASE_ALGORITHM, cipherText, plainText);
    }

    public static boolean match(SecretKey key, String algorithm, String cipherText, String plainText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String decryptedText = decrypt(algorithm, cipherText, key, generateIv());
        return  decryptedText.equals(plainText);
    }

    public static boolean match(String rawKey, String salt, String algorithm, String cipherText, String plainText) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey key = getKeyFromPassword(rawKey, salt);
        return match(key, algorithm, cipherText, plainText);
    }

    public static boolean match(String rawKey, String cipherText, String plainText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return match(rawKey, BASE_SALT, BASE_ALGORITHM, cipherText, plainText);
    }
}
