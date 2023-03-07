package com.beekeeper.passwallet.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {

    public static final String SHA_512 = "SHA-512";

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

    public static String calculateHMAC(String password, String key) {
        return "";
    }
}
