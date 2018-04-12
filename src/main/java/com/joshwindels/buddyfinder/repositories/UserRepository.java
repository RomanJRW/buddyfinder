package com.joshwindels.buddyfinder.repositories;

import com.joshwindels.buddyfinder.dos.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void storeUser(UserDO userDO) {

    }

    public boolean userNameIsAvailable(String username) {

        return true;
    }

    public String getStoredPasswordForUser(String username) {

        return null;
    }

    public void updateUser(UserDO userDO) {

    }
}
