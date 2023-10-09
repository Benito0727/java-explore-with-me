package ru.practicum.ewm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.dto.NewCommentDto;
import ru.practicum.ewm.dto.response.Response;
import ru.practicum.ewm.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PrivateCommentController {  // Закрытый API для работы с комментариями

    private final CommentService service;

    @Autowired
    public PrivateCommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable(value = "userId") Long userId,
                                 @PathVariable(value = "eventId") Long eventId,
                                 @RequestBody NewCommentDto comment) {
        // авторизованный пользователь добавляет новый комментарий к событию

        return service.addComment(userId, eventId, comment);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentDto editComment(@PathVariable(value = "userId") Long userId,
                                  @PathVariable(value = "commentId") Long commentId,
                                  @RequestBody NewCommentDto comment) {
        // позволяет пользователю изменить свой комментарий

        return service.editComment(userId, commentId, comment);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public Response deleteComment(@PathVariable(value = "userId") Long userId,
                                  @PathVariable(value = "commentId") Long commentId) {

        // позволяет пользователю удалить свой комментарий

        return service.userDeleteComment(userId, commentId);
    }

    @PatchMapping("/{userId}/comments/{commentId}/rate")
    public CommentDto rateComment(@PathVariable(value = "userId") Long userId,
                                @PathVariable(value = "commentId") Long commentId,
                                @RequestParam(value = "rate") Boolean rate) {

        // позволяет пользователю оценить чужой комментарий
        // где boolean rate true - like | false - dislike

        return service.rateComment(userId, commentId, rate);
    }

    @GetMapping("/{userId}/comments/events/{eventId}")
    public List<CommentDto> getCommentsByEvent(@PathVariable(value = "userId") Long userId,
                                               @PathVariable(value = "eventId") Long eventId,
                                               @RequestParam(value = "from", defaultValue = "0") Integer from,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {

        // возвращает комментарии к конкретному событию

        return service.getCommentsByEvent(userId, eventId, from, size).getContent();
    }
}
