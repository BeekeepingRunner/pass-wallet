package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.dto.SingupModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/perform_login")
    public void performLogin() {

    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupModel", new SingupModel());
        return "sign-up";
    }

    @PostMapping("/signup/proceed")
    public String signup(SingupModel singupModel) {
        System.out.println(singupModel.getPasswordStorageOption());
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfile() {
        return "my-profile";
    }
}
