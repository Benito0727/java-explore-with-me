package ru.practicum.ewm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PrivateUserRequestController { // Закрытый API для работы с запросами текущего пользователя на участие в событиях

    private final ParticipationRequestService service;

    public PrivateUserRequestController(ParticipationRequestService service) {
        this.service = service;
    }

    // получение информации о заявках текущего пользователя на участие в чужих событиях

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getUserParticipationRequests(@PathVariable(value = "userId") Long userId) {
        /*
        В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
         */
        return service.getUserRequests(userId);
    }

    // добавление запроса текущего пользователя на участие в событии

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addParticipationRequest(@PathVariable(value = "userId") Long userId,
                                                           @RequestParam(value = "eventId") Long eventId) {
        /*
        Обратите внимание:

        - нельзя добавить повторный запрос (Ожидается код ошибки 409)
        - инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
        - нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
        - если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
        - если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
         */
        return service.addRequest(userId, eventId);
    }

    // отмена своего запроса на участие в событии

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto canceledParticipationRequest(@PathVariable(value = "userId") Long userId,
                                                                @PathVariable(value = "requestId") Long requestId) {
        return service.canceledRequest(userId, requestId);
    }
}
