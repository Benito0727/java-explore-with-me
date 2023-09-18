package ru.practicum.ewm.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.practicum.ewm.model.entity.EndpointHit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EndpointHitMapper implements RowMapper<EndpointHit> {

    @Override
    public EndpointHit mapRow(ResultSet rs, int rowNum) throws SQLException {

        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setId(rs.getLong("hit_id"));
        endpointHit.setApp(rs.getString("app_name"));
        endpointHit.setUri(rs.getString("client_ip"));
        endpointHit.setDate(LocalDateTime.parse(rs.getString("hit_date")));
        return endpointHit;

    }
}
