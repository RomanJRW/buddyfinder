package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.junit.Test;
import org.mockito.Mock;

public class AuthenticationControllerTest {

    @Mock UserRepository userRepositoryMock;

    private AuthenticationController authenticationController = new AuthenticationController();

    @Test
    public void givenBlankUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("", "Pa55word", "test@example.com", "0123456789");
        assertEquals("invalid username",
                authenticationController.registerUser("", "Pa55word", "test@example.com", "0123456789"));
    }
}
