package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.dto.NewPasswordRequest;
import com.beekeeper.passwallet.entities.Password;
import com.beekeeper.passwallet.entities.UserEntity;
import com.beekeeper.passwallet.repositories.PasswordRepository;
import com.beekeeper.passwallet.utils.CryptoUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PasswordService {

    private final UserService userService;
    private final PasswordRepository passwordRepository;

    @Transactional
    public Password saveNewPassword(final NewPasswordRequest request) {
        final UserEntity user = userService.getById(request.getUserId());
        if (!user.getMasterPassword().equals(request.getMasterPassword())) {
            throw new RuntimeException("Cannot save new password: Master password is incorrect");
        }

        final Password newPassword = new Password();
        newPassword.setLogin(request.getLogin());
        newPassword.setDescription(request.getDescription());
        newPassword.setWebAddress(request.getWebAddress());
        newPassword.setUser(user);

        try {
            final String plainRequestPassword = request.getPassword();
            final String encryptedPassword = CryptoUtils.encrypt(plainRequestPassword, user.getMasterPassword());
            newPassword.setPassword(encryptedPassword);
            return passwordRepository.save(newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot save new password: Encryption error");
        }
    }

    public Collection<Password> getUserPasswords(Long userId, boolean decrypt) {
        final UserEntity user = userService.getById(userId);
        final Collection<Password> passwords = user.getPasswords();
        if (!decrypt) {
            return passwords;
        } else {
            final String masterPass = user.getMasterPassword();
            return passwords.stream()
                    .map(pass -> decrypt(masterPass, pass))
                    .collect(Collectors.toList());
        }
    }

    private Password decrypt(String masterPass, Password pass) {
        try {
            final String decryptedPass = CryptoUtils.decrypt(pass.getPassword(), masterPass);
            pass.setPassword(decryptedPass);
            return pass;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot get user passwords: decryption error");
        }
    }
}
