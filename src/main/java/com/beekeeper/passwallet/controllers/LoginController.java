package com.beekeeper.passwallet.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LoginController {

    @GetMapping("/log-in")
    public String login() {
        return "login";
    }
}
