package com.TrungTinhBackend.banking_backend.Service.Email;

public interface EmailService {
    void sendMail(String to, String subject,String body);
}
