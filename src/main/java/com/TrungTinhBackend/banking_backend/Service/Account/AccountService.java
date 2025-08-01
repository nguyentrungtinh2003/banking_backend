package com.TrungTinhBackend.banking_backend.Service.Account;

import com.TrungTinhBackend.banking_backend.RequestDTO.AccountDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;

public interface AccountService {
    APIResponse addAccount(AccountDTO accountDTO, HttpServletRequest request, Authentication authentication);
    APIResponse getAccountByPage(int page,int size);
    APIResponse getAccountById(Long id, Authentication authentication) throws AccessDeniedException;
    APIResponse updateAccount(Long id, AccountDTO accountDTO, HttpServletRequest request,Authentication authentication) throws AccessDeniedException;
    APIResponse deleteAccount(Long id, HttpServletRequest request,Authentication authentication) throws AccessDeniedException;
    APIResponse restoreAccount(Long id, HttpServletRequest request,Authentication authentication) throws AccessDeniedException;
}
