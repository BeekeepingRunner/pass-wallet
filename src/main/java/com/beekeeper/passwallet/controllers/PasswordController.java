package com.beekeeper.passwallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/password")
public class PasswordController {

    @GetMapping("/add")
    public String showProfile() {
        return "add-password";
    }
}
