package com.TrungTinhBackend.banking_backend.RequestDTO;

import com.TrungTinhBackend.banking_backend.Enum.Gender;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    private String username;
    private String password;
    private String citizenId;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String address;
    private Gender gender;
    private String img;
    private Role role;

}
