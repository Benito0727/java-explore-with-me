package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.entity.Category;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.Location;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.model.mapper.EventEntityDtoMapper;
import ru.practicum.ewm.model.response.Response;
import ru.practicum.ewm.model.status.EventStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository storage;
    private final UserRepository userStorage;

    private final LocationRepository locationStorage;

    private final CategoryRepository categoryStorage;

    private final CompilationRepository compilationStorage;

    @Autowired
    public EventService(EventRepository storage,
                        UserRepository userStorage,
                        LocationRepository locStorage,
                        CategoryRepository categoryStorage,
                        CompilationRepository compilationStorage) {
        this.storage = storage;
        this.userStorage = userStorage;
        this.locationStorage = locStorage;
        this.categoryStorage = categoryStorage;
        this.compilationStorage = compilationStorage;
    }

    public FullEventDto save(Long userId, NewEventDto dto) {
        User initiator = checkUser(userId);
        Location location = new Location(dto.getLocation().getLat(),
                dto.getLocation().getLot());
        Category category = null;
        if (dto.getCategory() != null) category = (checkCategory(dto.getCategory()));
        Event event = EventEntityDtoMapper.mappingEntityFrom(dto,
                initiator,
                locationStorage.save(location));
        return EventEntityDtoMapper.mappingFullDtoFrom(storage.save(event), category);
    }

    public Response delete(Long eventId) {
        storage.delete(checkEvent(eventId));
        return new Response("Event has been deleted");
    }

    public FullEventDto adminUpdateEvent(Long eventId, UpdateEventAdminRequest dto) {
        Event event = checkEvent(eventId);
        if (dto.getAnnotation() != null) event.setAnnotation(dto.getAnnotation());
        if (dto.getCategory() != null) event.setCategory(dto.getCategory());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) event.setEventDate(LocalDateTime.parse(dto.getEventDate()));
        if (dto.getLocation() != null) {
            Location location = new Location(dto.getLocation().getLat(), dto.getLocation().getLot());
            event.setLocation(locationStorage.save(location));
        }
        if (dto.getPaid() != null) event.setIsPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getRequestModeration() != null) event.setIsRequestModeration(dto.getRequestModeration());
        if (dto.getStateAction() != null) event.setStatusId(EventStatus.getIdFrom(dto.getStateAction()));
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());

        return EventEntityDtoMapper.mappingFullDtoFrom(storage.save(event), checkCategory(event.getCategory()));
    }

    public FullEventDto userUpdateEvent(Long eventId, UpdateEventUserRequest dto) {
        Event event = checkEvent(eventId);
        if (dto.getAnnotation() != null) event.setAnnotation(dto.getAnnotation());
        if (dto.getCategory() != null) event.setCategory(dto.getCategory());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) event.setEventDate(LocalDateTime.parse(dto.getEventDate()));
        if (dto.getLocation() != null) {
            Location location = new Location(dto.getLocation().getLat(), dto.getLocation().getLot());
            event.setLocation(locationStorage.save(location));
        }
        if (dto.getPaid() != null) event.setIsPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getRequestModeration() != null) event.setIsRequestModeration(dto.getRequestModeration());
        if (dto.getStateAction() != null) event.setStatusId(EventStatus.getIdFrom(dto.getStateAction()));
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());
        return EventEntityDtoMapper.mappingFullDtoFrom(storage.save(event), checkCategory(event.getCategory()));
    }

    public Page<FullEventDto> getEventsByParameters(List<Long> initiatorsId,
                                                    List<String> states,
                                                    List<Long> categoriesId,
                                                    String rangeStart,
                                                    String rangeEnd,
                                                    Integer from,
                                                    Integer size) {
        List<FullEventDto> events = new ArrayList<>();

        boolean inits = false;
        boolean state = false;
        boolean cats = false;
        boolean start = false;
        boolean end = false;

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (initiatorsId != null) inits = true;
        if (states != null) state = true;
        if (categoriesId != null) cats = true;
        if (rangeStart != null) start = true;
        if (rangeEnd != null) end = true;

        if (start) {
            startDate = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        if (end) {
            endDate = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        if (inits && !state && !cats && !start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdIn(initiatorsId)));
        }
        if (!inits && state && !cats && !start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameIn(states)));
        }
        if (!inits && !state && cats && !start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByCategoryIn(categoriesId)));
        }
        if (!inits && !state && !cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByEventDateBefore(endDate)));
        }
        if (!inits && !state && !cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByEventDateAfter(startDate)));
        }
        if (inits && state && !cats && !start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndStatusNameIn(initiatorsId,
                            states)));
        }
        if (inits && state && cats && !start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndStatusNameInAndCategoryIn(initiatorsId,
                            states,
                            categoriesId)));
        }
        if (inits && !state && cats && !start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndCategoryIn(initiatorsId,
                            categoriesId)));
        }
        if (inits && !state && !cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndEventDateAfter(initiatorsId,
                            startDate)));
        }
        if (inits && !state && !cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndEventDateBefore(initiatorsId,
                            endDate)));
        }
        if (!inits && state && cats && !start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameInAndCategoryIn(states,
                            categoriesId)));
        }
        if (!inits && state && !cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameInAndEventDateAfter(states,
                            startDate)));
        }
        if (!inits && state && !cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameInAndEventDateBefore(states,
                            endDate)));
        }
        if (!inits && !state && cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByCategoryInAndEventDateAfter(categoriesId,
                            startDate)));
        }
        if (!inits && !state && cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByCategoryInAndEventDateBefore(categoriesId,
                            endDate)));
        }
        if (!inits && !state && !cats && start && end) {
            events.addAll(getDtoListFrom(storage.findEventsByEventDateIsAfterAndEventDateBefore(startDate,
                            endDate)));
        }
        if (inits && state && !cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndStatusNameInAndEventDateAfter(initiatorsId,
                            states,
                            startDate)));
        }
        if (inits && state && !cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndStatusNameInAndEventDateBefore(initiatorsId,
                            states,
                            endDate)));
        }
        if (inits && !state && cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndCategoryInAndEventDateAfter(initiatorsId,
                            categoriesId,
                            startDate)));
        }
        if (inits && !state && cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndCategoryInAndEventDateBefore(initiatorsId,
                            categoriesId,
                            endDate)));
        }
        if (!inits && state && cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameInAndCategoryInAndEventDateAfter(states,
                            categoriesId,
                            startDate)));
        }
        if (!inits && state && cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameInAndCategoryInAndEventDateBefore(states,
                            categoriesId,
                            endDate)));
        }
        if (!inits && !state && cats && start && end) {
            events.addAll(getDtoListFrom(storage.findAllByCategoryInAndEventDateAfterAndEventDateBefore(categoriesId,
                            startDate,
                            endDate)));
        }
        if (inits && state && cats && start && !end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndCategoryInAndStatusNameInAndEventDateAfter(initiatorsId,
                            categoriesId,
                            states,
                            startDate)));
        }
        if (inits && state && cats && !start && end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndCategoryInAndStatusNameInAndEventDateBefore(initiatorsId,
                            categoriesId,
                            states,
                            endDate)));
        }
        if (inits && state && !cats && start && end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndStatusNameInAndEventDateAfterAndEventDateBefore(initiatorsId,
                            states,
                            startDate,
                            endDate)));
        }
        if (inits && !state && cats && start && end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndCategoryInAndEventDateAfterAndEventDateBefore(initiatorsId,
                            categoriesId,
                            startDate,
                            endDate)));
        }
        if (inits && state && cats && start && end) {
            events.addAll(getDtoListFrom(storage
                    .findAllByInitiatorIdInAndStatusNameInAndCategoryInAndEventDateAfterAndEventDateBefore(initiatorsId,
                            states,
                            categoriesId,
                            startDate,
                            endDate)));
        }
        if (!inits && state && cats && start && end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameInAndCategoryInAndEventDateAfterAndEventDateBefore(states,
                            categoriesId,
                            startDate,
                            endDate)));
        }
        if (inits && !state && !cats && start && end) {
            events.addAll(getDtoListFrom(storage.findAllByInitiatorIdInAndEventDateIsAfterAndEventDateIsBefore(initiatorsId,
                            startDate,
                            endDate)));
        }
        if (!inits && state && !cats && start && end) {
            events.addAll(getDtoListFrom(storage.findAllByStatusNameInAndEventDateAfterAndEventDateBefore(states,
                            startDate,
                            endDate)));
        }
        return getPageFrom(events, from, size);
    }

    private List<FullEventDto> getDtoListFrom(List<Event> events) {
        return events.stream()
                .map(o -> EventEntityDtoMapper.mappingFullDtoFrom(o, checkCategory(o.getCategory())))
                .collect(Collectors.toList());
    }

    private Page<FullEventDto> getPageFrom(List<FullEventDto> list, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size, Sort.by("id"));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());
        List<FullEventDto> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private User checkUser(Long userId) {
        try {
            return userStorage.findById(userId)
                    .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId),
                            "The required object was not found.",
                            String.format(LocalDateTime.now().toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Location checkLocation(Long locationId) {
        try {
            return locationStorage.findById(locationId)
                    .orElseThrow(() -> new NotFoundException(String.format("Location with id=%d was not found", locationId),
                    "The required object was not found.",
                    String.format(LocalDateTime.now().toString(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Event checkEvent(Long eventId) {
        try {
            return storage.findById(eventId)
                    .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId),
                            "The required object was not found.",
                            String.format(LocalDateTime.now().toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Category checkCategory(Long categoryId) {
        try {
            return categoryStorage.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", categoryId),
                            "The required object was not found.",
                            String.format(LocalDateTime.now().toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
