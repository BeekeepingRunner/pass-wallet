package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.entities.UserEntity;
import com.beekeeper.passwallet.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info")
@AllArgsConstructor
public class InfoController {

    private final UserRepository userRepository;

    @GetMapping("users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        final List<UserEntity> users = this.userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
