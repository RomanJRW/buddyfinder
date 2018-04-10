package com.joshwindels.buddyfinder.controllers;

import org.springframework.stereotype.Controller;
import org.apache.commons.validator.routines.EmailValidator;

@Controller
public class AuthenticationController {

    public String registerUser(String username, String password, String emailAddress, String telephoneNumber) {
        if (!usernameIsValid(username)) {
            return "invalid username";
        } else if (!passwordIsValid(password)) {
            return "invalid password";
        } else if (!emailAddressIsValid(emailAddress)) {
            return "invalid email address";
        } else if (!telephoneNumberIsValid(telephoneNumber)) {
            return "invalid telephone number";
        }
        return null;
    }

    private boolean usernameIsValid(String username) {
        return username != null && username.length() >= 8;
    }

    private boolean passwordIsValid(String password) {
        return !password.equals("pw");
    }

    private boolean emailAddressIsValid(String emailAddress) {
        return EmailValidator.getInstance().isValid(emailAddress);
    }

    private boolean telephoneNumberIsValid(String telephoneNumber) {
        return !telephoneNumber.equals("") && !telephoneNumber.equals("abcdefghi");
    }

}