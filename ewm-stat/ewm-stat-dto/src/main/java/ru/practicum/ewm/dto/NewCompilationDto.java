package ru.practicum.ewm.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {    //  новая подборка событий

    private List<Long> events;      // список идентификаторов событий uniqueItems: true

    private Boolean pinned;         // флаг, закреплена ли подброрка на главной странице сайта default: false

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;           // заголовок подборки example: Летние концерты
}
