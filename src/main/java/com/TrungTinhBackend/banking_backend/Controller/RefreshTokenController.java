package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.RefreshToken.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestController
@RequestMapping("/api/refresh-token")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;


    @PostMapping("/access-token")
    public ResponseEntity<APIResponse> createAccessToken(@RequestBody Map<String,String> refreshToken) throws AccessDeniedException {
        return ResponseEntity.ok(refreshTokenService.createAccessToken(refreshToken.get("refreshToken")));
    }
}
