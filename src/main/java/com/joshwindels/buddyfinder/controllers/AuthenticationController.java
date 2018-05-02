package com.joshwindels.buddyfinder.controllers;

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
        authenticationHelper.validateUser(userDTO);
        if (!userRepository.userNameIsAvailable(userDTO.getUsername())) {
            throw new RuntimeException("username unavailable");
        }
        UserDO userDO = authenticationHelper.convertToUserDO(userDTO);
        userRepository.storeUser(userDO);
        return "registration successful";
    }

    @PatchMapping("/edit")
    public @ResponseBody String updateUserDetails(UserDTO userDTO) {
        checkAuthentication();
        if (userDTO.getEmailAddress() != null && !authenticationHelper.emailAddressIsValid(userDTO.getEmailAddress())) {
            throw new RuntimeException("invalid email address");
        } else if (userDTO.getTelephoneNumber() != null && !authenticationHelper.telephoneNumberIsValid(userDTO.getTelephoneNumber())) {
            throw new RuntimeException("invalid telephone number");
        } else {
            userRepository.updateUser(authenticationHelper.convertToUserDO(userDTO));
            return "account updated successfully";
        }
    }

    @PostMapping("/auth")
    public @ResponseBody String authenticateUser(UserDTO userDTO) {
        String storedPassword = userRepository.getStoredPasswordForUser(userDTO.getUsername());
        if (storedPassword == null) {
            throw new RuntimeException("username not found");
        } else if (authenticationHelper.isValidAuthenticationDetails(userDTO.getPassword(), storedPassword)) {
            currentUser.setUsername(userDTO.getUsername());
            currentUser.setId(userRepository.getIdForUsername(userDTO.getUsername()));
            return "authentication successful";
        } else {
            throw new RuntimeException("incorrect password");
        }
    }

    @GetMapping("/deauth")
    public @ResponseBody String deuathenticateUser() {
        checkAuthentication();

        currentUser.setUsername(null);
        currentUser.setId(-1);
        return "deauthenticated";

    }

    private void checkAuthentication() {
        if (currentUser.getUsername() == null) {
            throw new RuntimeException("not authenticated");
        }
    }
}