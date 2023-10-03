package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.EwmClient;
import ru.practicum.ewm.dao.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.model.entity.*;
import ru.practicum.ewm.model.mapper.EventEntityDtoMapper;
import ru.practicum.ewm.model.mapper.RequestEntityDtoMapper;
import ru.practicum.ewm.dto.response.Response;
import ru.practicum.ewm.model.state.EventState;
import ru.practicum.ewm.model.state.EventStateAction;
import ru.practicum.ewm.model.state.RequestState;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository storage;

    private final LocationRepository locationStorage;

    private final LocationService locationService;

    private final ParticipationRequestRepository requestStorage;

    private final ObjectChecker checker;

    private final EntityManager em;

    private final EwmClient client;

    private static final String appName = "ewm-main-service";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String CURRENT_DATE_TIME = LocalDateTime.now().format(DATE_TIME_FORMATTER);

    @Autowired
    public EventService(EventRepository storage,
                        LocationRepository locStorage,
                        LocationService locationService,
                        ParticipationRequestRepository requestStorage,
                        ObjectChecker checker,
                        EntityManager em,
                        EwmClient client) {
        this.storage = storage;
        this.locationStorage = locStorage;
        this.locationService = locationService;
        this.requestStorage = requestStorage;
        this.checker = checker;
        this.em = em;
        this.client = client;
    }

    public EventRequestStatusUpdateResult changeEventRequestsStatus(Long userId,
                                                                    Long eventId,
                                                                    EventRequestStatusUpdateRequest updateRequest) {

//        Обратите внимание:
//
//        - если для события лимит заявок равен 0 или отключена пре-модерация заявок,
//         то подтверждение заявок не требуется
//
//        - нельзя подтвердить заявку,
//         если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
//
//        - статус можно изменить только у заявок,
//         находящихся в состоянии ожидания (Ожидается код ошибки 409)
//
//        - если при подтверждении данной заявки,
//         лимит заявок для события исчерпан,
//         то все неподтверждённые заявки необходимо отклонить

        User user = checker.checkUser(userId);
        Event event = checker.checkEvent(eventId);

        long memberLimit = event.getParticipantLimit();

        Integer confirmedRequestsForEvent = event.getConfirmedRequests();

        if (memberLimit == confirmedRequestsForEvent) {
            throw new ConflictException("Maximum members reached",
                    "The maximum number of event participants has been reached", CURRENT_DATE_TIME);
        }

        if (!event.getIsRequestModeration()) memberLimit = 0;

        List<ParticipationRequest> requestList = requestStorage.findAllByIdIn(updateRequest.getRequestIds());


        for (ParticipationRequest request : requestList) {
            if ((confirmedRequestsForEvent == memberLimit) && (memberLimit != 0)) {
                request.setState(RequestState.REJECTED.toString());

            } else if (memberLimit == 0) {
                request.setState(RequestState.CONFIRMED.toString());
                if (event.getConfirmedRequests() != null) {
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                } else {
                    event.setConfirmedRequests(1);
                }
            } else {
                if (request.getEventId().equals(event.getId())) {
                    if (event.getInitiator().equals(user)) {
                        if (request.getState().equals(RequestState.PENDING.toString())) {
                            request.setState(updateRequest.getStatus());
                            if (request.getState().equals(RequestState.CONFIRMED.toString())) {
                                if (event.getConfirmedRequests() != null) {
                                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                                } else {
                                    event.setConfirmedRequests(1);
                                }
                            }
                        } else {
                            throw new ConflictException("You can confirm or reject an request only at the waiting stage",
                                    "Incorrectly state", CURRENT_DATE_TIME);
                        }
                    }
                }
            }
            requestStorage.save(request);
        }

        List<ParticipationRequestDto> requestDtoList = requestList.stream()
            .map(RequestEntityDtoMapper::mappingDtoFromEntity)
            .collect(Collectors.toList());

        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();

        for (ParticipationRequestDto dto : requestDtoList) {
            if (dto.getStatus().equals(RequestState.CONFIRMED.toString())) confirmedRequests.add(dto);
            if (dto.getStatus().equals(RequestState.REJECTED.toString())) rejectedRequests.add(dto);
        }

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setConfirmedRequests(confirmedRequests);
        result.setRejectedRequests(rejectedRequests);
        storage.save(event);
        return result;
    }

    public List<ParticipationRequestDto> getRequestsForEvent(Long userId, Long eventId) {
        checker.checkUser(userId);
        return requestStorage.findAllByEventId(eventId).stream()
                .map(RequestEntityDtoMapper::mappingDtoFromEntity)
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .collect(Collectors.toList());
    }

    public FullEventDto getUserEventById(Long userId, Long eventId) {
        User user = checker.checkUser(userId);
        Event event = checker.checkEvent(eventId);
        Category category = checker.checkCategory(event.getCategory().getId());
        if (event.getInitiator().equals(user)) {
            return EventEntityDtoMapper.mappingFullDtoFrom(event);
        } else {
            throw new ValidationException("This event doesn't belong to you",
                    "You can only get your own event", CURRENT_DATE_TIME);
        }
    }

    public FullEventDto getEventById(Long eventId, HttpServletRequest request) {
        Event event = checker.checkEvent(eventId);

        if (!event.getState().equals(EventState.PUBLISHED.toString())) {
            throw new NotFoundException(String.format("Event with id=%d not found", eventId),
                    "The required object was not found", CURRENT_DATE_TIME);
        } else {
            Integer countConfirmedRequests = requestStorage.countAllByEventIdAndState(eventId,
                    RequestState.CONFIRMED.toString());
            event.setConfirmedRequests(countConfirmedRequests);

            String uri = request.getRequestURI();

            List<ViewStatsDto> viewStatsList = client.getStats(
                    LocalDateTime.now().minusYears(1).format(DATE_TIME_FORMATTER),
                    LocalDateTime.now().plusYears(1).format(DATE_TIME_FORMATTER),
                    List.of(uri),
                    true
            );

            if (viewStatsList != null && !viewStatsList.isEmpty()) {
                event.setViews(viewStatsList.get(0).getHits());
            }

            addHit(request);
            storage.save(event);
            return EventEntityDtoMapper.mappingFullDtoFrom(event);
        }
    }


    public Page<ShortEventDto> getUserEvents(Long userId, Integer from, Integer size) {
        List<Event> events = storage.findEventsByInitiatorId(userId);
        List<ShortEventDto> shortEvents = events.stream()
                        .map(EventEntityDtoMapper::mappingShortDtoFrom)
                                .collect(Collectors.toList());
        return getPageFrom(shortEvents, from, size, null);
    }

    public FullEventDto addNewEvent(Long userId, NewEventDto dto) {
        if (LocalDateTime.now().plusHours(2)
                .isBefore(LocalDateTime.parse(dto.getEventDate(), DATE_TIME_FORMATTER))) {

            User initiator = checker.checkUser(userId);
            Location location = locationService.addNewLocation(dto.getLocation());
            Category category = null;
            if (dto.getCategory() != null) category = (checker.checkCategory(dto.getCategory()));
            Event event = EventEntityDtoMapper.mappingEntityFrom(dto);
            event.setInitiator(initiator);
            event.setLocation(location);
            event.setCategory(category);
            event.setState(EventState.PENDING.toString());
            return EventEntityDtoMapper.mappingFullDtoFrom(storage.save(event));

        } else {
            throw new BadRequestException(
                    "The date and time of the new event should be no earlier than two hours later",
                    "Incorrectly date",
                    CURRENT_DATE_TIME);
        }
    }

    public Response delete(Long eventId) {
        Event event = checker.checkEvent(eventId);
        storage.delete(checker.checkEvent(eventId));
        return new Response("Event has been deleted");
    }

    public FullEventDto adminUpdateEvent(Long eventId, UpdateEventRequest dto) {

        /*
        Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:

        - дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
        - событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
        - событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
         */

        Event event = checker.checkEvent(eventId);

        if (dto.getEventDate() != null) {
            LocalDateTime dtoEvenDate = LocalDateTime.parse(dto.getEventDate(), DATE_TIME_FORMATTER);
            if (dtoEvenDate.isBefore(LocalDateTime.now())) {
                throw new BadRequestException("The beginning of the event should be in the future",
                        "Incorrectly date time",
                        CURRENT_DATE_TIME);
            }
            if (event.getPublishedOn() != null) {
                if (event.getPublishedOn().isAfter(dtoEvenDate.plusHours(1))) {
                    event.setEventDate(dtoEvenDate);
                } else {
                    throw new BadRequestException("the start date of the event must" +
                            " be no earlier than an hour from the date of publication",
                            "Incorrectly date time",
                            CURRENT_DATE_TIME);
                }
            } else {
                event.setEventDate(dtoEvenDate);
            }

        }

        if (dto.getPaid() != null) event.setIsPaid(dto.getPaid());

        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());

        if (dto.getRequestModeration() != null) event.setIsRequestModeration(dto.getRequestModeration());

        updateEvent(event, dto);

        if (dto.getStateAction() != null) {
            if (dto.getStateAction().equals(EventStateAction.PUBLISH_EVENT.toString())) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                if (currentDateTime.plusHours(1).isBefore(event.getEventDate())) {
                    if (event.getState().equals(EventState.PUBLISHED.toString())) {
                        throw new ConflictException("You cannot publish an event that has already been published",
                                "Incorrectly action", CURRENT_DATE_TIME);
                    }
                    if (event.getState().equals(EventState.CANCELED.toString())) {
                        throw new ConflictException("An event canceled by the initiator cannot be published",
                                "Incorrectly action", CURRENT_DATE_TIME);
                    }
                    event.setState(EventState.PUBLISHED.toString());
                    event.setPublishedOn(currentDateTime);
                } else {
                    throw new ConflictException("The event can be published no later than an hour before its start",
                            "Incorrectly date time", CURRENT_DATE_TIME);
                }
            }
            if (dto.getStateAction().equals(EventStateAction.REJECT_EVENT.toString())) {
                if (event.getState().equals(EventState.PUBLISHED.toString())) {
                    throw new ConflictException("You cannot reject a published event",
                            "Incorrectly action", CURRENT_DATE_TIME);
                }
                event.setState(EventState.CANCELED.toString());
            }
        }

        return EventEntityDtoMapper.mappingFullDtoFrom(storage.save(event));
    }

    public FullEventDto userUpdateEvent(Long userId, Long eventId, UpdateEventRequest dto) {

        //Обратите внимание:
        //
        // изменить можно только отмененные события
        // или события в состоянии ожидания модерации (Ожидается код ошибки 409)

        //дата и время на которые намечено событие не может быть раньше,
        // чем через два часа от текущего момента (Ожидается код ошибки 409)

        Event event = checker.checkEvent(eventId);
        User user = checker.checkUser(userId);

        if (event.getInitiator() != user) {
            throw new ValidationException("You can't edit something that doesn't belong to you",
                    "The user is not the initiator", CURRENT_DATE_TIME);
        }

        if (!event.getState().equals(EventState.PUBLISHED.toString())) {
            if (dto.getAnnotation() != null) event.setAnnotation(dto.getAnnotation());

            if (dto.getEventDate() != null) {
                LocalDateTime dtoEventDate = LocalDateTime.parse(dto.getEventDate(), DATE_TIME_FORMATTER);

                if (dtoEventDate.isBefore(LocalDateTime.now())) {
                    throw new BadRequestException("The beginning of the event should be in the future",
                            "Incorrectly date time",
                            CURRENT_DATE_TIME);
                }
                if (dtoEventDate.isBefore(LocalDateTime.now().plusHours(2))) {
                    event.setEventDate(dtoEventDate);
                } else {
                    throw new ConflictException("The date of the event should not be earlier than in two hours",
                            "Incorrectly date-time", CURRENT_DATE_TIME);
                }
            }

            if (dto.getStateAction() != null) {
                if (dto.getStateAction().equals(EventStateAction.SEND_TO_REVIEW.toString())) {
                    event.setState(EventState.PENDING.toString());
                }

                if (dto.getStateAction().equals(EventStateAction.CANCEL_REVIEW.toString())) {
                    event.setState(EventState.CANCELED.toString());
                }
            }

            updateEvent(event, dto);

            return EventEntityDtoMapper.mappingFullDtoFrom(storage.save(event));
        } else {
            throw new ConflictException("Only canceled or rejected events can be changed",
                    "Incorrectly state", CURRENT_DATE_TIME);
        }
    }

    private void updateEvent(Event event, UpdateEventRequest dto) {

        if (dto.getCategory() != null) event.setCategory(checker.checkCategory(dto.getCategory()));

        if (dto.getAnnotation() != null) {
            if (dto.getAnnotation().length() < 20) {
                throw new BadRequestException("the annotation cannot be shorter than 20 characters",
                        "Incorrectly annotation",
                        CURRENT_DATE_TIME);
            }
            if (dto.getAnnotation().length() > 2000) {
                throw new BadRequestException("the annotation cannot be longer than 2000 characters",
                        "Incorrectly annotation",
                        CURRENT_DATE_TIME);
            }
            event.setAnnotation(dto.getAnnotation());
        }

        if (dto.getDescription() != null) {
            if (dto.getDescription().length() < 20) {
                throw new BadRequestException("the description cannot be shorter than 20 characters",
                        "Incorrectly description",
                        CURRENT_DATE_TIME);
            }
            if (dto.getDescription().length() > 7000) {
                throw new BadRequestException("the description cannot be longer than 7000 characters",
                        "Incorrectly description",
                        CURRENT_DATE_TIME);
            }
            event.setDescription(dto.getDescription());
        }
        if (dto.getLocation() != null) {
            Location location = new Location(dto.getLocation().getLat(), dto.getLocation().getLon());
            event.setLocation(locationStorage.save(location));
        }

        if (dto.getPaid() != null) event.setIsPaid(dto.getPaid());

        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());

        if (dto.getRequestModeration() != null) event.setIsRequestModeration(dto.getRequestModeration());

        if (dto.getTitle() != null) {
            if (dto.getTitle().length() < 3) {
                throw new BadRequestException("the title cannot be shorter than 3 characters",
                        "Incorrectly title",
                        CURRENT_DATE_TIME);
            }
            if (dto.getTitle().length() > 120) {
                throw new BadRequestException("the title cannot be longer than 120 characters",
                        "Incorrectly title",
                        CURRENT_DATE_TIME);
            }
            event.setTitle(dto.getTitle());
        }
    }

    public Page<ShortEventDto> getEventsByParamsFromPublic(SearchParams params,
                                                           Integer from,
                                                           Integer size,
                                                           HttpServletRequest request) {

        if (params.getRangeStart() != null && params.getRangeEnd() != null) {
            LocalDateTime start = LocalDateTime.parse(params.getRangeStart(), DATE_TIME_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(params.getRangeEnd(), DATE_TIME_FORMATTER);
            if (start.isAfter(end)) throw new BadRequestException("The beginning cannot be later than the end",
                    "Incorrectly date time", CURRENT_DATE_TIME);
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);

        Root<Event> root = cq.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getText() != null) {

            Predicate title = cb.like(root.get("title"), "%" + params.getText() + "%");
            Predicate annotation = cb.like(root.get("annotation"), "%" + params.getText() + "%");

            Predicate titleOrAnnotation = cb.or(title, annotation);

            predicates.add(titleOrAnnotation);
        }

        if (params.getCategoriesId() != null && !params.getCategoriesId().isEmpty()) {
            predicates.add(cb.isTrue(root.get("category").in(params.getCategoriesId())));
        }

        if (params.getIsPaid() != null) {
            predicates.add(cb.equal(root.get("isPaid"), params.getIsPaid()));
        }

        if (params.getRangeStart() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("eventDate"),
                    LocalDateTime.parse(params.getRangeStart(), DATE_TIME_FORMATTER)));
        }

        if (params.getRangeEnd() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("eventDate"),
                    LocalDateTime.parse(params.getRangeEnd(), DATE_TIME_FORMATTER)));
        }
        if (params.getIsPaid() != null) {
            predicates.add(cb.equal(root.get("state"), EventState.PUBLISHED.toString()));
        }

        cq.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        List<Event> events = em.createQuery(cq).getResultList();

        List<ShortEventDto> shortEventsList = events.stream()
                .map(EventEntityDtoMapper::mappingShortDtoFrom)
                .collect(Collectors.toList());

        addHit(request);

        return getPageFrom(shortEventsList, from, size, params.getSort());
    }

    public Page<FullEventDto> getEventsByParamsFromAdmin(SearchParams params,
                                                         Integer from,
                                                         Integer size) {


        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);

        Root<Event> root = cq.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getUsersId() != null) {
            predicates.add(cb.isTrue(root.get("initiator").in(params.getUsersId())));
        }

        if (params.getStates() != null) {
            predicates.add(cb.isTrue(root.get("state").in(params.getStates())));
        }

        if (params.getCategoriesId() != null) {
            predicates.add(cb.isTrue(root.get("category").in(params.getCategoriesId().toArray())));
        }

        if (params.getRangeStart() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("eventDate"), LocalDateTime.parse(params.getRangeStart(),
                    DATE_TIME_FORMATTER)));
        }

        if (params.getRangeEnd() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("eventDate"), LocalDateTime.parse(params.getRangeEnd(),
                    DATE_TIME_FORMATTER)));
        }

        cq.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        List<Event> eventList = em.createQuery(cq).getResultList();

        List<FullEventDto> fullEventsList = eventList.stream()
                .map(EventEntityDtoMapper::mappingFullDtoFrom)
                .collect(Collectors.toList());

        return getPageFrom(fullEventsList, from, size, params.getSort());
    }

    private <T> Page<T> getPageFrom(List<T> list, Integer from, Integer size, String sort) {
        Pageable pageRequest;
        if (sort == null) sort = "ID";

        switch (sort) {
            case "EVENT_DATE" :
                pageRequest = PageRequest.of((from / size), size, Sort.by("eventDate"));
                break;
            case "VIEWS" :
                pageRequest = PageRequest.of((from / size), size, Sort.by("views"));
                break;
            default:
                pageRequest = PageRequest.of((from / size), size, Sort.by("id"));
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());
        List<T> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private void addHit(HttpServletRequest request) {
        client.addHit(new EndpointHitDto(appName,
                request.getRequestURI(),
                request.getRemoteAddr(),
                CURRENT_DATE_TIME));
    }
}
