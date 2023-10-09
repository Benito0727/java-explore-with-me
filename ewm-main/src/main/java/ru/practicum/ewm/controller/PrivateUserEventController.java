package ru.practicum.ewm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class PrivateUserEventController { // Закрытый API для работы с событиями


    private final EventService service;

    public PrivateUserEventController(EventService service) {
        this.service = service;
    }

    // получение событий добавленных текущим пользователем

    @GetMapping("/{userId}/events")
    public List<ShortEventDto> getUserEvents(@PathVariable(value = "userId") Long userId,
                                       @RequestParam(value = "from", defaultValue = "0") Integer from,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
        return service.getUserEvents(userId, from, size).getContent();
    }

    // получение полной информации о событии добавленном текущим пользователем

    @GetMapping("/{userId}/events/{eventId}")
    public FullEventDto getUserEventById(@PathVariable(value = "userId") Long userId,
                                         @PathVariable(value = "eventId") Long eventId) {
        // В случае, если события с заданным id не найдено, возвращает статус код 404
        return service.getUserEventById(userId, eventId);
    }

    // добавление нового события

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public FullEventDto addNewEvents(@PathVariable(value = "userId") Long userId,
                                      @RequestBody @Valid NewEventDto eventDto) {

        // Обратите внимание: дата и время на которые намечено событие не может быть раньше,
        // чем через два часа от текущего момента
        return service.addNewEvent(userId, eventDto);
    }

    // изменение события добавленного текущим пользователем

    @PatchMapping("/{userId}/events/{eventId}")
    public FullEventDto updateEventById(@PathVariable(value = "userId") Long userId,
                                         @PathVariable(value = "eventId") Long eventId,
                                         @RequestBody UpdateEventRequest updateRequest) {

        //Обратите внимание:
        //
        //изменить можно только отмененные события
        // или события в состоянии ожидания модерации (Ожидается код ошибки 409)

        //дата и время на которые намечено событие не может быть раньше,
        // чем через два часа от текущего момента (Ожидается код ошибки 409)
        return service.userUpdateEvent(userId, eventId, updateRequest);
    }

    // получение информации о запросах на участие в событии текущего пользователя

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequestsForEvent(@PathVariable(value = "userId") Long userId,
                                                                          @PathVariable(value = "eventId") Long eventId) {
        // В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
        return service.getRequestsForEvent(userId, eventId);
    }

    // Изменение статуса (подтверждена/отклонена) заявок на участие в событии текущего пользователя

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeEventRequestsStatus(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "eventId") Long eventId,
            @RequestBody EventRequestStatusUpdateRequest updateRequest) {

        /*
        Обратите внимание:

        - если для события лимит заявок равен 0 или отключена пре-модерация заявок,
         то подтверждение заявок не требуется

        - нельзя подтвердить заявку,
         если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)

        - статус можно изменить только у заявок,
         находящихся в состоянии ожидания (Ожидается код ошибки 409)

        - если при подтверждении данной заявки,
         лимит заявок для события исчерпан,
         то все неподтверждённые заявки необходимо отклонить
         */

        return service.changeEventRequestsStatus(userId, eventId, updateRequest);
    }
}
