package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.model.entity.EndpointHit;
import ru.practicum.ewm.model.entity.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntityDtoMapper {

    public static EndpointHit getEntityFrom(EndpointHitDto dto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(dto.getApp());
        endpointHit.setIp(dto.getIp());
        endpointHit.setUri(dto.getUri());
        if (dto.getTimestamp() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            endpointHit.setDate(LocalDateTime.parse(dto.getTimestamp(), formatter));
        }
        return endpointHit;
    }

    public static EndpointHitDto getDtoFrom(EndpointHit entity) {
        EndpointHitDto dto = new EndpointHitDto();
        dto.setApp(entity.getApp());
        dto.setUri(entity.getUri());
        dto.setIp(entity.getIp());
        dto.setTimestamp(entity.getDate().toString());
        return dto;
    }

    public static ViewStatsDto getViewStatsDto(ViewStats entity) {
        ViewStatsDto dto = new ViewStatsDto();
        dto.setHits(entity.getHits());
        dto.setApp(entity.getApp());
        dto.setUri(entity.getUri());
        return dto;
    }
}
