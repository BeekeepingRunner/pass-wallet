package com.beekeeper.passwallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequest {

    private Long userId;
    private String masterPassword;

    private String login;
    private String password;
    private String webAddress;
    private String description;
}
