package com.joshwindels.buddyfinder.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.joshwindels.buddyfinder.dos.ExcursionDO;
import com.joshwindels.buddyfinder.helpers.FilterTypes;
import com.joshwindels.buddyfinder.rowmappers.ExcursionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExcursionRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    ExcursionRowMapper excursionRowMapper;

    public void storeExcursion(ExcursionDO excursionDO) {
        String sql = " INSERT INTO excursions (owner_id, name, start_location, finish_location, "
                + "    start_date, end_date, est_cost_pp_sterling, required_buddies, description) "
                + "    VALUES (:owner_id, :name, :start_location, :finish_location, "
                + "    :start_date, :end_date, :est_cost_pp_sterling, :required_buddies, :description) ";

        MapSqlParameterSource params = getParameters(excursionDO);

        namedParameterJdbcTemplate.update(sql, params);
    }

    public void updateExcursion(ExcursionDO excursionDO) {
        String sql = " UPDATE excursions"
                + "       SET owner_id = :owner_id, "
                + "           name = :name, "
                + "           start_location = :start_location, "
                + "           finish_location = :finish_location, "
                + "           start_date = :start_date, "
                + "           end_date = :end_date, "
                + "           est_cost_pp_sterling = :est_cost_pp_sterling, "
                + "           required_buddies = :required_buddies, "
                + "           description = :description ) ";

        MapSqlParameterSource params = getParameters(excursionDO);

        namedParameterJdbcTemplate.update(sql, params);
    }

    public Optional<ExcursionDO> getExcursionForId(int excursionId) {
        String sql = " SELECT * FROM excursions WHERE id = :id LIMIT 1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", excursionId);
        try {
            return Optional.of((ExcursionDO) namedParameterJdbcTemplate.queryForObject(sql, params, excursionRowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public void deleteExcursion(int excursionId) {
        String sql = " DELETE FROM excursions WHERE id = :id LIMIT 1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", excursionId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<ExcursionDO> getExcursionsMatchingFilterParameters(Map<FilterTypes, Object> filterParameters) {
        return null;
    }

    private MapSqlParameterSource getParameters(ExcursionDO excursionDO) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("owner_id", excursionDO.getOwnerId());
        params.addValue("name", excursionDO.getName());
        params.addValue("start_location", excursionDO.getStartLocation());
        params.addValue("finish_location", excursionDO.getEndLocation());
        params.addValue("start_date", excursionDO.getStartDate());
        params.addValue("end_date", excursionDO.getEndDate());
        params.addValue("est_cost_pp_sterling", excursionDO.getEstimatedCost());
        params.addValue("required_buddies", excursionDO.getRequiredBuddies());
        params.addValue("description", excursionDO.getDescription());
        return params;
    }
}
