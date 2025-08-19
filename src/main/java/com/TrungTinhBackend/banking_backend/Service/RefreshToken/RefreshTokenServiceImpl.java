package com.TrungTinhBackend.banking_backend.Service.RefreshToken;

import com.TrungTinhBackend.banking_backend.Entity.RefreshToken;
import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Repository.RefreshTokenRepository;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public APIResponse createAccessToken(String refreshToken) {
        APIResponse apiResponse = new APIResponse();

        RefreshToken refreshToken1 = refreshTokenRepository.findByToken(refreshToken);
        User user = userRepository.findByCitizenId(refreshToken1.getUser().getCitizenId());

        if(refreshToken1.getExpiraDate().before(new Date())) {
            throw new IllegalArgumentException("RefreshToken không đúng");
        }
        String token = jwtUtils.generateToken(user);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Create access token success");
        apiResponse.setToken(token);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public void deleteRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
