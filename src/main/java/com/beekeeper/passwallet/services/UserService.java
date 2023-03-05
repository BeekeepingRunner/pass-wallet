package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.dto.PasswordStorageOption;
import com.beekeeper.passwallet.dto.SignupModel;
import com.beekeeper.passwallet.entities.*;
import com.beekeeper.passwallet.repositories.UserRepository;
import jakarta.transaction.*;
import jakarta.validation.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(@Valid final SignupModel signupModel) {
        PasswordStorageOption passwordStorageOption = getPasswordStorageOption(signupModel);

        // create user record, store password in selected way
        User user = new User();
        user.setLogin(signupModel.getLogin());
       //  user.setPasswordHash(getPasswordHash(user));
    }

    private PasswordStorageOption getPasswordStorageOption(final SignupModel signupModel) {
        return switch (signupModel.getPasswordStorageOption()) {
            case 0 -> PasswordStorageOption.SHA_513;
            case 1 -> PasswordStorageOption.HMAC;
            default -> throw new RuntimeException("Cannot sign-up: bad request: invalid password storage option value = " + signupModel.getPasswordStorageOption());
        };
    }

}
