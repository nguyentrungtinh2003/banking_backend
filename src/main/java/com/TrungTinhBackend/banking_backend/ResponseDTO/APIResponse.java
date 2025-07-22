package com.TrungTinhBackend.banking_backend.ResponseDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private int statusCode;
    private String message;
    private Object data;
    private String token;
    private LocalDateTime timestamp;
}
