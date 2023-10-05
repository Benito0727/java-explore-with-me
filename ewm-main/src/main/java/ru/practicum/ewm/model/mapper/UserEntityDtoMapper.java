package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.ShortUserDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.model.entity.User;

public class UserEntityDtoMapper {

    public static UserDto mappingDtoFrom(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User mappingEntityFrom(NewUserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static ShortUserDto mappingShortDtoFrom(User user) {
        ShortUserDto dto = new ShortUserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }
}
