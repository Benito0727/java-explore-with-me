package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {    //  новая подборка событий

    private List<Long> events; // список идентификаторов событий uniqueItems: true

    private Boolean pinned; // флаг, закреплена ли подброрка на главной странице сайта default: false

    @Min(1)
    @Max(50)
    private String title; // заголовок подборки example: Летние концерты
}
