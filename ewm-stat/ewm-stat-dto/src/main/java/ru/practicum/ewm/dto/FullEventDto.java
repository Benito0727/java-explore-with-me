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

    private Long id;                    // идетификатор события

    private String title;               // заголовок события пример: Знаменитое шоу "Летающая кукуруза"

    private String annotation;          // краткое описание события

    private CategoryDto category;       // категория

    private Boolean paid;               // флаг, нужно ли оплачивать событие

    private String eventDate;           // дата и время на которое намечено событие в формате "yyyy-MM-dd HH:mm:ss"

    private ShortUserDto initiator;     // пользователь создавший событие

    private String description;         // полное описание события

    private Long participantLimit;      // Ограничение на количество участников, значение 0 - отсутствие ограничения

    private String state;               // состояние жизненого цикла события Enum[ PENDING, PUBLISHED, CANCELED ]

    private LocalDateTime createdOn;    // дата и время создания события в формате "yyyy-MM-dd HH:mm:ss"

    private LocationDto location;       // широта и долгота места проведения события

    private Boolean requestModeration;  // флаг, нужна ли пре-модерация заявок на участие

    private Integer confirmedRequests;     // количество одобреных заявок на участие в событии

    private String publishedOn;         // дата и время публикации события в формате "yyyy-MM-dd HH:mm:ss",

    private Long views;                 // количество просмотров события
}
