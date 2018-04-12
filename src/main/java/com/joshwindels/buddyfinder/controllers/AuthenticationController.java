package com.joshwindels.buddyfinder.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.exception.BadOperationException;
import com.amdelamar.jhash.exception.InvalidHashException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.UserDO;
import com.joshwindels.buddyfinder.dtos.UserDTO;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrentUser currentUser;

    @PostMapping("/users/create")
    public @ResponseBody String registerUser(UserDTO userDTO) {
        Optional<String> validationErrorMessage = getValidationErrorMessage(userDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage.get();
        } else {
            UserDO userDO = convertToUserDO(userDTO);
            userRepository.storeUser(userDO);
            return "registration successful";
        }
    }

    @PostMapping("/users/edit")
    public @ResponseBody String updateUserDetails(UserDTO userDTO) {
        if (currentUser.getUsername() == null || !currentUser.getUsername().equals(userDTO.getUsername())) {
            return "not authenticated";
        }
        if (userDTO.getEmailAddress() != null && !emailAddressIsValid(userDTO.getEmailAddress())) {
            return "invalid email address";
        } else if (userDTO.getTelephoneNumber() != null && !telephoneNumberIsValid(userDTO.getTelephoneNumber())) {
            return "invalid telephone number";
        } else {
            userRepository.updateUser(convertToUserDO(userDTO));
            return "account updated successfully";
        }
    }

    @PostMapping("/users/auth")
    public @ResponseBody String authenticateUser(UserDTO userDTO) {
        String storedPassword = userRepository.getStoredPasswordForUser(userDTO.getUsername());
        if (storedPassword == null) {
            return "username not found";
        } else if (isValidAuthenticationDetails(userDTO.getPassword(), storedPassword)) {
            currentUser.setUsername(userDTO.getUsername());
            return "authentication successful";
        } else {
            return "incorrect password";
        }
    }

    @PostMapping("/users/deauth")
    public @ResponseBody String deuathenticateUser(String username) {
        if (currentUser.getUsername() == null || !currentUser.getUsername().equals(username)) {
            return "not authenticated";
        } else {
            currentUser.setUsername(null);
            return "deauthenticated";
        }
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

    private UserDO convertToUserDO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null) {
            String encryptedPassword = getEncryptedPassword(userDTO.getPassword());
            userDO.setPassword(encryptedPassword);
        }
        userDO.setEmailAddress(userDTO.getEmailAddress());
        userDO.setTelephoneNumber(userDTO.getTelephoneNumber());
        return userDO;
    }

    private String getEncryptedPassword(String password) {
        try {
            return Hash.create(password);
        } catch (BadOperationException | NoSuchAlgorithmException ex) {
            throw new RuntimeException("There was a problem creating account");
        }
    }

    private boolean isValidAuthenticationDetails(String password, String storedPassword) {
        try {
            return Hash.verify(password, storedPassword);
        } catch (NoSuchAlgorithmException | InvalidHashException | BadOperationException e) {
            throw new RuntimeException("There was a problem authenticating account");
        }
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