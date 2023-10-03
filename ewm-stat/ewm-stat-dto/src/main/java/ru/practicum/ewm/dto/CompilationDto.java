package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto { // подборка событий

    private List<ShortEventDto> events = new ArrayList<>(); // список событий входящий в подборку uniqueItems: true

    private Long id;                    // идентификатор подборки

    private Boolean pinned;             // флаг, закреплена ли подборка на главной странице сайта

    private String title;               // заголовок подборки
}
