package com.TrungTinhBackend.banking_backend.Service.Account;

import com.TrungTinhBackend.banking_backend.RequestDTO.AccountDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;

public interface AccountService {
    APIResponse addAccount(AccountDTO accountDTO);
    APIResponse getAccountByPage(int page,int size);
    APIResponse getAccountById(Long id, Authentication authentication) throws AccessDeniedException;
    APIResponse updateAccount(Long id, AccountDTO accountDTO, Authentication authentication);
    APIResponse deleteAccount(Long id, Authentication authentication);
    APIResponse restoreAccount(Long id, Authentication authentication);
}
