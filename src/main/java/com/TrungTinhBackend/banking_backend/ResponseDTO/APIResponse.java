package com.TrungTinhBackend.banking_backend.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private int statusCode;
    private String message;
    private Object data;
    private String token;
    private LocalDateTime timestamp;
}
