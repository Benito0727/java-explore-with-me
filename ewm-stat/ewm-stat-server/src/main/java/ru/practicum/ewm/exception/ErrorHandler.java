package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.model.response.ErrorResponse;

import javax.validation.constraints.NotNull;

@ControllerAdvice("ru.practicum.ewm")
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestHandler(@NotNull final BadRequestException exception) {
        log.error(exception.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
    }
}
