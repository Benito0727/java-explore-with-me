package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.entity.ParticipationRequest;

public class RequestEntityDtoMapper {

    public static ParticipationRequestDto mappingDtoFromEntity(ParticipationRequest request) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(request.getId());
        dto.setRequester(request.getRequester().getId());
        dto.setCreated(request.getCreatedOn().toString());
        dto.setEvent(request.getEventId());
        dto.setStatus(request.getState());
        return dto;
    }
}
