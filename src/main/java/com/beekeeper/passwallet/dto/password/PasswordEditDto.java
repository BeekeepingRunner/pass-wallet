package com.beekeeper.passwallet.dto.password;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordEditDto extends NewPasswordDto {

    private Long passwordId;
}
