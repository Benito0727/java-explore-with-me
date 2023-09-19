package ru.practicum.ewm.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.practicum.ewm.model.entity.ViewStats;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewStatsMapper implements RowMapper<ViewStats> {

    @Override
    public ViewStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        ViewStats viewStats = new ViewStats();
        viewStats.setHits(rs.getLong(1));
        viewStats.setApp(rs.getString(2));
        viewStats.setUri(rs.getString(3));
        return viewStats;
    }
}
