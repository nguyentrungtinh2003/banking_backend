package com.TrungTinhBackend.banking_backend.Entity;

import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LogAction action;
    private String details;
    private String ipAddress;
    private String userAgent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
