package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto implements Comparable<ViewStatsDto> {

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotNull
    private Long hits;

    @Override
    public int compareTo(ViewStatsDto o) {
        return this.hits.compareTo(o.getHits());
    }
}
