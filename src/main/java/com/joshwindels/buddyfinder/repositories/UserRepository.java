package com.joshwindels.buddyfinder.repositories;

import com.joshwindels.buddyfinder.dos.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void storeUser(UserDO userDO) {
        String sql = " INSERT INTO users (username, password, email_address, telephone_no) "
                + "    VALUES (:username, :password, :emailAddress, :telephoneNumber) ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userDO.getUsername());
        params.addValue("password", userDO.getPassword());
        params.addValue("emailAddress", userDO.getEmailAddress());
        params.addValue("telephoneNumber", userDO.getTelephoneNumber());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public boolean userNameIsAvailable(String username) {
        String sql = " SELECT NOT exists(SELECT 1 FROM users WHERE username = :username)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public String getStoredPasswordForUser(String username) {
        String sql = " SELECT password FROM users where username = :username ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
    }

    public void updateUser(UserDO userDO) {
        String sql = "";
        if (userDO.getEmailAddress() != null && userDO.getTelephoneNumber() != null) {
            sql = "     UPDATE users SET email_address = :emailAddress, "
                    + "     telephone_no = :telephoneNumber "
                    + " WHERE username = :username ";
        } else if (userDO.getEmailAddress() != null) {
            sql = "     UPDATE users SET email_address = :emailAddress "
                    + " WHERE username = :username ";
        } else if (userDO.getTelephoneNumber() != null) {
            sql = "     UPDATE users SET telephone_no = :telephoneNumber "
                    + " WHERE username = :username ";
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userDO.getUsername());
        params.addValue("emailAddress", userDO.getEmailAddress());
        params.addValue("telephoneNumber", userDO.getTelephoneNumber());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public Integer getIdForUsername(String username) {
        String sql = " SELECT id FROM users where username = :username LIMIT 1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
