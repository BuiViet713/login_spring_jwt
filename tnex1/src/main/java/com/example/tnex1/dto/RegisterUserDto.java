package com.example.tnex1.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserDto {
    String email;
    String password;
    String name;
    String username;
}
