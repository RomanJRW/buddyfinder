package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @Mock UserRepository userRepositoryMock;

    private AuthenticationController authenticationController = new AuthenticationController();

    @Test
    public void givenBlankUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("", "Pa55word", "test@example.com", "0123456789");
        assertEquals("invalid username",
                authenticationController.registerUser("", "Pa55word", "test@example.com", "0123456789"));
    }

    @Test
    public void givenShortPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", "pw", "test@example.com", "0123456789");
        assertEquals("invalid password",
                authenticationController.registerUser("username", "pw", "test@example.com", "0123456789"));
    }

    @Test
    public void givenSimpleStringEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", "Pa55word", "test", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "test", "0123456789"));
    }

    @Test
    public void givenNoDomainEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", "Pa55word", "test@", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "test@", "0123456789"));
    }

    @Test
    public void givenNoTLDEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", "Pa55word", "test@example", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "test@example", "0123456789"));
    }

    @Test
    public void givenNoLocalPartEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", "Pa55word", "@example.com", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "@example.com", "0123456789"));
    }
}
