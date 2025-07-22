package com.TrungTinhBackend.banking_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private Double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
