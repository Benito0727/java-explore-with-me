package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.FullEventDto;
import ru.practicum.ewm.dto.ShortEventDto;

import java.util.List;

@RestController
@RequestMapping("/events")
public class PublicEventController { // публичный API для работы с событиями

    // получение событий с возможной фильтрацией

    @GetMapping
    public List<ShortEventDto> getEvents(@RequestParam(value = "text") String text,
                                         @RequestParam(value = "categories") List<Integer> categoriesId,
                                         @RequestParam(value = "paid") Boolean isPaid,
                                         @RequestParam(value = "rangeStar") String rangeStar,
                                         @RequestParam(value = "rangeEnd") String rangeEnd,
                                         @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(value = "sort") String sort,
                                         @RequestParam(value = "from", defaultValue = "0") Integer from,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        // todo

        /*
        text string (query) текст для поиска в содержимом аннотации и подробном описании события

        categories array[integer] (query) список идентификаторов категорий в которых будет вестись поиск

        paid boolean (query) поиск только платных/бесплатных событий

        rangeStart string (query) дата и время не раньше которых должно произойти событие

        rangeEnd string (query) дата и время не позже которых должно произойти событие

        onlyAvailable boolean (query) только события у которых не исчерпан лимит запросов на участие Default value : false

        sort string (query) Вариант сортировки: по дате события или по количеству просмотров Available values : EVENT_DATE, VIEWS

        from integer($int32) (query) количество событий, которые нужно пропустить для формирования текущего набора Default value : 0

        size integer($int32) (query) количество событий в наборе Default value : 10
        */
        return null;
    }

    // получение подробной информации о опубликованном событии по его идентификатору

    @GetMapping("/{eventId}")
    public FullEventDto getEventById(@PathVariable(value = "eventId") Long eventId) {

        // todo

        /*
        Обратите внимание:

        - событие должно быть опубликовано
        - информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
        - информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики

        В случае, если события с заданным id не найдено, возвращает статус код 404
         */

        return null;
    }
}
