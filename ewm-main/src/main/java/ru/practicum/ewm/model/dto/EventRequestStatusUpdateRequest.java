package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest { // Изменение статуса запроса на участие в событии текущего пользователя

    private List<Long> requestId; // Идентификаторы запросов на участие в событии текущего пользователя

    private String status; // Новый статус запроса на участие в событии текущего пользователя Enum [ CONFIRMED, REJECTED ]
}
