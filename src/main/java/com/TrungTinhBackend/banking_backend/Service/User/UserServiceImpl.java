package com.TrungTinhBackend.banking_backend.Service.User;

import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import com.TrungTinhBackend.banking_backend.Exception.NotFoundException;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.RequestDTO.LoginRequestDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterRequestDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public APIResponse register(RegisterRequestDTO registerRequestDTO) {
        APIResponse apiResponse = new APIResponse();

        User user = userRepository.findByCitizenId(registerRequestDTO.getCitizenId());
        if(user != null) {
            throw new NotFoundException("User already exists");
        }

        User user1 = new User();
        if (registerRequestDTO.getUsername() != null && !registerRequestDTO.getUsername().isEmpty()) {
            user1.setUsername(registerRequestDTO.getUsername());
        }

        if (registerRequestDTO.getPassword() != null && !registerRequestDTO.getPassword().isEmpty()) {
            user1.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        }

        if (registerRequestDTO.getAddress() != null && !registerRequestDTO.getAddress().isEmpty()) {
            user1.setAddress(registerRequestDTO.getAddress());
        }

        if (registerRequestDTO.getImg() != null && !registerRequestDTO.getImg().isEmpty()) {
            user1.setImg(registerRequestDTO.getImg());
        }

        if (registerRequestDTO.getPhone() != null && !registerRequestDTO.getPhone().isEmpty()) {
            user1.setPhone(registerRequestDTO.getPhone());
        }

        if (registerRequestDTO.getBirthday() != null) {
            user1.setBirthday(registerRequestDTO.getBirthday());
        }

        if (registerRequestDTO.getEmail() != null && !registerRequestDTO.getEmail().isEmpty()) {
            user1.setEmail(registerRequestDTO.getEmail());
        }

        if (registerRequestDTO.getCitizenId() != null && !registerRequestDTO.getCitizenId().isEmpty()) {
            user1.setCitizenId(registerRequestDTO.getCitizenId());
        }

        if (registerRequestDTO.getRole() != null) {
            user1.setRole(registerRequestDTO.getRole());
        }else {
            user1.setRole(Role.CUSTOMER);
        }

        if (registerRequestDTO.getGender() != null) {
            user1.setGender(registerRequestDTO.getGender());
        }

        user1.setDeleted(false);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Login success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse login(LoginRequestDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request) {
        APIResponse apiResponse = new APIResponse();

        User user = userRepository.findByUsernameAndDeletedFalse(loginRequestDTO.getUsername());
        if(user == null) {
            throw new NotFoundException("User not found");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()));

        String token = jwtUtils.generateToken(user);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Login success");
        apiResponse.setToken(token);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }
}
