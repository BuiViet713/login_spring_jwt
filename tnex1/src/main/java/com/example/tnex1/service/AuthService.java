package com.example.tnex1.service;

import com.example.tnex1.dto.*;
import com.example.tnex1.responses.AuthResponse;


public interface AuthService {
    String register(RegisterUserDto dto);
    AuthResponse login(LoginUserDto dto);
    String verify(VerifyUserDto dto);
}
