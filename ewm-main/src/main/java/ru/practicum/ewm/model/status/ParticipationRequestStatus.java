package ru.practicum.ewm.model.status;

import ru.practicum.ewm.exception.NotFoundException;

import java.util.Arrays;

public enum ParticipationRequestStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELED;

    public ParticipationRequestStatus from(String statusString) {
        try {
            return Arrays.stream(ParticipationRequestStatus.values())
                    .filter(status -> status.toString().equals(statusString.toUpperCase()))
                    .findAny().orElseThrow(() -> new NotFoundException("Incorrectly status"));
        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
