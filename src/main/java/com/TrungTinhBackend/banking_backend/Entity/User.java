package com.TrungTinhBackend.banking_backend.Entity;

import com.TrungTinhBackend.banking_backend.Enum.Gender;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean deleted;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
}
