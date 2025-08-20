package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.Enum.Gender;
import com.TrungTinhBackend.banking_backend.RequestDTO.*;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> register(@RequestPart(value = "user") RegisterDTO registerDTO,
                                                @RequestPart(value = "img",required = false) MultipartFile img,HttpServletRequest request,Authentication authentication) throws IOException {
        return ResponseEntity.ok(userService.register(registerDTO,img,request,authentication));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request,Authentication authentication) {
        return ResponseEntity.ok(userService.login(loginRequestDTO,response,request,authentication));
    }

    @GetMapping("/customer/user-info")
    public ResponseEntity<APIResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getUserInfo(userDetails));
    }

    @GetMapping("/employee/user/page")
    public ResponseEntity<APIResponse> getUserByPage(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(userService.getUserByPage(page,size));
    }

    @GetMapping("/employee/user")
    public ResponseEntity<APIResponse> filterUser(@RequestParam(required = false) String keyword,
                                                     @RequestParam(required = false) Gender gender,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(userService.filterUser(keyword,gender,birthday,page,size));
    }

    @GetMapping("/customer/user/{id}")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getUserById(id,authentication));
    }

    @PutMapping(value = "/customer/user/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> updateUser(@PathVariable Long id,
                                              @RequestPart(value = "user") UserDTO userRequestDTO,
                                              @RequestPart(value = "img",required = false) MultipartFile img,HttpServletRequest request, Authentication authentication) throws IOException {
        return ResponseEntity.ok(userService.updateUser(id,userRequestDTO,img,request,authentication));
    }

    @DeleteMapping("/employee/user/delete/{id}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long id,HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.deleteUser(id,request,authentication));
    }

    @PutMapping("/employee/user/restore/{id}")
    public ResponseEntity<APIResponse> restoreUser(@PathVariable Long id,HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.restoreUser(id,request,authentication));
    }

    @PostMapping("/auth/otp/{email}")
    public ResponseEntity<APIResponse> sendOTP(@PathVariable String email,HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.sendOTP(email,request,authentication));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<APIResponse> logout(Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.logout(authentication));
    }

    @PostMapping("/auth/reset-pass")
    public ResponseEntity<APIResponse> resetPass(@RequestBody ResetPassword resetPassword, HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.resetPassword(resetPassword,request,authentication));
    }
}
