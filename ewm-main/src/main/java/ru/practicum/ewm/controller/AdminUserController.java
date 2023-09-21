package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.model.response.Response;
import ru.practicum.ewm.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {  // API для работы с пользователями

    private final UserService service;

    public AdminUserController(UserService service) {
        this.service = service;
    }

    // получение информации о пользователях

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) List<Long> usersId,
                                  @RequestParam(value = "from", defaultValue = "0") Integer from,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {

        /*
        Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки),
         либо о конкретных (учитываются указанные идентификаторы)
        В случае, если по заданным фильтрам не найдено ни одного пользователя, возвращает пустой список
         */
        return service.getAllByParameter(usersId, from, size).getContent();
    }

    // добавление нового пользователя

    @PostMapping
    public UserDto addNewUser(@RequestBody NewUserDto userDto) {
        return service.save(userDto);
    }

    // удаление пользователя

    @DeleteMapping("/{userId}")
    public Response deleteUser(@PathVariable(value = "userId") Long userId) {
        /*
        204	Пользователь удален
        404 Пользователь не найден или недоступен
         */
        return service.delete(userId);
    }
}
