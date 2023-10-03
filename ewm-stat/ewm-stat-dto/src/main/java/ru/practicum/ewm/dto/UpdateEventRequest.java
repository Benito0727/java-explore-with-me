package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {          // Данные для изменения информации о событии.
                                                // Если поле в запросе не указано (равно null)
                                                // - значит изменение этих данных не треубется.
    private String annotation;                  // новое краткое описание события

    private Long category;                      // идентификатор новой категории

    private String description;                 // новое описание события

    private String eventDate;                   // Новые дата и время на которые намечено событие.
                                                // Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"

    private LocationDto location;               // новые координаты места проведения события

    private Boolean paid;                       // новое значение флага о платности мероприятия

    private Long participantLimit;              // новый лимит участников события

    private Boolean requestModeration;          // новое значение флага, нужна ли пре-модерация участников

    private String stateAction;                 // Новое состояние события  Enum [ PUBLISH_EVENT, REJECT_EVENT ]

    private String title;                       // Новый заголовок
}
