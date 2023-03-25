package com.beekeeper.passwallet.dto.password;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordDto {

    private Long userId;
    private String masterPassword;

    private String login;
    private String password;
    private String webAddress;
    private String description;
}
