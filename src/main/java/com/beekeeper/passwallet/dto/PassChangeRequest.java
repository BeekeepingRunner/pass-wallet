package com.beekeeper.passwallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassChangeRequest {

    private Long userId;
    private String oldPassword;
    private String newPassword;
}
