package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.RequestDTO.AccountDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.LogDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Account.AccountService;
import com.TrungTinhBackend.banking_backend.Service.Log.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LogService logService;

    @PostMapping("/employee/account/add")
    public ResponseEntity<APIResponse> addAccount(@RequestBody AccountDTO accountDTO, HttpServletRequest request,Authentication authentication) {
        return ResponseEntity.ok(accountService.addAccount(accountDTO,request,authentication));
    }

    @GetMapping("/employee/account/page")
    public ResponseEntity<APIResponse> getAccountByPage(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(accountService.getAccountByPage(page,size));
    }

    @GetMapping("/employee/account/{id}")
    public ResponseEntity<APIResponse> getAccountById(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(accountService.getAccountById(id,authentication));
    }

    @PutMapping("/employee/account/update/{id}")
    public ResponseEntity<APIResponse> updateAccount(@PathVariable Long id,@RequestBody AccountDTO accountDTO, HttpServletRequest request,Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(accountService.updateAccount(id,accountDTO,request,authentication));
    }

    @DeleteMapping("/employee/account/delete/{id}")
    public ResponseEntity<APIResponse> deleteAccount(@PathVariable Long id,HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(accountService.deleteAccount(id,request,authentication));
    }

    @PutMapping("/employee/account/restore/{id}")
    public ResponseEntity<APIResponse> restoreAccount(@PathVariable Long id,HttpServletRequest request, Authentication authentication) throws AccessDeniedException {
        return ResponseEntity.ok(accountService.restoreAccount(id,request,authentication));
    }
}
