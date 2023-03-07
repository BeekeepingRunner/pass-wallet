package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.dto.SignupModel;
import com.beekeeper.passwallet.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController {

    private final static String SIGNUP_MODEL_ATTR_NAME = "signupModel";

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/perform_login")
    public void performLogin() {

    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute(SIGNUP_MODEL_ATTR_NAME, new SignupModel());
        return "sign-up";
    }

    @PostMapping("/signup/proceed")
    public String signup(@Valid @ModelAttribute(SIGNUP_MODEL_ATTR_NAME) SignupModel signupModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new RuntimeException("Cannot sign up: user input is incorrect");

        userService.signup(signupModel);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfile() {
        return "my-profile";
    }
}
