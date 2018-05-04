package com.joshwindels.buddyfinder.repositories;

import java.util.List;

import com.joshwindels.buddyfinder.dos.InterestedUser;
import com.joshwindels.buddyfinder.rowmappers.InterestedUserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InterestRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    InterestedUserRowMapper interestedUserRowMapper;

    public void expressUserInterestInExcursion(Integer userId, int excursionId) {
        String sql = " INSERT INTO user_excursion_interest_map "
                + " (user_id, excursion_id) "
                + " VALUES (:userId, :excursionId) ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("excursionId", excursionId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<InterestedUser> getUsersInterestedInExcursion(int excursionId) {
        String sql = " SELECT * FROM users "
                + " JOIN user_excursion_interest_map ON id = user_excursion_interest_map.user_id"
                + " WHERE user_excursion_interest_map.excursion_id = :excursionId ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("excursionId", excursionId);
        return namedParameterJdbcTemplate.query(sql, params, interestedUserRowMapper);
    }
}
