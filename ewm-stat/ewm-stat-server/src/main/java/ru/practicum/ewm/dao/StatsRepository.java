package ru.practicum.ewm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.practicum.ewm.dao.mapper.ViewStatsMapper;
import ru.practicum.ewm.model.entity.EndpointHit;
import ru.practicum.ewm.model.entity.ViewStats;

import java.time.LocalDateTime;

import java.util.*;

@Repository
public class StatsRepository {

    private final JdbcTemplate template;

    @Autowired
    public StatsRepository(JdbcTemplate template) {
        this.template = template;
    }

    public EndpointHit addHit(EndpointHit endpointHit) {

        String sqlQuery = "INSERT INTO endpoint_hits (app_name, hit_uri, client_ip, hit_date)" +
                "VALUES(?, ?, ?, ?)";

        try {
            template.update(sqlQuery,
                    endpointHit.getApp(),
                    endpointHit.getUri(),
                    endpointHit.getIp(),
                    endpointHit.getDate());
        } catch (BadSqlGrammarException exception) {
            throw new RuntimeException(exception);
        }
        return endpointHit;
    }

    public List<ViewStats> getHitsByParameter(LocalDateTime start,
                                                 LocalDateTime end,
                                                 List<String> uris,
                                                 Boolean unique) {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<>();



        if (!unique) {
            query.append("SELECT COUNT(hit_id) AS hits," +
                    " app_name," +
                    " hit_uri" +
                    " FROM endpoint_hits" +
                    " WHERE");
        } else {
            query.append("SELECT COUNT(DISTINCT client_ip) AS hits," +
                    " app_name," +
                    " hit_uri" +
                    " FROM endpoint_hits" +
                    " WHERE");
        }

        if (start != null) {
            query.append(" hit_date > ?");
            params.add(start);
            if (end != null) {
                query.append(" AND");
            }
        }
        if (end != null) {
            query.append(" hit_date < ?");
            params.add(end);
            if (uris != null) {
                query.append(" AND");
            }
        }
        if (uris != null) {
            String inSql = String.join(",", Collections.nCopies(uris.size(), "?"));
            query.append(" hit_uri IN(");
            query.append(inSql);
            query.append(")");
            params.addAll(uris);
        }

        query.append(" GROUP BY app_name, hit_uri");

        return new LinkedList<>(template.query(query.toString(), new ViewStatsMapper(), params.toArray()));
    }
}
