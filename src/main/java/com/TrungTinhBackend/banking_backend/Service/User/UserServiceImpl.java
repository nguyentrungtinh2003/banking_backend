package com.TrungTinhBackend.banking_backend.Service.User;

import com.TrungTinhBackend.banking_backend.Entity.RefreshToken;
import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.Gender;
import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import com.TrungTinhBackend.banking_backend.Exception.NotFoundException;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.RequestDTO.*;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Email.EmailService;
import com.TrungTinhBackend.banking_backend.Service.Img.ImgService;
import com.TrungTinhBackend.banking_backend.Service.Jwt.JwtUtils;
import com.TrungTinhBackend.banking_backend.Service.Log.LogService;
import com.TrungTinhBackend.banking_backend.Service.RefreshToken.RefreshTokenService;
import com.TrungTinhBackend.banking_backend.Service.Specification.User.UserSpecification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

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

    @Autowired
    private LogService logService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public APIResponse register(RegisterDTO registerRequestDTO, MultipartFile img,HttpServletRequest request,Authentication authentication) throws IOException {
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

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.REGISTER,
                "User register citizenId = "+registerRequestDTO.getCitizenId(),
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Register success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse login(LoginDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request, Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        User user = userRepository.findByCitizenId(loginRequestDTO.getCitizenId());
        if(user == null) {
            throw new NotFoundException("User not found");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getCitizenId(),
                loginRequestDTO.getPassword()));

        String token = jwtUtils.generateToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(),user);

        RefreshToken refreshToken1 = new RefreshToken();
        refreshToken1.setUser(user);
        refreshToken1.setExpiraDate(new Date());
        refreshToken1.setToken(refreshToken);

        refreshTokenService.addRefreshToken(refreshToken1);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.LOGIN,
                "User login userId = "+ user.getId(),
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Login success");
        apiResponse.setToken(token);
        apiResponse.setRefreshToken(refreshToken);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse getUserInfo(UserDetails userDetails) {
        APIResponse apiResponse = new APIResponse();

        User user = userRepository.findByCitizenId(userDetails.getUsername());
        if(user == null) {
            throw new NotFoundException("User not found");
        }

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("get user info success");
        apiResponse.setData(user);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @Cacheable(value = "userPage")
    public APIResponse getUserByPage(int page, int size) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> userPage = userRepository.findAll(pageable);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get user by page "+page+" size "+size+" success");
        apiResponse.setData(userPage);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse filterUser(String keyword, Gender gender, LocalDate birthday, int page, int size) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Specification<User> specification = UserSpecification.filter(keyword,gender,birthday);
        Page<User> userPage = userRepository.findAll(specification,pageable);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Filter user success");
        apiResponse.setData(userPage);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public APIResponse getUserById(Long id, Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByCitizenId(userDetails.getUsername());

        if(!authUser.getId().equals(id) && !(authUser.getRole().equals(Role.ADMIN) || authUser.getRole().equals(Role.EMPLOYEE))) {
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }

        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get user by id "+id+" success");
        apiResponse.setData(user);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @CacheEvict(value = {"userPage","user"},allEntries = true)
    public APIResponse updateUser(Long id, UserDTO userRequestDTO, MultipartFile img,HttpServletRequest request, Authentication authentication) throws IOException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User authUser = userRepository.findByCitizenId(userDetails.getUsername());

        if(!authUser.getId().equals(id) && !(authUser.getRole().equals(Role.ADMIN) || authUser.getRole().equals(Role.EMPLOYEE))) {
            throw new AccessDeniedException("Bạn không có quyên thực hiên");
        }

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
            if(authUser.getRole().equals(Role.ADMIN)) {
                user1.setRole(userRequestDTO.getRole());
            }
        }else {
            user1.setRole(Role.CUSTOMER);
        }

        if (userRequestDTO.getGender() != null) {
            user1.setGender(userRequestDTO.getGender());
        }

        user1.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user1);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.UPDATE,
                "Update user id = "+id,
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Update user id "+id+" success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @CacheEvict(value = {"userPage","user"},allEntries = true)
    public APIResponse deleteUser(Long id,HttpServletRequest request,Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByCitizenId(userDetails.getUsername());

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

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.DELETE,
                "Delete user id = "+id,
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Delete user id "+id+" success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @CacheEvict(value = {"userPage","user"},allEntries = true)
    public APIResponse restoreUser(Long id,HttpServletRequest request,Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByCitizenId(userDetails.getUsername());

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

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.RESTORE,
                "Restore user id = "+id,
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Restore user id "+id+" success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse sendOTP(String email,HttpServletRequest request,Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        Random random = new Random();
        int rawOto = 100000 + random.nextInt(900000);
        String otp = String.valueOf(rawOto);

        emailService.sendMail(email,"Mã OTP của bạn là",otp);

        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new NotFoundException("User not found");
        }

        user.setOtp(otp);
        user.setExOtp(LocalDateTime.now().plusMinutes(3));
        userRepository.save(user);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.OTP,
                "Send OTP with email "+email,
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Send OTP success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse resetPassword(ResetPassword resetPassword,HttpServletRequest request,Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        User user = userRepository.findByEmail(resetPassword.getEmail());
        if(user == null) {
            throw new NotFoundException("User not found");
        }

        if(!user.getOtp().equals(resetPassword.getOtp())) {
            throw new IllegalArgumentException("OTP không hợp lệ");
        }

        if(user.getExOtp().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP đã hết hạn");
        }

        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        user.setOtp(null);
        user.setExOtp(null);
        userRepository.save(user);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.RESET_PASS,
                "Reset password with email = "+resetPassword.getEmail(),
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Reset password success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }
}
