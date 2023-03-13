package com.beekeeper.passwallet.utils;

import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static javax.xml.crypto.dsig.SignatureMethod.HMAC_SHA512;
import static javax.xml.crypto.dsig.SignatureMethod.SHA512_RSA_MGF1;

public class CryptoUtils {
    public static final String SECRET_KEY = "ThebesTSecretKey";
    public static final String SHA_512 = "SHA-512";
    public static final String HMAC_SHA_512 = "HmacSHA512";
    public static final int SALT_LENGTH = 20;

    private static final String ENCRYPTION_ALGO = "AES";

    public static String calculateSHA512(String text) {
        try {
            byte[] digest = MessageDigest.getInstance(SHA_512)
                    .digest(text.getBytes());

            BigInteger num = new BigInteger(1, digest);
            StringBuilder hashBuilder = new StringBuilder(num.toString(16));
            while (hashBuilder.length() < 32) {
                hashBuilder.append("0");
            }

            return hashBuilder.toString();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String calculateHMAC(String text, String key) {
        try {
            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            final Mac sha512Hmac = Mac.getInstance(HMAC_SHA_512);
            final SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA_512);
            sha512Hmac.init(keySpec);
            final byte[] macData = sha512Hmac.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(macData);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't calculate HMAC");
        }
    }

    public static String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
    }

    public static String encrypt(String data, String keyValue) throws Exception {
        if (data.isEmpty() || keyValue.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Cipher c = Cipher.getInstance(ENCRYPTION_ALGO);
        final byte[] keyMD5 = calculateMD5(keyValue);
        c.init(Cipher.ENCRYPT_MODE, generateKey(keyMD5));
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    public static String decrypt(String encryptedData, String keyValue) throws Exception {
        if (encryptedData.isEmpty() || keyValue.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Cipher c = Cipher.getInstance(ENCRYPTION_ALGO);
        final byte[] keyMD5 = calculateMD5(keyValue);
        c.init(Cipher.DECRYPT_MODE, generateKey(keyMD5));
        byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private static Key generateKey(byte[] keyValue) {
        return new SecretKeySpec(keyValue, ENCRYPTION_ALGO);
    }

    public static byte[] calculateMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
