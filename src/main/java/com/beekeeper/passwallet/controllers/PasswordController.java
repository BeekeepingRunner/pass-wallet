package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.dto.password.NewPasswordDto;
import com.beekeeper.passwallet.dto.password.PasswordEditDto;
import com.beekeeper.passwallet.entities.Password;
import com.beekeeper.passwallet.services.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/password")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @GetMapping("/all")
    public ResponseEntity<Collection<Password>> getAllUserPasswords(@RequestParam Long userId, @RequestParam boolean decrypt) {
        final Collection<Password> passwords = passwordService.getUserPasswords(userId, decrypt);
        return ResponseEntity.ok(passwords);
    }

    @PostMapping("/add")
    public ResponseEntity<Password> addPassword(@RequestBody NewPasswordDto newPasswordDto) {
        final Password password = passwordService.saveNewPassword(newPasswordDto);
        return ResponseEntity.ok(password);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editPassword(@RequestBody PasswordEditDto editDto) {
        final Optional<Password> password = passwordService.editPassword(editDto);
        if (password.isPresent()) {
            return ResponseEntity.ok(password.get());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Cannot edit password. You are not in the modify mode.");
        }
    }
}
