package ru.practicum.ewm.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String status;      // HTTP status code ("status": "BAD_REQUEST")

    private String reason;      // из-за чего произошла ошибка ("reason": "Incorrectly made request.")

    private String  message;    // сообщение ошибки ("message": "Failed to convert value of type java.lang.String to required type long; nested exception is java.lang.NumberFormatException: For input string: ad")

    private String timestamp;   // время ошибки ("timestamp": "2022-09-07 09:10:50")
}
