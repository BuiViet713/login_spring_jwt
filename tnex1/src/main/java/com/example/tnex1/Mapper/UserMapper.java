package com.example.tnex1.Mapper;

import com.example.tnex1.dto.UserDto;
import com.example.tnex1.entity.User;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
//        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .enabled(true)
                .build();
    }
}
