package com.beekeeper.passwallet.utils;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

    public static Stream<Arguments> calculate_SHA512_dataProvider() {
        return Stream.of(
                Arguments.of("", "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e"),
                Arguments.of(" ", "f90ddd77e400dfe6a3fcf479b00b1ee29e7015c5bb8cd70f5f15b4886cc339275ff553fc8a053f8ddc7324f45168cffaf81f8c3ac93996f6536eef38e5e40768"),
                Arguments.of("1", "4dff4ea340f0a823f15d3f4f01ab62eae0e5da579ccb851f8db9dfe84c58b2b37b89903a740e1ee172da793a6e79d560e5f7f9bd058a12a280433ed6fa46510a"),
                Arguments.of("\n", "be688838ca8686e5c90689bf2ab585cef1137c999b48c70b92f67a5c34dc15697b5d11c982ed6d71be1e1e7f7b4e0733884aa97c3f7a339a8ed03577cf74be09"),
                Arguments.of("text", "eaf2c12742cb8c161bcbd84b032b9bb98999a23282542672ca01cc6edd268f7dce9987ad6b2bc79305634f89d90b90102bcd59a57e7135b8e3ceb93c0597117b"),
                Arguments.of("text with spaces", "11fc8bc7e2b2fee5f73effaeaa9b0791eabf769e0092fad791e15cae9e410a3b1d1430bd76553a52c852f8deefd66b49dfe21d7e05e662ac90955dd51ced1e12"),
                Arguments.of(
                        "eaf2c12742cb8c161bcbd84b032b9bb98999a23282542672ca01cc6edd268f7dce9987ad6b2bc79305634f89d90b90102bcd59a57e7135b8e3ceb93c0597117b",
                        "799ff083ec93d13ee0ef6a43684b9dd405cad950d590551850184ed08cae96bfff95a72e489ffdc71eb877c25a3eca22420e0b3f8e1e426b49a8dac1a1813e52"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("calculate_SHA512_dataProvider")
    void should_calculate_SHA512_when_given_any_input_text(String text, String expectedHash) {
        String result = CryptoUtils.calculateSHA512(text);
        assertEquals(expectedHash, result);
    }

    public static Stream<Arguments> calculate_HMAC_dataProvider() {
        String whiteSpaceKey = " ";
        String ordinaryKey = "key";

        return Stream.of(
                Arguments.of("", whiteSpaceKey, "pdnAzbaWpwrODsNOqv/nFB9OVK4teGClTBF1wN0RtvJhmVGrCrDqR7ZJTRJ4civllFG9iKRlFwy2Ki6RaiH68w=="),
                Arguments.of("", ordinaryKey, "hPpaoCebvEcyZ9BaU+oDMQqYfOzEwVNf8pttdrjxREpyjfOq24nUqaZwnhmY83NWbo+CSoypOxgh8LabwqL2Xg=="),

                Arguments.of(" ", whiteSpaceKey, "C4pyFjuSW7th/6mOkDOeV/DtXIlWZlr4NpGuu967h+frYJCod7Yv3Pyi4pdoFZ0AZufvh1qH1qiy/50oapj/Vg=="),
                Arguments.of(" ", ordinaryKey, "vdEvgECGBqr9YY5B87FQSUx/uTFxQkBkgHscf2GuE+xxN1yvVvDEKg7PMGjGplvFF/WtwcqrQ3+8U+4CLbSmRQ=="),

                Arguments.of("1", whiteSpaceKey, "m8rn7m6dkHUR2pAQBd4APw9GThBXn3ktbHCwVvyrCcSfGaC+89Lr0koXfoUlx+XE6JLSt/LMdq5iKV0i3rPTjQ=="),
                Arguments.of("1", ordinaryKey, "VIFpBeldCnQDadX7QMw3zhN2HZ9W6JdQhZD68jBhUgkxR0CSkFkrau3caUst5KgWUmvjmee/UKlxoVN9+DHKSg=="),

                Arguments.of("text", whiteSpaceKey, "22Ex5kyDzb0ftI17/1EfLFNx4DHJRysw8rR8QwjRBiOEjkO5Pl5oqg07RlzNwry1xSJeVlkvXXYcFdDB5pmdNQ=="),
                Arguments.of("text", ordinaryKey, "tYUxKs3TjsE/E7tMujWnVHPzK2rkoDA5JoFb1D16JjFRaysDHzTYntqFPpSNUFfeVKiAwWaXJC2+ahrZlLxOXQ=="),

                Arguments.of("text with spaces", whiteSpaceKey, "f3RmQywE7lHwmZ7SqIA8tIBrUjZOxyL3xqoExXuMnWxCA3yWnimN42XeEs6pitRUSYlFlt4zZ2xliYAD1Gg0Ag=="),
                Arguments.of("text with spaces", ordinaryKey, "9dnuH/L7xccK7OyujnwKngCvfvi29BP+Y1Oda/lc21eWsdApGad9hxfhJDznaCdlWTcD1Yq83tpN+uJ1zLuK3w=="),

                Arguments.of(
                        "9Np9dCW/c5txlbwKPKgJTwsUylKdLOOcSklE7KJbzSE+i+zcC5mZzOtE3ivh96Wdb54c4e1/+7EOquWjXoA/pQ==",
                        whiteSpaceKey,
                        "zFpO5oEKAVozC7Qju+mPb7/38UtqYEzosn6aPEXXIWlkbaEMlpg0tS823xROy7hELpNjRcikpWZtGwZyzQwi5w=="
                ),
                Arguments.of(
                        "9Np9dCW/c5txlbwKPKgJTwsUylKdLOOcSklE7KJbzSE+i+zcC5mZzOtE3ivh96Wdb54c4e1/+7EOquWjXoA/pQ==",
                        ordinaryKey,
                        "5Pqav6dOYF3Hg5i1XiNXmGL1TQkGhQ7Vm4plRqgVKpeTcuIUoJl1rZJVTthr3JJESXtZu3k8JFvofl0SjbfvGw=="
                )
        );
    }

    @ParameterizedTest
    @MethodSource("calculate_HMAC_dataProvider")
    void should_calculateHMAC_given_any_text_and_not_empty_key(String plainText, String secretKey, String expectedHMAC) {
        String result = CryptoUtils.calculateHMAC(plainText, secretKey);
        assertEquals(expectedHMAC, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1", "\n", "text", "text with spaces"})
    void should_throw_exception_in_calculateHMAC_given_empty_key(String plainText) {
        Executable executable = () -> CryptoUtils.calculateHMAC(plainText, "");
        assertThrows(IllegalArgumentException.class, executable);
    }

    public static Stream<Arguments> aes_encrypt_dataProvider() {
        String whiteSpaceKey = " ";
        String ordinaryKey = "key";

        return Stream.of(
                Arguments.of("1", whiteSpaceKey, "vQC6JrhMZ/3zkrbV2TKgMA=="),
                Arguments.of("1", ordinaryKey, "lUFW1S2PHg6GHEx9NM6Xug=="),

                Arguments.of("text", whiteSpaceKey, "rdy3zcUD2f2uGI5RMyY9jg=="),
                Arguments.of("text", ordinaryKey, "i+JdrQvZT+JVRDB2d+Otrw=="),

                Arguments.of("text with spaces", whiteSpaceKey, "jB4HFmxtMhNuTEDR8suRwchVrRsrsE4LW0dYc6tY4Po="),
                Arguments.of("text with spaces", ordinaryKey, "MggPpBqImAJVNFTOlX1rpSrKcsAC5A0AiHqnJAKKHTA=")
        );
    }

    @ParameterizedTest
    @MethodSource("aes_encrypt_dataProvider")
    void should_AES_encrypt_any_text(String text, String key, String expectedResult) throws Exception {
        String result = CryptoUtils.encrypt(text, key);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("aes_encrypt_dataProvider")
    void should_throw_exception_in_aes_encryption_given_empty_key(String text) {
    }

    @Test
    void decrypt() {
    }

    @Test
    void calculateMD5() {
    }
}