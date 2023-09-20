package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ShortEventDto;


import java.util.List;

@RestController
@RequestMapping("/compilations")
public class PublicCompilationsController { // Публичный API для работы с подборками событий

    // todo

    /*
    В случае, если по заданным фильтрам не найдено ни одной подборки, возвращает пустой список
     */
    @GetMapping
    public List<ShortEventDto> getCompilations(@RequestParam(value = "pinned", required = false) Boolean pinned,
                                               @RequestParam(value = "size", defaultValue = "0") Integer size,
                                               @RequestParam(value = "from", defaultValue = "10") Integer from) {
        return null;
    }

    // todo

    /*
    В случае, если подборки с заданным id не найдено, возвращает статус код 404
     */

    @GetMapping("/{compId}")
    public ShortEventDto getCompilationsById(@PathVariable(name = "compId") Long compId) {
        return null;
    }
}
