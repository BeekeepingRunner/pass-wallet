package com.beekeeper.passwallet.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupModel {

    @NotEmpty
    @Size(min = 2, max = 30)
    private String login;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String password;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String masterPassword;

    @NotNull
    private int passwordStorageOption;
}
