package com.TrungTinhBackend.banking_backend.Service.RefreshToken;

import com.TrungTinhBackend.banking_backend.Entity.RefreshToken;
import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;

public interface RefreshTokenService {
    void addRefreshToken(RefreshToken refreshToken);
    APIResponse createAccessToken(String refreshToken);
    void deleteRefreshToken(User user);
}
