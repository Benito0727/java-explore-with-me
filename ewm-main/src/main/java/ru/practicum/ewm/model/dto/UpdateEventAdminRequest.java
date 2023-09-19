package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest { // Данные для изменения информации о событии.
                                        // Если поле в запросе не указано (равно null)
                                        // - значит изменение этих данных не треубется.
    @Min(20)
    @Max(2000)
    private String annotation; // новое краткое описание события

    private Long category; // идентификатор новой категории

    private String description; // новое описание события

    private String eventDate; // Новые дата и время на которые намечено событие.
                              // Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"

    private Location location; // новые координаты места проведения события

    private Boolean paid; // новое значение флага о платности мероприятия

    private Integer participantLimit; // новый лимит участников события

    private Boolean requestModeration; // новое значение флага, нужна ли пре-модерация участников

    private String stateAction; // Новое состояние события  Enum [ PUBLISH_EVENT, REJECT_EVENT ]

    @Min(3)
    @Max(120)
    private String title; // Новый заголовок
}
