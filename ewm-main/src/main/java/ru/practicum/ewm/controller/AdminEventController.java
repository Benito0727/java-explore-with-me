package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.FullEventDto;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {  // API для работы с событиями

    // поиск событий

    @GetMapping
    public List<FullEventDto> getEvents(@RequestParam(value = "users") List<Integer> usersId,
                                        @RequestParam(value = "states") List<String> states,
                                        @RequestParam(value = "categories") List<Integer> categoriesId,
                                        @RequestParam(value = "rangeStart") String rangeStar,
                                        @RequestParam(value = "rangeEnd") String rangeEnd,
                                        @RequestParam(value = "from", defaultValue = "0") Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        // todo

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

        return null;
    }

    // редактирование данных события и его статуса (отклонено/публикация)

    @PatchMapping("/{eventId}")
    public FullEventDto updateEvent(@PathVariable(value = "eventId") Long eventId,
                                    @RequestBody UpdateEventAdminRequest updateRequest) {
        // todo

        /*
        Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:

        - дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
        - событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
        - событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
         */

        return null;
    }
}
