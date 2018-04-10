package com.joshwindels.buddyfinder.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {

    public String registerUser(String username, String password, String emailAddress, String telephoneNumber) {
        if (usernameIsInvalid(username)) {
            return "invalid username";
        } else if (passwordIsInvalid(password)) {
            return "invalid password";
        } else if (emailAddressIsInvalid(emailAddress)) {
            return "invalid email address";
        }
        return null;
    }

    private boolean usernameIsInvalid(String username) {
        return username.equals("");
    }

    private boolean passwordIsInvalid(String password) {
        return password.equals("pw");
    }

    private boolean emailAddressIsInvalid(String emailAddress) {
        return emailAddress.equals("test");
    }

}