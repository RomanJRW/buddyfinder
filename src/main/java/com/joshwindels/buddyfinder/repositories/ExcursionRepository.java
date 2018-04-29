package com.joshwindels.buddyfinder.repositories;

import java.util.List;
import java.util.Map;
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

    public void updateExcursion(ExcursionDO excursionDO) {
    }

    public Optional<ExcursionDO> getExcursionForId(int excursionId) {
        return Optional.empty();
    }

    public void deleteExcursion(int excursionId) {

    }

    public List<ExcursionDO> getExcursionsMatchingFilterParameters(Map<String, Object> filterParameters) {
        return null;
    }
}
