package ru.practicum.ewm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.dto.response.Response;
import ru.practicum.ewm.model.entity.SearchParams;
import ru.practicum.ewm.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final CommentService service;

    @Autowired
    public AdminCommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping()
    public List<CommentDto> getComments(@RequestParam(value = "users", required = false) List<Long> usersId,
                                        @RequestParam(value = "states", required = false) String state,
                                        @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(value = "sort", required = false) String sort,
                                        @RequestParam(value = "from", defaultValue = "0") Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {

        // Эндпоинт возвращает информацию о всех комментариях подходящих под заданные параметры,
        // если по заданным фильтрам ничего не найдено, возвращает пустой список

        // users array[Long] список идентификаторов пользователей, чьи комментарии нужно найти
        // events array[Long] список идентификаторов событий, комментрарии на которые нужно найти
        // states array[string] список статусов комментариев которые нужно найти
        // rangeStart string (query) дата и время не раньше которых должно произойти событие
        // rangeEnd string (query) дата и время не позже которых должно произойти событие
        // from integer($int32) (query) количество событий,
        //      которые нужно пропустить для формирования текущего набора Default value : 0
        // size integer($int32) (query) количество событий в наборе Default value : 10

        SearchParams params = new SearchParams(
                usersId,
                state,
                rangeStart,
                rangeEnd);

        return service.getCommentsByParameter(params, from, size, sort).getContent();
    }

    @DeleteMapping("/{commentId}")
    public Response deleteComment(@PathVariable(value = "commentId") Long commentId) {
        // Админское удаление любого комментария, никакой валидации

        return service.adminDeleteComment(commentId);
    }
}
