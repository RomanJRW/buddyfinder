package com.joshwindels.buddyfinder.helpers;

import java.security.NoSuchAlgorithmException;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.exception.BadOperationException;
import com.amdelamar.jhash.exception.InvalidHashException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.joshwindels.buddyfinder.dos.UserDO;
import com.joshwindels.buddyfinder.dtos.UserDTO;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {

    public UserDO convertToUserDO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null) {
            String encryptedPassword = getEncryptedPassword(userDTO.getPassword());
            userDO.setPassword(encryptedPassword);
        }
        userDO.setEmailAddress(userDTO.getEmailAddress());
        userDO.setTelephoneNumber(userDTO.getTelephoneNumber());
        return userDO;
    }

    public String getEncryptedPassword(String password) {
        try {
            return Hash.create(password);
        } catch (BadOperationException | NoSuchAlgorithmException ex) {
            throw new RuntimeException("There was a problem creating account");
        }
    }

    public boolean isValidAuthenticationDetails(String password, String storedPassword) {
        try {
            return Hash.verify(password, storedPassword);
        } catch (NoSuchAlgorithmException | InvalidHashException | BadOperationException e) {
            throw new RuntimeException("There was a problem authenticating account");
        }
    }

    public void validateUser(UserDTO userDTO) {
        if (!usernameIsValid(userDTO.getUsername())) {
            throw new RuntimeException("invalid username");
        } else if (!passwordIsValid(userDTO.getPassword())) {
            throw new RuntimeException("invalid password");
        } else if (!emailAddressIsValid(userDTO.getEmailAddress())) {
            throw new RuntimeException("invalid email address");
        } else if (!telephoneNumberIsValid(userDTO.getTelephoneNumber())) {
            throw new RuntimeException("invalid telephone number");
        }
    }

    private boolean usernameIsValid(String username) {
        return username != null && username.length() >= 8;
    }

    private boolean passwordIsValid(String password) {
        return password != null && password.matches("^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})[\\s\\S]{8,}$");
    }

    public boolean emailAddressIsValid(String emailAddress) {
        return EmailValidator.getInstance().isValid(emailAddress);
    }

    public boolean telephoneNumberIsValid(String telephoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            return telephoneNumber != null &&
                    phoneUtil.isPossibleNumber(phoneUtil.parse(telephoneNumber, "GB"));
        } catch (NumberParseException e) {
            return false;
        }
    }

}
