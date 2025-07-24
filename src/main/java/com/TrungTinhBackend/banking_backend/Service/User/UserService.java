package com.TrungTinhBackend.banking_backend.Service.User;

import com.TrungTinhBackend.banking_backend.RequestDTO.LoginRequestDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterRequestDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    APIResponse register(RegisterRequestDTO registerRequestDTO);
    APIResponse login(LoginRequestDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request);
}
