package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult { // Результат подтверждения/отклонения заявок на участие в событии


    private ParticipationRequestDto confirmedRequests; // подтвержденные заявки

    private ParticipationRequestDto rejectedRequests; // отклоненные заявки
}
