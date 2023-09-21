package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dao.UserRepository;
import ru.practicum.ewm.dto.FullEventDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.entity.Location;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.model.mapper.EventEntityDtoMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EventService {

    private final EventRepository storage;
    private final UserRepository userStorage;

    @Autowired
    public EventService(EventRepository storage,
                        UserRepository userStorage) {
        this.storage = storage;
        this.userStorage = userStorage;
    }

    public FullEventDto save(Long userId, NewEventDto dto) {
        return null;
    }

    private User check(Long userId) {
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

//    private Location check(Long locationId) {
//        try {
//            return locationStorage.findById(locationId)
//                    .orElseTherow(() -> new NotFoundException(String.format("Location with id=%d was not found", locationId)),
//                            "The required object was not found.",
//                            String.format(LocalDateTime.now().toString(),
//                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
//        } catch (NotFoundException exception) {
//            throw new RuntimeException(exception);
//        }
//    }

}
