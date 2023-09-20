package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullEventDto {

    private String annotation; // краткое описание события

    private CategoryDto category; // категория

    private Integer confirmedRequests; // количество одобреных заявок на участие в событии

    private LocalDateTime createdOn; // дата и время создания события в формате "yyyy-MM-dd HH:mm:ss"

    private String description; // полное описание события

    private String eventDate; // дата и время на которое намечено событие в формате "yyyy-MM-dd HH:mm:ss"

    private Long id; // идетификатор события

    private ShortUserDto initiator; // пользователь создавший событие

    private Location location; // широта и долгота места проведения события

    private Boolean paid; // флаг, нужно ли оплачивать событие

    private Integer participantLimit; // Ограничение на количество участников, значение 0 - отсутствие ограничения

    private String publishedOn; // дата и время публикации события в формате "yyyy-MM-dd HH:mm:ss",

    private Boolean requestModeration; // флаг, нужна ли пре-модерация заявок на участие

    private String status; // состояние жизненого цикла события Enum[ PENDING, PUBLISHED, CANCELED ]

    private String title; // заголовок события пример: Знаменитое шоу "Летающая кукуруза"

    private Long views; // количество просмотров события
}
