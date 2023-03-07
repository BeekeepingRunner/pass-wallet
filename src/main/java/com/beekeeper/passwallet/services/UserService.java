package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.dto.PasswordStorageOption;
import com.beekeeper.passwallet.dto.SignupModel;
import com.beekeeper.passwallet.entities.User;
import com.beekeeper.passwallet.repositories.PasswordRepository;
import com.beekeeper.passwallet.repositories.UserRepository;
import com.beekeeper.passwallet.utils.CryptoUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.beekeeper.passwallet.utils.CryptoUtils.*;

@Service
@AllArgsConstructor
public class UserService {


    @Value("${pepper}")
    private final String PEPPER;

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    @Transactional
    public void signup(@Valid final SignupModel signupModel) {
        User user = new User();
        user.setLogin(signupModel.getLogin());
        setUserPassword(user, signupModel);
        userRepository.save(user);
    }

    private void setUserPassword(final User user, SignupModel signupModel) {
        final String salt = CryptoUtils.generateSalt();
        user.setSalt(salt);

        String hash = hashPassword(user, signupModel);
        user.setPasswordHash(hash);
    }

    private String hashPassword(User user, final SignupModel signupModel) {
        int storageOption = signupModel.getPasswordStorageOption();
        String plainPassword = signupModel.getPassword();
        String fullPassword = plainPassword + user.getSalt() + PEPPER;

        if (storageOption == PasswordStorageOption.SHA_513.getValue()) {
            return calculateSHA512(fullPassword);
        } else if (storageOption == PasswordStorageOption.HMAC.getValue()) {
            return calculateHMAC(fullPassword, SECRET_KEY);
        } else {
            throw new RuntimeException("Cannot sign up the user: Invalid password storage option.");
        }
    }
}
