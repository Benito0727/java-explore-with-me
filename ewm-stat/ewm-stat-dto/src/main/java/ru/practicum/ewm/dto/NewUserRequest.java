package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest { // заявка на участие в событии

    private String created; // Дата и время создания заявки

    private Long event; // Идентификатор события

    private Long id; // Идентификатор заявки

    private Long requester; // Идентификатор пользователя, отправившего заявку

    private String status; // Статус заявки example: PENDING
}
