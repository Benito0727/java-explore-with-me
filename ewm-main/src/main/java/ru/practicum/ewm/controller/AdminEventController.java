package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.FullEventDto;
import ru.practicum.ewm.dto.UpdateEventRequest;
import ru.practicum.ewm.model.entity.SearchParams;
import ru.practicum.ewm.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {  // API для работы с событиями

    private final EventService service;

    public AdminEventController(EventService service) {
        this.service = service;
    }

    // поиск событий

    @GetMapping
    public List<FullEventDto> getEvents(@RequestParam(value = "users", required = false) List<Long> usersId,
                                        @RequestParam(value = "states", required = false) List<String> states,
                                        @RequestParam(value = "categories", required = false) List<Long> categoriesId,
                                        @RequestParam(value = "rangeStart", required = false) String rangeStar,
                                        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(value = "from", defaultValue = "0") Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {

        /*
        Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия
        В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
        */

        /*
        users array[integer] (query) список id пользователей, чьи события нужно найти

        states array[string] (query) список состояний в которых находятся искомые события

        categories array[integer] (query) список id категорий в которых будет вестись поиск

        rangeStart string (query) дата и время не раньше которых должно произойти событие

        rangeEnd string (query) дата и время не позже которых должно произойти событие

        from integer($int32) (query) количество событий,
         которые нужно пропустить для формирования текущего набора Default value : 0

        size integer($int32) (query) количество событий в наборе Default value : 10
        */

        SearchParams params = new SearchParams(
                usersId,
                states,
                categoriesId,
                rangeStar,
                rangeEnd);

        return service.getEventsByParamsFromAdmin(params,
                from,
                size).getContent();
    }

    // редактирование данных события и его статуса (отклонено/публикация)

    @PatchMapping("/{eventId}")
    public FullEventDto updateEvent(@PathVariable(value = "eventId") Long eventId,
                                    @RequestBody UpdateEventRequest updateRequest) {

        /*
        Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:

        - дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
        - событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
        - событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
         */

        return service.adminUpdateEvent(eventId, updateRequest);
    }
}
