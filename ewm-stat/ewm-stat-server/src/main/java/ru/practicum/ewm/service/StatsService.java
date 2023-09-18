package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.dao.StatsRepository;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.model.entity.ViewStats;
import ru.practicum.ewm.model.mapper.EntityDtoMapper;
import ru.practicum.ewm.model.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.model.mapper.EntityDtoMapper.getEntityFrom;

@Service
public class StatsService {

    private final StatsRepository storage;

    @Autowired
    public StatsService(StatsRepository storage) {
        this.storage = storage;
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Response addEndpointHit(EndpointHitDto endpointHitDto) {
        storage.addHit(getEntityFrom(endpointHitDto));
        return new Response("Информация сохранена");
    }

    public List<ViewStatsDto> getStatsByParameter(String startString,
                                                  String endString,
                                                  List<String> uris,
                                                  Boolean isUnique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startString, formatter);
        LocalDateTime end = LocalDateTime.parse(endString, formatter);
        return storage.getHitsByParameter(start, end, uris, isUnique).stream()
                .sorted((Comparator.comparing(ViewStats::getHits).reversed()))
                .map(EntityDtoMapper::getViewStatsDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
