package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.LocationDto;
import ru.practicum.ewm.model.entity.Location;

public class LocationEntityDtoMapper {

    public static LocationDto mappingDtoFrom(Location location) {
        LocationDto dto = new LocationDto();
        dto.setLat(location.getLat());
        dto.setLon(location.getLon());
        return dto;
    }
}
