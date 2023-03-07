package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.dto.PasswordStorageOption;
import com.beekeeper.passwallet.dto.SignupModel;
import com.beekeeper.passwallet.entities.User;
import com.beekeeper.passwallet.repositories.PasswordRepository;
import com.beekeeper.passwallet.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beekeeper.passwallet.utils.CryptoUtils.calculateHMAC;
import static com.beekeeper.passwallet.utils.CryptoUtils.calculateSHA512;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    @Transactional
    public void signup(@Valid final SignupModel signupModel) {
        User user = new User();
        user.setLogin(signupModel.getLogin());
        String hash = getPasswordHash(user, signupModel);
        user.setPasswordHash(hash);
    }

    private String getPasswordHash(User user, final SignupModel signupModel) {
        int storageOption = signupModel.getPasswordStorageOption();
        if (storageOption == PasswordStorageOption.SHA_513.getValue()) {
            return calculateSHA512(signupModel.getPassword());
        } else if (storageOption == PasswordStorageOption.HMAC.getValue()) {
            return calculateHMAC(signupModel.getPassword(), "HARDCODED_KEY"); // TODO: secure key or just move it
        } else {
            throw new RuntimeException("Cannot sign up the user: Invalid password storage option.");
        }
    }
}
