package ru.practicum.ewm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.UserRepository;
import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UpdateUserDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.model.mapper.UserEntityDtoMapper;
import ru.practicum.ewm.dto.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.ewm.model.mapper.UserEntityDtoMapper.*;

@Service
@Slf4j
public class UserService {

    private final UserRepository storage;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public UserService(UserRepository storage) {
        this.storage = storage;
    }

    public UserDto addNewUser(NewUserDto dto) {
        Optional<User> user = storage.findUserByEmail(dto.getEmail());
        if (user.isPresent()) {
            throw new ConflictException("Incorrectly email",
                    "This email is already in use",
                    LocalDateTime.now().format(FORMATTER));
        }
        return mappingDtoFrom(storage.save(mappingEntityFrom(dto)));
    }

    public Response delete(Long userId) {
        User user = check(userId);
        storage.delete(user);
        return new Response("User deleted");
    }

    public UserDto getById(Long userId) {
        return mappingDtoFrom(check(userId));
    }

    public Page<UserDto> getAllByParameter(List<Long> userIds, Integer from, Integer size) {
        List<UserDto> users = new ArrayList<>();
        if (userIds != null && !userIds.isEmpty()) {
            users.addAll(storage.findAllByIdIn(userIds).stream()
                    .map(UserEntityDtoMapper::mappingDtoFrom)
                    .collect(Collectors.toList()));
        } else {
            users.addAll(storage.findAll().stream()
                    .map(UserEntityDtoMapper::mappingDtoFrom)
                    .collect(Collectors.toList()));
        }
        return getPageFrom(users, from, size);
    }

    public UserDto update(Long userId, UpdateUserDto dto) {
        User user = check(userId);
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        return mappingDtoFrom(storage.save(user));
    }

    private User check(Long userId) {
        try {
            return storage.findById(userId)
                    .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId),
                            "The required object was not found.",
                            LocalDateTime.now().format(FORMATTER)));

        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Page<UserDto> getPageFrom(List<UserDto> list, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size, Sort.by("id"));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());
        List<UserDto> pageContent = list.subList(start, end);
        return new PageImpl<UserDto>(pageContent, pageRequest, list.size());
    }
}
