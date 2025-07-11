package com.example.tnex1.controller;

import com.example.tnex1.dto.UserDto;
import com.example.tnex1.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new  ResponseEntity<>(createdUser,HttpStatus.CREATED );
    }
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUser(id);
        return new  ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return new  ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<UserDto>> getUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> usersPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersPage);
    }
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id,@Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return new  ResponseEntity<>(updatedUser,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("xoa thanh cong");
    }
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // lấy từ token JWT

        UserDto userDto = userService.getUserByEmail(email); // thêm hàm này trong service
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> allUsersRaw() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


}
