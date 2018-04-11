package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.joshwindels.buddyfinder.dos.UserDO;
import com.joshwindels.buddyfinder.dtos.UserDTO;
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
        UserDTO userDTO = makeUserDto("", "Pa55word", "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid username", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenShortUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("SevenLe", "Pa55word", "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid username", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto(null, "Pa55word", "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid username", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenShortPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "pw", "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoUpperPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "pa55word", "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoLowerCasePassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "PA55WORD", "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoNumberPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Password", "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", null, "test@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenSimpleStringEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoDomainEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoTLDEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoLocalPartEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "@example.com", "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", null, "0123456789");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenEmptyTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example.com", "");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid telephone number", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenCharacterStringTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example.com", "abcdefghi");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid telephone number", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example.com", null);

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid telephone number", authenticationController.registerUser(userDTO));
    }

    private UserDTO makeUserDto(String username, String password, String emailAddress, String telephoneNumber) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setEmailAddress(emailAddress);
        userDTO.setTelephoneNumber(telephoneNumber);
        return userDTO;
    }
}
