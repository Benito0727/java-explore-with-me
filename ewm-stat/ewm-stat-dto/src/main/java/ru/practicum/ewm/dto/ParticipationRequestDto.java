package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto { // Заявка на участие в событии

    private String created; // Дата и время создания заявки example: 2022-09-06T21:10:05.432

    private Long event; // Идентификатор события

    private Long id; // Идентификатор заявки

    private Long requester; // Идентификатор пользователя, отправившего заявку

    private String status; // Статус заявки example: PENDING
}
