package com.joshwindels.buddyfinder.controllers;

import com.joshwindels.buddyfinder.dtos.UserDTO;
import org.springframework.stereotype.Controller;
import org.apache.commons.validator.routines.EmailValidator;

@Controller
public class AuthenticationController {

    public String registerUser(UserDTO userDTO) {
        if (!usernameIsValid(userDTO.getUsername())) {
            return "invalid username";
        } else if (!passwordIsValid(userDTO.getPassword())) {
            return "invalid password";
        } else if (!emailAddressIsValid(userDTO.getEmailAddress())) {
            return "invalid email address";
        } else if (!telephoneNumberIsValid(userDTO.getTelephoneNumber())) {
            return "invalid telephone number";
        }
        return null;
    }

    private boolean usernameIsValid(String username) {
        return username != null && username.length() >= 8;
    }

    private boolean passwordIsValid(String password) {
        return password != null && password.matches("^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})[\\s\\S]{8,}$");
    }

    private boolean emailAddressIsValid(String emailAddress) {
        return EmailValidator.getInstance().isValid(emailAddress);
    }

    private boolean telephoneNumberIsValid(String telephoneNumber) {
        return telephoneNumber != null
                && (!telephoneNumber.equals("") && !telephoneNumber.equals("abcdefghi"));
    }

}