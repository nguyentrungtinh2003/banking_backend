package com.TrungTinhBackend.banking_backend.Service.User;

import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import com.TrungTinhBackend.banking_backend.Exception.NotFoundException;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.RequestDTO.LoginRequestDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.RegisterRequestDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.UserRequestDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.ResponseDTO.UserResponseDTO;
import com.TrungTinhBackend.banking_backend.Service.Img.ImgService;
import com.TrungTinhBackend.banking_backend.Service.Jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
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

    @Autowired
    private ImgService imgService;

    @Override
    public APIResponse register(RegisterRequestDTO registerRequestDTO, MultipartFile img) throws IOException {
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

        if (img != null && !img.isEmpty()) {
            user1.setImg(imgService.upload(img));
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

        userRepository.save(user1);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Register success");
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

    @Override
    public APIResponse getUserByPage(int page, int size) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> userPage = userRepository.findAll(pageable);

        Page<UserResponseDTO> userResponseDTOPage = userPage.map(user -> {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
                    userResponseDTO.setId(user.getId());
                    userResponseDTO.setUsername(user.getUsername());
                    userResponseDTO.setAddress(user.getAddress());
                    userResponseDTO.setEmail(user.getEmail());
                    userResponseDTO.setPhone(user.getPhone());
                    userResponseDTO.setBirthday(user.getBirthday());
                    userResponseDTO.setCitizenId(user.getCitizenId());
                    userResponseDTO.setRole(user.getRole());
                    userResponseDTO.setGender(user.getGender());
                    userResponseDTO.setImg(user.getImg());
                    userResponseDTO.setDeleted(user.isDeleted());
                    userResponseDTO.setCreatedAt(user.getCreatedAt());
                    userResponseDTO.setUpdatedAt(user.getUpdatedAt());
                    return userResponseDTO;
                }
        );

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get user by page "+page+" size "+size+" success");
        apiResponse.setData(userResponseDTOPage);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse getUserById(Long id, Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        if(!user.getId().equals(id) && !user.getRole().equals(Role.ADMIN) || !user.getRole().equals(Role.EMPLOYEE)) {
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }

                    UserResponseDTO userResponseDTO = new UserResponseDTO();
                    userResponseDTO.setId(user.getId());
                    userResponseDTO.setUsername(user.getUsername());
                    userResponseDTO.setAddress(user.getAddress());
                    userResponseDTO.setEmail(user.getEmail());
                    userResponseDTO.setPhone(user.getPhone());
                    userResponseDTO.setBirthday(user.getBirthday());
                    userResponseDTO.setCitizenId(user.getCitizenId());
                    userResponseDTO.setRole(user.getRole());
                    userResponseDTO.setGender(user.getGender());
                    userResponseDTO.setImg(user.getImg());
                    userResponseDTO.setDeleted(user.isDeleted());
                    userResponseDTO.setCreatedAt(user.getCreatedAt());
                    userResponseDTO.setUpdatedAt(user.getUpdatedAt());

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get user by id "+id+" success");
        apiResponse.setData(userResponseDTO);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse updateUser(Long id, UserRequestDTO userRequestDTO, MultipartFile img) throws IOException {
        APIResponse apiResponse = new APIResponse();

        User user1 = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        if (userRequestDTO.getUsername() != null && !userRequestDTO.getUsername().isEmpty()) {
            user1.setUsername(userRequestDTO.getUsername());
        }

        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user1.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        if (userRequestDTO.getAddress() != null && !userRequestDTO.getAddress().isEmpty()) {
            user1.setAddress(userRequestDTO.getAddress());
        }

        if (img != null && !img.isEmpty()) {
            user1.setImg(imgService.update(user1.getImg(),img));
        }

        if (userRequestDTO.getPhone() != null && !userRequestDTO.getPhone().isEmpty()) {
            user1.setPhone(userRequestDTO.getPhone());
        }

        if (userRequestDTO.getBirthday() != null) {
            user1.setBirthday(userRequestDTO.getBirthday());
        }

        if (userRequestDTO.getEmail() != null && !userRequestDTO.getEmail().isEmpty()) {
            user1.setEmail(userRequestDTO.getEmail());
        }

        if (userRequestDTO.getCitizenId() != null && !userRequestDTO.getCitizenId().isEmpty()) {
            user1.setCitizenId(userRequestDTO.getCitizenId());
        }

        if (userRequestDTO.getRole() != null) {
            user1.setRole(userRequestDTO.getRole());
        }else {
            user1.setRole(Role.CUSTOMER);
        }

        if (userRequestDTO.getGender() != null) {
            user1.setGender(userRequestDTO.getGender());
        }

        user1.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user1);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Update user id "+id+" success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse deleteUser(Long id,Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        if(!user.getRole().equals(Role.ADMIN) && !user.getRole().equals(Role.EMPLOYEE) ) {
            throw new AccessDeniedException("Bạn không có quyền thực hiện");
        }

        User ownerUser = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        if(!ownerUser.isDeleted()) {
            ownerUser.setDeleted(true);
        }
        userRepository.save(ownerUser);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Delete user id "+id+" success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse restoreUser(Long id,Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        if(!user.getRole().equals(Role.ADMIN) && !user.getRole().equals(Role.EMPLOYEE) ) {
            throw new AccessDeniedException("Bạn không có quyền thực hiện");
        }

        User ownerUser = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        if(ownerUser.isDeleted()) {
            ownerUser.setDeleted(false);
        }
        userRepository.save(ownerUser);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Restore user id "+id+" success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }
}
