package com.example.tnex1.controller;

import com.example.tnex1.dto.LoginUserDto;
import com.example.tnex1.dto.RegisterUserDto;
import com.example.tnex1.dto.VerifyUserDto;
import com.example.tnex1.responses.AuthResponse;
import com.example.tnex1.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserDto dto) {
        try {
            String result = authService.register(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi đăng ký: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDto dto) {
        try {
            AuthResponse response = authService.login(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Đăng nhập thất bại: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody @Valid VerifyUserDto dto) {
        try {
            String result = authService.verify(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xác thực thất bại: " + e.getMessage());
        }
    }
}
