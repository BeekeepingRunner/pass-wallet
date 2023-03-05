package com.beekeeper.passwallet.dto;

public enum PasswordStorageOption {
    SHA_513(0),
    HMAC(1),
    INVALID(-1);

    private final int value;

    PasswordStorageOption(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PasswordStorageOption toEnumValue(int option) {
        return switch (option) {
            case 0 -> PasswordStorageOption.SHA_513;
            case 1 -> PasswordStorageOption.HMAC;
            default -> PasswordStorageOption.INVALID;
        };
    }
}
