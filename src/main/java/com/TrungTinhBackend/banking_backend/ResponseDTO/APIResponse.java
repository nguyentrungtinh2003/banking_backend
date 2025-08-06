package com.TrungTinhBackend.banking_backend.ResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class APIResponse {

    private int statusCode;
    private String message;
    private Object data;
    private String token;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public APIResponse() {
    }

    public APIResponse(int statusCode, String message, Object data, String token, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.token = token;
        this.timestamp = timestamp;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
