package com.joshwindels.buddyfinder.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dos.UserDO;
import com.joshwindels.buddyfinder.dtos.UserDTO;
import com.joshwindels.buddyfinder.helpers.AuthenticationHelper;
import com.joshwindels.buddyfinder.repositories.UserRepository;
import org.junit.Before;
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

    @Mock
    AuthenticationHelper authenticationHelper;

    @InjectMocks
    AuthenticationController authenticationController;

    @Captor
    private ArgumentCaptor<UserDO> userDOCaptor;

    @Before
    public void setup() {
        when(authenticationHelper.convertToUserDO(any(UserDTO.class))).thenCallRealMethod();
        when(authenticationHelper.emailAddressIsValid(anyString())).thenCallRealMethod();
        when(authenticationHelper.telephoneNumberIsValid(anyString())).thenCallRealMethod();
        when(authenticationHelper.isValidAuthenticationDetails(anyString(), anyString())).thenCallRealMethod();
        when(authenticationHelper.getEncryptedPassword(anyString())).thenCallRealMethod();
    }

    @Test(expected = RuntimeException.class)
    public void givenBlankUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("", "Pa55word", "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid username")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid username");
    }

    @Test(expected = RuntimeException.class)
    public void givenShortUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("SevenLe", "Pa55word", "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid username")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid username");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto(null, "Pa55word", "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid username")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid username");
    }

    @Test(expected = RuntimeException.class)
    public void givenShortPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "pw", "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid password")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid password");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoUpperPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "pa55word", "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid password")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid password");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoLowerCasePassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "PA55WORD", "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid password")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid password");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoNumberPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Password", "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid password")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid password");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoPassword_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", null, "test@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid password")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid password");
    }

    @Test(expected = RuntimeException.class)
    public void givenSimpleStringEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test", "01234 567890");
        doThrow(new RuntimeException("invalid email address")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoDomainEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@", "01234 567890");
        doThrow(new RuntimeException("invalid email address")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoTLDEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example", "01234 567890");
        doThrow(new RuntimeException("invalid email address")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoLocalPartEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "@example.com", "01234 567890");
        doThrow(new RuntimeException("invalid email address")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoEmailAddress_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", null, "01234 567890");
        doThrow(new RuntimeException("invalid email address")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenEmptyTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example.com", "");
        doThrow(new RuntimeException("invalid telephone number")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid telephone number");
    }

    @Test(expected = RuntimeException.class)
    public void givenCharacterStringTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example.com", "abcdefghi");
        doThrow(new RuntimeException("invalid telephone number")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid telephone number");
    }

    @Test(expected = RuntimeException.class)
    public void givenNoTelephoneNumber_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", "test@example.com", null);
        doThrow(new RuntimeException("invalid telephone number")).when(authenticationHelper).validateUser(any(UserDTO.class));

        verifyRegistrationError(userDTO, "invalid telephone number");
    }

    @Test(expected = RuntimeException.class)
    public void givenUnavailableUsername_whenRegisteringNewUser_thenDetailsNotStoredAndErrorMessageReturned() {
        when(userRepositoryMock.userNameIsAvailable("alreadyTaken")).thenReturn(false);

        UserDTO userDTO = makeUserDto("alreadyTaken", "Pa55word", "test@example.com", "01234 567890");

        verifyRegistrationError(userDTO, "username unavailable");
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

    @Test(expected = RuntimeException.class)
    public void givenAuthenticationDetailsForUnregisteredUser_whenAuthenticating_thenErrorMessageIsReturned() {
        UserDTO userDTO = makeUserDto("username", "Pa55word", null, null);

        when(userRepositoryMock.getStoredPasswordForUser("username")).thenReturn(null);

        verifyAuthenticationError(userDTO, "username not found");
    }

    @Test(expected = RuntimeException.class)
    public void givenIncorrectAuthenticationDetailsForRegisteredUser_whenAuthenticating_thenErrorMessageIsReturned() {
        UserDTO userDTO = makeUserDto("username", "1ncorrectPa55word", null, null);

        when(userRepositoryMock.getStoredPasswordForUser("username")).thenReturn(ENCRYPTED_PASSWORD);

        verifyAuthenticationError(userDTO, "incorrect password");
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

    @Test(expected = RuntimeException.class)
    public void givenAuthenticatedUser_whenUpdatingWithInvalidEmailAdd_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, "newtest", null);

        verifyUpdateError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenAuthenticatedUser_whenUpdatingWithNoDomainEmailAdd_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, "newtest@", null);

        verifyUpdateError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenAuthenticatedUser_whenUpdatingWithNoTLDEmailAdd_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, "newtest@example", null);

        verifyUpdateError(userDTO, "invalid email address");
    }

    @Test(expected = RuntimeException.class)
    public void givenAuthenticatedUser_whenUpdatingWithNoFirstPartEmailAdd_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, "@example.com", null);

        verifyUpdateError(userDTO, "invalid email address");
    }

    @Test
    public void givenAuthenticatedUser_whenUpdatingTelNo_thenStoredAndSuccessMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, null, "09876 543210");

        assertEquals("account updated successfully", authenticationController.updateUserDetails(userDTO));
        verifyDtoUpdatedvalues(userDTO);
    }

    @Test
    public void givenAuthenticatedUser_whenUpdatingExtendedTelNo_thenStoredAndSuccessMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, null, "+449876 543210");

        assertEquals("account updated successfully", authenticationController.updateUserDetails(userDTO));
        verifyDtoUpdatedvalues(userDTO);
    }

    @Test(expected = RuntimeException.class)
    public void givenAuthenticatedUser_whenUpdatingWithInvalidTelNo_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");
        UserDTO userDTO = makeUserDto("username", null, null, "asdfgh");

        verifyUpdateError(userDTO, "invalid telephone number");
    }

    @Test(expected = RuntimeException.class)
    public void givenUnauthenticatedUser_whenUpdatingEmailAddAndTelNo_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        UserDTO userDTO = makeUserDto("username", null, "newtest@example.com", "+449876 543210");

        verifyUpdateError(userDTO, "not authenticated");
    }

    @Test(expected = RuntimeException.class)
    public void givenUnauthenticatedUser_whenUpdatingEmailAdd_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        UserDTO userDTO = makeUserDto("username", null, "newtest@example.com", null);

        verifyUpdateError(userDTO, "not authenticated");
    }

    @Test(expected = RuntimeException.class)
    public void givenUnauthenticatedUser_whenUpdatingTelNo_thenNotStoredAndErrorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);
        UserDTO userDTO = makeUserDto("username", null, null, "+449876 543210");

        verifyUpdateError(userDTO, "not authenticated");
    }

    @Test
    public void givenAuthenticatedUser_whenDeauthenticating_userIsDeauthenticatedAndSuccessMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn("username");

        assertEquals("deauthenticated", authenticationController.deuathenticateUser());
        verify(currentUserMock, times(1)).setUsername(null);
    }

    @Test(expected = RuntimeException.class)
    public void givenUnauthenticatedUser_whenDeauthenticating_errorMessageIsReturned() {
        when(currentUserMock.getUsername()).thenReturn(null);

        verifyDeauthenticationError("not authenticated");
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

    private void verifyRegistrationError(UserDTO userDTO, String errorMessage) {
        try {
            authenticationController.registerUser(userDTO);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(userRepositoryMock, never()).storeUser(any(UserDO.class));
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }


    private void verifyAuthenticationError(UserDTO userDTO, String errorMessage) {
        try {
            authenticationController.authenticateUser(userDTO);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(userRepositoryMock, times(1)).getStoredPasswordForUser(eq("username"));
            verify(currentUserMock, times(0)).setUsername("username");
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }

    private void verifyUpdateError(UserDTO userDTO, String errorMessage) {
        try {
            authenticationController.updateUserDetails(userDTO);
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(userRepositoryMock, times(0)).updateUser(any(UserDO.class));
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }

    private void verifyDeauthenticationError(String errorMessage) {
        try {
            authenticationController.deuathenticateUser();
        } catch (RuntimeException ex) {
            assertEquals(errorMessage, ex.getMessage());
            verify(currentUserMock, times(0)).setUsername(null);
            throw ex;
        }
        fail("expected Exception wasn't thrown");
    }
}
