package com.example.tnex1.service.Impl;

import com.example.tnex1.Enum.Role;
import com.example.tnex1.dto.*;
import com.example.tnex1.entity.User;
import com.example.tnex1.repository.UserRepository;
import com.example.tnex1.responses.AuthResponse;
import com.example.tnex1.service.AuthService;
import com.example.tnex1.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Đăng ký tài khoản
     */
    @Override
    public String register(RegisterUserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "Email đã được sử dụng.";
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "Tên đăng nhập đã tồn tại.";
        }

        User user = User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(false) // Chưa xác thực
                .verificationCode(UUID.randomUUID().toString())
                .build();

        userRepository.save(user);

        // TODO: Gửi email xác thực nếu cần
        return "Đăng ký thành công. Vui lòng xác thực email.";
    }

    /**
     * Đăng nhập và trả JWT
     */
    @Override
    public AuthResponse login(LoginUserDto dto) {
        Optional<User> optionalEmail = userRepository.findByEmail(dto.getEmail());

        if (optionalEmail.isEmpty()) {
            throw new RuntimeException("Email không tồn tại.");
        }

        User user = optionalEmail.get();

        if (!user.isEnabled()) {
            throw new RuntimeException("Tài khoản chưa được xác thực.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Email hoặc mật khẩu không đúng.");
        }

        // Tạo đối tượng UserDetails với email
        UserDetails userDetails = User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .role(Role.ADMIN)
                .build();

        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token, "Bearer");
    }

    /**
     * Xác thực tài khoản qua mã
     */
    @Override
    public String verify(VerifyUserDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());

        if (optionalUser.isEmpty()) {
            return "Không tìm thấy người dùng.";
        }

        User user = optionalUser.get();

        if (user.getVerificationCode() != null && user.getVerificationCode().equals(dto.getVerificationCode())) {
            user.setEnabled(true);
            user.setVerificationCode(null); // mã xác thực chỉ dùng 1 lần
            userRepository.save(user);
            return "Xác thực tài khoản thành công.";
        } else {
            return "Mã xác thực không đúng.";
        }
    }
}
