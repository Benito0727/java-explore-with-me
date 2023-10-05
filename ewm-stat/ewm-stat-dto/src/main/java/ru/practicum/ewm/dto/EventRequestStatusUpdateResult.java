package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult { // Результат подтверждения/отклонения заявок на участие в событии


    private List<ParticipationRequestDto> confirmedRequests; // подтвержденные заявки

    private List<ParticipationRequestDto> rejectedRequests; // отклоненные заявки
}
