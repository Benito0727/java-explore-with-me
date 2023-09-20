package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PrivateUserRequestController { // Закрытый API для работы с запросами текущего пользователя на участие в событиях

    // получение информации о заявках текущего пользователя на участие в чужих событиях

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getUserParticipationRequests(@PathVariable(value = "userId") Long userId) {
        // todo

        /*
        В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
         */
        return null;
    }

    // добавление запроса текущего пользователя на участие в событии

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto addParticipationRequest(@PathVariable(value = "userId") Long userId,
                                                           @RequestBody ParticipationRequestDto requestDto) {
        // todo

        /*
        Обратите внимание:

        - нельзя добавить повторный запрос (Ожидается код ошибки 409)
        - инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
        - нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
        - если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
        - если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
         */
        return null;
    }

    // отмена своего запроса на участие в событии

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto canceledParticipationRequest(@PathVariable(value = "userId") Long userId,
                                                                @PathVariable(value = "requestId") Long requestId) {
        // todo
        return null;
    }
}
