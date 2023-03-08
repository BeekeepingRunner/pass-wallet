package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.dto.NewPasswordRequest;
import com.beekeeper.passwallet.entities.Password;
import com.beekeeper.passwallet.services.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/password")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/add")
    public ResponseEntity<Password> addPassword(@RequestBody NewPasswordRequest request) {
        final Password password = passwordService.saveNewPassword(request);
        return ResponseEntity.ok(password);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Password>> getAllUserPasswords(@RequestParam Long userId, @RequestParam boolean decrypt) {
        final Collection<Password> passwords = passwordService.getUserPasswords(userId, decrypt);
        return ResponseEntity.ok(passwords);
    }
}
