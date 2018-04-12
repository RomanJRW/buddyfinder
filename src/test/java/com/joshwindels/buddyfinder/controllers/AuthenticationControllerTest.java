package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.UserDO;
import com.joshwindels.buddyfinder.dtos.UserDTO;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    private static final String ENCRYPTED_PASSWORD = "pbkdf2sha1:64000:18:n:Phm0K4I/jZoloFK8gJVOIcZW:fty4Mup4VGlRnbC6pRHaf4dT";

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    CurrentUser currentUserMock;

    @InjectMocks
    AuthenticationController authenticationController;

    @Captor
    private ArgumentCaptor<UserDO> userDOCaptor;

    @Test
    public void givenBlankUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("", "Pa55word", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid username", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenShortUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("SevenLe", "Pa55word", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid username", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto(null, "Pa55word", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid username", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenShortPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "pw", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoUpperPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "pa55word", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoLowerCasePassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "PA55WORD", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoNumberPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Password", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", null, "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid password", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenSimpleStringEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoDomainEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoTLDEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoLocalPartEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("invalid email address", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenNoEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", null, "01234 567890");

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

    @Test
    public void givenUnavailableUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        when(userRepositoryMock.userNameIsAvailable("alreadyTaken")).thenReturn(false);

        UserDTO userDTO = makeUserDto("alreadyTaken", "Pa55word", "test@example.com", "01234 567890");

        verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
        assertEquals("username unavailable", authenticationController.registerUser(userDTO));
    }

    @Test
    public void givenValidRegistrationDetails_whenRegisteringNewUser_thenDetailsStoredAndSuccessMessageReturned() {
        when(userRepositoryMock.userNameIsAvailable("username")).thenReturn(true);

        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example.com", "01234 567890");

        assertEquals("registration successful", authenticationController.registerUser(userDTO));
        verifyDtoStoredvalues(userDTO);
    }

    @Test
    public void givenValidRegistrationDetailsWithFullTelephoneNo_whenRegisteringNewUser_thenDetailsStoredAndSuccessMessageReturned() {
        when(userRepositoryMock.userNameIsAvailable("USERNAME")).thenReturn(true);

        UserDTO userDTO = makeUserDto("USERNAME", "Pa55word", "test@example.com", "+441234 567890");

        assertEquals("registration successful", authenticationController.registerUser(userDTO));
        verifyDtoStoredvalues(userDTO);
    }

    @Test
    public void givenAuthenticationDetailsForRegisteredUser_whenAuthenticating_thenSuccessMessageIsReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", null, null);

        when(userRepositoryMock.getStoredPasswordForUser("username")).thenReturn(ENCRYPTED_PASSWORD);

        assertEquals("authentication successful", authenticationController.authenticateUser(userDTO));
        verify(userRepositoryMock, times(1)).getStoredPasswordForUser(eq("username"));
        verify(currentUserMock, times(1)).setUsername("username");
    }

    @Test
    public void givenAuthenticationDetailsForUnregisteredUser_whenAuthenticating_thenErrorMessageIsReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", null, null);

        when(userRepositoryMock.getStoredPasswordForUser("username")).thenReturn(null);

        assertEquals("username not found", authenticationController.authenticateUser(userDTO));
        verify(userRepositoryMock, times(1)).getStoredPasswordForUser(eq("username"));
        verify(currentUserMock, times(0)).setUsername("username");
    }

    @Test
    public void givenIncorrectAuthenticationDetailsForRegisteredUser_whenAuthenticating_thenErrorMessageIsReturned() {
        UserDTO userDTO = makeUserDto("username", "1ncorrectPa55word", null, null);

        when(userRepositoryMock.getStoredPasswordForUser("username")).thenReturn(ENCRYPTED_PASSWORD);

        assertEquals("incorrect password", authenticationController.authenticateUser(userDTO));
        verify(userRepositoryMock, times(1)).getStoredPasswordForUser(eq("username"));
        verify(currentUserMock, times(0)).setUsername("username");
    }

    @Test
    public void givenAuthenticatedUser_whenUpdatingEmailAddAndTelNo_thenStoredAndSuccessMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, "newtest@example.com", "+449876 543210");

        assertEquals("account updated successfully", authenticationController.updateUserDetails(userDTO));
        verifyDtoUpdatedvalues(userDTO);
    }

    @Test
    public void givenAuthenticatedUser_whenUpdatingEmailAdd_thenStoredAndSuccessMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, "newtest@example.com", null);

        assertEquals("account updated successfully", authenticationController.updateUserDetails(userDTO));
        verifyDtoUpdatedvalues(userDTO);
    }

    private void verifyDtoStoredvalues(UserDTO userDTO) {
        verify(userRepositoryMock, times(1)).storeUser(userDOCaptor.capture());
        UserDO storedDO = userDOCaptor.getValue();
        assertEquals(storedDO.getUsername(), userDTO.getUsername());
        assertNotEquals(storedDO.getPassword(), userDTO.getPassword());
        assertEquals(storedDO.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(storedDO.getTelephoneNumber(), userDTO.getTelephoneNumber());
    }

    private void verifyDtoUpdatedvalues(UserDTO userDTO) {
        verify(userRepositoryMock, times(1)).updateUser(userDOCaptor.capture());
        UserDO storedDO = userDOCaptor.getValue();
        assertEquals(storedDO.getUsername(), userDTO.getUsername());
        assertEquals(storedDO.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(storedDO.getTelephoneNumber(), userDTO.getTelephoneNumber());
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
