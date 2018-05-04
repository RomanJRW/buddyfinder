package com.joshwindels.buddyfinder.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.joshwindels.buddyfinder.dos.InterestedUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InterestedUserRowMapper implements RowMapper<InterestedUser> {

    @Override
    public InterestedUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        InterestedUser interestedUser = new InterestedUser();
        interestedUser.setUsername(rs.getString("username"));
        interestedUser.setEmailAddress(rs.getString("email_address"));
        interestedUser.setEmailAddress(rs.getString("telephone_number"));
        return interestedUser;
    }
}
