package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;

    @Email(regexp = ".+[@].+[\\.].+")
    @NotBlank
    @Size(min = 6, max = 254)
    private String email;
}
