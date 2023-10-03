package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dao.*;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component(value = "ObjectChecker")
public class ObjectChecker {

    private final UserRepository userStorage;
    private final LocationRepository locationStorage;
    private final EventRepository eventStorage;
    private final CategoryRepository categoryStorage;

    private final CompilationRepository compilationStorage;

    private final ParticipationRequestRepository requestStorage;

    @Autowired
    public ObjectChecker(UserRepository userStorage,
                         LocationRepository locationStorage,
                         EventRepository eventStorage,
                         CategoryRepository categoryStorage,
                         ParticipationRequestRepository requestStorage,
                         CompilationRepository compilationStorage) {
        this.userStorage = userStorage;
        this.locationStorage = locationStorage;
        this.eventStorage = eventStorage;
        this.categoryStorage = categoryStorage;
        this.requestStorage = requestStorage;
        this.compilationStorage = compilationStorage;
    }

    public User checkUser(Long userId) {
        return userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found.",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    public Location checkLocation(Long locationId) {
        return locationStorage.findById(locationId)
                .orElseThrow(() -> new NotFoundException(String.format("Location with id=%d was not found", locationId),
                        "The required object was not found.",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    public Event checkEvent(Long eventId) {
        return eventStorage.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId),
                        "The required object was not found.",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

    }

    public Category checkCategory(Long categoryId) {
        return categoryStorage.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", categoryId),
                        "The required object was not found.",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

    }

    public void checkCategory(String name) {
        Optional<Category> optionalCategory = categoryStorage.findCategoryByName(name);
        if (optionalCategory.isPresent()) {
            throw new ConflictException(String.format("Category with name=%s already created", name),
                    "You cannot have multiple categories with the same name",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        }
    }

    public ParticipationRequest checkRequest(Long requestId) {
        return requestStorage.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id=%d was not found", requestId),
                        "The required object was not found",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    public Compilation checkCompilation(Long id) {
        return compilationStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d was not found", id),
                        "The required object was not found",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }
}
