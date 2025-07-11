package com.example.tnex1.dto;

import com.example.tnex1.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;


    @NotBlank(message = "Ten khong duoc de trong")
    @Size(min = 2, max = 50, message = "Ten phai co tu 2 den 50 ki tu")
    private String name;

    @NotBlank(message = "Ten đang nhap khong đuoc de trong")
    @Size(min = 3, max = 30, message = "Ten dang nhap phai co tu 3 đen 30 ky tu")
    private String username;


    @NotBlank(message = "Email khong duoc de trong")
    @Email(message = "Email khong hop le")
    private String email;

    @NotBlank(message = "Mat khau khong duoc de trong")
    @Size(min = 6, max = 100, message = "Mat khau phai co tren 6 ki tu")
    private String password;

    @NotBlank(message = "Vai trò không được để trống") // có thể bỏ nếu không cần nhập từ client
    private Role role; // Thêm vào để phù hợp với entity User





}
