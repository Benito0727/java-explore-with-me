package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Long id; // идетификатор категории

    @NotBlank
    @Size(max = 50)
    private String name; // название категории (пример: "Концерты")
}
