package com.joshwindels.buddyfinder.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.joshwindels.buddyfinder.dos.ExcursionDO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExcursionRowMapper implements RowMapper<ExcursionDO> {

    @Override
    public ExcursionDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExcursionDO excursionDO = new ExcursionDO();
        excursionDO.setId(rs.getInt("id"));
        excursionDO.setOwnerId(rs.getInt("owner_id"));
        excursionDO.setName(rs.getString("name"));
        excursionDO.setStartLocation(rs.getString("start_location"));
        excursionDO.setEndLocation(rs.getString("finish_location"));
        excursionDO.setStartDate(rs.getDate("start_date"));
        excursionDO.setEndDate(rs.getDate("end_date"));
        excursionDO.setEstimatedCost(rs.getDouble("est_cost_pp_sterling"));
        excursionDO.setRequiredBuddies(rs.getInt("required_buddies"));
        excursionDO.setDescription(rs.getString("description"));
        return excursionDO;
    }
}
