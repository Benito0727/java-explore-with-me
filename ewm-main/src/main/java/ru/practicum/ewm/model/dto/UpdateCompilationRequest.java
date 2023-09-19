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
public class UpdateCompilationRequest { // Изменение информации о подборке событий.
                                        // Если поле в запросе не указано (равно null) -
                                        //значит изменение этих данных не треубется.

    private List<Long> events; // Список id событий подборки для полной замены текущего списка uniqueItems: true

    private Boolean pinned; // Закреплена ли подборка на главной странице сайта

    @Min(1)
    @Max(50)
    private String title; // Заголовок подборки example: Необычные фотозоны
}
