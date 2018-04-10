package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AuthenticationControllerTest {

    private AuthenticationController authenticationController = new AuthenticationController();

    @Test
    public void givenBlankUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        assertEquals("invalid username",
                authenticationController.registerUser("", "Pa55word", "test@example.com", "0123456789"));
    }
}
