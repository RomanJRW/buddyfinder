package com.joshwindels.buddyfinder.repositories;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDBRepository {

    @Autowired NamedParameterJdbcTemplate npjt;

    public Integer getFirstExcursionDetails() {
        String sql = " SELECT id FROM excursions LIMIT 1 ";
        return npjt.queryForObject(sql, Collections.emptyMap(), Integer.class);
    }

}
