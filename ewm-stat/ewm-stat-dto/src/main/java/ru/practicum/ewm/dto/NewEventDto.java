package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {  // Новое событие

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;      // краткое описание события

    private Long category;          // идентификатор категории к которой относится событие

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;     // Полное описание события

    private String eventDate;       // Дата и время на которые намечено событие.
                                    // Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"

    private LocationDto location;   // широта и долгота места проведения события

    private Boolean paid;           // флаг, нужно ли оплачивать участие default: false

    private Long participantLimit;  // Ограничение на количество участников.
                                    // Значение 0 - означает отсутствие ограничения default: 0

    private Boolean requestModeration; // Нужна ли пре-модерация заявок на участие.
                                       // Если true, то все заявки будут ожидать подтверждения инициатором события.
                                       // Если false - то будут подтверждаться автоматически. default: true
    @Size(min = 3, max = 120)
    private String title;           // заголовок события
}
