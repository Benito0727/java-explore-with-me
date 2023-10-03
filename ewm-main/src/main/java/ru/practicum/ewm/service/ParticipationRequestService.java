package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dao.ParticipationRequestRepository;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.ParticipationRequest;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.model.mapper.RequestEntityDtoMapper;
import ru.practicum.ewm.model.state.EventState;
import ru.practicum.ewm.model.state.RequestState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipationRequestService {

    private final ParticipationRequestRepository storage;

    private final EventRepository eventStorage;

    private final ObjectChecker checker;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String CURRENT_DATE_TIME = LocalDateTime.now().format(DATE_TIME_FORMATTER);

    @Autowired
    public ParticipationRequestService(ParticipationRequestRepository storage,
                                       EventRepository eventStorage,
                                       ObjectChecker checker) {
        this.storage = storage;
        this.eventStorage = eventStorage;
        this.checker = checker;
    }

    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        Event event = checker.checkEvent(eventId);
        User user = checker.checkUser(userId);
        if (!event.getState().equals(EventState.PUBLISHED.toString())) {
            throw new ConflictException("You can only apply for a published event",
                    "Incorrectly event state", CURRENT_DATE_TIME);
        }
        if (event.getInitiator().equals(user))
            throw new ConflictException("You can't apply to participate in your own event",
                    "Incorrectly request", CURRENT_DATE_TIME);

        Long memberLimit = event.getParticipantLimit();

        List<ParticipationRequest> confirmedRequests = storage
                .findAllByEventIdAndState(eventId, RequestState.CONFIRMED.toString());

        if (memberLimit != 0 && memberLimit == confirmedRequests.size()) {
            throw new ConflictException("The limit of participants of this event has already been reached",
                    "Incorrectly request", CURRENT_DATE_TIME);
        }

        Optional<ParticipationRequest> request = storage
                .findParticipationRequestsByRequesterIdAndEventId(userId, eventId);

        if (request.isPresent()) {
            throw new ConflictException("You cannot apply to participate" +
                    " in an event for which you have already applied.",
                    "Incorrectly request", CURRENT_DATE_TIME);
        }

        ParticipationRequest newRequest = new ParticipationRequest();

        newRequest.setRequester(user);
        newRequest.setEventId(eventId);
        if (event.getIsRequestModeration()) {
            newRequest.setState(RequestState.PENDING.toString());
        }
        if (event.getParticipantLimit() == 0 || !event.getIsRequestModeration()) {
            newRequest.setState(RequestState.CONFIRMED.toString());
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        newRequest.setCreatedOn(LocalDateTime.parse(CURRENT_DATE_TIME, DATE_TIME_FORMATTER));
        eventStorage.save(event);
        return RequestEntityDtoMapper.mappingDtoFromEntity(storage.save(newRequest));
    }

    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        User user = checker.checkUser(userId);
        List<ParticipationRequest> requestsList = storage.findAllByRequesterId(user.getId());
        return requestsList.stream()
                .map(RequestEntityDtoMapper::mappingDtoFromEntity)
                .collect(Collectors.toList());
    }

    public ParticipationRequestDto canceledRequest(Long userId, Long requestId) {
        User user = checker.checkUser(userId);
        ParticipationRequest request = checker.checkRequest(requestId);

        request.setState(RequestState.CANCELED.toString());

        return RequestEntityDtoMapper.mappingDtoFromEntity(request);
    }
}
