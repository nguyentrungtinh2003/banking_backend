package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.RequestDTO.LoginRequestDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterRequestDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.UserRequestDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<APIResponse> register(@RequestPart(value = "user") RegisterRequestDTO registerRequestDTO,
                                                @RequestPart(value = "img",required = false) MultipartFile img) throws IOException {
        return ResponseEntity.ok(userService.register(registerRequestDTO,img));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request) {
        return ResponseEntity.ok(userService.login(loginRequestDTO,response,request));
    }

    @GetMapping("/employee/user/page")
    public ResponseEntity<APIResponse> getUserByPage(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(userService.getUserByPage(page,size));
    }

    @GetMapping("/customer/user/{id}")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getUserById(id,authentication));
    }

    @PutMapping("/customer/user/update/{id}")
    public ResponseEntity<APIResponse> updateUser(@PathVariable Long id,
                                              @RequestPart(value = "user") UserRequestDTO userRequestDTO,
                                              @RequestPart(value = "img",required = false) MultipartFile img) throws IOException {
        return ResponseEntity.ok(userService.updateUser(id,userRequestDTO,img));
    }

    @DeleteMapping("/customer/user/delete/{id}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.deleteUser(id,authentication));
    }

    @PutMapping("/customer/user/restore/{id}")
    public ResponseEntity<APIResponse> restoreUser(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.restoreUser(id,authentication));
    }
}
