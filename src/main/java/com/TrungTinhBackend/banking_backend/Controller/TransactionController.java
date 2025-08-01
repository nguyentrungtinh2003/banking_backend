package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.RequestDTO.TransactionDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/employee/transaction/deposit")
    public ResponseEntity<APIResponse> deposit(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.deposit(transactionDTO));
    }

    @PostMapping("/employee/transaction/withdraw")
    public ResponseEntity<APIResponse> withdraw(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.withdraw(transactionDTO));
    }

    @PostMapping("/customer/transaction/transfer")
    public ResponseEntity<APIResponse> transfer(@RequestBody TransactionDTO transactionDTO, Authentication authentication) throws AccessDeniedException, IllegalAccessException {
        return ResponseEntity.ok(transactionService.transfer(transactionDTO,authentication));
    }

    @GetMapping("/employee/transaction/page")
    public ResponseEntity<APIResponse> getTransactionByPage(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "6") int size) throws AccessDeniedException, IllegalAccessException {
        return ResponseEntity.ok(transactionService.getTransactionByPage(page,size));
    }

    @GetMapping("/employee/transaction/{id}")
    public ResponseEntity<APIResponse> getTransactionById(@PathVariable Long id,Authentication authentication) throws AccessDeniedException, IllegalAccessException {
        return ResponseEntity.ok(transactionService.getTransactionById(id,authentication));
    }
}
