package com.example.tnex1.service;

import com.example.tnex1.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface UserService {
     UserDto createUser(UserDto userDto);
     UserDto getUser( Long userId);
     List<UserDto> getAllUsers();
     Page<UserDto> getAllUsers(Pageable pageable);
     UserDto updateUser(Long userId, UserDto updateUser);
     void deleteUser(Long userId);
     UserDto getUserByEmail(String email);

}
