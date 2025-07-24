package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.RequestDTO.LoginRequestDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterRequestDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<APIResponse> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(userService.register(registerRequestDTO));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request) {
        return ResponseEntity.ok(userService.login(loginRequestDTO,response,request));
    }
}
