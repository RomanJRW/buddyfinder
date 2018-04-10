package com.joshwindels.buddyfinder.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {

    public String registerUser(String username, String password, String emailAddress, String telephoneNumber) {
        return "invalid username";
    }

}