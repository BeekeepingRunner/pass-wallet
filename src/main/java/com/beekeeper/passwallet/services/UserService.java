package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.dto.LoginModel;
import com.beekeeper.passwallet.dto.PassChangeRequest;
import com.beekeeper.passwallet.dto.PasswordStorageOption;
import com.beekeeper.passwallet.dto.SignupModel;
import com.beekeeper.passwallet.entities.UserEntity;
import com.beekeeper.passwallet.repositories.UserRepository;
import com.beekeeper.passwallet.utils.CryptoUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.beekeeper.passwallet.utils.CryptoUtils.SECRET_KEY;
import static com.beekeeper.passwallet.utils.CryptoUtils.calculateHMAC;
import static com.beekeeper.passwallet.utils.CryptoUtils.calculateSHA512;
import static com.beekeeper.passwallet.utils.CryptoUtils.generateSalt;

@Service
public class UserService {

    @Value("${pepper}")
    private String PEPPER;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getById(Long id) {
        return this.userRepository.getReferenceById(id);
    }

    @Transactional
    public UserEntity signup(@Valid final SignupModel signupModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(signupModel.getLogin());
        userEntity.setMasterPassword(signupModel.getMasterPassword());
        setUserPassword(userEntity, signupModel);
        return userRepository.save(userEntity);
    }

    private void setUserPassword(final UserEntity user, SignupModel signupModel) {
        String hash = hashPassword(user, signupModel);
        user.setPasswordHash(hash);
    }

    private String hashPassword(UserEntity user, final SignupModel signupModel) {
        int storageOption = signupModel.getPasswordStorageOption();
        String plainPassword = signupModel.getPassword();
        String fullPassword = plainPassword + PEPPER;
        if (storageOption == PasswordStorageOption.SHA_513.getValue()) {
            user.setIsPasswordKeptAsHmac(false);
            final String salt = CryptoUtils.generateSalt();
            user.setSalt(salt);
            fullPassword += user.getSalt();
            return calculateSHA512(fullPassword);
        } else if (storageOption == PasswordStorageOption.HMAC.getValue()) {
            user.setIsPasswordKeptAsHmac(true);
            return calculateHMAC(fullPassword, SECRET_KEY);
        } else {
            throw new RuntimeException("Cannot sign up the user: Invalid password storage option.");
        }
    }

    @Transactional
    public Optional<UserEntity> login(final LoginModel loginModel) {
        final Optional<UserEntity> optionalUser = userRepository.findByLogin(loginModel.getLogin());
        if (optionalUser.isPresent()) {
            final UserEntity user = optionalUser.get();
            String loginHash = "";
            String plainLoginPassword = loginModel.getPassword() + PEPPER;
            if (user.getIsPasswordKeptAsHmac()) {
                loginHash = calculateHMAC(plainLoginPassword, SECRET_KEY);
            } else {
                plainLoginPassword += user.getSalt();
                loginHash = calculateSHA512(plainLoginPassword);
            }

            final String passwordHash = user.getPasswordHash();
            return loginHash.equals(passwordHash)
                    && loginModel.getMasterPassword().equals(user.getMasterPassword())
                    ? optionalUser
                    : Optional.empty();
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public String changePassword(PassChangeRequest request) {
        final UserEntity user = getById(request.getUserId());

        // TODO: refactor
        // chech old pass
        String oldPassHash = "";
        String plainOldPassword = request.getOldPassword() + PEPPER;
        if (user.getIsPasswordKeptAsHmac()) {
            oldPassHash = calculateHMAC(plainOldPassword, SECRET_KEY);
        } else {
            plainOldPassword += user.getSalt();
            oldPassHash = calculateSHA512(plainOldPassword);
        }

        if (!oldPassHash.equals(user.getPasswordHash())) {
            return "Old password is incorrect";
        }

        // change pass
        String newPassHash = "";
        String plainNewPassword = request.getNewPassword() + PEPPER;
        if (user.getIsPasswordKeptAsHmac()) {
            newPassHash = calculateHMAC(plainNewPassword, SECRET_KEY);
        } else {
            user.setSalt(generateSalt());
            plainNewPassword += user.getSalt();
            newPassHash = calculateSHA512(plainNewPassword);
        }

        user.setPasswordHash(newPassHash);
        userRepository.save(user);
        return "Password changed successfully";
    }
}
