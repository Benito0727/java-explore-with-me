package ru.practicum.ewm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.practicum.ewm.dao.mapper.ViewStatsMapper;
import ru.practicum.ewm.model.entity.EndpointHit;
import ru.practicum.ewm.model.entity.ViewStats;

import java.time.LocalDateTime;

import java.util.LinkedList;
import java.util.List;

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
        List<ViewStats> views = new LinkedList<>();
        if (!unique) {
            String sqlQuery = "SELECT COUNT(h.hit_id) AS hits, " +
                    "h.app_name, " +
                    "h.hit_uri " +
                    "FROM endpoint_hits AS h " +
                    "WHERE (h.hit_date BETWEEN ? AND ?) " +
                    "AND h.hit_uri = ? " +
                    "GROUP BY h.app_name, h.hit_uri " +
                    "ORDER BY hits DESC";
            try {
                if (uris != null) {
                    for (String uri : uris) {
                        views.addAll(template.query(sqlQuery, new ViewStatsMapper(), start, end, uri));
                    }
                } else {
                    sqlQuery = "SELECT COUNT(h.hit_id) AS hits," +
                            "h.app_name," +
                            "h.hit_uri " +
                            "FROM endpoint_hits AS h " +
                            "WHERE (h.hit_date BETWEEN ? AND ?) " +
                            "GROUP BY h.app_name, h.hit_uri " +
                            "ORDER BY hits DESC";
                    views.addAll(template.query(sqlQuery, new ViewStatsMapper(), start, end));
                }
            } catch (BadSqlGrammarException exception) {
                throw new RuntimeException(exception);
            }
        } else {
            String sqlQuery = "SELECT COUNT(DISTINCT h.client_ip) AS hits, " +
                    "h.app_name, " +
                    "h.hit_uri " +
                    "FROM endpoint_hits AS h " +
                    "WHERE (hit_date BETWEEN ? AND ?) " +
                    "AND hit_uri = ? " +
                    "GROUP BY h.app_name, h.hit_uri, h.client_ip " +
                    "ORDER BY hits DESC";
            try {
                if (uris != null) {
                    for (String uri : uris) {
                        views.addAll(template.query(sqlQuery, new ViewStatsMapper(), start, end, uri));
                    }
                } else {
                    sqlQuery = "SELECT COUNT(DISTINCT h.client_ip) AS hits, " +
                            "h.app_name, " +
                            "h.hit_uri " +
                            "FROM endpoint_hits AS h " +
                            "WHERE (hit_date BETWEEN ? AND ?) " +
                            "GROUP BY h.app_name, h.hit_uri, h.client_ip " +
                            "ORDER BY hits DESC";
                    views.addAll(template.query(sqlQuery, new ViewStatsMapper(), start, end));
                }
            } catch (BadSqlGrammarException exception) {
                throw new RuntimeException(exception);
            }
        }

        return views;
    }
}
