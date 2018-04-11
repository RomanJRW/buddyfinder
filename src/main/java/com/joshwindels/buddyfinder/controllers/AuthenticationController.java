package com.joshwindels.buddyfinder.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.exception.BadOperationException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.joshwindels.buddyfinder.dos.UserDO;
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
        Optional<String> validationErrorMessage = getValidationErrorMessage(userDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage.get();
        } else {
            UserDO userDO = convertToUserDO(userDTO);
            userRepository.storeUser(userDO);
            return "registration successful";
        }
    }

    private UserDO convertToUserDO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setUsername(userDTO.getUsername());
        String encryptedPassword = getEncryptedPassword(userDTO.getPassword());
        userDO.setPassword(encryptedPassword);
        userDO.setEmailAddress(userDTO.getEmailAddress());
        userDO.setTelephoneNumber(userDTO.getTelephoneNumber());
        return userDO;
    }

    private String getEncryptedPassword(String password) {
        String encryptedPassword;
        try {
            encryptedPassword = Hash.create(password);
        } catch (BadOperationException | NoSuchAlgorithmException ex) {
            throw new RuntimeException("There was a problem creating account");
        }
        return encryptedPassword;
    }

    private Optional<String> getValidationErrorMessage(UserDTO userDTO) {
        if (!usernameIsValid(userDTO.getUsername())) {
            return Optional.of("invalid username");
        } else if (!passwordIsValid(userDTO.getPassword())) {
            return Optional.of("invalid password");
        } else if (!emailAddressIsValid(userDTO.getEmailAddress())) {
            return Optional.of("invalid email address");
        } else if (!telephoneNumberIsValid(userDTO.getTelephoneNumber())) {
            return Optional.of("invalid telephone number");
        }

        if (!userRepository.userNameIsAvailable(userDTO.getUsername())) {
            return Optional.of("username unavailable");
        }

        return Optional.empty();
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