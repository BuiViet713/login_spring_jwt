package com.example.tnex1.service.Impl;

import com.example.tnex1.Mapper.UserMapper;
import com.example.tnex1.dto.UserDto;
import com.example.tnex1.entity.User;
import com.example.tnex1.exception.ResourceNotFoundException;
import com.example.tnex1.repository.UserRepository;
import com.example.tnex1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User saveUser= userRepository.save(user);

        return UserMapper.mapToUserDto(saveUser);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("khong ton tai nguoi dung id:"+userId));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll((Pageable) pageable);

        List<UserDto> userDtos = usersPage.getContent().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());

        return new PageImpl<>(userDtos, (Pageable) pageable, usersPage.getTotalElements());
    }



    @Override
    public UserDto updateUser(Long userid, UserDto updateUser) {
        User user = userRepository.findById(userid).orElseThrow(() ->new ResourceNotFoundException("khong ton tai nguoi dung id:"+ userid));
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        user.setRole(updateUser.getRole());
        userRepository.save(user);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("khong ton tai nguoi dung id:"+userId));
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));
        return UserMapper.mapToUserDto(user);
    }



}
