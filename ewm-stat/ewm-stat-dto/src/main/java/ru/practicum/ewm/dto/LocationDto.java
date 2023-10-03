package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {  // Широта и долгота места проведения события

    @NotBlank
    private Double lat;     // широта

    @NotBlank
    private Double lon;     // долгота
}
