package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.LocationRepository;
import ru.practicum.ewm.dto.LocationDto;
import ru.practicum.ewm.model.entity.Location;

@Service
public class LocationService {

    private final LocationRepository storage;

    @Autowired
    public LocationService(LocationRepository storage) {
        this.storage = storage;
    }

    public Location addNewLocation(LocationDto dto) {
        Location location = new Location();
        location.setLat(dto.getLat());
        location.setLon(dto.getLon());
        return storage.save(location);
    }
}
