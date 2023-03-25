package com.beekeeper.passwallet.dto.password;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassChangeDto {

    private Long userId;
    private String oldPassword;
    private String newPassword;
}
