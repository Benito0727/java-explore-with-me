package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.FullEventDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.ShortEventDto;
import ru.practicum.ewm.model.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventEntityDtoMapper {

    public static FullEventDto mappingFullDtoFrom(Event event) {
        FullEventDto dto = new FullEventDto();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryEntityDtoMapper.mappingDtoFrom(event.getCategory()));
        dto.setCreatedOn(event.getCreatedOn());
        dto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dto.setDescription(event.getDescription());
        dto.setInitiator(UserEntityDtoMapper.mappingShortDtoFrom(event.getInitiator()));
        dto.setLocation(LocationEntityDtoMapper.mappingDtoFrom(event.getLocation()));
        dto.setPaid(event.getIsPaid());
        dto.setParticipantLimit(event.getParticipantLimit());
        if (event.getPublishedOn() != null) {
            dto.setPublishedOn(event.getPublishedOn().toString());
        }
        dto.setRequestModeration(event.getIsRequestModeration());
        if (event.getState() != null) {
            dto.setState(event.getState());
        }

        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setTitle(event.getTitle());
        dto.setViews(event.getViews());
        return dto;
    }

    public static ShortEventDto mappingShortDtoFrom(Event event) {
        ShortEventDto dto = new ShortEventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setAnnotation(event.getAnnotation());
        dto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dto.setPaid(event.getIsPaid());
        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setViews(event.getViews());
        dto.setCategory(CategoryEntityDtoMapper.mappingDtoFrom(event.getCategory()));
        dto.setInitiator(UserEntityDtoMapper.mappingShortDtoFrom(event.getInitiator()));
        return dto;
    }

    public static Event mappingEntityFrom(NewEventDto dto) {
        Event event = new Event();
        event.setDescription(dto.getDescription());
        event.setTitle(dto.getTitle());
        if (dto.getPaid() != null) {
            event.setIsPaid(dto.getPaid());
        } else {
            event.setIsPaid(false);
        }
        event.setCreatedOn(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (dto.getRequestModeration() != null) {
            event.setIsRequestModeration(dto.getRequestModeration());
        } else {
            event.setIsRequestModeration(true);
        }
        event.setAnnotation(dto.getAnnotation());
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        } else {
            event.setParticipantLimit(0L);
        }

        return event;
    }
}
