package com.TrungTinhBackend.banking_backend.Service.User;

import com.TrungTinhBackend.banking_backend.RequestDTO.LoginDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.UserDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface UserService {
    APIResponse register(RegisterDTO registerDTO, MultipartFile img) throws IOException;
    APIResponse login(LoginDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request);
    APIResponse getUserByPage(int page, int size);
    APIResponse getUserById(Long id, Authentication authentication) throws AccessDeniedException;
    APIResponse updateUser(Long id, UserDTO userRequestDTO, MultipartFile img, Authentication authentication) throws IOException;
    APIResponse deleteUser(Long id, Authentication authentication) throws AccessDeniedException;
    APIResponse restoreUser(Long id, Authentication authentication) throws AccessDeniedException;
}
