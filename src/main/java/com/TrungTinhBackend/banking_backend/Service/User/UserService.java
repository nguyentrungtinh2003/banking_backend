package com.TrungTinhBackend.banking_backend.Service.User;

import com.TrungTinhBackend.banking_backend.RequestDTO.LoginDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.ResetPassword;
import com.TrungTinhBackend.banking_backend.RequestDTO.UserDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface UserService {
    APIResponse register(RegisterDTO registerDTO, MultipartFile img,HttpServletRequest request,Authentication authentication) throws IOException;
    APIResponse login(LoginDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request,Authentication authentication);
    APIResponse getUserByPage(int page, int size);
    APIResponse searchUser(String keyword,int page, int size);
    APIResponse getUserById(Long id, Authentication authentication) throws AccessDeniedException;
    APIResponse updateUser(Long id, UserDTO userRequestDTO, MultipartFile img, HttpServletRequest request,Authentication authentication) throws IOException;
    APIResponse deleteUser(Long id, HttpServletRequest request,Authentication authentication) throws AccessDeniedException;
    APIResponse restoreUser(Long id,HttpServletRequest request,Authentication authentication) throws AccessDeniedException;
    APIResponse sendOTP(String email,HttpServletRequest request,Authentication authentication);
    APIResponse resetPassword(ResetPassword resetPassword,HttpServletRequest request,Authentication authentication);
}
