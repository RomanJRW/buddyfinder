package com.joshwindels.buddyfinder.repositories;

import java.util.Optional;

import com.joshwindels.buddyfinder.dos.ExcursionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExcursionRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void storeExcursion(ExcursionDO excursionDO) {
    }

    public void updateExcursion(ExcursionDO any) {
    }

    public Optional<ExcursionDO> getExcursionForId(int id) {
        return null;
    }
}
