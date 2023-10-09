package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class NewCommentDto {

    @Size(min = 5, max = 5000)
    private String content;
}
