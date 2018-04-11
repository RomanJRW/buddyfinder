package com.joshwindels.buddyfinder.controllers;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.joshwindels.buddyfinder.dtos.UserDTO;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

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

        if (!userRepository.userNameIsAvailable(userDTO.getUsername())) {
            return "username unavailable";
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
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            return telephoneNumber != null &&
                    phoneUtil.isPossibleNumber(phoneUtil.parse(telephoneNumber, "GB"));
        } catch (NumberParseException e) {
            return false;
        }
    }

}