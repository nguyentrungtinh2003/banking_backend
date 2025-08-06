package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.RequestDTO.LogDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.LoginDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.UserDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Log.LogService;
import com.TrungTinhBackend.banking_backend.Service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/auth/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> register(@RequestPart(value = "user") RegisterDTO registerDTO,
                                                @RequestPart(value = "img",required = false) MultipartFile img,HttpServletRequest request,Authentication authentication) throws IOException {
        return ResponseEntity.ok(userService.register(registerDTO,img,request,authentication));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request,Authentication authentication) {
        return ResponseEntity.ok(userService.login(loginRequestDTO,response,request,authentication));
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

    @PutMapping(value = "/customer/user/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> updateUser(@PathVariable Long id,
                                              @RequestPart(value = "user") UserDTO userRequestDTO,
                                              @RequestPart(value = "img",required = false) MultipartFile img,HttpServletRequest request, Authentication authentication) throws IOException {
        return ResponseEntity.ok(userService.updateUser(id,userRequestDTO,img,request,authentication));
    }

    @DeleteMapping("/customer/user/delete/{id}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long id,HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.deleteUser(id,request,authentication));
    }

    @PutMapping("/customer/user/restore/{id}")
    public ResponseEntity<APIResponse> restoreUser(@PathVariable Long id,HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(userService.restoreUser(id,request,authentication));
    }
}
