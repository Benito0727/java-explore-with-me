package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private List<String> errors; // список стектрейсов или описание ошибок

    private String message;  // сообщение об ошибке

    private String reason; // Общее описание причины ошибки

    private String status; // код статуса HTTP ответа

    private String timestamp; // дата и время когда произошла ошибка в формате "yyyy-MM-dd HH:mm:ss"
}
