package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.dto.LoginModel;
import com.beekeeper.passwallet.dto.password.PassChangeDto;
import com.beekeeper.passwallet.dto.SignupModel;
import com.beekeeper.passwallet.entities.UserEntity;
import com.beekeeper.passwallet.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel loginModel) {
        final Optional<UserEntity> user = userService.login(loginModel);
        if (user.isPresent()) {
            return ResponseEntity.ok("Hi " + loginModel.getLogin());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupModel signupModel) {
        userService.signup(signupModel);
        return ResponseEntity.ok("Successfull sign up");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PassChangeDto request) {
        final String response = userService.changePassword(request);
        return ResponseEntity.ok(response);
    }
}
