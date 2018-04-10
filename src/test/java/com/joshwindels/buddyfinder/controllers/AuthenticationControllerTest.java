package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
        verify(userRepositoryMock, never()).storeUser("", anyString(), "test@example.com", "0123456789");
        assertEquals("invalid username",
                authenticationController.registerUser("", "Pa55word", "test@example.com", "0123456789"));
    }

    @Test
    public void givenShortUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("SevenLe", anyString(), "test@example.com", "0123456789");
        assertEquals("invalid username",
                authenticationController.registerUser("SevenLe", "Pa55word", "test@example.com", "0123456789"));
    }

    @Test
    public void givenNoUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser(null, anyString(), "test@example.com", "0123456789");
        assertEquals("invalid username",
                authenticationController.registerUser(null, "Pa55word", "test@example.com", "0123456789"));
    }

    @Test
    public void givenShortPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example.com", "0123456789");
        assertEquals("invalid password",
                authenticationController.registerUser("username", "pw", "test@example.com", "0123456789"));
    }

    @Test
    public void givenNoUpperPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example.com", "0123456789");
        assertEquals("invalid password",
                authenticationController.registerUser("username", "pa55word", "test@example.com", "0123456789"));
    }

    @Test
    public void givenNoLowerCasePassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example.com", "0123456789");
        assertEquals("invalid password",
                authenticationController.registerUser("username", "PA55WORD", "test@example.com", "0123456789"));
    }

    @Test
    public void givenNoNumberPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example.com", "0123456789");
        assertEquals("invalid password",
                authenticationController.registerUser("username", "Password", "test@example.com", "0123456789"));
    }

    @Test
    public void givenNoPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example.com", "0123456789");
        assertEquals("invalid password",
                authenticationController.registerUser("username", null, "test@example.com", "0123456789"));
    }

    @Test
    public void givenSimpleStringEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "test", "0123456789"));
    }

    @Test
    public void givenNoDomainEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "test@", "0123456789"));
    }

    @Test
    public void givenNoTLDEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "test@example", "0123456789"));
    }

    @Test
    public void givenNoLocalPartEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "@example.com", "0123456789");
        assertEquals("invalid email address",
                authenticationController.registerUser("username", "Pa55word", "@example.com", "0123456789"));
    }

    @Test
    public void givenEmptyTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example.com", "");
        assertEquals("invalid telephone number",
                authenticationController.registerUser("username", "Pa55word", "test@example.com", ""));
    }

    @Test
    public void givenCharacterStringTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        verify(userRepositoryMock, never()).storeUser("username", anyString(), "test@example.com", "abcdefghi");
        assertEquals("invalid telephone number",
                authenticationController.registerUser("username", "Pa55word", "test@example.com", "abcdefghi"));
    }
}
