package com.joshwindels.buddyfinder.controllers;

import java.util.Optional;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.UserDO;
import com.joshwindels.buddyfinder.dtos.UserDTO;
import com.joshwindels.buddyfinder.helpers.AuthenticationHelper;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrentUser currentUser;

    @Autowired AuthenticationHelper authenticationHelper;

    @PostMapping("/create")
    public @ResponseBody String registerUser(UserDTO userDTO) {
        Optional<String> validationErrorMessage = authenticationHelper.getValidationErrorMessage(userDTO);
        if (validationErrorMessage.isPresent()) {
            return validationErrorMessage.get();
        } else if (!userRepository.userNameIsAvailable(userDTO.getUsername())) {
            return "username unavailable";
        }

        UserDO userDO = authenticationHelper.convertToUserDO(userDTO);
        userRepository.storeUser(userDO);
        return "registration successful";
    }

    @PatchMapping("/edit")
    public @ResponseBody String updateUserDetails(UserDTO userDTO) {
        if (currentUser.getUsername() == null || !currentUser.getUsername().equals(userDTO.getUsername())) {
            return "not authenticated";
        }
        if (userDTO.getEmailAddress() != null && !authenticationHelper.emailAddressIsValid(userDTO.getEmailAddress())) {
            return "invalid email address";
        } else if (userDTO.getTelephoneNumber() != null && !authenticationHelper.telephoneNumberIsValid(userDTO.getTelephoneNumber())) {
            return "invalid telephone number";
        } else {
            userRepository.updateUser(authenticationHelper.convertToUserDO(userDTO));
            return "account updated successfully";
        }
    }

    @GetMapping("/auth")
    public @ResponseBody String authenticateUser(UserDTO userDTO) {
        String storedPassword = userRepository.getStoredPasswordForUser(userDTO.getUsername());
        if (storedPassword == null) {
            return "username not found";
        } else if (authenticationHelper.isValidAuthenticationDetails(userDTO.getPassword(), storedPassword)) {
            currentUser.setUsername(userDTO.getUsername());
            currentUser.setId(userRepository.getIfForUsername(userDTO.getUsername()));
            return "authentication successful";
        } else {
            return "incorrect password";
        }
    }

    @GetMapping("/deauth")
    public @ResponseBody String deuathenticateUser(String username) {
        if (currentUser.getUsername() == null || !currentUser.getUsername().equals(username)) {
            return "not authenticated";
        } else {
            currentUser.setUsername(null);
            currentUser.setId(-1);
            return "deauthenticated";
        }
    }
}