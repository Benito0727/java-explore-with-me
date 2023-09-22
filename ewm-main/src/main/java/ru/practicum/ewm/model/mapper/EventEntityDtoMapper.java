package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.FullEventDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.ShortEventDto;
import ru.practicum.ewm.model.entity.*;
import ru.practicum.ewm.model.status.EventStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventEntityDtoMapper {

    public static FullEventDto mappingFullDtoFrom(Event event, Category category) {
        FullEventDto dto = new FullEventDto();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryEntityDtoMapper.mappingDtoFrom(category));
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

    public static ShortEventDto mappingShortDtoFrom(Event event, Category category) {
        ShortEventDto dto = new ShortEventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setAnnotation(event.getAnnotation());
        dto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setPaid(event.getIsPaid());
        dto.setViews(event.getViews());
        dto.setCategory(CategoryEntityDtoMapper.mappingDtoFrom(category));
        dto.setConfirmedRequests(event.getConfirmedRequest());
        dto.setInitiator(UserEntityDtoMapper.mappingShortDtoFrom(event.getInitiator()));
        return dto;
    }

    public static Event mappingEntityFrom(NewEventDto dto,
                                          User initiator,
                                          Location location) {
        Event event = new Event();
        event.setDescription(dto.getDescription());
        event.setTitle(dto.getTitle());
        event.setIsPaid(dto.getPaid());
        event.setStatusId(EventStatus.getIdFrom(EventStatus.WAITING_EVENT));
        event.setLocation(location);
        event.setCreatedOn(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        event.setInitiator(initiator);
        if (dto.getCategory() != null) event.setCategory(dto.getCategory());
        event.setIsRequestModeration(dto.getRequestModeration());
        event.setAnnotation(dto.getAnnotation());
        event.setParticipantLimit(dto.getParticipantLimit());
        return event;
    }
}
