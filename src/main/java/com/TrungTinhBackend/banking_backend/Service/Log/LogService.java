package com.TrungTinhBackend.banking_backend.Service.Log;

import com.TrungTinhBackend.banking_backend.RequestDTO.LogDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface LogService {
    APIResponse addLog(LogDTO logDTO, HttpServletRequest request, Authentication authentication);
}
