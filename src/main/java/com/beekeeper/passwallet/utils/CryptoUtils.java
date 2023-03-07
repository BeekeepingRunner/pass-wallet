package com.beekeeper.passwallet.utils;

import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static javax.xml.crypto.dsig.SignatureMethod.HMAC_SHA512;

public class CryptoUtils {
    public static final String SECRET_KEY = "ThebesTSecretKey";
    public static final String SHA_512 = "SHA-512";
    public static final int SALT_LENGTH = 20;

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
            final Mac sha512Hmac = Mac.getInstance(HMAC_SHA512);
            final SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
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
}
