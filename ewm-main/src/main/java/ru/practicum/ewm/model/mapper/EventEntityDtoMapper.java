package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.FullEventDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.ShortEventDto;
import ru.practicum.ewm.model.entity.*;
import ru.practicum.ewm.model.status.EventStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventEntityDtoMapper {

    public static FullEventDto mappingFullDtoFrom(Event event) {
        FullEventDto dto = new FullEventDto();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(event.getCategories().stream()
                .map(CategoryEntityDtoMapper::mappingDtoFrom).collect(Collectors.toList()));
        dto.setCreatedOn(event.getCreatedOn());
        dto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setDescription(event.getDescription());
        dto.setConfirmedRequests(event.getConfirmedRequest());
        dto.setInitiator(UserEntityDtoMapper.mappingShortDtoFrom(event.getInitiator()));
        dto.setLocation(LocationEntityDtoMapper.mappingDtoFrom(event.getLocation()));
        dto.setPaid(event.getIsPaid());
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setPublishedOn(event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setRequestModeration(event.getIsRequestModeration());
        dto.setStatus(event.getStatus().toString());
        dto.setTitle(event.getTitle());
        dto.setViews(event.getViews());
        return dto;
    }

    public static ShortEventDto mappingShortDtoFrom(Event event) {
        ShortEventDto dto = new ShortEventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setAnnotation(event.getAnnotation());
        dto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setPaid(event.getIsPaid());
        dto.setViews(event.getViews());
        dto.setCategory(event.getCategories().stream()
                .map(CategoryEntityDtoMapper::mappingDtoFrom)
                .collect(Collectors.toList()));
        dto.setConfirmedRequests(event.getConfirmedRequest());
        dto.setInitiator(UserEntityDtoMapper.mappingShortDtoFrom(event.getInitiator()));
        return dto;
    }

    public static Event mappingEntityFrom(NewEventDto dto,
                                          User initiator,
                                          Location location,
                                          List<Category> categories,
                                          List<Compilation> compilations) {
        Event event = new Event();
        event.setDescription(dto.getDescription());
        event.setTitle(dto.getTitle());
        event.setIsPaid(dto.getPaid());
        event.setStatusId(EventStatus.from(EventStatus.WAITING));
        event.setLocation(location);
        event.setCreatedOn(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(dto.getEventDate()));
        event.setInitiator(initiator);
        event.setCategories(categories);
        event.setIsRequestModeration(dto.getRequestModeration());
        event.setCompilations(Objects.requireNonNullElse(compilations, Collections.emptyList()));
        event.setAnnotation(dto.getAnnotation());
        event.setParticipantLimit(dto.getParticipantLimit());
        return event;
    }
}
