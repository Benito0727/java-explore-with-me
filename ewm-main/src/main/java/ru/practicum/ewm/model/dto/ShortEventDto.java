package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortEventDto { // кратка информация о событии

    private String annotation; // краткое описание события

    private CategoryDto category; // категория события

    private Integer confirmedRequests; // количество одобреных заявок на событие

    private String eventDate; // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")

    private Long id; // идентификатор события

    private ShortUserDto initiator; // Пользователь создавший событие

    private Boolean paid; // флаг, нужно ли оплачивать участие

    private String title; // Заголовок события

    private Long views; // количество просмотров события
}
