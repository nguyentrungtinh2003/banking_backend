package com.TrungTinhBackend.banking_backend.Service.Transaction;

import com.TrungTinhBackend.banking_backend.RequestDTO.TransactionDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;

public interface TransactionService {
    APIResponse deposit(TransactionDTO transactionDTO);
    APIResponse withdraw(TransactionDTO transactionDTO);
    APIResponse transfer(TransactionDTO transactionDTO, Authentication authentication) throws AccessDeniedException, IllegalAccessException;
    APIResponse getTransactionByPage(int page, int size);
    APIResponse getTransactionById(Long id, Authentication authentication) throws AccessDeniedException;
}
